package com.example.tomwilkins.translation;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onTranslateClick(View view) {

        EditText translateEditText = (EditText) findViewById(R.id.editText);

        if(!isEmpty(translateEditText)){
            Toast.makeText(this, "Getting Translation...", Toast.LENGTH_LONG).show();

            new SaveTheFeed().execute();
        }else{
            Toast.makeText(this, "Enter words to translate", Toast.LENGTH_SHORT).show();
        }
    }

    protected boolean isEmpty(EditText text){
        return text.getText().toString().trim().length() ==0;
    }

    // Using an Async task inner class allows this task to run in the background
    // so the app doesn't wait for it (like JS)
    // 1st Void = this is not receiving any parameters,
    // 2nd Void = do not monitor the progress of the task
    // 3rd Void = doInBackground will not pass anything to onPostExecute
    class SaveTheFeed extends AsyncTask<Void, Void, Void>{

        String jsonString = "";
        String result ="";

        // executes in the background (class.execute())
        // used to get data and then send to onPostExec
        @Override
        protected Void doInBackground(Void... params) {

            EditText translateEditText = (EditText) findViewById(R.id.editText);

            String wordsToTranslate = translateEditText.getText().toString();
            wordsToTranslate = wordsToTranslate.replace(" ", "+");

            // create http client to get JSON data
            DefaultHttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());

            // enter url to post data to (web service)
            HttpPost httpPost = new HttpPost("http://newjustin.com/translateit.php?action=translations&english_words="
                    + wordsToTranslate);

            httpPost.setHeader("Content-type", "application/json");

            InputStream inputStream = null;

            try{
                HttpResponse response = httpClient.execute(httpPost);

                // message sent back
                HttpEntity entity = response.getEntity();

                inputStream = entity.getContent();

                // convert bytes to characters
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream, "UTF-8"), 8);

                StringBuilder sb = new StringBuilder();

                String line = null;

                while((line = reader.readLine()) != null){

                    sb.append(line + "\n");

                }

                jsonString = sb.toString();

                JSONObject jObject = new JSONObject(jsonString);

                JSONArray jArray = jObject.getJSONArray("translations");

                outputTranslations(jArray);


            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        // used to do stuff with the data, e.g. output to screen
        @Override
        protected void onPostExecute(Void aVoid) {

            TextView translation = (TextView) findViewById(R.id.translationTextView);

            translation.setText(result);

        }

        private void outputTranslations(JSONArray jsonArray) {

            String[] languages = {"arabic", "chinese", "danish", "dutch",
                "french", "german", "italian", "portuguese", "russian",
                "spanish"};

            try{

                // each translation is stored as a jSON object of { language : text }
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject translationObject = jsonArray.getJSONObject(i);

                    result = result + languages[i] + " : " +
                            translationObject.getString(languages[i]) + "\n";
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
