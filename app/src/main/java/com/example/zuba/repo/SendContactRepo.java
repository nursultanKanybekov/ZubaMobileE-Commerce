package com.example.zuba.repo;

import com.example.zuba.model.ContactModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SendContactRepo {
    @POST("contact/create/")
    Call<ResponseBody> addProducts(@Body List<ContactModel> contactModels);
}

