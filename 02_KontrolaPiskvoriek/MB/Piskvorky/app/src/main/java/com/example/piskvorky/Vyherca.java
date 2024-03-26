package com.example.piskvorky;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Vyherca extends AppCompatActivity {

    String player1;
    String player2;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vyherca);

        Intent intent = getIntent();
        player1 = intent.getStringExtra("player1");
        player2 = intent.getStringExtra("player2");

        String winnerSymbol = intent.getStringExtra("winner");
        boolean tie = intent.getBooleanExtra("tie", false);

        if(tie){
            TextView textViewWinner = findViewById(R.id.textView);
            textViewWinner.setText("Remíza!");
        }else{
            TextView textViewWinner = findViewById(R.id.textView);
            textViewWinner.setText("Vyhral " + winnerSymbol + " !");
        }

        Button resetButton = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(e -> resetGame());

    }

//    funkcia ktora spusti novu hru (nove kolo)
    private void resetGame() {

        Intent intent = new Intent(this, Game.class);
        intent.putExtra("resetGame", true);
        intent.putExtra("player1", player1);
        intent.putExtra("player2", player2);
        finish();
        startActivity(intent);
    }
}