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

public class EditPredmet extends AppCompatActivity {

    private EditText nazov;
    private Button naspak;
    private Button uprav;
    private Intent intent;
    private Databaza databaza = new Databaza(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_predmet);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        intent = getIntent();

        nazov = findViewById(R.id.EditPredmet_Meno);
        System.out.println(intent.getLongExtra("idPredmet", 0));

        nazov.setText(databaza.getPredmetName(intent.getLongExtra("idPredmet", 0)));
        naspak = findViewById(R.id.EditPredmet_Koniec);
        naspak.setOnClickListener(e -> finish());
        uprav = findViewById(R.id.EditPredmet_Edit);
        uprav.setOnClickListener(e -> upravPredmet());

    }



    private void upravPredmet() {
    Predmet predmet = new Predmet(intent.getLongExtra("idPredmet", 0), nazov.getText().toString());
    databaza.updatePredmet(predmet);
        finish();
    }

}