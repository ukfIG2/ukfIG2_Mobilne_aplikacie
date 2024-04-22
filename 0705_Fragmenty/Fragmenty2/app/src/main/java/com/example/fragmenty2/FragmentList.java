package com.example.fragmenty2;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class FragmentList extends Fragment {
    // odchytenie kliknutia, ktore sa prenesie do aktivity,
    // kde vyvola aktualizaciu druheho fragmentu
    public interface KlikaciListener {
        public void onKlik(Student s); // jedina metoda rozhrania (abstraktna)
    }

    @Override // metoda, ktora sa vola vtedy, ked sa fragment stava sucastou aktivity
    public void onAttach(Context context) {
        super.onAttach(context); // vstupom je context = aktivita
        // do nášho mListenera vložíme odkaz na listener, ktorý je implementovaný
        // v aktivite, čím ich prepojíme – tento listener je presne ten istý, ktorý
        // v aktivite pošle údaje do fragmentu s detailom
        mListener = (KlikaciListener) context;
    }

    KlikaciListener mListener; // bude osetreny az v aktivite, udalost sa prenesie

    public FragmentList() {
        // Required empty public constructor
    }

    StudentList sList = new StudentList(); // udaje

    // z fragmentu do nej cez rozhranie KlikaciListener

    public void onCreateView(Bundle savedInstanceState) {

    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        displayListView(); // napojime udaje o studentoch na list
    }

    private void displayListView() {
        // pripojí adaptér
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
                getActivity(),  // vrati aktivitu, v ktorej je fragment
                android.R.layout.simple_list_item_1, // layout pre zoznam
                sList.getMena());      // zoznam stringov – mien studentov
        ListView listView = (ListView) getView().findViewById(R.id.studentList);
        listView.setAdapter(dataAdapter);

        // položke listvievu nastavíme reakciu na kliknutie
        listView.setOnItemClickListener((parent, view, position, id) -> {
                // kliknutie posle udaje o studentovi do host activity
                mListener.onKlik(sList.getStudent(((TextView) view).getText().toString()));});
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }
}