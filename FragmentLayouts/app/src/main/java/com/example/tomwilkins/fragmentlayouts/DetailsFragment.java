package com.example.tomwilkins.fragmentlayouts;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Created by tomwilkins on 22/12/2014.
 */
public class DetailsFragment extends Fragment {

    public static DetailsFragment newInstance(int index) {
        DetailsFragment f = new DetailsFragment();

        // bundles are used to send data
        Bundle args = new Bundle();
        args.putInt("index", index);

        f.setArguments(args);

        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        // create scroll view for heros
        ScrollView scroller = new ScrollView(getActivity());

        TextView text = new TextView(getActivity());

        // get padding for text view
        int padding = (int)
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        4, getActivity().getResources().getDisplayMetrics());

        text.setPadding(padding,padding,padding,padding);

        scroller.addView(text);

        // set text as info
        text.setText(SuperHeroInfo.HISTORY[getShownIndex()]);

        return scroller;
    }

    public int getShownIndex() {
        return getArguments().getInt("index", 0);
    }
}
