package com.example.skuska_2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class UpravKnihu extends AppCompatActivity {

    private EditText nazov;
    private EditText autor;
    private EditText rok;
    private Spinner zaner;
    private EditText obsah;
    private Databaza_lokalna dbh = new Databaza_lokalna(this);
    private Button uprav;
    private Button zatvorit;

    long id ;



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
        Intent intent = getIntent();

        id = intent.getLongExtra("id", 0);

        zaner = findViewById(R.id.edit_zaner);


        List<zaner> zanerList = dbh.getAllZaner();

        // Create an ArrayAdapter with the data
        ArrayAdapter<zaner> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, zanerList);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the ArrayAdapter to the spinner
        zaner.setAdapter(adapter);

        nazov = findViewById(R.id.editNazov);
        autor = findViewById(R.id.editAutor);
        rok = findViewById(R.id.edit_rok);
        obsah = findViewById(R.id.editObsah);
        uprav = findViewById(R.id.edit_edit);
        uprav.setOnClickListener(e -> {
            updateKniha();
            finish();
        });
        zatvorit = findViewById(R.id.editExit);
        zatvorit.setOnClickListener(e -> finish());

        kniha kniha = dbh.getKniha(id);

        if (kniha != null) {
            nazov.setText(kniha.getNazov());
            autor.setText(kniha.getAutor());
            rok.setText(String.valueOf(kniha.getRok()));
            obsah.setText(kniha.getObsah());

            // Get the selected zaner's id from the Spinner
            zaner selectedZaner = (zaner) zaner.getSelectedItem();
            long selectedZanerId = selectedZaner.getId();

            // Find the index of the selected zaner in the Spinner
            int spinnerPosition = 0;
            for (int i = 0; i < zaner.getCount(); i++) {
                zaner zanerItem = (zaner) zaner.getItemAtPosition(i);
                if (zanerItem.getId() == kniha.getZaner_id()) {
                    spinnerPosition = i;
                    break;
                }
            }

            // Set the selected item of the Spinner to the found index
            zaner.setSelection(spinnerPosition);


        }
    }

    private void updateKniha() {
        // Get the data from the fields
        String nazovText = nazov.getText().toString();
        String autorText = autor.getText().toString();
        int rokValue = Integer.parseInt(rok.getText().toString());
        String obsahText = obsah.getText().toString();
        zaner selectedZaner = (zaner) zaner.getSelectedItem();
        long selectedZanerId = selectedZaner.getId();

        // Create a new kniha object with the data
        kniha updatedKniha = new kniha(id, nazovText, autorText, rokValue, obsahText, selectedZanerId);

        // Update the kniha in the database
        dbh.updateKniha(updatedKniha);
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