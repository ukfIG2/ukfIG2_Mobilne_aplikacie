package com.example.a04_05_gridfotolayout;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<ImageItem> imageItems = new ArrayList<>();
    private GridView gridView;
    private GridViewAdapter gridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        PripojAdapter();
    }

    private ArrayList<ImageItem> NaplnList() {

        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.sample_0);
        imageItems.add(new ImageItem(b, "Jožko"));

        b = BitmapFactory.decodeResource(getResources(), R.drawable.sample_7);
        imageItems.add(new ImageItem(b, "Miško"));

        b = BitmapFactory.decodeResource(getResources(), R.drawable.sample_1);
        imageItems.add(new ImageItem(b, "Anička"));

        return imageItems;
    }

    private void PripojAdapter() {
        gridView = (GridView) findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(
                this,
                R.layout.grid_item_layout,
                NaplnList()
        );
        //gridView.setAdapter(gridAdapter);
        //gridView.setAdapter((ListAdapter) gridAdapter);
        //set the fridView to use the adapter
        gridView.setAdapter(gridAdapter);


    }



}