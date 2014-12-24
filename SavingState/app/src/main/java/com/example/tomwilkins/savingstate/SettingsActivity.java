package com.example.tomwilkins.savingstate;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by tomwilkins on 24/12/2014.
 */
public class SettingsActivity extends PreferenceActivity {

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // is deprecation but here to be backwards compatible
        addPreferencesFromResource(R.xml.preferences);
    }
}
