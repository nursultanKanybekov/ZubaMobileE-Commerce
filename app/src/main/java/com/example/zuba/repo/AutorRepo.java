package com.example.zuba.repo;

import com.example.zuba.model.GetUserDetailSerizlizerModel;
import com.example.zuba.model.ProductsModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface AutorRepo {
    @GET("user/get_detail/")
    Call<GetUserDetailSerizlizerModel> getProfile(@Header("Authorization") String token);
}


