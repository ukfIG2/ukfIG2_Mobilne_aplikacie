package com.example.piskvorky20243x3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main3);
        Intent intent = getIntent();
        String msg = intent.getStringExtra("msg");
        ((TextView) findViewById(R.id.textView2)).setText(msg);
        ((Button)findViewById(R.id.backButton)).setOnClickListener(event->backHandler());
    }

    private void backHandler() {
        finish();
    }
}