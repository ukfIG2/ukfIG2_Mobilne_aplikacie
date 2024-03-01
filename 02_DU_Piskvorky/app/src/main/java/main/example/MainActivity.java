package main.example;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //Lets make tic tac toe game
    //We will use 3x3 grid
    //We will use 2 players
    //We will use 2 symbols X and O
    //We will use 9 buttons
    //We will use 1 reset button
    //We will use 1 textview to display the result
    //We will use 1 textview to display the current player
    //We will use 1 textview to display the current player symbol

    //We will use 1 array to store the current state of the game
    //We will use 1 array to store the winning combinations

    //We will use 1 function to check the winning combinations
    //We will use 1 function to check the draw

    //We will use 1 function to reset the game


    //Lets start coding
    //First we will create the layout, the layout is alredy set in xml file
    //We will create the buttons, textviews and set the id's, done
    //We will create the winning combinations arrays example of button id is B00 and B33 B stands for button first number for row and second number for column
    String [] winningCombination = {"B00B01B02","B10B11B12","B20B21B22","B00B10B20","B01B11B21","B02B12B22","B00B11B22","B02B11B20"};
    //We will create the current state of the game array
    String [] gameState = {"","","","","","","","","",""};
    //We will create the current player
    String currentPlayer = "X";
    //We will create the current player symbol
    String currentPlayerSymbol = "X";
    //We will create the textview to display the result
    TextView resultTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}