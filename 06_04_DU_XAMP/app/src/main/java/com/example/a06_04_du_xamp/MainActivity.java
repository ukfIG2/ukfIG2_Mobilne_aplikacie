package com.example.a06_04_du_xamp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
//import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView mainText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new FetchJSONTask().execute("http://10.0.2.2/MobulneAplikacie/DU_1/vypisObjednavatel.php");

        Toolbar tb = findViewById(R.id.main_toolbar);
        setSupportActionBar(tb);
    }

    private class FetchJSONTask extends AsyncTask<String, Void, List<Objednavatel>> {

        @Override
        protected List<Objednavatel> doInBackground(String... strings) {
            List<Objednavatel> objednavatelia = new ArrayList<>();
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

                JSONArray jsonArray = new JSONArray(response.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Objednavatel objednavatel = new Objednavatel(
                            jsonObject.getString("idPouzivatel"),
                            jsonObject.getString("Meno"),
                            jsonObject.getString("Priezvisko"),
                            jsonObject.getString("Mesto")
                    );
                    objednavatelia.add(objednavatel);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return objednavatelia;
        }

        @Override
        protected void onPostExecute(List<Objednavatel> result) {
            super.onPostExecute(result);
            ListView listView = findViewById(R.id.main_listView);
            ObjednavatelAdapter adapter = new ObjednavatelAdapter(MainActivity.this, result);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Objednavatel objednavatel = adapter.getItem(position);
                    Intent intent = new Intent(MainActivity.this, Osoba.class);
                    intent.putExtra("id", objednavatel.getIdPouzivatel());
                    startActivity(intent);
                }
            });
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_main_Pridaj) {
            startActivityForResult(new Intent(this, PridajOsobu.class), 1);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new FetchJSONTask().execute("http://10.0.2.2/MobulneAplikacie/DU_1/vypisObjednavatel.php");
    }

}