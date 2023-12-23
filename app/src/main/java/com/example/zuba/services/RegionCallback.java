package com.example.zuba.services;

import com.example.zuba.model.RegionModel;
import com.example.zuba.model.VillageModel;

import java.util.List;

public interface RegionCallback {
    void onSuccess(List<RegionModel> products);
    void onFailure(String errorMessage);
}
