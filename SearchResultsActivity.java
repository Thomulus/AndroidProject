package com.bigfoot.bigfoot;

import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
//import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SearchResultsActivity extends AppCompatActivity {
    String albertServer = "http://albert.caslab.queensu.ca/";
    String phpString = "textDBsearch.php";
    String txtPHPvarName = "?searchText=";
    String queryString;
    TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        handleIntent(getIntent());
    }
    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            queryString = intent.getStringExtra(SearchManager.QUERY);
            queryString = queryString.replaceAll("[^a-zA-Z0-9]", "");
            //Toast.makeText(getApplicationContext(),queryString,Toast.LENGTH_SHORT).show();
            tableLayout = (TableLayout) findViewById(R.id.tableLayout);
            downloadJSON(albertServer + phpString + txtPHPvarName + '"' + queryString+ '"');
        }
    }

    public void downloadJSON(final String urlWebService) {

        class DownloadJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    processResult(s);
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        DownloadJSON getJSON = new DownloadJSON();
        getJSON.execute();
    }

    public void processResult(String json) throws JSONException {

        if (json.equals("[]")) {
            Log.d("error" ,"help");
            Toast.makeText(getApplicationContext(), "Not found :(", Toast.LENGTH_SHORT).show();

        } else {
            //ArrayList<String> niceStrings = new ArrayList<>();
            Log.d("error" ,"else");
            JSONArray jsonArray = new JSONArray(json);
            String[] itemList = new String[jsonArray.length()];
            TableRow tableRow = new TableRow(this);
            TextView textView1 = new TextView(this);
            TextView textView2 = new TextView(this);
            textView1.setTextSize(20);
            textView2.setTextSize(20);
            textView1.setTypeface(Typeface.DEFAULT_BOLD);
            textView2.setTypeface(Typeface.DEFAULT_BOLD);
            textView1.setText("Item Name");
            textView2.setText("        " + "Bin Colour");
            tableRow.addView(textView1);
            tableRow.addView(textView2);
            tableLayout.addView(tableRow);
            for (int i = 0; i < jsonArray.length(); i++) {
                tableRow = new TableRow(this);
                textView1 = new TextView(this);
                textView2 = new TextView(this);
                JSONObject obj = jsonArray.getJSONObject(i);
                textView1.setTextSize(20);
                textView2.setTextSize(20);
                textView1.setText(obj.getString("itemName"));
                textView2.setText("              " + obj.getString("binColour"));
                tableRow.addView(textView1);
                tableRow.addView(textView2);
                tableLayout.addView(tableRow);
            }
//            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itemList);
//            tableLayout.setAdapter(arrayAdapter);
        }
    }
}
