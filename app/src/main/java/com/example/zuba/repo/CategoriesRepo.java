package com.example.zuba.repo;

import com.example.zuba.model.CategoriesModel;
import com.example.zuba.model.ProductsModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CategoriesRepo {
    @GET("product/category_list/")
    Call<List<CategoriesModel>> getCategories();
}
