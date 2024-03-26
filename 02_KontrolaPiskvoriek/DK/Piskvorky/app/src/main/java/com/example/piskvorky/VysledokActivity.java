package com.example.piskvorky;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.piskvorky.R;

public class VysledokActivity extends AppCompatActivity {
    private TextView textViewVysledok;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vysledok);
        textViewVysledok = findViewById(R.id.text5);
        textViewVysledok.setText("Vyhral hrac: " + getIntent().getStringExtra("winner"));
        button = findViewById(R.id.Button5);
        button.setOnClickListener(e -> reset());
    }

    private void reset() {
        finish();
    }

}