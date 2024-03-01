package Main.example;

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
        Button b1 = findViewById(R.id.Button01);
        b1.setOnClickListener(ect -> naKlik());
    }

    public void naKlik(){
        EditText e1 = findViewById(R.id.Text01);
        EditText e2 = findViewById(R.id.Text02);
        //Odkial sa kam posiela intent
        Intent intent = new Intent(MainActivity.this, SpocitajActivity.class);
        intent.putExtra("a", e1.getText().toString());
        intent.putExtra("b", e2.getText().toString());
        startActivity(intent);


    }

}