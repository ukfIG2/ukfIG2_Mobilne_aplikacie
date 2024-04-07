package com.example.a0506_vlastnykurzoradapter;

/**
 * Created by Sinus on 21.03.2017.
 */

public class Student {
    private long ID;
    private String meno;
    private String email;
    private int vek;

    public Student(long ID, String meno, String email, int vek) {
        this.ID = ID;
        this.meno = meno;
        this.email = email;
        this.vek = vek;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getMeno() {
        return meno;
    }

    public void setMeno(String meno) {
        this.meno = meno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getVek() {
        return vek;
    }

    public void setVek(int vek) {
        this.vek = vek;
    }

    @Override
    public String toString() {
        return "Student{" +
                "ID=" + ID +
                ", meno='" + meno + '\'' +
                ", email='" + email + '\'' +
                ", vek=" + vek +
                '}';
    }
}