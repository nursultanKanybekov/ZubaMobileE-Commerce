package com.example.zuba.model;

public class OrderItem {
    private int price;
    private int quantity;
    private int product;

    public OrderItem(int price, int quantity, int product) {
        this.price = price;
        this.quantity = quantity;
        this.product = product;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getProduct() {
        return product;
    }

    public void setProduct(int product) {
        this.product = product;
    }
}
