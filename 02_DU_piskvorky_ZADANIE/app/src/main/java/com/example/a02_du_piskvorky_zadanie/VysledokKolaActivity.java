package com.example.a02_du_piskvorky_zadanie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class VysledokKolaActivity extends AppCompatActivity {
    private TextView textViewVysledok;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vysledok_kola);
        textViewVysledok = findViewById(R.id.tv5);
        textViewVysledok.setText("Vyhral hrac: " + getIntent().getStringExtra("winner"));
        button = findViewById(R.id.bt5);
        button.setOnClickListener(e -> reset());
    }

    private void reset() {
        //I want to return to previus intent
        finish();
    }

}

//Q:How to check data type of variable in Java give me example. String a = "Hello";
//A:You can use the getClass() method to get the type of a variable. For example:
// String a = "Hello";
// System.out.println(a.getClass());