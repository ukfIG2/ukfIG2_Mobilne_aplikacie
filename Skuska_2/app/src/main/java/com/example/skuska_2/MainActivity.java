package com.example.skuska_2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Databaza_lokalna dl = new Databaza_lokalna(this);

    private SimpleCursorAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dl = new Databaza_lokalna(this);

       /* Button importButton = findViewById(R.id.main_import);
        importButton.setOnClickListener(v -> importujDatabazu());*/

        Button exportButton = findViewById(R.id.main_export);
        exportButton.setOnClickListener(v -> new PostJSONTask(dl).execute("http://10.0.2.2/MobilneAplikacie_SKUSKA/importToXamp.php"));        Toolbar tb = findViewById(R.id.main_tool);
        setSupportActionBar(tb);
        //pridajZaner();

        pripojAdapter();
        pridajListener();
        pridajDlhyListener();

        for (zaner z : dl.getAllZaner()) {
            System.out.println(z.toString2());
        }    }

    ;

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.main_menu_Pridaj) {
            Intent intent = new Intent(this, PridajKnihu.class);
            startActivity(intent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void pripojAdapter() {


        myAdapter = new SimpleCursorAdapter(this,
                R.layout.list_layout_main,
                dl.getKnihy(),
                new String[]{"nazov", "autor", "rok"},
                new int[]{R.id.main_nazov, R.id.main_autor, R.id.main_rok},
                0);
        ListView lv = findViewById(R.id.main_list);
        lv.setAdapter(myAdapter);
    }

    private void pridajZaner() {
        Databaza_lokalna db = new Databaza_lokalna(this);
        zaner zanerRomantika = new zaner("fantazia");
        db.addZaner(zanerRomantika);
    }

    private void pridajListener() {
        ListView lv = findViewById(R.id.main_list);
        lv.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this, UpravKnihu.class);
            System.out.println("id: " + id);
            intent.putExtra("id", id);
            startActivity(intent);
        });

    }


    protected void onResume() {
        super.onResume();
        pripojAdapter();
    }

    private void pridajDlhyListener() {
        ListView lv = findViewById(R.id.main_list);
        lv.setOnItemLongClickListener((parent, view, position, id) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Chcete zmazat knihu?")
                    .setTitle("Mazanie ...")
                    .setPositiveButton("Ano", (dialog, which) -> {
                        dl.deleteKniha(id);
                        pripojAdapter();
                    })
                    .setNegativeButton("Nie", (dialog, which) -> {
                        // Do nothing
                    })
                    .show();
            return true;
        });


    }
}
class PostJSONTask extends AsyncTask<String, Void, Void> {

    private Databaza_lokalna dl;

    public PostJSONTask(Databaza_lokalna dl) {
        this.dl = dl;


    }
    @Override
    protected Void doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/json; utf-8");

            // Create JSON objects for each table
            JSONObject jsonZaner = new JSONObject();
            for (zaner z : dl.getAllZaner()) {
                jsonZaner.put(String.valueOf(z.getId()), z.getZaner());
            }

            JSONObject jsonKniha = new JSONObject();
            for (kniha k : dl.getAllKniha()) {
                JSONObject knihaDetails = new JSONObject();
                knihaDetails.put("nazov", k.getNazov());
                knihaDetails.put("autor", k.getAutor());
                knihaDetails.put("rok", k.getRok());
                knihaDetails.put("obsah", k.getObsah());
                knihaDetails.put("zaner", k.getZaner_id());
                jsonKniha.put(String.valueOf(k.getId()), knihaDetails);
            }

            // Create a main JSON object to hold both table's data
            JSONObject mainJson = new JSONObject();
            mainJson.put("zaner", jsonZaner);
            mainJson.put("kniha", jsonKniha);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(mainJson.toString());
            writer.flush();
            writer.close();
            os.close();

            conn.connect();
            System.out.println("Response code: " + conn.getResponseCode());

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
}


   /* private void importujDatabazu() {
        System.out.println("Importujem databazu");
*/
