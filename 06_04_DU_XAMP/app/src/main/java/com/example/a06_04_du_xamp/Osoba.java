package com.example.a06_04_du_xamp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Osoba extends AppCompatActivity {

    private Intent intent;
    private Button naspak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_osoba);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        intent = getIntent();
        String id = intent.getStringExtra("id");

        new FetchJSONTask().execute("http://10.0.2.2/MobulneAplikacie/DU_1/vypisOsoby.php?id=" + id);

        naspak = findViewById(R.id.pridajosoba_naspak);
        naspak.setOnClickListener(v -> {
            finish();
        });
    }

    private class FetchJSONTask extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... strings) {
            JSONObject jsonObject = null;
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String output;
                StringBuilder response = new StringBuilder();
                while ((output = in.readLine()) != null) {
                    response.append(output);
                }
                in.close();
                conn.disconnect();

                jsonObject = new JSONObject(response.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);
            try {
                ((EditText) findViewById(R.id.pridajosoba_meno)).setText(result.getString("Meno"));
                ((EditText) findViewById(R.id.pridajosoba_priezvisko)).setText(result.getString("Priezvisko"));
                ((EditText) findViewById(R.id.pridajosoba_ulica)).setText(result.getString("Ulica"));
                ((EditText) findViewById(R.id.pridajosoba_psc)).setText(result.getString("Psc"));
                ((EditText) findViewById(R.id.pridajosoba_mesto)).setText(result.getString("Mesto"));
                ((EditText) findViewById(R.id.pridajosoba_email)).setText(result.getString("E-mail"));
                ((EditText) findViewById(R.id.pridajosoba_telCislo)).setText(result.getString("TelCislo"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}