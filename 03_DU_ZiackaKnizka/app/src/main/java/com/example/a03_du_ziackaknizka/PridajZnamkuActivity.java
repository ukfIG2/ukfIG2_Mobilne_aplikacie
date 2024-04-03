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

import java.util.Objects;

public class PridajZnamkuActivity extends AppCompatActivity {
    private TextView meno;
    private TextView predmet;
    private EditText znamka;
    private Button pridaj;
    private Button spat;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_activity2_pridaj_znamku);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        intent = getIntent();

        meno = findViewById(R.id.NZ_meno);
        predmet = findViewById(R.id.NZ_predmet);
        znamka = findViewById(R.id.NZ_NZ);
        pridaj = findViewById(R.id.NZ_priadj);
        spat = findViewById(R.id.NZ_naspak);

        //System.out.println(intent.getStringExtra("meno"));
        //System.out.println(intent.getStringExtra("nazov"));
        //System.out.println(intent.getStringExtra("priezvisko"));

        spat.setOnClickListener(e -> finish());

        pridaj.setOnClickListener(e -> Pridaj());

        meno.setText(intent.getStringExtra("meno") + " " + intent.getStringExtra("priezvisko"));
        predmet.setText(intent.getStringExtra("nazov"));

    }

  private void Pridaj() {
      //System.out.println("Pridaj");
      String meno = intent.getStringExtra("meno");
      String priezvisko = intent.getStringExtra("priezvisko");
      String predmet = intent.getStringExtra("nazov");
      String znamka = PridajStudentaActivity.skontroluZnamku(this.znamka.getText().toString());
      //System.out.println(znamka);

      MainActivity.zoznam.forEach(e -> {
          //System.out.println(MainActivity.zoznam);
          if(Objects.equals(e.get("Meno"), intent.getStringExtra("meno")) && Objects.equals(e.get("Priezvisko"), intent.getStringExtra("priezvisko"))){
              e.forEach((k, v) -> {
                  if(k.equals(intent.getStringExtra("nazov"))){
                      //System.out.println("k: " + k);
                      //System.out.println("v: " + v);
                      String existingGrades = e.get(k);
                      String updatedGrades = existingGrades + "," + znamka;
                      updatedGrades = updatedGrades.replaceFirst("^,|Ešte nič,", ""); // remove leading comma or "Ešte nič"
                      e.put(k, updatedGrades);
                  }
              });
          }
      });
      finish();
  }

}