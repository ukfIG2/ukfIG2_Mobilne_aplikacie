package Main.example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class PozdravActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pozdrav);

        Button b2 = findViewById(R.id.Button2);
        b2.setOnClickListener(evt -> naKlik2());
    }

    private void naKlik2(){
        EditText et = findViewById(R.id.Text3);
        EditText et2 = findViewById(R.id.Text4);

        Intent intent = getIntent();
        intent.putExtra("meno", et.getText().toString());
        intent.putExtra("priezvisko", et2.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }
}