package hu.mobilalk.porteka_furkeszo.models;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Product implements Serializable {
    private String id;
    private String price;
    private String name;
    private String description;
    private String image;
    private String category;

    public Product(String id, String price, String name, String description, String image, String category) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.description = description;
        this.image = image;
        this.category = category;
    }

    public Product(String name, String price, String description, String image) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
    }

    public Product() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @NonNull
    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", price='" + price + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
