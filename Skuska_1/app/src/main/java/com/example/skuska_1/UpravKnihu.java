package com.example.skuska_1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UpravKnihu extends AppCompatActivity {

   private EditText nazov;
    private EditText autor;
    private EditText rok;
    private Spinner zaner;
    private EditText obsah;
    private Databaza_lokalna dbh = new Databaza_lokalna(this);
    private Button uprav;
    private Button zatvorit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_uprav_knihu);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nazov = findViewById(R.id.editNazov);



        }

    private void printAllIntentExtras() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                for (String key : extras.keySet()) {
                    System.out.println("Key: " + key + ", Value: " + extras.get(key));
                }
            }
        }
    }

}