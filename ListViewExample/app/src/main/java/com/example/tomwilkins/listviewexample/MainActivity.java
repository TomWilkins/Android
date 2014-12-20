package com.example.tomwilkins.listviewexample;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import static android.widget.AdapterView.*;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Simple array with a list of my favorite TV shows
        String[] favoriteTVShows = {"Pushing Daisies", "Better Off Ted",
            "Twin Peaks", "Freaks and Geeks", "Orphan Black", "Walking Dead",
            "Breaking Bad", "The 400", "Alphas", "Life on Mars"};

        // converts the array to a list view
        // default list item - android.R.layout.simple_list_item_1
        ListAdapter theAdapter = new MyAdapter(this,
            favoriteTVShows);

        // get our list view and add the adapter
        ListView theListView = (ListView) findViewById(R.id.theListView);
        theListView.setAdapter(theAdapter);

        // set up click event
        theListView.setOnItemClickListener(new

            AdapterView.OnItemClickListener(){

                // on item click, get selected item and display a toast
                public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l){
                    String tvShowPicked = "You selected "+
                        String.valueOf(adapterView.getItemAtPosition(pos));

                    Toast.makeText(MainActivity.this, tvShowPicked, Toast.LENGTH_SHORT).show();
                }
            });
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
}
