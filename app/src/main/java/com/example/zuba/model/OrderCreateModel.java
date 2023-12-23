package com.example.zuba.model;

import java.util.List;

public class OrderCreateModel {
    private int total;
    private int paid;
    private int term;
    private int i_p;
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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
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

    public int getI_p() {
        return i_p;
    }

    public void setI_p(int i_p) {
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
