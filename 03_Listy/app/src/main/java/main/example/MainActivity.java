package main.example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> zoznamUloh = new ArrayList<String>();
    ArrayList<String> zoznamPopis = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NacitajDat();
        PridajListener();

    }

    private void NacitajDat() {
        zoznamUloh.add("Nakup");
        zoznamPopis.add("potraviny, ponozky");

        zoznamUloh.add("Posta");
        zoznamPopis.add("Zaplatit PO box");

        zoznamUloh.add("Vybrat deti");
        zoznamPopis.add("zo skolky - svoje");

        adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                zoznamUloh
        );

        ListView lv = findViewById(R.id.List01);
        lv.setAdapter(adapter);

    }

    private void PridajListener() {
        AdapterView.OnItemClickListener mMessageClickedHandler =
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView parent,
                                            View v,
                                            int position,
                                            long id) {
                        //  ((TextView)v).setText("selected");
                        Intent intent = new Intent(MainActivity.this, PopisActivity.class);
                        // precitaj data z poli podla selectnutej polozky
                        String uloha = zoznamUloh.get(position);
                        String popis = zoznamPopis.get(position);
                        // vloz data do intentu
                        intent.putExtra("uloha", uloha);
                        intent.putExtra("popis", popis);
                        // zobraz druhu aktivitu
                        startActivity(intent);
                    }
                };
        ListView myList = findViewById(R.id.List01);
        myList.setOnItemClickListener(mMessageClickedHandler);

    }


}