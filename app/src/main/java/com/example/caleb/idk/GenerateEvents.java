package com.example.caleb.idk;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;
import java.util.Random;

public class GenerateEvents extends AppCompatActivity {

    public static final String PREFS_NAME = "SaveFile";

    SharedPreferences sharedPref = null;
    SharedPreferences.Editor editor = null;

    int miles = 0;
    int money = 0;
    double longitude;
    double latitude;
    String inJson = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_events);
        final TextView temp = (TextView) findViewById(R.id.textView2);
        SharedPreferences sharedPref = this.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        this.miles = sharedPref.getInt("Miles",10);
        this.money  = sharedPref.getInt("Money", 1);

        GPSTracker gps = new GPSTracker(GenerateEvents.this);
        if (gps.canGetLocation()){
            longitude = gps.getLongitude();
            latitude = gps.getLatitude();

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_generate_events, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, SelectRange.class);
        startActivity(intent);

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

    public void getStuff(View view) {

        int meters = miles;
        meters *= 1609.34;

        String url = "http://www.mazj.me/api/?radius=" + meters +"&lon=" + longitude +"&lat=" + latitude +"&max_price=" + money;

        RequestQueue queue = Volley.newRequestQueue(this);

// Request a string response from the provided URL.

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       inJson = response;

                        Context context = getApplicationContext();

                        int duration = Toast.LENGTH_SHORT;

                        //Toast toast = Toast.makeText(context, inJson, duration);

                        //toast.show();
                        fix();


                    }
                }, new Response.ErrorListener() {
            @Override

            public void onErrorResponse(VolleyError error) {

            }
        });

  //      Context context = getApplicationContext();

//        int duration = Toast.LENGTH_SHORT;

       // Toast a = Toast.makeText(context, stringRequest.toString(), duration);
        //toast.show();

// Add the request to the RequestQueue.
        queue.add(stringRequest);

        Intent intent = new Intent(this, Results.class);
        startActivity(intent);

    }

    public void fix(){

    //    Context context = getApplicationContext();

  //

        try {


            double real = Math.random()*10;
            int rand = (int) real;


            JSONObject items = new JSONObject(inJson);

            JSONArray arr = items.getJSONArray("items");

            JSONArray value = arr.getJSONArray(rand);

            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, arr.toString(), duration);
            toast.show();

           JSONObject n = new JSONObject("name");
            //SONObject price= new JSONObject("price");
            //JSONObject  rate= new JSONObject("name");


            String name = value.getJSONObject(0).toString();

            sharedPref = getSharedPreferences(PREFS_NAME, 0);
            editor = sharedPref.edit();

            editor.putString("Name", name);
            //editor.putString("Rating", rating.toString());
            //editor.putString("Price", price.toString());
            //editor.putString("Vicinity", vicinity.toString());
            //editor.putString("Icon", icon.toString());
            editor.commit();

        } catch (JSONException e) {
            e.printStackTrace();
        }




    }
}
