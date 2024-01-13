package com.example.zuba.model;

import android.graphics.Bitmap;

import java.io.File;
import java.io.Serializable;
import java.util.List;

public class OrderCreateModel implements Serializable {
    private double total;
    private int paid;
    private File image;
    private int term;
    private double i_p;
    private boolean agreement;
    private AddressSerialzerModel address;
    private List<OrderItem> order_item;

    public OrderCreateModel(int total, int paid, int term, int i_p, boolean agreement, AddressSerialzerModel address, List<OrderItem> order_item) {
        this.total = total;
        this.paid = paid;
        this.term = term;
        this.i_p = i_p;
        this.agreement = agreement;
        this.address = address;
        this.order_item = order_item;
    }

    public OrderCreateModel() {
        super();
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public double getI_p() {
        return i_p;
    }

    public void setI_p(double i_p) {
        this.i_p = i_p;
    }

    public boolean isAgreement() {
        return agreement;
    }

    public void setAgreement(boolean agreement) {
        this.agreement = agreement;
    }

    public AddressSerialzerModel getAddress() {
        return address;
    }

    public void setAddress(AddressSerialzerModel address) {
        this.address = address;
    }

    public List<OrderItem> getOrder_item() {
        return order_item;
    }

    public void setOrder_item(List<OrderItem> order_item) {
        this.order_item = order_item;
    }
}
