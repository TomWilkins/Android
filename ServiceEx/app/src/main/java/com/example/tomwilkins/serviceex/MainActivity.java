package com.example.tomwilkins.serviceex;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;


public class MainActivity extends Activity {

    EditText downloadEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        downloadEditText = (EditText) findViewById(R.id.downloadedEditText);

        IntentFilter intentFilter = new IntentFilter();

        intentFilter.addAction(FileService.TRANSACTION_DONE);

        registerReceiver(downloadReceiver, intentFilter);

    }

    public void startFileService(View view) {

        // we intend to use the file service class to download a file
        Intent intent = new Intent(this, FileService.class);

        // this is the url we want to download
        intent.putExtra("url", "https://www.newthinktank.com/wordpress/lotr.txt");

        // start intent service
        this.startService(intent);

    }

    private BroadcastReceiver downloadReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("FileService", "Service received");

            showFileContents();
        }
    };

    public void showFileContents(){
        StringBuilder sb;

        try{
            FileInputStream fileInputStream = this.openFileInput("myFile");

            InputStreamReader inputStreamReader =
                    new InputStreamReader(fileInputStream, "UTF-8");

            BufferedReader br = new BufferedReader(inputStreamReader);

            sb = new StringBuilder();

            String line;

            while((line = br.readLine()) !=null){
                sb.append(line).append("\n");
            }

            downloadEditText.setText(sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
