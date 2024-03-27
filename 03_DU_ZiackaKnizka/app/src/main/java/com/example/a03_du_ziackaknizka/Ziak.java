package com.example.a03_du_ziackaknizka;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.widget.TextView;
//import android.widget.Toolbar;
import androidx.appcompat.widget.Toolbar;


public class Ziak extends AppCompatActivity {
    Intent intent;
    Toolbar toolbar;
    private TextView matematika;
    private TextView slovenskyJazyk;
    private TextView fyzika;
    private TextView informatika;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ziak);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        intent = getIntent();
        toolbar = findViewById(R.id.ziak_toolbar_01);
        matematika = findViewById(R.id.Matematika);
        slovenskyJazyk = findViewById(R.id.SKJazyk);
        fyzika = findViewById(R.id.Fyzika);
        informatika = findViewById(R.id.Info);
        nacitajData();
        //Put

    }

    private void nacitajData(){
        String meno = intent.getStringExtra("meno");
        String priezvisko = intent.getStringExtra("priezvisko");

        toolbar.setTitle(meno + " " + priezvisko);

        String Matematika = intent.getStringExtra("matematika");
        matematika.setText(Matematika);
        String SlovenskyJazyk = intent.getStringExtra("Slovenský jazyk");
        slovenskyJazyk.setText(SlovenskyJazyk);
        String Fyzika = intent.getStringExtra("fyzika");
        fyzika.setText(Fyzika);
        String Informatika = intent.getStringExtra("informatika");
        informatika.setText(Informatika);
    }





}