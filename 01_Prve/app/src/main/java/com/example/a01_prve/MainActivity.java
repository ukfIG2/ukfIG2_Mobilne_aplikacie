package com.example.a01_prve;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void poKliknuti(View view) {
        //precitat plain text
       EditText et =  findViewById(R.id.editTextText);
        String text = et.getText().toString();
        //otocit retayec
String zrkadlo = "";
for (int i=text.length()-1; i>=0; i--){
    zrkadlo+=text.charAt(i);
}

        //nastavit texteview
TextView tv = findViewById(R.id.textView2);
tv.setText(zrkadlo);

    }
}