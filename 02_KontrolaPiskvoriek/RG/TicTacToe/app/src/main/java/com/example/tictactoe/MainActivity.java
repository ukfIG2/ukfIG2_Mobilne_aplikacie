package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText hrac1EditText;
    EditText hrac2EditText;
    Button hrajButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        hrac1EditText = findViewById(R.id.hrac1);
        hrac2EditText = findViewById(R.id.hrac2);
        hrajButton = findViewById(R.id.hraj);

        hrajButton.setOnClickListener(v -> {
            String hrac1 = hrac1EditText.getText().toString();
            String hrac2 = hrac2EditText.getText().toString();

            Intent intent = new Intent(MainActivity.this, TicTacToe.class);
            intent.putExtra("hrac1", hrac1);
            intent.putExtra("hrac2", hrac2);
            startActivity(intent);
        });
    }
}
