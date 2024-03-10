package com.example.zoznamy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class PridajActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pridaj);

        Button b = findViewById(R.id.button2);
        b.setOnClickListener(e -> button2click());
    }

    public void button2click() {
        EditText uloha = (EditText)findViewById(R.id.editTextText2);
        EditText popis = (EditText)findViewById(R.id.editTextText3);
        Intent intentNavrat = new Intent();
        intentNavrat.putExtra("uloha", uloha.getText().toString());
        intentNavrat.putExtra("popis", popis.getText().toString());
        setResult(RESULT_OK,intentNavrat);
        finish();
    }
}