package com.example.piskvorky;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Game extends AppCompatActivity {

    private int currentPlayer = 1;
    private boolean game_end = false;
    TextView player;
    String player1;
    String player2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        boolean resetGame = getIntent().getBooleanExtra("resetGame", false);

//        kontrola, či hra nebola znovu spustená
        if (resetGame) {
            game_end = false;
        }

//        inicializacia hracov
        Intent intent = getIntent();
        player = findViewById(R.id.currentPlayerTextView);
        player1 = intent.getStringExtra("player1");
        player2 = intent.getStringExtra("player2");
        player.setText(player1);

        player.setText(getIntent().getStringExtra("player1"));

        Button b1 = findViewById(R.id.button1);
        b1.setOnClickListener(e -> buttonClick(b1));

        Button b2 = findViewById(R.id.button2);
        b2.setOnClickListener(e -> buttonClick(b2));

        Button b3 = findViewById(R.id.button3);
        b3.setOnClickListener(e -> buttonClick(b3));

        Button b4 = findViewById(R.id.button4);
        b4.setOnClickListener(e -> buttonClick(b4));

        Button b5 = findViewById(R.id.button5);
        b5.setOnClickListener(e -> buttonClick(b5));

        Button b6 = findViewById(R.id.button6);
        b6.setOnClickListener(e -> buttonClick(b6));

        Button b7 = findViewById(R.id.button7);
        b7.setOnClickListener(e -> buttonClick(b7));

        Button b8 = findViewById(R.id.button8);
        b8.setOnClickListener(e -> buttonClick(b8));

        Button b9 = findViewById(R.id.button9);
        b9.setOnClickListener(e -> buttonClick(b9));

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(e -> backToMain());
    }

//    funkcia na spustenie novej hry
    private void backToMain(){
        Intent intent = new Intent(Game.this, MainActivity.class);
        finish();
        startActivity(intent);
    }

//    funkcia na kontrolu procesu hry
    private void buttonClick(Button button) {

        if (!game_end && button.getText().toString().equals("")) {
            if (currentPlayer == 1) {
                button.setText("X");
            } else {
                button.setText("O");
            }
            checkWinner();
            currentPlayer = (currentPlayer % 2) + 1;
        }

        if(currentPlayer == 1){
            player.setText(player1);
        }else{
            player.setText(player2);
        }
    }

//    funkcia ktorá kontroluje, či niekto nevyhral
    private void checkWinner() {
        Button[][] buttons = new Button[3][3];

        buttons[0][0] = findViewById(R.id.button1);
        buttons[0][1] = findViewById(R.id.button2);
        buttons[0][2] = findViewById(R.id.button3);
        buttons[1][0] = findViewById(R.id.button4);
        buttons[1][1] = findViewById(R.id.button5);
        buttons[1][2] = findViewById(R.id.button6);
        buttons[2][0] = findViewById(R.id.button7);
        buttons[2][1] = findViewById(R.id.button8);
        buttons[2][2] = findViewById(R.id.button9);

        boolean allButtonsFilled = true;


        for (int i = 0; i < 3; i++) {
            if (checkRow(buttons[i][0], buttons[i][1], buttons[i][2])) {
                announceWinner(currentPlayer);
                return;
            }
            if (checkColumn(buttons[0][i], buttons[1][i], buttons[2][i])) {
                announceWinner(currentPlayer);
                return;
            }
        }

        if (checkDiagonal(buttons[0][0], buttons[1][1], buttons[2][2]) || checkDiagonal(buttons[0][2], buttons[1][1], buttons[2][0])) {
            announceWinner(currentPlayer);
        }

//        kontrola remizi
        for (Button[] button : buttons) {
            for (Button value : button) {
                if (value.getText().toString().equals("")) {
                    allButtonsFilled = false;
                }
            }
            if (!allButtonsFilled) {
                break;
            }
        }

        if(allButtonsFilled){
            Intent intent = new Intent(Game.this, Vyherca.class);
            intent.putExtra("tie", true);
            finish();
            startActivity(intent);
        }

    }

//    funkcie na proces kontroli vyhercu
    private boolean checkRow(Button b1, Button b2, Button b3) {
        return b1.getText().toString().equals(b2.getText().toString()) &&
                b2.getText().toString().equals(b3.getText().toString()) &&
                !b1.getText().toString().equals("");
    }

    private boolean checkColumn(Button b1, Button b2, Button b3) {
        return b1.getText().toString().equals(b2.getText().toString()) &&
                b2.getText().toString().equals(b3.getText().toString()) &&
                !b1.getText().toString().equals("");
    }

    private boolean checkDiagonal(Button b1, Button b2, Button b3) {
        return b1.getText().toString().equals(b2.getText().toString()) &&
                b2.getText().toString().equals(b3.getText().toString()) &&
                !b1.getText().toString().equals("");
    }

//    funkcia, ktora sa vykona keď niekto vyhrá
    private void announceWinner(int winner) {

        game_end = true;
        Intent intent = new Intent(Game.this, Vyherca.class);

        if(winner == 1) intent.putExtra("winner", player1);
        else intent.putExtra("winner", player2);

        intent.putExtra("player1", player1);
        intent.putExtra("player2", player2);
        finish();
        startActivity(intent);
    }




}