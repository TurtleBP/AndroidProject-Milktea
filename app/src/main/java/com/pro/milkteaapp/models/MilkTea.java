package com.pro.milkteaapp.models;

import java.io.Serializable;

public class MilkTea implements Serializable {
    private int id;
    private String name;
    private double price;
    private String description;
    private int imageResource;
    private String category;

    public MilkTea(int id, String name, double price, String description, int imageResource, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageResource = imageResource;
        this.category = category;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getDescription() { return description; }
    public int getImageResource() { return imageResource; }
    public String getCategory() { return category; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
    public void setDescription(String description) { this.description = description; }
    public void setImageResource(int imageResource) { this.imageResource = imageResource; }
    public void setCategory(String category) { this.category = category; }
}