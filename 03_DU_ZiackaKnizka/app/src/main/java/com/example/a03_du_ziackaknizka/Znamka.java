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

public class Znamka extends AppCompatActivity {

    private TextView Meno;
    private TextView Predmet;
    private TextView staraZnamka;
    private EditText novaZnamka;
    private Intent intent;
    private Button update;
    private Button naspat;
    private Button pridajZnamku;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_znamka);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        intent = getIntent();

        Meno = findViewById(R.id.znamkaMenoPriezvisko);
        Meno.setText(intent.getStringExtra("meno") + " " + intent.getStringExtra("priezvisko"));
        Predmet = findViewById(R.id.znamkaPredmet);
        Predmet.setText(intent.getStringExtra("nazov"));
        staraZnamka = findViewById(R.id.znamkaStara);
        //staraZnamka.setText(intent.getStringExtra("hodnota"));
        MainActivity.zoznam.forEach(e -> {
            if(e.get("Meno").equals(intent.getStringExtra("meno")) && e.get("Priezvisko").equals(intent.getStringExtra("priezvisko"))){
                e.forEach((k, v) -> {
                    if(k.equals(intent.getStringExtra("nazov"))){
                        //put shor if if empty "Zatedy ništ" else put value
                        if(v.equals("")){
                            staraZnamka.setText("Zatedy ništ");
                        }else
                        staraZnamka.setText(v);
                    }
                });
            }
        });
        novaZnamka = findViewById(R.id.znamkaNova);

        update = findViewById(R.id.znamkaUpdate);
        update.setOnClickListener(e -> update());

        naspat = findViewById(R.id.znamka_Finish);
        naspat.setOnClickListener(e -> finish());

        pridajZnamku = findViewById(R.id.ZnamkaPridajYnamku);
        pridajZnamku.setOnClickListener(e -> pridajZnamku());

        /*System.out.println("nazov: " + intent.getStringExtra("nazov"));
        System.out.println("hodnota: " + intent.getStringExtra("hodnota"));
        System.out.println("meno: " + intent.getStringExtra("meno"));
        System.out.println("priezvisko: " + intent.getStringExtra("priezvisko"));*/
    }

    private void update() {
        //go to AraayList zoznam, then then in hashmap find the right student by meno priezvisko check the predmet and then update the value
        MainActivity.zoznam.forEach(e -> {
            System.out.println(MainActivity.zoznam);
            if(e.get("Meno").equals(intent.getStringExtra("meno")) && e.get("Priezvisko").equals(intent.getStringExtra("priezvisko"))){
                e.forEach((k, v) -> {
                    if(k.equals(intent.getStringExtra("nazov"))){
                        System.out.println("k: " + k);
                        e.put(k, novaZnamka.getText().toString());
                    }
                });
            }
        });
        System.out.println(MainActivity.zoznam);
        setResult(RESULT_OK);
        finish();


    }

    /*protected void onResumw(){
        super.onResume();
        System.out.println("onResume");
    }*/

    private void pridajZnamku() {
        //go to AraayList zoznam, then then in hashmap find the right student by meno priezvisko check the predmet and then update the value
        MainActivity.zoznam.forEach(e -> {
            System.out.println(MainActivity.zoznam);
            if(e.get("Meno").equals(intent.getStringExtra("meno")) && e.get("Priezvisko").equals(intent.getStringExtra("priezvisko"))){
                e.forEach((k, v) -> {
                    if(k.equals(intent.getStringExtra("nazov"))){
                        System.out.println("k: " + k);
                        ///e.put(k, staraZnamka.getText().toString() + "," + novaZnamka.getText().toString());
                        //put short if if staraZnamka is "Zatedz ništ" alebo "Ešte nič" then put only novaZnamka else put staraZnamka + novaZnamka
                        e.put(k, staraZnamka.getText().toString().equals("Zatedy ništ") || staraZnamka.getText().toString().equals("Ešte nič") ? novaZnamka.getText().toString() : staraZnamka.getText().toString() + "," + novaZnamka.getText().toString());
                    }
                });
            }
        });
        System.out.println(MainActivity.zoznam);
        setResult(RESULT_OK);
        finish();
    }

}