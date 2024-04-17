package com.example.a0602_servercommunication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b1 = (Button) findViewById(R.id.button2);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stiahniData();
            }
        });
        Button b2 = (Button) findViewById(R.id.button1);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                odosliData();
            }
        });

    }

    public void stiahniData() {
        // adresa stránky, ktorú chceme získať
        String myUrl = "https://www.studujvedu.sk/android/vypis.php"; // "https://.../android/output.php";
        // Vytvorenie inštancie pre download
        Komunikator downl = new Komunikator();
        // zavolanie metódy na spustenie AsyncTask-u
        // tu by sme mohli použiť ľubovoľný počet stringových parametrov
        downl.execute(myUrl, "1");
    }

    public void odosliData() {
        String myUrl = "https://www.studujvedu.sk/android/zapis.php";
        String _name = ((EditText) (findViewById(R.id.editName))).getText().toString();
        String _surname = ((EditText) (findViewById(R.id.editSurname))).getText().toString();
        String _ttime = ((EditText) (findViewById(R.id.editTime))).getText().toString();
        _ttime.replace(',','.'); // aby som mal desatinnú bodku, nie čiarku
        // vytvorenie inštancie
        Komunikator sender = new Komunikator();
        // odoslanie parametrov
        sender.execute(myUrl, "2", _name, _surname, _ttime);
    }

    public class Komunikator extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String stringUrl = params[0]; // precitanie URL
            int task_type = Integer.parseInt(params[1]); // 2. parameter - co ma urobit
            String result = "", riadok;

            try {
                // vytvorenie URL
                URL url = new URL(stringUrl);
                // pripojenie k danemu URL
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true); // nastavenie na pristup post
                // parametre pripojenia
                conn.setReadTimeout(15000); conn.setConnectTimeout(15000);

                switch (task_type) {
                    case 1: // vyrob connect - vsetko je pripravene, posle sa len dotaz
                        conn.connect();
                        break;
                    case 2: // prečítame parametre z volania metódy
                        String meno = params[2];
                        String priezvisko = params[3];
                        String cas = params[4];
                        // prístup k output streamu requestu, vytvorenie parametrov
                        OutputStream output = conn.getOutputStream();
                        String data = URLEncoder.encode("meno", "UTF-8") + "=" +
                                URLEncoder.encode(meno, "UTF-8");
                        data += "&" + URLEncoder.encode("priezvisko", "UTF-8") + "=" +
                                URLEncoder.encode(priezvisko, "UTF-8");
                        data += "&" + URLEncoder.encode("cas", "UTF-8") + "=" +
                                URLEncoder.encode(cas, "UTF-8");
                        // zapis do streamu
                        output.write(data.getBytes("UTF-8"));
                        output.flush(); // odoslanie
                        output.close();
                        break;
                }

                // spristupnenie odpovedacieho (response) streamu
                InputStreamReader streamReader = new InputStreamReader(conn.getInputStream());
                // vytvorenie readera
                BufferedReader reader = new BufferedReader(streamReader);
                // citanie udajov zo streamu
                while ((riadok = reader.readLine()) != null) {
                    result += riadok;
                }
                // close
                reader.close();
                streamReader.close();
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