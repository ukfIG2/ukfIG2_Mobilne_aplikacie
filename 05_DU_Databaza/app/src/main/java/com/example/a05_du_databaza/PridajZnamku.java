package com.example.a05_du_databaza;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PridajZnamku extends AppCompatActivity {

    private TextView meno;
    private TextView predmet;
    private EditText znamka;
    private Button pridaj;
    private Button spat;
    private Intent intent;
    private long idStudent;
    private long idPredmet;

    Databaza dbh = new Databaza(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pridaj_znamku);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        meno = findViewById(R.id.pridajZnamku_meno);
        predmet = findViewById(R.id.pridajZnamku_Predmet);
        znamka = findViewById(R.id.PridajZnamku_znamka);
        pridaj = findViewById(R.id.PridajZnamku_insert);
        pridaj.setOnClickListener(e -> Pridaj());
        spat = findViewById(R.id.pridakZnmaku_koniec);
        spat.setOnClickListener(e -> finish());

        intent = getIntent();
        idStudent = intent.getLongExtra("idStudent", -1);
        idPredmet = intent.getLongExtra("idPredmet", -1);

        /*Bundle extras = getIntent().getExtras();
        if (extras != null) {
            for (String key : extras.keySet()) {
                System.out.println(key + ": " + extras.get(key));
            }
        }*/

        updateViews();

    }

    private void Pridaj() {
        Znamka znamka = new Znamka(idPredmet, idStudent, Integer.parseInt(this.znamka.getText().toString()));
        dbh.addZnamka(znamka);
        finish();
        System.out.println("Pridaj");
    }

    private void updateViews() {
        meno.setText(dbh.getStudentName(idStudent) + " " + dbh.getStudentSurname(idStudent));
        predmet.setText(dbh.getPredmet(idPredmet).getName());
    }

}