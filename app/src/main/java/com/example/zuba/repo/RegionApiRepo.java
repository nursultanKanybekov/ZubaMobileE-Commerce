package com.example.zuba.repo;

import com.example.zuba.model.RegionModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RegionApiRepo {
    @GET("/address/region_list/")
    Call<List<RegionModel>> getRegionList();
}
