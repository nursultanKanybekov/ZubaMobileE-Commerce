package com.example.zuba.repo;

import com.example.zuba.model.RegisterModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegisterRepo {
    @POST("user/register/")
    Call<ResponseBody> addProduct(@Body RegisterModel product);
}
