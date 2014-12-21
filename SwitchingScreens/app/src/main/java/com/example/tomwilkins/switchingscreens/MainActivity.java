package com.example.tomwilkins.switchingscreens;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onGetNameClick(View view) {

        // before switching activities you need to create an intent first
        // with the current context and the next activity
        /* Intent getNameScreenIntent = new Intent(this,
                SecondScreen.class);*/

        final int result = 1;

        // put data to send to second screen as key => value pair
        // getNameScreenIntent.putExtra("callingActivity", "MainActivity");

        // to call for activity to run without any result data
        // startActivity(getNameScreenIntent);

        // pass an object to the second screen
        Human bob = new Human("Bob", 6.25, 185);
        Intent sendBob = new Intent(this, SecondScreen.class);
        sendBob.putExtra("bob", bob);

        // start activity and get result data
        startActivityForResult(sendBob, result);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // A result will trigger when Second Screen has finished its work

        // get the text view to add name to
        TextView usersNameMessage = (TextView) findViewById(R.id.users_name_message);

        // get the name from data
        String nameSentBack = data.getStringExtra("UsersName");

        // add name to message
        usersNameMessage.append(" " + nameSentBack);
    }
}
