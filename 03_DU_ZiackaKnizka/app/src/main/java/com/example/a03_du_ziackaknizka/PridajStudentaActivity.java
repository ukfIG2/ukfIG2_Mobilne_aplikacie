package com.example.a03_du_ziackaknizka;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashMap;

public class PridajStudentaActivity extends AppCompatActivity {

    private Button naspak;
    private Button pridaj;
    private EditText meno;
    private EditText priezvisko;
    private EditText matematika;
    private EditText slovenskyJazyk;
    private EditText fyzika;
    private EditText informatika;

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

        naspak = findViewById(R.id.PridajNaspak);
        naspak.setOnClickListener(e -> finish());

        pridaj = findViewById(R.id.PridajUpdate);
        pridaj.setOnClickListener(e -> pridajStudenta());

        meno = findViewById(R.id.PridajMeno);
        priezvisko = findViewById(R.id.PridajPriezvisko);
        matematika = findViewById(R.id.PridajMatematika);
        slovenskyJazyk = findViewById(R.id.PridajSJ);
        fyzika = findViewById(R.id.PridajFyzika);
        informatika = findViewById(R.id.PridajInformatika);
    }

    public static String skontroluZnamku(String znamka){
        if(Integer.parseInt(znamka) < 1 ) {return "1";}
        else if(Integer.parseInt(znamka) > 5) {return "5";}
        else {return znamka;}
    }

    private void pridajStudenta() {
        HashMap<String, String> riadok = new HashMap<String, String>();
        riadok.put("Meno", meno.getText().toString());
        riadok.put("Priezvisko", priezvisko.getText().toString());
        riadok.put("Matematika", matematika.getText().toString().isEmpty() ? "Ešte nič" : skontroluZnamku(matematika.getText().toString()));
        riadok.put("Slovenský jazyk", slovenskyJazyk.getText().toString().isEmpty() ? "Ešte nič" : skontroluZnamku(slovenskyJazyk.getText().toString()));
        riadok.put("Fyzika", fyzika.getText().toString().isEmpty() ? "Ešte nič" : skontroluZnamku(fyzika.getText().toString()));
        riadok.put("Informatika", informatika.getText().toString().isEmpty() ? "Ešte nič" : skontroluZnamku(informatika.getText().toString()));

        MainActivity.zoznam.add(riadok);
        finish();
        //System.out.println(MainActivity.zoznam);

    }
}