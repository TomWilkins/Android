package com.example.tomwilkins.multipanefragment;

import android.app.Activity;
import android.content.res.Configuration;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get the screen orientation from config
        int screenOrientation =
                getResources().getConfiguration().orientation;

        // if its in portrait, hide side panel
        if (screenOrientation == Configuration.ORIENTATION_PORTRAIT){
            hideSidePanel();
        }
    }

    private void hideSidePanel() {

        // gets the side panel and if its visible, hide it
        View sidePane = findViewById(R.id.side_panel);
        if(sidePane.getVisibility() == View.VISIBLE){
            sidePane.setVisibility(View.GONE);
        }

    }


}
