package com.example.a06_04_du_xamp;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class PridajOsobu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pridaj_osobu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button buttonNaspak = findViewById(R.id.pridajosoba_naspak);
        buttonNaspak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button buttonPridaj = findViewById(R.id.pridajosoba_pridaj);
        buttonPridaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String meno = ((EditText) findViewById(R.id.pridajosoba_meno)).getText().toString();
                String priezvisko = ((EditText) findViewById(R.id.pridajosoba_priezvisko)).getText().toString();
                String ulica = ((EditText) findViewById(R.id.pridajosoba_ulica)).getText().toString();
                String psc = ((EditText) findViewById(R.id.pridajosoba_psc)).getText().toString();
                String mesto = ((EditText) findViewById(R.id.pridajosoba_mesto)).getText().toString();
                String email = ((EditText) findViewById(R.id.pridajosoba_email)).getText().toString();
                String telCislo = ((EditText) findViewById(R.id.pridajosoba_telCislo)).getText().toString();

                new PostJSONTask(meno, priezvisko, ulica, psc, mesto, email, telCislo).execute("http://10.0.2.2/MobulneAplikacie/DU_1/pridajOsobu.php");
            }
        });
    }

    private class PostJSONTask extends AsyncTask<String, Void, Void> {

        private String meno;
        private String priezvisko;
        private String ulica;
        private String psc;
        private String mesto;
        private String email;
        private String telCislo;

        public PostJSONTask(String meno, String priezvisko, String ulica, String psc, String mesto, String email, String telCislo) {
            this.meno = meno;
            this.priezvisko = priezvisko;
            this.ulica = ulica;
            this.psc = psc;
            this.mesto = mesto;
            this.email = email;
            this.telCislo = telCislo;
        }

        @Override
        protected Void doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("Meno", meno)
                        .appendQueryParameter("Priezvisko", priezvisko)
                        .appendQueryParameter("Ulica", ulica)
                        .appendQueryParameter("Psc", psc)
                        .appendQueryParameter("Mesto", mesto)
                        .appendQueryParameter("E-mail", email)
                        .appendQueryParameter("TelCislo", telCislo);
                String query = builder.build().getEncodedQuery();

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();

                conn.connect();

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Success
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                }

                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            finish();
        }

    }
}