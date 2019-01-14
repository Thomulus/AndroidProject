package com.bigfoot.bigfoot;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.nfc.Tag;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


import me.dm7.barcodescanner.zbar.ZBarScannerView;



public class ScanActivity extends AppCompatActivity implements ZBarScannerView.ResultHandler {
    private ZBarScannerView mScannerView;
    private GetBarcode gb;
    private static String item;
    private static String recycleType;
    private static String binType;
    private static String description;
    private static long[] barcodeArray;
    String albertServer = "http://albert.caslab.queensu.ca/";
    String phpString = "getItemByBC.php";
    String bcPHPvarName = "?barcode=";
    public static final String RESULTS_MESSAGE= "com.bigfoot.bigfoot.RESULTS";
    public static final String BARCODE_MESSAGE= "com.bigfoot.bigfoot.BARCODE";

    private static String[] fields = {"itemName", "recyclingType", "binColour", "description" };
    private static ArrayList<String> sendToresults = new ArrayList<>();


    static{
        //load native cpp libraries
        System.loadLibrary("native-lib");
        System.loadLibrary("GetBarcode");
        System.loadLibrary("RollingArray");
    }


    //camera permission is needed.

    @Override
    public void onCreate(Bundle state) {
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            android.support.v4.app.ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 1);
        }
        gb = new GetBarcode();

        super.onCreate(state);
        mScannerView = new ZBarScannerView(this);    // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(me.dm7.barcodescanner.zbar.Result result) {
        // Do something with the result here
        Log.v("kkkk", result.getContents()); // Prints scan results
        Log.v("uuuu", result.getBarcodeFormat().getName()); // Prints the scan format (qrcode, pdf417 etc.)

        long upc = Long.parseLong(result.getContents());    //parse long upc from string results


        //call native c++ code to determine if there is a barcode match
        if(gb.barcodeMatch(upc)){
            gotBarcode(result.getContents());    //got a barcode match, call method to deal with it
            //ArrayList<String> sendToResults = processResult();
//            Intent i = new Intent(this, ResultsActivity.class);
//            i.putExtra(RESULTS_MESSAGE, sendToresults);
//            Log.d("weird", sendToresults.get(0));
//            startActivity(i);
            //onBackPressed();
        }

        // If you would like to resume scanning, call this method below:
        mScannerView.resumeCameraPreview(this);
    }
    public static String getItem(){
        return item;
    }
    public static String getBinType(){
        return binType;
    }
    public static String getRecycleType(){
        return recycleType;
    }
    public static String getDescription(){
        return description;
    }
    public static void setItem(String it){
        item = it;

    }
    public static void setBinType(String b){
        binType = b;
    }
    public static void setRecycleType(String re){
        recycleType = re;
    }
    public static void setDescription(String de){
        description = de;
    }
    public void gotBarcode(String barcode) {
        long code = Long.parseLong(barcode);
        barcodeArray = new long[1];
        barcodeArray[0] = code;

        //String type = getBinTypeFromUpc(code);
        //ResultsActivity.binType.setText(getBinTypeFromUpc(code));

        //TextView item = findViewById(R.id.itemName);
        //Toast.makeText(getApplicationContext(),"Hello",Toast.LENGTH_SHORT).show();
        // String hey = "Hello";
        // String ho = "Hi";
        // String wooo = "It worked";
        // item = "hello";

        //REPLACE THESE @ CHRISOTPHER
        binType = getBinTypeFromUpc(code);
        item = getNameFromUpc(code);
        recycleType = getRecycleTypeFromUpc(code);
        description = "";

        downloadJSON(albertServer + phpString + bcPHPvarName + barcode);


        //ResultsActivity.item1.setText("HELLO!");
        //MainActivity.tvresult.setText("HELLO!");
        //ResultsActivity.item.setText(getNameFromUpc(code));
        //ResultsActivity.recycleType.setText(getRecycleTypeFromUpc(code));
        //ResultsActivity.binType.setText(type);
    }
        public static void getFromSearch(String input){
       // binType = getBinTypeFromName(input);
            binType = "woo";
        description = "descr";
        item = "name";
        recycleType = "something";

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
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
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
            Intent intent = new Intent(this, AddItemActivity.class);
            intent.putExtra(BARCODE_MESSAGE, barcodeArray);
            startActivity(intent);

        } else {
            //ArrayList<String> niceStrings = new ArrayList<>();
            Log.d("error" ,"else");
            JSONArray jsonArray = new JSONArray(json);
            JSONObject obj = jsonArray.getJSONObject(0);
            for (int i = 0; i < fields.length; i++) {
                sendToresults.add(obj.getString(fields[i]));
            }
            Intent intent = new Intent(this, ResultsActivity.class);
            intent.putExtra(RESULTS_MESSAGE, sendToresults);
            startActivity(intent);
        }
    }

    public native String getBinTypeFromUpc(long UPC);
    public native String getNameFromUpc(long UPC);
    public native String getRecycleTypeFromUpc(long UPC);
    public native String getBinTypeFromName(String name);
}