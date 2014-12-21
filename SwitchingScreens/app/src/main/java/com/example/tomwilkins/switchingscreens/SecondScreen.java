package com.example.tomwilkins.switchingscreens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by tomwilkins on 21/12/2014.
 */
public class SecondScreen extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.second_layout);

        // get the previous activity called
        Intent activityThatCalledIntent = getIntent();

        // get data from the previous activity
        /*String previousActivity = activityThatCalledIntent
                .getExtras()
                .getString("callingActivity");*/

        // get the serialised object 
        Human bob = (Human) activityThatCalledIntent.getSerializableExtra("bob");

        // get the text view from layout to put data into
        TextView callingActivityMessage =
                (TextView) findViewById(R.id.calling_activity_info_text_view);

        callingActivityMessage.append(bob.getName() + " " +
            bob.getHeight() + " ft " + bob.getWeight() + " lbs");

    }

    public void onSendUsersName(View view) {

        // when the button is clicked, get users name from text field
        EditText usersNameET = (EditText) findViewById(R.id.users_name_edit_text);
        String usersname = String.valueOf(usersNameET.getText());

        // send it back to the last activity as data
        Intent goBackIntent = new Intent();
        goBackIntent.putExtra("UsersName", usersname);

        // set the result with intent
        setResult(RESULT_OK, goBackIntent);

        // clear screen
        finish();
    }
}
