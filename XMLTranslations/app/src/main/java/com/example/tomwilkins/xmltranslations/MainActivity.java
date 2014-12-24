package com.example.tomwilkins.xmltranslations;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.AsyncTask;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Logger;


public class MainActivity extends Activity implements TextToSpeech.OnInitListener {

    private Locale currentSpokenLang = Locale.UK;

    // some languages are not supported in Locale to begin with,
    // so we create our own
    private Locale locSpanish = new Locale("es", "MX");
    private Locale locRussian = new Locale("ru", "RU");
    private Locale locPortugese = new Locale("pt", "BR");
    private Locale locDutch = new Locale("nl", "NL");

    // the languages we are going to perform speech translation with
    private Locale[] languages = {locDutch, Locale.FRENCH, Locale.GERMAN,
    Locale.ITALIAN, locPortugese, locRussian, locSpanish};

    private TextToSpeech textToSpeech;
    private Spinner languageSpinner;
    private int spinnerIndex =0;
    private String[] arrayOfTranslations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        languageSpinner = (Spinner) findViewById(R.id.lang_spinner);

        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentSpokenLang = languages[position];
                spinnerIndex = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        textToSpeech = new TextToSpeech(this, this);
    }

    @Override
    protected void onDestroy() {
        if(textToSpeech != null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }

        super.onDestroy();
    }

    public void onTranslateText(View view) {

        EditText translateEditText = (EditText) findViewById(R.id.words_edit_text);

        if(!isEmpty(translateEditText)){
            Toast.makeText(this, "Getting Translation...", Toast.LENGTH_LONG).show();

            new GetXMLData().execute();
        }else{
            Toast.makeText(this, "Enter words to translate", Toast.LENGTH_SHORT).show();
        }
    }

    protected boolean isEmpty(EditText text){
        return text.getText().toString().trim().length() ==0;
    }

    @Override
    public void onInit(int status) {

        if(status == TextToSpeech.SUCCESS){

            int result = textToSpeech.setLanguage(currentSpokenLang);

            if(result == TextToSpeech.LANG_MISSING_DATA ||
                    result == TextToSpeech.LANG_NOT_SUPPORTED){
                Toast.makeText(this, "Language not supported", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "Text to speech failed", Toast.LENGTH_SHORT).show();
        }
    }

    public void readTheText(View view) {

        textToSpeech.setLanguage(currentSpokenLang);

        if(arrayOfTranslations.length >= 9){
            // spinner is missing the first 3 language translations
            // so perform work around (+4 to spinner index)
            textToSpeech.speak(arrayOfTranslations[spinnerIndex+4],
                    textToSpeech.QUEUE_FLUSH, null);
        }else{
            Toast.makeText(this, "Translate Text First", Toast.LENGTH_SHORT).show();
        }

    }

    class GetXMLData extends AsyncTask<Void, Void, Void>{

        String stringToPrint = "";

        @Override
        protected Void doInBackground(Void... params) {

            String xmlString = "";
            String wordsToTranslate = "";

            EditText translateEditText = (EditText) findViewById(R.id.words_edit_text);

            wordsToTranslate = translateEditText.getText().toString();

            wordsToTranslate = wordsToTranslate.replace(" ", "+");

            DefaultHttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());

            HttpPost httpPost = new HttpPost("http://newjustin.com/translateit.php?action=xmltranslations&english_words=" + wordsToTranslate);

            httpPost.setHeader("Content-type", "text/xml");

            InputStream inputStream = null;

            try {
                HttpResponse response = httpClient.execute(httpPost);

                HttpEntity entity = response.getEntity();

                inputStream = entity.getContent();

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);

                StringBuilder sb = new StringBuilder();

                String line = null;

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                xmlString = sb.toString();

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

                factory.setNamespaceAware(true);

                XmlPullParser xpp = factory.newPullParser();

                xpp.setInput(new StringReader(xmlString));

                int eventType = xpp.getEventType();

                // filter start/ end xml tags + other tags we dont want
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if ((eventType == XmlPullParser.START_TAG) &&
                            (!xpp.getName().equals("translations"))) {

                        // get the language name
                        stringToPrint = stringToPrint + xpp.getName() + " : ";
                    } else if (eventType == XmlPullParser.TEXT) {
                        // get the translation
                        stringToPrint = stringToPrint + xpp.getText() + "\n";
                    }
                    eventType = xpp.next();
                }

            }catch (MalformedURLException e){ // added
                e.printStackTrace();
            }catch (UnsupportedEncodingException e){ // added for UTF-8
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            TextView textView = (TextView) findViewById(R.id.translate_text_view);

            // make text view scrollable
            textView.setMovementMethod(new ScrollingMovementMethod());

            // remove the language name and colon from the string
            String stringOfTranslations = stringToPrint.replaceAll("\\w+\\s:","#");

            // split into array
            arrayOfTranslations = stringOfTranslations.split("#");



            textView.setText(stringToPrint);

        }
    }

    public void ExceptSpeechInput(View view) {

        // we intent to make speaking popup activity
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault());

        // text that pops up on speech button press
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_input_phrase));

        try{
            startActivityForResult(intent, 100);
        }catch (ActivityNotFoundException e){
            Toast.makeText(this, getString(R.string.stt_not_supported_message), Toast.LENGTH_SHORT).show();
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        if((requestCode == 100 ) && (data != null) && (resultCode == RESULT_OK)){
            ArrayList<String> spokenText = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            EditText wordsEntered = (EditText) findViewById(R.id.words_edit_text);

            wordsEntered.setText(spokenText.get(0));
        }

    }

}
