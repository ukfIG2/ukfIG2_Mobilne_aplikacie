package com.example.fragmenty2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements FragmentList.KlikaciListener {
    boolean detailPage = false; // v pripade ineho dizajnu – pozri nizsie

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);

        // podla natocenia nacitame layout aktivity s dvoma fragmentami vedla seba alebo pod sebou
        Configuration config = getResources().getConfiguration();
        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE)
            setContentView(R.layout.activity_main_land);
        else  setContentView(R.layout.activity_main_port);

        if(savedInstanceState == null) { // ak sa aktivita zobrazuje po prvy raz
            FragmentList listFragment = new FragmentList(); // vytvorim novy fragment
            // cez transakciu ho vlozim do kontajnera displayList
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.displayList, listFragment, "List_Fragment");
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit(); // potvrdene, hotovo
        }
        // ak existuje fragment detailu, u nas stale ale je
        // vhodne pouzit ak je zariadenia nastojato a nechceme mat vtedy v aktivite detail
        if(findViewById(R.id.displayDetail) != null){
            detailPage = true;
            // ziskame nan odkaz
            FragmentDetail detailFragment = (FragmentDetail)
                    getSupportFragmentManager().findFragmentById(R.id.displayDetail);
            if (detailFragment == null){ // ak este neexistuje vytvorime ho
                detailFragment = new FragmentDetail();
                // a cez transakciu zabezpecime pridanie
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.displayDetail, detailFragment, "Detail_Fragment1");
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        }

    }

    @Override
    public void onKlik(Student s) {
        if (detailPage){ // ak uz je zobrazeny fragment detail napln ho
            FragmentDetail detailFragment =
                    (FragmentDetail) getSupportFragmentManager().findFragmentById(R.id.displayDetail);
            detailFragment.updateStudent(s);
        } else { // inak ho vytvor a nahrad existujuci list-fragment
            // my detail mame vzdy, lebo sme ho vytvorili vyssie
            // tato vetva sa vykona, len ak mame layout v ktorom detail nie je,
            // a vtedy sa layout listu nahradi layoutom detailu
            FragmentDetail detailFragment = new FragmentDetail(); // vytvor novy
            detailFragment.setContent(s); // nastav mu obsah podla studenta s

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.displayList, detailFragment, "Detail_Fragment2");
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(null); // aby sme sa tlacidlom spat mohli vratit zase
            // k listu, nastavime list do pozadia
            ft.commit();
        }
    }
}