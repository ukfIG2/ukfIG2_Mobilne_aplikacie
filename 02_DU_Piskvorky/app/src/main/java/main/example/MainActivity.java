package main.example;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //Lets make tic tac toe game
    //There will be two players X and O
    //The 3x3 grid is already created in the activity_main.xml file

    private Button[][] buttons = new Button[3][3];

    //Lets make wiinButton array to store the winning combinations and set which ones there are
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

    private TextView textViewSkore;

    private TextView textViewTurn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewSkore = findViewById(R.id.TextSkore);

        textViewTurn = findViewById(R.id.TextCurrentPlayer);

        roundCount = 0;
        klikCount = 0;
        playerOPoints = 0;
        playerXPoints = 0;

        //Lets set it to show X = playerXPoints and O = playerOPoints
        textViewSkore.setText("Skore je \nX: " + playerXPoints + " \t\t O: " + playerOPoints);
        textViewTurn.setText("Na rade je X");

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "B" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                //System.out.println("BUTTOM " + buttons[i][j]);
                //System.out.println("BUTTOM " + buttons[i][j].getId());
                //System.out.println(buttonID);
                buttons[i][j].setOnClickListener(this::onClick);

            }
        }
        //https://gist.github.com/codinginflow/5b37262635152fd91af5df92490624ce
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
            resetGame();
        }


        klikCount++;

        System.out.println("ROUND COUNT " + roundCount);    //DEBUG
    }

    private void changePlayerText() {
        if (PX) {
            textViewTurn.setText("Na rade je X");
        } else {
            textViewTurn.setText("Na rade je O");
        }
    }

    //Lets make a function to get text from buttom
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
                } else {
                    playerOPoints++;
                }
                textViewSkore.setText("X: " + playerXPoints + " O: " + playerOPoints);
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
    }


}
