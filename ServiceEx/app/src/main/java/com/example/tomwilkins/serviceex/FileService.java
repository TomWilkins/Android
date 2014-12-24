package com.example.tomwilkins.serviceex;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by tomwilkins on 24/12/2014.
 *
 * And intent service is used when downloading files so that the
 * main activity can keep running
 */
public class FileService extends IntentService {

    // alert main activity when the intent service finishes downloading
    public static final String TRANSACTION_DONE = "com.example.tomwilkins.TRANSACTION_DONE";

    // this validates the resource references inside of the android xml files
    public FileService(){
        super(FileService.class.getName());
    }

    public FileService(String name){
        super(name);
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        Log.e("FileService", "Service started");

        String passedURL = intent.getStringExtra("url");

        downloadFile(passedURL);

        Log.e("FileService", "Service Stopped");

        // broadcast that the file service is done
        Intent broadcast = new Intent(TRANSACTION_DONE);

        FileService.this.sendBroadcast(broadcast);
    }

    protected void downloadFile(String url){
        String fileName = "myFile";

        try{
            FileOutputStream outputStream =
                    openFileOutput(fileName, Context.MODE_PRIVATE);

            URL fileURL = new URL(url);

            HttpURLConnection urlConnection =
                    (HttpURLConnection) fileURL.openConnection();

            urlConnection.setRequestMethod("GET");

            urlConnection.setDoOutput(true);

            urlConnection.connect();

            // read the byte into a buffer and then write them to the new file
            // that is going to be stored on our system
            InputStream inputStream = urlConnection.getInputStream();
            byte[] buffer = new byte[1024];
            int bufferLength = 0;
            while((bufferLength = inputStream.read(buffer)) > 0){

                outputStream.write(buffer, 0, bufferLength);
            }

            outputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
