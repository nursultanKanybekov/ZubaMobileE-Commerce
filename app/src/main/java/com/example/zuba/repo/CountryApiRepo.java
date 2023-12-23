package com.example.zuba.repo;

import com.example.zuba.controller.CountryModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CountryApiRepo {
    @GET("/address/country_list/")
    Call<List<CountryModel>> getCountryList();
}
