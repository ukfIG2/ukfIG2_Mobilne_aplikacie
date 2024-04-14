package com.example.a05_du_databaza;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PridajStudenta extends AppCompatActivity {

    EditText meno;
    EditText priezvisko;
    Button pridaj;
    Button naspak;

    Databaza dbh = new Databaza(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pridaj_studenta);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        meno = findViewById(R.id.EditPredmet_Meno);
        priezvisko = findViewById(R.id.EditStudent_Priezvisko);
        pridaj = findViewById(R.id.EditPredmet_Edit);
        pridaj.setOnClickListener(e -> {
            dbh.addStudent(new Student(meno.getText().toString(), priezvisko.getText().toString()));
            finish();
        });

        naspak = findViewById(R.id.EditPredmet_Koniec);
        naspak.setOnClickListener(e -> finish());


    }
}