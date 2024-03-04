package com.example.a02_du_piskvorky_zadanie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class VysledokKolaActivity extends AppCompatActivity {
    private TextView textViewVysledok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vysledok_kola);
        textViewVysledok = findViewById(R.id.tv5);
        textViewVysledok.setText("Vyhral hrac: " + getIntent().getStringExtra("winner"));
    }
}

//Q:How to check data type of variable in Java give me example. String a = "Hello";
//A:You can use the getClass() method to get the type of a variable. For example:
// String a = "Hello";
// System.out.println(a.getClass());