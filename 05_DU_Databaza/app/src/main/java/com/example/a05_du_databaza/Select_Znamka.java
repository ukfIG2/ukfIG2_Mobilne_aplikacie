package com.example.a05_du_databaza;

import android.annotation.SuppressLint;
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

public class Select_Znamka extends AppCompatActivity {

    private TextView name;
    private TextView predmet;
    private TextView staraZnamka;
    private EditText novaZnamka;
    private Button update;
    private Button naspat;
    private Button vymazat;
    private long idZnamky;

    Databaza dbh = new Databaza(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_select_znamka);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        name = findViewById(R.id.tv_Select_znamka_Name);
        predmet = findViewById(R.id.tv_Select_znamka_Predmet);
        staraZnamka = findViewById(R.id.tv_Select_Znamka_StZnamka);
        novaZnamka = findViewById(R.id.tv_Select_znamka_nvZnamka);
        update = findViewById(R.id.bt_Selebt_znamka_Aktualizuj);
        update.setOnClickListener(e -> update());
        naspat = findViewById(R.id.bt_Select_znamka_Finish);
        naspat.setOnClickListener(e -> finish());
        vymazat = findViewById(R.id.bt_Select_znamka_delete);
        vymazat.setOnClickListener(e -> delete());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            for (String key : extras.keySet()) {
                System.out.println(key + ": " + extras.get(key));
            }
        }

        idZnamky = getIntent().getLongExtra("idZnamky", -1);
        updateViews();

    }

    private void update() {
        String znamka = novaZnamka.getText().toString();
        if (znamka.isEmpty()) {
            return;
        }
        dbh.updateZnamka(idZnamky, znamka);
        updateViews();
        finish();
    }


    private void delete() {
        dbh.deleteZnamka(idZnamky);
        finish();
    }

    public void updateViews() {

        Cursor cursor = dbh.getSelectZnamka(idZnamky);
        if (cursor == null) {
            System.out.println("Cursor is null");
            return;
        }
        if (cursor.getCount() == 0) {
            System.out.println("Cursor is empty");
            cursor.close();
            return;
        }
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") int predmet = cursor.getInt(cursor.getColumnIndex(Znamka.COLUMN_PREDMET));
            @SuppressLint("Range") int student = cursor.getInt(cursor.getColumnIndex(Znamka.COLUMN_STUDENT));
            @SuppressLint("Range") int znamka = cursor.getInt(cursor.getColumnIndex(Znamka.COLUMN_ZNAMKA));

            // Assuming you have methods to get student name and surname, and predmet name
            String studentName = dbh.getStudentName(student);
            String studentSurname = dbh.getStudentSurname(student);
            String predmetName = dbh.getPredmetName(predmet);

            name.setText(studentName + " " + studentSurname);
            this.predmet.setText(predmetName);
            staraZnamka.setText(String.valueOf(znamka));
        }
        cursor.close();
    }





}