package hu.mobilalk.porteka_furkeszo.models;

import java.util.ArrayList;
import java.util.Date;

public class Cart {
    private final String id;
    private final String uid;
    private final ArrayList<Product> products;
    private final Date date;
    private final int total;

    public Cart(String id, String uid, ArrayList<Product> products, Date date, int total) {
        this.id = id;
        this.uid = uid;
        this.products = products;
        this.date = date;
        this.total = total;
    }

}