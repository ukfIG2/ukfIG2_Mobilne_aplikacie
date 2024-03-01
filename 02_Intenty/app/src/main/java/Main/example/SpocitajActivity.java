package Main.example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class SpocitajActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spocitaj);
        Intent myIntent = getIntent();
        String c1 = myIntent.getStringExtra("a");
        String c2 = myIntent.getStringExtra("b");
        int vysledok = Integer.parseInt(c1) + Integer.parseInt(c2);
        TextView tv = findViewById(R.id.Text03);
        tv.setText(c1+" + "+c2+" = "+ vysledok);

        Button b2 = findViewById(R.id.Button02);
        b2.setOnClickListener(evt -> finish());
    }
}