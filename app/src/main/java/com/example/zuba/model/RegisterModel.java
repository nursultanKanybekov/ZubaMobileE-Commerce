package com.example.zuba.model;

public class RegisterModel {
    private String phone;
    private String password;
    private ProfileModel profile;

    public RegisterModel(String phone, String password, ProfileModel profile) {
        this.phone = phone;
        this.password = password;
        this.profile = profile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ProfileModel getProfile() {
        return profile;
    }

    public void setProfile(ProfileModel profile) {
        this.profile = profile;
    }
}

