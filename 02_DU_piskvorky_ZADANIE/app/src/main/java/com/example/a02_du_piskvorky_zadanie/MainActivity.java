package com.example.a02_du_piskvorky_zadanie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText e1;
    EditText e2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.BT2);
        button.setOnClickListener(e -> click());
        e1 = findViewById(R.id.pt1);
        e2 = findViewById(R.id.pt2);
    }

    private void click() {
        System.out.println("Button clicked");
        Intent intent = new Intent(this, HraActivity.class);
        intent.putExtra("x", e1.getText().toString());
        intent.putExtra("o", e2.getText().toString());
        startActivity(intent);
    }
}