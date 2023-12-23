package com.example.zuba.model;

public class CreateOrderModel {
    private String total;
    private String paid;
    private String term;
    private String i_p;
    private String agreement;
    private String image;
    private AddressSerialzerModel address;

    private OrderItem order_item;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getI_p() {
        return i_p;
    }

    public void setI_p(String i_p) {
        this.i_p = i_p;
    }

    public String getAgreement() {
        return agreement;
    }

    public void setAgreement(String agreement) {
        this.agreement = agreement;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public AddressSerialzerModel getAddress() {
        return address;
    }

    public void setAddress(AddressSerialzerModel address) {
        this.address = address;
    }

    public OrderItem getOrder_item() {
        return order_item;
    }

    public void setOrder_item(OrderItem order_item) {
        this.order_item = order_item;
    }
}
