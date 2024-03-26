package com.example.piskvorky20243x3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    
    private final Button[][] buttons = new Button[3][3]; // 4x4 visual game board (buttons)
    private final String[][] fields = new String[3][3]; // 4x4 internal game field (X, O, or empty string)
    private int movesCount = 0; // Count of totally made moves
    private boolean lock = false; // Lock the game board after the game ends (until the game is reset)

    /**
     * Called when the activity is first created.
     * @param savedInstanceState Used for restoring the activity state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button buttonChangeName1 = findViewById(R.id.buttonChangePlayer1Name);
        buttonChangeName1.setOnClickListener(view -> changePlayer1Name());
        Button buttonChangeName2 = findViewById(R.id.buttonChangePlayer2Name);
        buttonChangeName2.setOnClickListener(view -> changePlayer2Name());

        // Initialize the game board
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String resID = "button_" + count; // Make the resource ID by name and index (button_0, button_1, ...)
                count++;
                int id = getResources().getIdentifier(resID, "id", getPackageName()); // Get the resource ID by name
                fields[i][j] = ""; // Set the internal game field cell to an empty string
                buttons[i][j] = findViewById(id); // Get the button by resource ID
                buttons[i][j].setOnClickListener(this); // Set the click listener for the button
            }
        }

        // Set the click listener for the reset button
        Button resetButton = findViewById(R.id.button_reset);
        resetButton.setOnClickListener(event->clickResetButtonHandler());

    }

    private void changePlayer1Name() {
        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        String player1Name = ((TextView)findViewById(R.id.player1NameTextView)).getText().toString();
        intent.putExtra("name", player1Name);
        intent.putExtra("player", 1);
        startActivityForResult(intent, 1);
    }

    private void changePlayer2Name() {
        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        String player2Name = ((TextView)findViewById(R.id.player2NameTextView)).getText().toString();
        intent.putExtra("name", player2Name);
        intent.putExtra("player", 2);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String changedName = data.getStringExtra("name");
                    int player = data.getIntExtra("player", 1);
                    if (player == 1)
                    ((TextView) findViewById(R.id.player1NameTextView)).setText(changedName);
                    else if (player == 2)
                    ((TextView) findViewById(R.id.player2NameTextView)).setText(changedName);
                }
        }
    }

    /**
     * Reset button click handler
     */
    private void clickResetButtonHandler() {
        resetGame();
    }

    /**
     * Player turn
     * @param v clicked button
     */
    @Override
    public void onClick(View v) {
        // If the button is already used or the game is locked (win or draw), do nothing
        if (!((Button) v).getText().toString().equals("") || lock) {
            return;
        }

        // Reflect the player's move on the game board
        // Set the button text to "X"
        ((Button) v).setText("X");
        // Get the button position (row and column) from the tag and set the internal game field cell to "X"
        String tag = v.getTag().toString();
        String[] pos = tag.split(",");
        int r = Integer.parseInt(pos[0]);
        int c = Integer.parseInt(pos[1]);
        fields[r][c] = "X";
        // Increase the count of totally made moves
        movesCount++;

        // Check if the game is over (win or draw) and if not, make the AI turn
        int result = checkForWin();
        if (result == 1) { // Player wins
            playerWins();
        } else if (result == 2) { // AI wins
            AIWins();
        } else if (movesCount > 8) { // Draw is reached
            draw();
        } else { // If the game is not over
            AIMove(); // AI turn
        }
    }

    /**
     * AI turn
     */
    private void AIMove() {
        Random random = new Random();
        int bestScore = 0;
        int bestRow = -1;
        int bestCol = -1;

        // Probability of blocking the player's win. If the probability is less than 50, the computer will try to block the player's win.
        if (random.nextInt(100) < 50) {
            // Check if the player can win on the next move
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    if (buttons[r][c].getText().toString().equals("")) {
                        fields[r][c] = "X";
                        int result = checkForWin();
                        fields[r][c] = "";
                        if (result == 1) {
                            // If player can win on the next move, block his hypothetical next move
                            bestRow = r;
                            bestCol = c;
                            break;
                        }
                    }
                }
                if (bestRow != -1) {
                    break;
                }
            }
        }

        // If player can't win on the next move, find the best move for the AI
        if (bestRow == -1) {
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    if (buttons[r][c].getText().toString().equals("")) {
                        fields[r][c] = "O";
                        int score = evaluatePosition( "O"); // Evaluate the move quality for the AI
                        fields[r][c] = "";
                        if (score > bestScore) { // If the move quality is better than the best move quality so far, set the new best move
                            bestScore = score;
                            bestRow = r;
                            bestCol = c;
                        }
                    }
                }
            }
        }

        // If haven't found the best move for AI, make a random move
        if (bestRow == -1) {
            do {
                bestRow = random.nextInt(3);
                bestCol = random.nextInt(3);
            } while (!buttons[bestRow][bestCol].getText().toString().equals(""));
        }

        // Reflect the AI's move on the game board
        buttons[bestRow][bestCol].setText("O");
        fields[bestRow][bestCol] = "O";
        movesCount++;

        // Check if the game is over (win or draw)
        int win = checkForWin();
        if (win == 1) { // Player wins
            playerWins();
        } else if (win == 2) { // AI wins
            AIWins();
        } else if (movesCount > 8) { // Draw is reached
            draw();
        }
    }


    /**
     * Evaluate next move quality for the AI
     * @param mark "X" or "O"
     * @return
     */
    private int evaluatePosition(String mark) {
        int score = 0;

        // Find filled rows and columns
        for (int i = 0; i < 3; i++) {
            if ((fields[i][0].equals(mark) && fields[i][1].equals(mark) && fields[i][2].equals(mark)) ||
                    (fields[0][i].equals(mark) && fields[1][i].equals(mark) && fields[2][i].equals(mark))) {
                score += 100;
            }
        }

        // Find filled diagonals
        if ((fields[0][0].equals(mark) && fields[1][1].equals(mark) && fields[2][2].equals(mark)) ||
                (fields[0][2].equals(mark) && fields[1][1].equals(mark) && fields[2][0].equals(mark))) {
            score += 100;
        }

        return score;
    }

    /**
     * Check if the game is over
     * @return 1 if player wins, 2 if AI wins, 0 if the game is not over
     */
    private int checkForWin() {
        // Check rows for a win
        for (int i = 0; i < 3; i++) {
            if (fields[i][0].equals("X") && fields[i][1].equals("X") && fields[i][2].equals("X")) {
                return 1; // Player wins
            } else if (fields[i][0].equals("O") && fields[i][1].equals("O") && fields[i][2].equals("O")) {
                return 2; // AI wins
            }
        }

        // Check columns for a win
        for (int i = 0; i < 3; i++) {
            if (fields[0][i].equals("X") && fields[1][i].equals("X") && fields[2][i].equals("X")) {
                return 1; // Player wins
            } else if (fields[0][i].equals("O") && fields[1][i].equals("O") && fields[2][i].equals("O")) {
                return 2; // AI wins
            }
        }

        // Check main diagonal for a win
        if (fields[0][0].equals("X") && fields[1][1].equals("X") && fields[2][2].equals("X")) {
            return 1; // Player wins
        } else if (fields[0][0].equals("O") && fields[1][1].equals("O") && fields[2][2].equals("O")) {
            return 2; // AI wins
        }

        // Check secondary diagonal for a win
        else
        if (fields[0][2].equals("X") && fields[1][1].equals("X") && fields[2][0].equals("X")) {
            return 1; // Player wins
        } else if (fields[0][2].equals("O") && fields[1][1].equals("O") && fields[2][0].equals("O")) {
            return 2; // AI wins
        }

        // If the game is not over, return 0
        return 0;
    }

    /**
     * Show a message that the player has won
     */
    private void playerWins() {
        Toast.makeText(this, "Player wins!", Toast.LENGTH_SHORT).show();
        lock = true; // Lock the game board
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showGameEndResult(1);
            }
        }, 1000);
    }

    /**
     * Show a message that the AI has won
     */
    private void AIWins() {
        Toast.makeText(this, "AI wins!", Toast.LENGTH_SHORT).show();
        lock = true; // Lock the game board
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showGameEndResult(2);
            }
        }, 1000);
    }

    /**
     * Show a message that the game ended in a draw
     */
    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        lock = true; // Lock the game board
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showGameEndResult(0);
            }
        }, 1000);
    }

    /**
     * Reset the game board
     */
    private void resetBoard() {
        // Clear the game board labels and the internal game field
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                fields[i][j] = "";
            }
        }
        movesCount = 0; // Reset the count of totally made moves
    }

    /**
     * Reset the game
     */
    private void resetGame() {
        resetBoard(); // Clear the game board labels and the internal game field
        lock = false; // Unlock the game board
    }
    private void showGameEndResult(int result) {
        Intent intent = new Intent(MainActivity.this, MainActivity3.class);
        String player1Name = ((TextView)findViewById(R.id.player1NameTextView)).getText().toString();
        String player2Name = ((TextView)findViewById(R.id.player2NameTextView)).getText().toString();
        switch (result) {
            case 0:
                intent.putExtra("msg", "Draw!");
                break;
            case 1:
                intent.putExtra("msg", player1Name + " wins!");
                break;
            case 2:
                intent.putExtra("msg", player2Name + " wins!");
                break;
        }
        startActivityForResult(intent, 1);
    }

}