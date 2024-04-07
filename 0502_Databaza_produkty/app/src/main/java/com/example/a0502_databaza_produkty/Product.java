package com.example.a0502_databaza_produkty;

public class Product {

    private long ID;
    private String product_name;
    private String product_category;
    private int product_sku;

    public Product(long ID, String product_name, String product_category, int product_sku) {
        this.ID = ID;
        this.product_name = product_name;
        this.product_category = product_category;
        this.product_sku = product_sku;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_category() {
        return product_category;
    }

    public void setProduct_category(String product_category) {
        this.product_category = product_category;
    }

    public int getProduct_sku() {
        return product_sku;
    }

    public void setProduct_sku(int product_sku) {
        this.product_sku = product_sku;
    }
}
