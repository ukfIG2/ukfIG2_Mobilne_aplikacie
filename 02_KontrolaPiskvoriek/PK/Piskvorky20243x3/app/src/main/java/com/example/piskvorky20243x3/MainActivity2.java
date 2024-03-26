package com.example.piskvorky20243x3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        ((EditText) findViewById(R.id.editTextText)).setText(name);
        ((Button)findViewById(R.id.buttonOk)).setOnClickListener(event->okHandler());
        ((Button)findViewById(R.id.buttonCancel)).setOnClickListener(event->cancelHandler());
    }

    private void cancelHandler() {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    private void okHandler() {
        Intent intent = getIntent();
        intent.putExtra("name", ((EditText) findViewById(R.id.editTextText)).getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }
}