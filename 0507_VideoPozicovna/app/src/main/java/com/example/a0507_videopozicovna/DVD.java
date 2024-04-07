package com.example.a0507_videopozicovna;

/**
 * Created by Sinus on 29.03.2017.
 */

public class DVD {
   private long ID;
   private String nazov;
   private double cena;


    public DVD(long ID, String nazov, double cena) {
        this.ID = ID;
        this.nazov = nazov;
        this.cena = cena;
    }

    public long getID() {  return ID;  }
    public String getNazov() {   return nazov;  }
    public double getCena() {   return cena;  }

    @Override
    public String toString() { return nazov +": " + cena +", "; }
}
