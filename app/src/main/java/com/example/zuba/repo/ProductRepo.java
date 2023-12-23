package com.example.zuba.repo;

import com.example.zuba.model.ProductsModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductRepo {
    @GET("product/product_list/")
    Call<List<ProductsModel>> getProducts();
}
