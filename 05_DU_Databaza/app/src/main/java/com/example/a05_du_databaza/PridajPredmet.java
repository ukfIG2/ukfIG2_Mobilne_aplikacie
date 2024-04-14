package com.example.a05_du_databaza;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PridajPredmet extends AppCompatActivity {

    private EditText nazov;
    private Button pridaj;
    private Button naspak;

    private Databaza dbh = new Databaza(this);
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pridaj_predmet);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        intent = getIntent();
        nazov = findViewById(R.id.EditPredmet_Meno);
        pridaj = findViewById(R.id.EditPredmet_Edit);
        naspak = findViewById(R.id.EditPredmet_Koniec);
        naspak.setOnClickListener(e -> finish());
        pridaj.setOnClickListener(e -> pridajPredmet());
    }

    private void pridajPredmet(){
        Predmet predmet = new Predmet(nazov.getText().toString());
        dbh.addPredmet(predmet);
        finish();
    }

}