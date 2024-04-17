package com.example.a0603_jsonservercomunicator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

public class DataActivity extends AppCompatActivity {
    String myJSON; // string pre udaje zo servera
    JSONArray peoples = null; // pole pre dekodovane udaje
    // list do ktoreho sa "prelozia" dekodovane udaje
    ArrayList<HashMap<String, String>> personList;
    ListView list; // zobrazovaci list
    SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        list = (ListView) findViewById(R.id.listView);
        personList = new ArrayList<HashMap<String, String>>();
        downloadData();
    }

    public void downloadData() {
        class JSONGetter extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                String result = "", riadok;

                try {
                    URL url = new URL("https://www.studujvedu.sk/android/vypisjson.php");
                    // vytvorenie pripojenia
                    URLConnection conn = url.openConnection();
                    conn.setDoOutput(true); // je to post
                    // connection properties
                    conn.setReadTimeout(15000);
                    conn.setConnectTimeout(15000);
                    conn.connect(); // pripojenie
                    InputStreamReader streamReader = new InputStreamReader(conn.getInputStream());

                    // precitanie udajov
                    BufferedReader reader = new BufferedReader(streamReader);
                    while ((riadok = reader.readLine()) != null) {
                        result += riadok;
                    }
                    reader.close();
                    streamReader.close();
                } catch (Exception e) {
                    Log.d("x", e.toString());
                    result = null;
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                showList(result); // preložím údaje do listu
            }
        }

        // kod metody getData, ktory vytvori instanciu AsyncTask-u a spusti ju
        JSONGetter g = new JSONGetter();
        g.execute();
    }

    protected void showList(String jsonString) {
        try {
            // string vlozime do objektu
            JSONObject jsonObj = new JSONObject(jsonString);
            // objekt resp. jeho cast data vlozime do pola
            peoples = jsonObj.getJSONArray("data");

            for (int i = 0; i < peoples.length(); i++) { // z pola precitame udaje do stringov
                JSONObject c = peoples.getJSONObject(i);
                String id = c.getString("id");
                String _name = c.getString("meno");
                String _surname = c.getString("priezvisko");
                String _time = c.getString("cas");

                // zo stringov pripravime hash prvky pre adapter
                HashMap<String, String> persons = new HashMap<String, String>();
                persons.put("id", id);
                persons.put("name", _name);
                persons.put("surname", _surname);
                persons.put("time", _time);

                personList.add(persons);
            }
            adapter = new SimpleAdapter(
                    this,
                    personList,
                    R.layout.list_layout,
                    new String[]{"id", "name", "surname", "time"},
                    new int[]{R.id._id, R.id._name, R.id._surname, R.id._time}
            );
            list.setAdapter(adapter);

        } catch (JSONException e) {
            Log.d("decode error", e.toString());
        }
    }
}