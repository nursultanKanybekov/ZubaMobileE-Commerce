package com.example.zuba.model;

import java.util.List;

public class ContactModel {
    private final String id;
    private final String displayName;
    private final List<String> phoneNumbers;

    public ContactModel(String id, String displayName, List<String> phoneNumbers) {
        this.id = id;
        this.displayName = displayName;
        this.phoneNumbers = phoneNumbers;
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }
}
