package com.example.fragmentyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b = findViewById(R.id.button);
        b.setOnClickListener((view)-> loadFragment(new MyFragment1()));
        Button b2 = findViewById(R.id.button2);
        b2.setOnClickListener((view)-> loadFragment(new MyFragment2()));
    }

    private void loadFragment(Fragment fragment) {
        // ziskame FragmentManager
        FragmentManager fm = getSupportFragmentManager();
        // vytvoríme transakciu
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // nahradíme FrameLayout Fragment-om
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit(); // zrealizujeme zmeny - potvrdíme transakciu
    }

}