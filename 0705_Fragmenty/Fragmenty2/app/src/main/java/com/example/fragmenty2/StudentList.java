package com.example.fragmenty2;

import java.util.ArrayList;

public class StudentList {
    ArrayList<Student> zoznam;

    public StudentList() { // naplni demo hodnotami
        zoznam  = new ArrayList<Student>();
        zoznam.add( new Student ("Jano","A"));
        zoznam.add( new Student ("Zuza","B"));
        zoznam.add( new Student ("Mara","C"));
    }

    ArrayList<String> getMena(){ // vrati do listu
        ArrayList<String> myZ = new ArrayList<String>();
        for(int i=0; i<zoznam.size();i++)
            myZ.add(zoznam.get(i).meno.toString());
        return myZ;
    }

    // vrati udaje studenta podla mena
    Student getStudent(String meno) {
        for(int i = 0; i < zoznam.size(); i++) {
            if (meno.equals(zoznam.get(i).meno))
                return zoznam.get(i);
        }
        return null;
    }

}
