package com.example.tomwilkins.savingstate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends Activity {

    EditText notesEditText;
    Button btnSettings;
    private static final int SETTINGS_INFO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notesEditText = (EditText) findViewById(R.id.notesEditText);

        // if data has been saved then set the notes edit text
        if(savedInstanceState != null){
            String notes = savedInstanceState.getString("NOTES");
            notesEditText.setText(notes);
        }

        String sPNotes = getPreferences(Context.MODE_PRIVATE).getString("NOTES",
                "EMPTY");

        if(!sPNotes.equals("EMPTY")){
            notesEditText.setText(sPNotes);
        }

        btnSettings = (Button) findViewById(R.id.btnSettings);

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPref = new Intent(getApplicationContext(),
                        SettingsActivity.class);

                startActivityForResult(intentPref, SETTINGS_INFO);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SETTINGS_INFO){
            updateNotes();
        }
    }
    private void updateNotes(){

        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);

        if(sharedPreferences.getBoolean("pref_text_bold", false)){
            notesEditText.setTypeface(null, Typeface.BOLD);
        }else{
            notesEditText.setTypeface(null, Typeface.NORMAL);
        }

        String textSizeStr = sharedPreferences.getString("pref_text_size", "16");

        float textSizeFloat = Float.parseFloat(textSizeStr);

        notesEditText.setTextSize(textSizeFloat);

    }

    // called when the OS kills the app/ orientation change, does NOT handle user killing app
    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString("NOTES",
                notesEditText.getText().toString());

        super.onSaveInstanceState(outState);
    }

    // called when application is killed by user
    @Override
    protected void onStop() {
        saveSettings();
        super.onStop();
    }

    private void saveSettings(){
        // Mode_private means only this app can use data
        SharedPreferences.Editor sPEditor = getPreferences(Context.MODE_PRIVATE).edit();

        sPEditor.putString("NOTES",
                notesEditText.getText().toString());

        sPEditor.commit();
    }
}
