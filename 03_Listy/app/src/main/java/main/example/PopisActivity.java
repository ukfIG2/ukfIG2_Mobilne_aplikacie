package main.example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class PopisActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popis);

        // view-y do ktorych zobrazime obsah
        TextView tv_nazov = (TextView) findViewById(R.id.TV1);
        TextView tv_popis = (TextView) findViewById(R.id.TV2);
        Button exit = findViewById(R.id.B1);
        // precitame intent, ktorym bola aktivita vyvolana
        Intent intent = getIntent();
        // z intentu precitame obsah odoslanych premennych
        String uloha = intent.getStringExtra("uloha");
        String popis = intent.getStringExtra("popis");
        // vypis
        tv_nazov.setText(uloha);
        tv_popis.setText(popis);
        exit.setOnClickListener(e -> finish());

    }
}