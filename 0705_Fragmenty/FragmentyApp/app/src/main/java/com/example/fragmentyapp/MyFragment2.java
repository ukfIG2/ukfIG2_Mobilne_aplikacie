package com.example.fragmentyapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyFragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyFragment2 extends Fragment {
    Button myButton;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyFragment2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyFragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static MyFragment2 newInstance(String param1, String param2) {
        MyFragment2 fragment = new MyFragment2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // view si zapamätáme, lebo sa očakáva ako návratová hodnota tejto metódy
        View view = inflater.inflate(R.layout.fragment_my2, container, false);
        myButton = (Button) view.findViewById(R.id.button4); // identifikujeme tlačidlo
        // pridáme mu onclick listener
        myButton.setOnClickListener((view1)-> {Toast.makeText(getActivity(), "Fr. 2 - Ahoj", Toast.LENGTH_LONG).show();});
        return view;
    }
}