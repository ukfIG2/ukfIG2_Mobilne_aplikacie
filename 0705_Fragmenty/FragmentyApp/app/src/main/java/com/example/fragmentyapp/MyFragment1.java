package com.example.fragmentyapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class MyFragment1 extends Fragment {
    private Button myButton;
    public MyFragment1() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // vytvoríme/naplníme layout podľa vzoru
        // view si zapamätáme, lebo sa očakáva ako návratová hodnota tejto metódy
        View view = inflater.inflate(R.layout.fragment_my1, container, false);
        myButton = (Button) view.findViewById(R.id.button3); // identifikujeme tlačidlo
        // pridáme mu onclick listener
        myButton.setOnClickListener((view1)-> {Toast.makeText(getActivity(), "Fr. 1 - Ahoj", Toast.LENGTH_LONG).show();});
        return view;
    }
}