package com.example.skuska_1;

public class kniha {

    public static String TABLE_NAME = "kniha";
    public static String COLUMN_ID = "_id";
    public static String COLUMN_NAZOV = "nazov";
    public static String COLUMN_AUTOR = "autor";
    public static String COLUMN_ROK = "rok";
    public static String COLUMN_OBSAH = "obsah";
    public static String COLUMN_ZANER = "zaner";

    private long id;
    private String nazov;
    private String autor;
    private int rok;
    private String obsah;
    private long zaner;



    public kniha(String nazov, String autor, int rok, String obsah, long zaner_id) {
        this.nazov = nazov;
        this.autor = autor;
        this.rok = rok;
        this.obsah = obsah;
        this.zaner = zaner_id;
    }

    public kniha(long id, String nazov, String autor, int rok, String obsah, long zaner_id) {
        this.id = id;
        this.nazov = nazov;
        this.autor = autor;
        this.rok = rok;
        this.obsah = obsah;
        this.zaner = zaner_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNazov() {
        return nazov;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getRok() {
        return rok;
    }

    public void setRok(int rok) {
        this.rok = rok;
    }

    public String getObsah() {
        return obsah;
    }

    public void setObsah(String obsah) {
        this.obsah = obsah;
    }

    public long getZaner_id() {
        return zaner;
    }

    public void setZaner_id(int zaner_id) {
        this.zaner = zaner_id;
    }
}
