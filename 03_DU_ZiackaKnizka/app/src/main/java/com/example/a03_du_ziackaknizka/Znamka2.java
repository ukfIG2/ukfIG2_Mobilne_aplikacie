package com.example.a03_du_ziackaknizka;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Znamka2 extends AppCompatActivity {

    private TextView Meno;
    private TextView Predmet;
    private TextView staraZnamka;
    private EditText novaZnamka;
    private Button update;
    private Button naspat;
    private Button vymazat;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_znamka2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        intent = getIntent();

        Meno = findViewById(R.id.Znamka2_Meno);
        Predmet = findViewById(R.id.Znamka2_predmet);
        staraZnamka = findViewById(R.id.Znamka2_stara);
        novaZnamka = findViewById(R.id.Znamka2_nova);

        naspat = findViewById(R.id.Znamka2_naspak);
        naspat.setOnClickListener(e -> finish());

        nacitajData();
        update = findViewById(R.id.Znamka2_update);
        update.setOnClickListener(e -> update());

        vymazat = findViewById(R.id.Znamka2_delete);
        vymazat.setOnClickListener(e -> delete());
    }

    private void nacitajData() {
        String meno = getIntent().getStringExtra("meno");
        String priezvisko = getIntent().getStringExtra("priezvisko");
        String predmet = getIntent().getStringExtra("predmet");
        String znamka = getIntent().getStringExtra("hodnota");


        Meno.setText(meno + " " + priezvisko);
        Predmet.setText(predmet);
        //System.out.println(predmet);
        staraZnamka.setText(znamka);
    }

    private void update() {
        String staraZnamka = intent.getStringExtra("hodnota");
        String NovaZnamka = PridajStudentaActivity.skontroluZnamku(novaZnamka.getText().toString());
        MainActivity.zoznam.forEach(e -> {
            //System.out.println(MainActivity.zoznam);
            if(e.get("Meno").equals(intent.getStringExtra("meno")) && e.get("Priezvisko").equals(intent.getStringExtra("priezvisko"))){
                e.forEach((k, v) -> {
                    if(k.equals(intent.getStringExtra("nazov"))){
                        //System.out.println("k: " + k);
                        //System.out.println("v: " + v);
                        String hodnota = v.toString();
                        String noveHodnoty = hodnota.replace(staraZnamka, NovaZnamka);
                        e.put(k, noveHodnoty);
                        //System.out.println("nove hodnoty: " + noveHodnoty);
                    }
                });
            }
        });
        finish();
    }

    private void delete(){
        String staraZnamka = intent.getStringExtra("hodnota");
        MainActivity.zoznam.forEach(e -> {
            if(Objects.equals(e.get("Meno"), intent.getStringExtra("meno")) && e.get("Priezvisko").equals(intent.getStringExtra("priezvisko"))){
                e.forEach((k, v) -> {
                    if(k.equals(intent.getStringExtra("nazov"))){
                        String hodnota = v.toString();
                        String noveHodnoty = hodnota.replaceFirst(staraZnamka, "");
                        noveHodnoty = noveHodnoty.replaceFirst(",,", ",");
                        noveHodnoty = noveHodnoty.replaceFirst(",,,", ",");
                        noveHodnoty = noveHodnoty.replaceFirst("^,", ""); // remove leading commas
                        noveHodnoty = noveHodnoty.replaceFirst("^,,", "");
                        noveHodnoty = noveHodnoty.replaceFirst(",$", "");
                        noveHodnoty = noveHodnoty.replaceFirst(",,$", "");
                        e.put(k, noveHodnoty);
                        //System.out.println("nove hodnoty: " + noveHodnoty);
                    }
                });
            }
        });
        finish();
    }

}