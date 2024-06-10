package com.example.skuska_2;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class PridajKnihu extends AppCompatActivity {

    private EditText nazov;
    private EditText autor;
    private EditText rok;
    private Spinner zaner;
    private EditText obsah;
    private Databaza_lokalna dbh = new Databaza_lokalna(this);

    private Button pridaj;
    private Button zatvorit;

    private long zanerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pridaj_knihu);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        List<zaner> zanre = dbh.getAllZaner();

        nazov = findViewById(R.id.pridajNazov);
        autor = findViewById(R.id.pridajAutor);
        rok = findViewById(R.id.pridajRok);
        obsah = findViewById(R.id.pridaj_Obsah);
        zaner = findViewById(R.id.pridajZaner);
        List<zaner> zanerList = dbh.getAllZaner();

        // Create an ArrayAdapter with the data
        ArrayAdapter<zaner> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, zanerList);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the ArrayAdapter to the spinner
        zaner.setAdapter(adapter);


        pridaj = findViewById(R.id.pridajInsert);
        pridaj.setOnClickListener(e -> {
            String rok = this.rok.getText().toString();
            int rokInt = Integer.parseInt(rok);
            long zanerId = ((zaner) this.zaner.getSelectedItem()).getId();

            dbh.addKniha(new kniha(nazov.getText().toString(), autor.getText().toString(), rokInt, obsah.getText().toString(), zanerId));
            finish();
        });

        zatvorit = findViewById(R.id.pridajExit);
        zatvorit.setOnClickListener(e -> finish());


    }
}