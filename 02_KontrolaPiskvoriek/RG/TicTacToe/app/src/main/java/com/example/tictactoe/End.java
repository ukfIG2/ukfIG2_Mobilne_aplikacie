package com.example.tictactoe;

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

public class End extends AppCompatActivity {
    TextView vitaz;
    String meno;
    Button restart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_end);
        vitaz = findViewById(R.id.winner);
        meno = getIntent().getStringExtra("winner");
        vitaz.setText(meno);

        restart = findViewById(R.id.restart);
        restart.setOnClickListener(v -> {
            Intent intent = new Intent(End.this, MainActivity.class);
            startActivity(intent);
        });
    }
}