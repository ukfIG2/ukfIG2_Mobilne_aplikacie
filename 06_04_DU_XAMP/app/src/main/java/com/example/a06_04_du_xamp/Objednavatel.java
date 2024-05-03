package com.example.a06_04_du_xamp;

public class Objednavatel {
    private String idPouzivatel;
    private String Meno;
    private String Priezvisko;
    private String Mesto;

    public Objednavatel(String idPouzivatel, String Meno, String Priezvisko, String Mesto) {
        this.idPouzivatel = idPouzivatel;
        this.Meno = Meno;
        this.Priezvisko = Priezvisko;
        this.Mesto = Mesto;
    }

    public String getIdPouzivatel() {
        return idPouzivatel;
    }

    public void setIdPouzivatel(String idPouzivatel) {
        this.idPouzivatel = idPouzivatel;
    }

    public String getMeno() {
        return Meno;
    }

    public void setMeno(String meno) {
        Meno = meno;
    }

    public String getPriezvisko() {
        return Priezvisko;
    }

    public void setPriezvisko(String priezvisko) {
        Priezvisko = priezvisko;
    }

    public String getMesto() {
        return Mesto;
    }

    public void setMesto(String mesto) {
        Mesto = mesto;
    }
}
