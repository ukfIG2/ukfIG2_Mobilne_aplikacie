package com.example.piskvorky;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.piskvorky.R;
import com.example.piskvorky.VysledokActivity;

public class HraActivity extends AppCompatActivity {

    private Button[][] buttons = new Button[3][3];

    private int[][] winButton = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, //rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, //columns
            {0, 4, 8}, {2, 4, 6} //diagonals
    };

    private boolean PX = true;

    private int roundCount;

    private int klikCount;

    private int playerXPoints;
    private int playerOPoints;

    private int remiza;

    private TextView textViewSkore;

    private TextView textViewTurn;

    private TextView textViewVysledok;
    String menoX;
    String menoO;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hra);

        Intent intent = getIntent();
        menoX = intent.getStringExtra("x");
        menoO = intent.getStringExtra("o");

        textViewSkore = findViewById(R.id.TextSkore);

        textViewTurn = findViewById(R.id.TextCurrentPlayer);

        textViewVysledok = findViewById(R.id.tv10);

        roundCount = 0;
        klikCount = 0;
        playerOPoints = 0;
        playerXPoints = 0;
        remiza = 0;

        textViewSkore.setText("Skore je \nX: " + playerXPoints + " \t\t O: " + playerOPoints);
        textViewSkore.setText("Skore je \n" + menoX + ": " + playerXPoints + " \t\t " + menoO + "  : " + playerOPoints);
        textViewTurn.setText("Na rade je X");

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "B" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this::onClick);

            }
        }
    }

    private void onClick(View v) {
        System.out.println("CLICK" + v.getId());
        if (!((Button) v).getText().toString().equals("")) {
            return;
        } else if (PX) {
            ((Button) v).setText("X");
            PX = !PX;
            changePlayerText();
        } else {
            ((Button) v).setText("O");
            PX = !PX;
            changePlayerText();
        }
        checkWinner();

        if(klikCount == 8){
            //automaticli pres the last button
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if(buttons[i][j].getText().toString().equals("")){
                        buttons[i][j].performClick();
                        return;
                    }
                }
            }
            remiza++;
            Intent intent2 = new Intent(this, VysledokActivity.class);
            intent2.putExtra("winner", "Remiza");
            startActivity(intent2);
            resetGame();
        }
        klikCount++;

    }

    private void changePlayerText() {
        if (PX) {
            textViewTurn.setText("Na rade je " + menoX);
        } else {
            textViewTurn.setText("Na rade je " + menoO);
        }
    }

    private String getTextFromButton(int i, int j) {
        return buttons[i][j].getText().toString();
    }

    private void checkWinner() {
        for (int i = 0; i < 8; i++) {
            String a = getTextFromButton(winButton[i][0] / 3, winButton[i][0] % 3);
            String b = getTextFromButton(winButton[i][1] / 3, winButton[i][1] % 3);
            String c = getTextFromButton(winButton[i][2] / 3, winButton[i][2] % 3);
            if (!a.equals("") && a.equals(b) && a.equals(c)) {
                if (a.equals("X")) {
                    playerXPoints++;
                    Intent intent2 = new Intent(this, VysledokActivity.class);
                    intent2.putExtra("winner", menoX);
                    startActivity(intent2);
                } else if(a.equals("O")) {
                    playerOPoints++;
                    Intent intent2 = new Intent(this, VysledokActivity.class);
                    intent2.putExtra("winner", menoO);
                    startActivity(intent2);
                }
                textViewSkore.setText("Skore je \n" + menoX + ": " + playerXPoints + " \t\t " + menoO + "  : " + playerOPoints);
                resetGame();
            }
        }
    }

    private void resetGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        roundCount++;
        PX = true;
        klikCount = 0;
        changePlayerText();
        //Na debugovacie účely
        textViewVysledok.setText("Kôl bolo " + roundCount + ", remiz bolo " + remiza);

    }
}