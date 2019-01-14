package com.bigfoot.bigfoot;

import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.MenuInflater;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {
    private String[] recyclingFacts = {"It takes roughly a thousand years for plastic bottles to naturally break down.",
            "Aluminium cans can be recycled and put back onto the shelf at your local grocery store in 2 months.",
            "Recycling all of the New York Times printed on one day would save over 75,000 trees.",
            "Over 60% of the trash that ends in dustbin could be recycled.",
            "The most thrown away products include diapers, pens, razor blades, tires and aluminium- all of which can be used to be recycled into other products.",
            "Recycling one glass bottle saves enough energy to power a 100-watt bulb for four hours.",
            "It takes 70% less energy to recycle paper than to make it from raw material.",
            "Making cans from recyled aluminum takes 5% of the energy required to make them from raw materials."};

    static{
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updateFact();

        Button btn = findViewById(R.id.btn);

        // Button calendarBtn = findViewById(R.id.calendarBtn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ScanActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onResume(){
        super.onResume();
        updateFact();
    }

        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            getMenuInflater().inflate(R.menu.menu_main, menu);
            SearchManager searchManager =
                    (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            SearchView searchView =
                    (SearchView) menu.findItem(R.id.search).getActionView();
            searchView.setSearchableInfo(
                    searchManager.getSearchableInfo(getComponentName()));
            return true;
        }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            switch (item.getItemId()) {
                case (R.id.action_schedule):
                    Intent i = new Intent(this, CalendarActivity.class);
                    startActivity(i);
                    return true;
                case R.id.action_facts:
                    return true;
                case R.id.action_settings:
                    Intent k = new Intent(this,FirstOpenActivity.class);
                    startActivity(k);
                    return true;
            }

            //noinspection SimplifiableIfStatement


            return super.onOptionsItemSelected(item);
        }

    private void updateFact(){
        TextView recFacts = findViewById(R.id.recyclingFacts);
        int randomNum = ThreadLocalRandom.current().nextInt(0, recyclingFacts.length);
        recFacts.setText(recyclingFacts[randomNum]);
    }

    //call the get_from name
    public native String getBinTypeFromName(String name);


}
