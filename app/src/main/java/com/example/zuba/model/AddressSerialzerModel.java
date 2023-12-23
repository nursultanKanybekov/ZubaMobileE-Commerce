package com.example.zuba.model;

public class AddressSerialzerModel {
    private int id;
    private String exact_address;
    private String country;
    private String region;
    private String villige;

    public AddressSerialzerModel(String exact_address, String country, String region, String villige) {
        this.exact_address = exact_address;
        this.country = country;
        this.region =region;
        this.villige = villige;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExact_address() {
        return exact_address;
    }

    public void setExact_address(String exact_address) {
        this.exact_address = exact_address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getVillige() {
        return villige;
    }

    public void setVillige(String villige) {
        this.villige = villige;
    }
}
