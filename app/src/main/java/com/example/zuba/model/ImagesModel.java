package com.example.zuba.model;

public class ImagesModel {
    private int id;
    private String image;
    private int productId;

    public ImagesModel(String image) {
        this.image = image;
    }

    public ImagesModel() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
