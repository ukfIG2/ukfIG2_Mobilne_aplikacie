package Main.example;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b1 = findViewById(R.id.Button01);
        b1.setOnClickListener(evt -> naKlik());
    }

    public void naKlik(){
        Intent intent = new Intent(MainActivity.this, PozdravActivity.class);
        startActivityForResult(intent,1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCpde, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCpde, data);
        switch (requestCode) {
            case 1: {
                if (resultCpde == RESULT_OK) {
                    Bundle vrateneData = data.getExtras(); //Mapa
                    String meno = vrateneData.getString("meno");
                    String priezvisko = vrateneData.getString("priezvisko");
                    TextView tv = findViewById(R.id.Text01);
                    tv.setText("Ahoj " + meno + " " + priezvisko);

                }
            }
            break;
        }
    }



}