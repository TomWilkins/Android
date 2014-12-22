package com.example.tomwilkins.fragmentlayouts;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // layout.activity_main is used for portrait layout
        setContentView(R.layout.activity_main);
    }
}
