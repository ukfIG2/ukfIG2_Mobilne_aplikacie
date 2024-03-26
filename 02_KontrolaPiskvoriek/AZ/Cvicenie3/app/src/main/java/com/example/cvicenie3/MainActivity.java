package com.example.cvicenie3;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private int hrac = 1;
    private Button[] tlacidla = new Button[9];
    private TextView textHraca;
    private final String SYMBOL_HRACA1 = "X";
    private final String SYMBOL_HRACA2 = "O";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textHraca = findViewById(R.id.textHraca);
        GridLayout mriezka = findViewById(R.id.mriezka);

        for (int i = 0; i < 9; i++) {
            tlacidla[i] = new Button(this);
            tlacidla[i].setTextSize(24);
            GridLayout.LayoutParams parametre = new GridLayout.LayoutParams();
            parametre.width = GridLayout.LayoutParams.WRAP_CONTENT;
            parametre.height = GridLayout.LayoutParams.WRAP_CONTENT;
            parametre.rowSpec = GridLayout.spec(i / 3, 1f);
            parametre.columnSpec = GridLayout.spec(i % 3, 1f);
            tlacidla[i].setLayoutParams(parametre);
            tlacidla[i].setOnClickListener(this::klikNaTlacidlo);
            mriezka.addView(tlacidla[i], parametre);
        }

        Button tlacidloRestart = findViewById(R.id.tlacidloRestart);
        tlacidloRestart.setOnClickListener(v -> restartHry());
    }

    public void klikNaTlacidlo(View view) {
        Button stlaceneTlacidlo = (Button) view;
        if (!stlaceneTlacidlo.getText().toString().equals("")) {
            return;
        }

        if (hrac == 1) {
            stlaceneTlacidlo.setText(SYMBOL_HRACA1);
            if (kontrolaVyhry(SYMBOL_HRACA1)) {
                textHraca.setText("Hráč 1 vyhral! Reštartujte hru.");
                zakazTlacidla();
                return;
            }
            hrac = 2;
            textHraca.setText("Na rade je hráč 2.");
        } else {
            stlaceneTlacidlo.setText(SYMBOL_HRACA2);
            if (kontrolaVyhry(SYMBOL_HRACA2)) {
                textHraca.setText("Hráč 2 vyhral! Reštartujte hru.");
                zakazTlacidla();
                return;
            }
            hrac = 1;
            textHraca.setText("Na rade je hráč 1.");
        }

        if (jeMriezkaPlna()) {
            textHraca.setText("Je to remíza! Reštartujte hru.");
            zakazTlacidla();
        }
    }

    private boolean kontrolaVyhry(String symbol) {
        for (int i = 0; i < 3; i++) {
            if (symbol.equals(tlacidla[i * 3].getText().toString()) &&
                    symbol.equals(tlacidla[i * 3 + 1].getText().toString()) &&
                    symbol.equals(tlacidla[i * 3 + 2].getText().toString())) {
                return true;
            }

            if (symbol.equals(tlacidla[i].getText().toString()) &&
                    symbol.equals(tlacidla[i + 3].getText().toString()) &&
                    symbol.equals(tlacidla[i + 6].getText().toString())) {
                return true;
            }
        }

        if (symbol.equals(tlacidla[0].getText().toString()) &&
                symbol.equals(tlacidla[4].getText().toString()) &&
                symbol.equals(tlacidla[8].getText().toString())) {
            return true;
        }

        return symbol.equals(tlacidla[2].getText().toString()) &&
                symbol.equals(tlacidla[4].getText().toString()) &&
                symbol.equals(tlacidla[6].getText().toString());
    }

    private void zakazTlacidla() {
        for (Button tlacidlo : tlacidla) {
            tlacidlo.setEnabled(false);
        }
    }

    private boolean jeMriezkaPlna() {
        for (Button tlacidlo : tlacidla) {
            if (tlacidlo.getText().toString().equals("")) {
                return false;
            }
        }
        return true;
    }

    private void restartHry() {
        for (Button tlacidlo : tlacidla) {
            tlacidlo.setText("");
            tlacidlo.setEnabled(true);
        }
        hrac = 1;
        textHraca.setText("Na rade je hráč 1.");
    }
}