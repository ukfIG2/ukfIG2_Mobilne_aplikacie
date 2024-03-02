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

    private boolean PX = true;

    private int roundCount = 0;

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

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
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

    private void onClick(View v){
        System.out.println("CLICK" + v.getId());
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
        else if(PX){
            ((Button) v).setText("X");
            PX = !PX;
        }
        else{
            ((Button) v).setText("O");
            PX = !PX;
        }

        roundCount++;

        System.out.println("ROUND COUNT " + roundCount);
    }
}