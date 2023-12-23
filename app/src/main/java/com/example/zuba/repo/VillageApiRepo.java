package com.example.zuba.repo;

import com.example.zuba.model.VillageModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface VillageApiRepo {
    @GET("/address/villige_list/")
    Call<List<VillageModel>> getVillageList();
}
