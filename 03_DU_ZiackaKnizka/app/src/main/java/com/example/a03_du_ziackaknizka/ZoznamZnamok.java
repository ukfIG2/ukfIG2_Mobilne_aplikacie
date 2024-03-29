package com.example.a03_du_ziackaknizka;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class ZoznamZnamok extends AppCompatActivity {

    Toolbar toolbar;
    ListView zoznamZnamok;
    Intent intent;
    ArrayList<String> listZnamok;
    Button naspat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_zoznam_znamok);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        intent = getIntent();
        toolbar = findViewById(R.id.ListZnamok_toolbar);
        zoznamZnamok = findViewById(R.id.ListZnamok_listview);

        nacitajData();
        pridajAdapter();
        pridajListener();

        naspat = findViewById(R.id.Zoznam_naspak);
        naspat.setOnClickListener(e -> finish());

        toolbar.setTitle(intent.getStringExtra("meno") + " " + intent.getStringExtra("priezvisko") + " " + intent.getStringExtra("nazov"));
        setSupportActionBar(toolbar);
    }

    private void nacitajData() {
        listZnamok = new ArrayList<>();
        String meno = intent.getStringExtra("meno");
        String priezvisko = intent.getStringExtra("priezvisko");
        String nazov = intent.getStringExtra("nazov");
        //System.out.println(meno + " " + priezvisko);
        MainActivity.zoznam.forEach((k) -> {
            if (Objects.equals(k.get("Meno"), meno) && Objects.equals(k.get("Priezvisko"), priezvisko)) {
                k.forEach((key, value) -> {
                    if (key.equals(nazov)) {
                        if (value.contains(",")) {
                            String[] znamky = value.split(",");
                            listZnamok.addAll(Arrays.asList(znamky));
                        }
                        else {
                            listZnamok.add(value);
                        }
                    }
                });
            }
        });
    }

    private void pridajAdapter() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listZnamok);

        zoznamZnamok.setAdapter(adapter);
        //System.out.println("Adapter");

    }



    private void pridajListener() {
        AdapterView.OnItemClickListener listener = (parent, view, position, id) -> {
            //System.out.println("Klikol si na " + listZnamok.get(position));
            Intent intent = new Intent(this, Znamka2.class);
            intent.putExtra("meno", getIntent().getStringExtra("meno"));
            intent.putExtra("priezvisko", getIntent().getStringExtra("priezvisko"));
            intent.putExtra("nazov", getIntent().getStringExtra("nazov"));
            intent.putExtra("hodnota", listZnamok.get(position));
            intent.putExtra("predmet", getIntent().getStringExtra("nazov"));
            startActivity(intent);
        };
        zoznamZnamok.setOnItemClickListener(listener);
    }

    protected void onRestart() {
        super.onRestart();
        nacitajData();
        pridajAdapter();
        //System.out.println("restart");
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nova_znamka, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.Menu_PZ) {
            Intent intent = new Intent(this, PridajZnamkuActivity.class);
            intent.putExtra("meno", getIntent().getStringExtra("meno"));
            intent.putExtra("priezvisko", getIntent().getStringExtra("priezvisko"));
            intent.putExtra("nazov", getIntent().getStringExtra("nazov"));
            startActivityForResult(intent, 1);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}