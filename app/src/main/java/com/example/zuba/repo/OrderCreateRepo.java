package com.example.zuba.repo;

import com.example.zuba.model.OrderCreateModel;
import com.example.zuba.model.RegisterModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface OrderCreateRepo {
    @POST("order/create/")
    Call<ResponseBody> addProduct(@Body OrderCreateModel product, @Header("Authorization") String token);
}
