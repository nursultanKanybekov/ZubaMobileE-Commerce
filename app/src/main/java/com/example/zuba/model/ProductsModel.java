package com.example.zuba.model;

import java.util.List;

public class ProductsModel {
    private int id;
    private String name;
    private String description;
    private int price;
    private int stock;
    private boolean available;
    private String created;
    private String updated;
    private int category;
    private int shop;
    private List<ImagesModel> images;

    public ProductsModel(int id, String name, String description, int price, int stock, boolean available, String created, String updated, int category, int shop, List<ImagesModel> images) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.available = available;
        this.created = created;
        this.updated = updated;
        this.category = category;
        this.shop = shop;
        this.images = images;
    }

    public ProductsModel() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getShop() {
        return shop;
    }

    public void setShop(int shop) {
        this.shop = shop;
    }

    public List<ImagesModel> getImages() {
        return images;
    }

    public void setImages(List<ImagesModel> images) {
        this.images = images;
    }

}