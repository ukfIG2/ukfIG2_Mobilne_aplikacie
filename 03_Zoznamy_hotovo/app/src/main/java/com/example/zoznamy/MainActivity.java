package com.example.zoznamy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
//import android.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> ulohyArray = new ArrayList<String>(); // zoznam uloh
    ArrayList<String> popisArray = new ArrayList<String>(); // popis uloh
    ArrayAdapter<String> myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView lv = findViewById(R.id.listView);
        NacitajData();
        PridajListener();

        Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_pridaj) {
            // sem
            startActivityForResult(new Intent(this, PridajActivity.class),1);
            return true;
        }
        if (item.getItemId() == R.id.menu_about) {
            Toast.makeText(getBaseContext(), "The me", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String uloha = data.getStringExtra("uloha");
                    String popis = data.getStringExtra("popis");
                    // vloženie do poľa popisov
                    popisArray.add(popis);
                    // vloženie do poľa úloh cez adaptér
                    myAdapter.add(uloha);
//                    ulohyArray.add(uloha);
                    break;
                }
        }
    }

    private void PridajListener() {
        AdapterView.OnItemClickListener mMessageClickedHandler =
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView parent,
                                            View v,
                                            int position,
                                            long id) {
                        // intent pre otvorenie druhej aktivity
                        Intent intent = new Intent(MainActivity.this, PopisActivity.class);
                        // precitaj data z poli podla selectnutej polozky
                        String uloha = ulohyArray.get(position);
                        String popis = popisArray.get(position);
                        // vloz data do intentu
                        intent.putExtra("uloha",uloha);
                        intent.putExtra("popis",popis);
                        // zobraz druhu aktivitu
                        startActivity(intent);
                    }
                };
        ListView myList =  findViewById(R.id.listView);
        myList.setOnItemClickListener(mMessageClickedHandler);
    }

    private void NacitajData() {
        ulohyArray.add("Nákup");
        popisArray.add("potraviny, ponožky");

        ulohyArray.add("Pošta");
        popisArray.add("zaplatiť PO BOX");

        ulohyArray.add("Vybrať deti");
        popisArray.add("zo škôlky - svoje");

        // vytvorenie adaptera
        myAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                ulohyArray);

        ListView lv = findViewById(R.id.listView);
        lv.setAdapter(myAdapter);
    }
}
