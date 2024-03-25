package com.example.a05_databazy;

public class Student {//alt + insert na vytvaranie veci
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


}
