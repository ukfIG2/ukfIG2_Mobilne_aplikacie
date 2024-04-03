package com.example.a03_du_ziackaknizka;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
//import android.widget.Toolbar;
import androidx.appcompat.widget.Toolbar;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {


    public static ArrayList<HashMap<String, String>> zoznam = new ArrayList<HashMap<String, String>>();


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

        NacitajData();
        PripojAdapter();
        PridajListener();

        Toolbar tb = findViewById(R.id.main_toolbar_01);
        setSupportActionBar(tb);

    }


    private void NacitajData() {
        HashMap<String, String> riadok = new HashMap<String, String>();
        riadok.put("Meno", "Jožko");
        riadok.put("Priezvisko", "Mrkvicka");
        riadok.put("Matematika", "1,5,8");
        riadok.put("Slovenský jazyk", "2");
        riadok.put("Fyzika", "3");
        riadok.put("Informatika", "4");
        zoznam.add(riadok);

        riadok = new HashMap<String, String>();
        riadok.put("Meno", "Miško");
        riadok.put("Priezvisko", "Mrkvicka");
        riadok.put("Matematika", "1");
        riadok.put("Slovenský jazyk", "2");
        riadok.put("Fyzika", "3");
        riadok.put("Informatika", "4");
        zoznam.add(riadok);

        riadok = new HashMap<String, String>();
        riadok.put("Meno", "Anička");
        riadok.put("Priezvisko", "Mrkvickova");
        riadok.put("Matematika", "1");
        riadok.put("Slovenský jazyk", "2");
        riadok.put("Fyzika", "3");
        riadok.put("Informatika", "4");
        zoznam.add(riadok);

        riadok = new HashMap<String, String>();
        riadok.put("Meno", "Zuzanka");
        riadok.put("Priezvisko", "Ferova");
        riadok.put("Matematika", "1");
        riadok.put("Slovenský jazyk", "2");
        riadok.put("Fyzika", "3");
        riadok.put("Informatika", "4");
        zoznam.add(riadok);

        riadok = new HashMap<String, String>();
        riadok.put("Meno", "Prazdny");
        riadok.put("Priezvisko", "Prazdny");
        riadok.put("Matematika", "");
        riadok.put("Slovenský jazyk", "");
        riadok.put("Fyzika", "");
        riadok.put("Informatika", "");
        zoznam.add(riadok);
    }

    private void PripojAdapter() {
        ArrayList<String> mena = new ArrayList<String>();

        for (HashMap<String, String> item : zoznam) {
            String meno = item.get("Meno");
            String priezvisko = item.get("Priezvisko");
            mena.add(meno + " " + priezvisko);
        }

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(
                this,                   // context, typically the activity
                android.R.layout.simple_list_item_1, // layout to use for display
                mena // data source
        );

        ListView lv = (ListView) findViewById(R.id.main_listView_01);
        lv.setAdapter(myAdapter);
    }

    private void PridajListener() {
        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, Ziak2.class);
                intent.putExtra("meno", zoznam.get(position).get("Meno"));
                intent.putExtra("priezvisko", zoznam.get(position).get("Priezvisko"));

                /*System.out.println("Meno: " + zoznam.get(position).get("Meno"));
                System.out.println("Priezvisko: " + zoznam.get(position).get("Priezvisko"));
                System.out.println("Matematika: " + zoznam.get(position).get("Matematika"));
                System.out.println("Slovenský jazyk: " + zoznam.get(position).get("Slovenský jazyk"));
                System.out.println("Fyzika: " + zoznam.get(position).get("Fyzika"));
                System.out.println("Informatika: " + zoznam.get(position).get("Informatika"));
*/
                startActivity(intent);
            }
        };
        ListView myList = findViewById(R.id.main_listView_01);
        myList.setOnItemClickListener(listener);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.Menu_pS) {
            startActivityForResult(new Intent(this, PridajStudentaActivity.class), 1);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        PripojAdapter();
    }


}
