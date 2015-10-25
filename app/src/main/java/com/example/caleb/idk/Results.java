package com.example.caleb.idk;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Results extends AppCompatActivity {

    public static final String PREFS_NAME = "SaveFile";

    SharedPreferences sharedPref = null;
    SharedPreferences.Editor editor = null;

    int miles = 0;
    int money = 0;
    double longitude = 0;
    double latitude = 0;
    String inJson = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        final TextView name = (TextView) findViewById(R.id.name);
        final TextView location = (TextView) findViewById(R.id.location);
        final TextView price = (TextView) findViewById(R.id.price);
        final TextView rate = (TextView) findViewById(R.id.rate);

        SharedPreferences sharedPref = this.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        //rate.setText(sharedPref.getString("Name","0"));


        //set strings!
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_results, menu);
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

    public void reroll(){
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

                        Toast toast = Toast.makeText(context, inJson, duration);
                        //toast.show();
                    }
                }, new Response.ErrorListener() {
            @Override

            public void onErrorResponse(VolleyError error) {

            }
        });

        Context context = getApplicationContext();

        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, stringRequest.toString(), duration);
        //toast.show();


// Add the request to the RequestQueue.
        queue.add(stringRequest);

        fix();

        Intent intent = new Intent(this, Results.class);
        startActivity(intent);

    }

    public void fix(){

        Context context = getApplicationContext();

        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, inJson, duration);
        //toast.show();

        try {
            JSONObject js = new JSONObject(inJson);

            double rand = (Math.random()*19);
            int real = (int) rand;

            String name = js.getJSONObject("name").toString();
            JSONObject rating = js.getJSONArray("rating").getJSONObject(real);
            JSONObject  price= js.getJSONArray("price").getJSONObject(real);
            JSONObject vicinity= js.getJSONArray("vicinity").getJSONObject(real);
            JSONObject icon = js.getJSONArray("icon").getJSONObject(real);
            JSONObject type = js.getJSONArray("type").getJSONObject(real);

            SharedPreferences sharedPref = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = sharedPref.edit();

            editor.putString("Name", name);
            editor.putString("Rating", rating.toString());
            editor.putString("Price", price.toString());
            editor.putString("Vicinity", vicinity.toString());
            editor.putString("Icon", icon.toString());

            editor.commit();

        } catch (JSONException e) {
            e.printStackTrace();
        }

}
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, SelectRange.class);
        startActivity(intent);

    }
}
