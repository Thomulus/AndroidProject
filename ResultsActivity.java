package com.bigfoot.bigfoot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import static com.bigfoot.bigfoot.ScanActivity.RESULTS_MESSAGE;

public class ResultsActivity extends MainActivity {
    public static TextView recycleType;
    public static TextView binType;

    public static TextView descr;

    public static TextView item1;

    Button imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__results);

        Intent intent = getIntent();
        ArrayList<String> resultsFromScan = intent.getStringArrayListExtra(RESULTS_MESSAGE);
        Log.d("debug", resultsFromScan.get(0) );


        //back button
        imageButton = (Button) findViewById(R.id.fab);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ResultsActivity.this, ScanActivity.class);
                startActivity(i);
            }
        });

        //setting the types
        item1 = findViewById(R.id.itemName);
        recycleType = findViewById(R.id.recycleType);
        binType = findViewById(R.id.recycleBin);
        descr = findViewById(R.id.recycleDescription);

       // setViews();

        item1.setText("Item: " + resultsFromScan.get(0) );
        recycleType.setText("Recycle Type: " + resultsFromScan.get(1));
        binType.setText("Bin: " + resultsFromScan.get(2));
        descr.setText("Description: " + resultsFromScan.get(3));

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
        switch (item.getItemId()){
            case (R.id.action_schedule):
                Intent i = new Intent(ResultsActivity.this, CalendarActivity.class);
                startActivity(i);
                return true;
            case R.id.action_facts:
                return true;
        }

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

}
