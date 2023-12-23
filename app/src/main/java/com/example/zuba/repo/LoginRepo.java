package com.example.zuba.repo;

import com.example.zuba.model.LoginModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginRepo {
    @POST("user/token/")
    Call<ResponseBody> addProduct(@Body LoginModel product);
}
