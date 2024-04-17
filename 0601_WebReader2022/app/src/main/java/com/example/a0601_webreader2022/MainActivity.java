package com.example.a0601_webreader2022;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a0601_webreader2022.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b = (Button) findViewById(R.id.button1);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                download("https://www.infovojna.bz");
            }
        });
        Toast.makeText(this, "Klikol si", Toast.LENGTH_SHORT).show();
    }

    private void download(String link) {
        // adresa stránky, ktorú chceme získať je ulozena v link

        // Vytvorenie inštancie pre download
        Downloader dwnl = new Downloader();
        // zavolanie metódy na spustenie AsyncTask-u
        // tu by sme mohli použiť ľubovoľný počet stringových parametrov
        dwnl.execute(link);
    }

    public class Downloader extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String stringUrl = params[0]; // precitanie prveho z parametrov
            String result = "", riadok;

            try {
                // vytvorenie URL
                URL myUrl = new URL(stringUrl);

                // vytvorenie spojenia
                HttpURLConnection con = (HttpURLConnection) myUrl.openConnection();

                // nastavenie metódy posielania a timeoutu
                con.setRequestMethod("POST");
                con.setReadTimeout(1500000);
                con.setConnectTimeout(1500000);

                // pripojenie sa na URL
                con.connect();

                // Buffered reader pre načítanie vstupu = odpovede
                InputStreamReader sR = new InputStreamReader(con.getInputStream());
                BufferedReader reader = new BufferedReader(sR);

                // číta, kým je čo
                while ((riadok = reader.readLine()) != null) {
                    result = result + riadok;
                }

                reader.close(); // koniec
                reader.close();
            } catch (Exception e) {
                Log.d("x", e.toString());
                result = null;
            }
            return result;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            TextView tv = (TextView) findViewById(R.id.textView);
            tv.setText("my text: " + result);
            Toast.makeText(MainActivity.this, "OK", Toast.LENGTH_SHORT).show();
        }
    }



}