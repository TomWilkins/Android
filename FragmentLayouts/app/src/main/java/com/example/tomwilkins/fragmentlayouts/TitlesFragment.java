package com.example.tomwilkins.fragmentlayouts;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by tomwilkins on 22/12/2014.
 */
public class TitlesFragment extends ListFragment {

    boolean mDuelPane; // portrait mode or landscape mode
    int mCurCheckPosition = 0; // current selected item in list view

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // create array of strings in a list view using SuperHeroInfo
        ArrayAdapter<String> connectArrayToListView = new
            ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_activated_1,
                SuperHeroInfo.NAMES);

        // set the list adapter
        setListAdapter(connectArrayToListView);

        View detailsFrame = getActivity().findViewById(R.id.details);

        // set value for orientation mode based on
        // if the details frame is not null and is visible
        mDuelPane = detailsFrame != null &&
                detailsFrame.getVisibility() == View.VISIBLE;

        // get the stored hero that was most recently selected
        if(savedInstanceState != null){
            mCurCheckPosition = savedInstanceState.getInt("curChoice",0);
        }

        // if its in landscape mode, get list view and highlight choice
        // and show the heros info
        if(mDuelPane){
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            showDetails(mCurCheckPosition);
        }
    }

    // called when a screen orientation changes or the application exits
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save character choice
        outState.putInt("curChoice", mCurCheckPosition);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        showDetails(position);

    }

    void showDetails(int index){
        mCurCheckPosition = index;

        // if in landscape mode set details frame layout (layout-land/activity_main)
        if(mDuelPane) {
            getListView().setItemChecked(index, true);

            // gets the details fragment
            DetailsFragment details = (DetailsFragment)
                    getFragmentManager().findFragmentById(R.id.details);

            // if the details fragment does not exist (nothing selected)
            // or if the showing index is not equal to the selected index
            if (details == null || details.getShownIndex() != index) {
                details = DetailsFragment.newInstance(index);

                FragmentTransaction ft = getFragmentManager().beginTransaction();

                ft.replace(R.id.details, details);

                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        // if in portrait mode, create details activity and fill
        // screen with activity
        }else{
            Intent intent = new Intent();
            intent.setClass(getActivity(), DetailsActivity.class);
            intent.putExtra("index", index);
            startActivity(intent);
        }

    }
}
