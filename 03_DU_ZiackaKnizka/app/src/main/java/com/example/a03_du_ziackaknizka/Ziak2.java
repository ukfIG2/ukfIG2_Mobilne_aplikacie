package com.example.a03_du_ziackaknizka;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class Ziak2 extends AppCompatActivity {

    private Intent intent;
    //Toolbar toolbar;
    private TextView toolbar;
    private ArrayList<String> zoznam;
    private Button button;
    String Matematika;
    String SlovenskyJazyk;
    String Fyzika;
    String Informatika;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ziak2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    button = findViewById(R.id.Ziak2Naspat);
    button.setOnClickListener(e -> finish());

        intent = getIntent();
        toolbar = findViewById(R.id.toolbar_title);
        nacitajData();
        pridajAdapter();
        pridajListener();

    }

    public void nacitajData(){
        String meno = intent.getStringExtra("meno");
        String priezvisko = intent.getStringExtra("priezvisko");

        toolbar.setText(meno + " " + priezvisko);

        MainActivity.zoznam.forEach(e -> {
            if(e.get("Meno").equals(meno) && e.get("Priezvisko").equals(priezvisko)){
                //System.out.println("Meno: " + e.get("Meno"));
                //System.out.println("Priezvisko: " + e.get("Priezvisko"));

                Matematika = e.get("Matematika").isEmpty() ? "Zatiaľ nič" : e.get("Matematika");
                SlovenskyJazyk = e.get("Slovenský jazyk").isEmpty() ? "Zatiaľ nič" : e.get("Slovenský jazyk");
                Fyzika = e.get("Fyzika").isEmpty() ? "Zatiaľ nič" : e.get("Fyzika");
                Informatika = e.get("Informatika").isEmpty() ? "Zatiaľ nič" : e.get("Informatika");
            }
        });
    }

    private void pridajAdapter(){
        zoznam = new ArrayList<String>();
        zoznam.add("Matematika: " + Matematika);
        zoznam.add("Slovenský jazyk: " + SlovenskyJazyk);
        zoznam.add("Fyzika: " + Fyzika);
        zoznam.add("Informatika: " + Informatika);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, zoznam);
        ListView listView = findViewById(R.id.ziak2_listview);
        listView.setAdapter(myAdapter);
    }

    private void pridajListener(){
        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {
                //System.out.println("Klikol si na: " + position);
                Intent intent = new Intent(Ziak2.this, ZoznamZnamok.class);
                String string = zoznam.get(position);//redudant
                String nazov = string.substring(0, string.indexOf(":"));
                String hodnota = string.substring(string.indexOf(":") + 2);
                intent.putExtra("nazov", nazov);
                intent.putExtra("meno", getIntent().getStringExtra("meno"));
                intent.putExtra("priezvisko", getIntent().getStringExtra("priezvisko"));
                startActivity(intent);
            }
        };
        ListView listView = findViewById(R.id.ziak2_listview);
        listView.setOnItemClickListener(listener);
    }

    protected void onResume() {
        super.onResume();
        nacitajData();
        pridajAdapter();
    }


}