package hu.mobilalk.porteka_furkeszo.models;

import java.util.ArrayList;
import java.util.Date;

public class Cart {
    private String id;
    private String uid;
    private ArrayList<Product> products;
    private Date date;
    private int total;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Cart(String id, String uid, ArrayList<Product> products, Date date, int total) {
        this.id = id;
        this.uid = uid;
        this.products = products;
        this.date = date;
        this.total = total;
    }
}