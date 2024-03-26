package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class TicTacToe extends AppCompatActivity {
    String symbol;
    String[][] matica = new String[3][3];
    Button[] buttons = new Button[9];
    boolean next = true;
    TextView player_text;

    String meno1;
    String meno2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tic_tac_toe);

        pole();
        // texty -------------------
        player_text = findViewById(R.id.player_name);
        meno1 = getIntent().getStringExtra("hrac1");
        meno2 = getIntent().getStringExtra("hrac2");
        player_text.setText("Na ťahu je " + meno1);


        // buttony ----------------
        buttons[0] = findViewById(R.id.b1);
        buttons[1] = findViewById(R.id.b2);
        buttons[2] = findViewById(R.id.b3);
        buttons[3] = findViewById(R.id.b4);
        buttons[4] = findViewById(R.id.b5);
        buttons[5] = findViewById(R.id.b6);
        buttons[6] = findViewById(R.id.b7);
        buttons[7] = findViewById(R.id.b8);
        buttons[8] = findViewById(R.id.b9);


        for (Button button : buttons) {
            button.setOnClickListener(v -> onButtonClick((Button) v));
        }

    }
    public void pole(){
        for (int i = 0; i<3; i++){
            for (int j = 0; j<3; j++){
                matica[i][j] = " ";
            }
        }
    }
    private int skontroluj(){
        boolean remiza = true;
        if ((matica[0][0] != " " && matica[0][0] == matica[0][1] && matica[0][1] == matica[0][2])
            ||(matica[1][0] != " " && matica[1][0] == matica[1][1] && matica[1][1] == matica[1][2])
            ||(matica[2][0] != " " && matica[2][0] == matica[2][1] && matica[2][1] == matica[2][2])
            ||(matica[1][1] != " " && matica[1][1] == matica[0][0] && matica[1][1] == matica[2][2])
            ||(matica[0][0] != " " && matica[0][0] == matica[1][0] && matica[1][0] == matica[2][0])
            ||(matica[0][1] != " " && matica[0][1] == matica[1][1] && matica[1][1] == matica[2][1])
            ||(matica[0][2] != " " && matica[0][2] == matica[1][2] && matica[1][2] == matica[2][2])
            ||(matica[0][2] != " " && matica[0][2] == matica[1][1] && matica[1][1] == matica[2][0]))
        {return 0;}
        for (int i = 0; i<3; i++){
            for (int j = 0; j<3; j++){
                if (matica[i][j] == " ") { remiza = false;
                }
            }
        }
        if (remiza){return 1;}
        return 2;
    }
    private void onButtonClick(Button clickedButton) {
        next = !next;
        if (next){
            player_text.setText("Na ťahu je " + meno1);}
        else player_text.setText("Na ťahu je: " + meno2);


        if (!clickedButton.getText().toString().isEmpty()) {
            return;
        }
        if (!next){
            symbol = "X";
            clickedButton.setText(symbol);
        } else
        {   symbol = "O";
            clickedButton.setText(symbol);}

        int index = Integer.parseInt(clickedButton.getTag().toString()) - 1; 
        int row = index / 3;
        int col = index % 3;
        matica[row][col] = symbol;

        int vys = skontroluj();
        if (vys == 0){
            Intent intent = new Intent(TicTacToe.this, End.class);
            if (next){intent.putExtra("winner", meno2 + " je víťaz");}
            if (!next){intent.putExtra("winner", meno1 + " je víťaz");}
            startActivity(intent);
        } if (vys == 1){
            Intent intent = new Intent(TicTacToe.this, End.class);
            intent.putExtra("winner", "remíza");
            startActivity(intent);
        }
    }
}

