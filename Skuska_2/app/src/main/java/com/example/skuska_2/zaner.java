package com.example.skuska_2;

public class zaner {

    public static String TABLE_NAME = "zaner";
    public static String COLUMN_ID = "id";
    public static String COLUMN_ZANER = "zaner";

    private long id;
    private String zaner;

    public zaner() {
        this.zaner = zaner;
    }

    public zaner(String zaner) {
        this.zaner = zaner;
    }

    public zaner(long id, String zaner) {
        this.id = id;
        this.zaner = zaner;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getZaner() {
        return zaner;
    }

    public void setZaner(String zaner) {
        this.zaner = zaner;
    }

    @Override
    public String toString() {
        return this.zaner;
    }

    public String toString2() {
        return "Zaner ID: " + this.getId() + ", Zaner: " + this.getZaner();
    }
}
