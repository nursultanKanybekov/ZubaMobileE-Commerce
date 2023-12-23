package com.example.zuba.services;

import com.example.zuba.controller.CountryModel;
import com.example.zuba.model.VillageModel;

import java.util.List;

public interface CountryCallback {
    void onSuccess(List<CountryModel> products);
    void onFailure(String errorMessage);
}
