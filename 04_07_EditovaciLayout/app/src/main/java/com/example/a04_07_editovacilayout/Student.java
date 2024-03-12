package com.example.a04_07_editovacilayout;

public class Student {
    public String meno;
    public String PR1;
    public String PR2;
    public boolean Pritomnost;

    public Student(String meno, String PR1, String PR2, boolean Pritomnost) {
        this.meno = meno;
        this.PR1 = PR1;
        this.PR2 = PR2;
        this.Pritomnost = Pritomnost;
    }

    @Override
    public String toString() {
        return meno+" - " + PR1 +", "+ PR2+" - "+Pritomnost;
    }
}
