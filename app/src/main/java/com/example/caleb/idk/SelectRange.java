package com.example.caleb.idk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class SelectRange extends AppCompatActivity {

        int meters = 0;
        int m = 0;
        int n=0;
    SharedPreferences sharedPref = null;
    SharedPreferences.Editor editor = null;
    public static final String PREFS_NAME = "SaveFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_range);

        final TextView miles = (TextView) findViewById(R.id.miles);

        final SeekBar sk = (SeekBar) findViewById(R.id.seekBar);

        SharedPreferences sharedPref = this.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        sk.setProgress( sharedPref.getInt("Miles",10));
        miles.setText(sk.getProgress() + " miles");
        sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub

                // added 3 for min of seek bar
                miles.setText(sk.getProgress() + 3 + " miles");

                meters = sk.getProgress() + 3;
                m = meters;
                meters *= 1609.34;
                SharedPreferences sharedPref = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("Miles", m);
                editor.commit();

            }
        });

        final TextView money = (TextView) findViewById(R.id.money);
        final SeekBar sk2 = (SeekBar) findViewById(R.id.seekBar2);
        sharedPref = this.getSharedPreferences(PREFS_NAME, 0);
        editor = sharedPref.edit();
        sk2.setProgress( sharedPref.getInt("Money",1)*10) ;
        String b = "";
        n = sk2.getProgress()/10;
        for (int i = 0; i <= n; i++) {
            b += "$";
        }
        money.setText(b);
        sk2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub
                String a = "";
                n = sk2.getProgress()/10;
                for (int i = 0; i <= n; i++) {
                    a += "$";
                }

                money.setText(a);
                SharedPreferences sharedPref = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("Money", n);
                editor.commit();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select_range, menu);
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

    public void getResults(View view) {

        Intent intent = new Intent(this, GenerateEvents.class);
        startActivity(intent);

    }
}
