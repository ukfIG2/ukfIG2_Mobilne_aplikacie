package com.example.fragmenty2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDetail extends Fragment {
    Student s = new Student("noname","");

    public FragmentDetail() {
        // Required empty public constructor
    }

    public static FragmentDetail newInstance(String param1, String param2) {
        FragmentDetail fragment = new FragmentDetail();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) { // ak uz boli udaje ulozene nacitaju sa
            s.meno = savedInstanceState.getString("meno", "");
            s.PR1 = savedInstanceState.getString("pr1", "");
            s.PR2 = savedInstanceState.getString("pr2", "");
            s.PR3 = savedInstanceState.getString("pr3", "");
        }
        // inak sa aktualizuje obsah podla s
        updateStudent(s);
    }

    @Override // ulozenie dat pri odchode z aktivity
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("meno", s.meno);
        outState.putString("pr1", s.PR1);
        outState.putString("pr2", s.PR2);
        outState.putString("pr3", s.PR3);
    }

    // vola sa v pripade ak detail je jediny fragment na aktivite z aktivity
    public void setContent(Student istud) {  s = istud; }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }
    public void updateStudent(Student s) { // aktualizacia obsahu detailu
        TextView m = (TextView) getView().findViewById(R.id.meno); m.setText(s.meno);
        TextView pr1 = (TextView) getView().findViewById(R.id.pr1); pr1.setText(s.PR1);
        //TextView pr2 = (TextView) getView().findViewById(R.id.pr2); pr2.setText(s.PR2);
        //TextView pr3 = (TextView) getView().findViewById(R.id.pr3); pr3.setText(s.PR3);
    }

}