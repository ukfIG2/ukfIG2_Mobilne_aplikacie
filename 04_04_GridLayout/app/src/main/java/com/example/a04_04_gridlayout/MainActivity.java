package com.example.a04_04_gridlayout;


import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

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
        zoznam.add("Ferko");
        zoznam.add("Janko");
        zoznam.add("Alexandra");
        zoznam.add("Zuza");
        zoznam.add("Jefim Abrahamovič");

        myAdapter = new ArrayAdapter(
                this,             			// kontext, štandardne aktivita
                android.R.layout.simple_list_item_1,  // predpis pre zobrazovanie
                zoznam                   		   // zdroj dát
        );

        // odkaz na view určený pre dáta
        GridView lv = (GridView) findViewById(R.id.gridView);
        lv.setAdapter(myAdapter); // napojenie cez adapter do LV

    }


}