package com.example.a04_01_basiclayout;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> zoznam = new ArrayList<String>();
    ArrayAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        NaplnData();
    }

    private void NaplnData() {
        zoznam.add("Jožko");
        zoznam.add("Miško");
        zoznam.add("Anička");

        myAdapter = new ArrayAdapter(
                this,             			// kontext, štandardne aktivita
                android.R.layout.simple_list_item_1,  // predpis pre zobrazovanie
                zoznam                   		   // zdroj dát
        );

        // odkaz na view určený pre dáta
        ListView lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(myAdapter); // napojenie cez adapter do LV

    }
}