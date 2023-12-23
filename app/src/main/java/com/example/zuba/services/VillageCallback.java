package com.example.zuba.services;

import com.example.zuba.model.VillageModel;

import java.util.List;

public interface VillageCallback {
    void onSuccess(List<VillageModel> products);
    void onFailure(String errorMessage);
}
