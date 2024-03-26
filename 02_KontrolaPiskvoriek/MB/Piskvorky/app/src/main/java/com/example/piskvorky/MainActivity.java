package com.example.piskvorky;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button submitButton = findViewById(R.id.submitButton);
        EditText player1EditText = findViewById(R.id.player1EditText);
        EditText player2EditText = findViewById(R.id.player2EditText);

        submitButton.setOnClickListener(v -> {
            String player1Name = player1EditText.getText().toString();
            String player2Name = player2EditText.getText().toString();

//          v pripade nezadania udajov automaticke nastavenie mena
            if (player1Name.trim().isEmpty()) {
                player1Name = "Hráč 1";
            }

            if (player2Name.trim().isEmpty()) {
                player2Name = "Hráč 2";
            }

            startGame(player1Name, player2Name);
        });
    }

    private void startGame(String player1, String player2) {
        Intent intent = new Intent(MainActivity.this, Game.class);
        intent.putExtra("player1", player1);
        intent.putExtra("player2", player2);
        finish();
        startActivity(intent);
    }
}
