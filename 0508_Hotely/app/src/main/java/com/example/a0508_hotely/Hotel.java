package com.example.a0508_hotely;

/**
 * Created by Sinus on 30.03.2017.
 */

public class Hotel {
    private long ID;
    private String nazov;
    private int kapacita;

    public Hotel(long ID, String nazov, int kapacita) {
        this.ID = ID;
        this.nazov = nazov;
        this.kapacita = kapacita;
    }

    public String getNazov() { return nazov; }
    public void setNazov(String nazov) {  this.nazov = nazov;  }
    public int getKapacita() {   return kapacita; }
    public void setKapacita(int kapacita) {   this.kapacita = kapacita;    }

    @Override
    public String toString() {  return "" + ID + ", " + nazov + ", " + kapacita;  }
}
