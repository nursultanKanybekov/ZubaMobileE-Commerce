package com.example.zuba.model;

public class ProfileModel {

    private String first_name;
    private String last_name;
    private int gender;
    private AddressSerialzerModel address;
    private String avatar;

    public ProfileModel(String first_name, String last_name, int gender, AddressSerialzerModel address, String avatar) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.address = address;
        this.avatar = avatar;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public AddressSerialzerModel getAddress() {
        return address;
    }

    public void setAddress(AddressSerialzerModel address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
