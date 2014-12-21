package com.example.tomwilkins.menuexamples;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by tomwilkins on 21/12/2014.
 */
public class MyDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder theDialog = new AlertDialog.Builder(getActivity());

        // set title and message
        theDialog.setTitle("Sample Dialog");
        theDialog.setMessage("Hello I am a Dialog");

        // set up event handlers
        theDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "Clicked Ok", Toast.LENGTH_SHORT).show();
            }
        });

        theDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "Clicked Cancel", Toast.LENGTH_SHORT).show();
            }
        });

        return theDialog.create();
    }
}
