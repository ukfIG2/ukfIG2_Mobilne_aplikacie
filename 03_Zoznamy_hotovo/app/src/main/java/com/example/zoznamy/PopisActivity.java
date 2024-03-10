package com.example.zoznamy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class PopisActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popis);

        TextView tv = findViewById(R.id.textView);
        TextView tv2 = findViewById(R.id.textView2);

        Intent intent = getIntent();
        String uloha = intent.getStringExtra("uloha");
        String popis = intent.getStringExtra("popis");

        tv.setText(uloha);
        tv2.setText(popis);

        Button exit = findViewById(R.id.button);
        exit.setOnClickListener(e -> finish());
    }
}