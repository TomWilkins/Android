package com.example.tomwilkins.androidfragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by tomwilkins on 22/12/2014.
 */
public class FragmentLandscape extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // inflater takes the provided XML layout and put it into the view
        return inflater.inflate(R.layout.landscape_fragment,
                container, // view the fragment could or could not attach to
                false); // do you want to attach to view name container
    }
}
