package com.example.a04_02_viacstlpcovylayout;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ArrayList<HashMap<String, String>> zoznam = new ArrayList<HashMap<String, String>>();
    SimpleAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        NaplnData();
        PripojAdapter();
    }

    private void PripojAdapter() {
        String[] from = new String[]{"meno", "PR1", "PR2"};
        int[] to = new int[]{R.id.item1, R.id.item2, R.id.item3};

        myAdapter = new SimpleAdapter(
                this,                   // kontext, štandardne aktivita
                zoznam,                  // zdroj dát
                R.layout.vs_list_layout, // predpis pre zobrazovanie
                from,
                to
        );

        // ziskame odkaz na view
        ListView lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(myAdapter); // napojime cez adapter data do LV
    }

    private void NaplnData() {
        HashMap<String, String> riadok = new HashMap<String, String>();
        riadok.put("meno", "Jožko");
        riadok.put("PR1", "A");
        riadok.put("PR2", "B");
        zoznam.add(riadok);

        riadok = new HashMap<String, String>();
        riadok.put("meno", "Miško");
        riadok.put("PR1", "C");
        riadok.put("PR2", "C");
        zoznam.add(riadok);

        riadok = new HashMap<String, String>();
        riadok.put("meno", "Anička");
        riadok.put("PR1", "E");
        riadok.put("PR2", "B");
        zoznam.add(riadok);

        riadok = new HashMap<String, String>();
        riadok.put("meno", "Zuzanka");
        riadok.put("PR1", "E");
        riadok.put("PR2", "B");
        zoznam.add(riadok);
    }
}