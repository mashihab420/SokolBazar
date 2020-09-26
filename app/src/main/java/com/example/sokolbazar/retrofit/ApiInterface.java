package com.example.sokolbazar.retrofit;


import com.example.sokolbazar.model.ModelProducts;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {



    @GET("get_category.php")
    Observable<List<ModelProducts>> getCategories();

    @GET("get_products.php")
    Observable<List<ModelProducts>> getProducts();

    @GET("get_offers.php")
    Observable<List<ModelProducts>> getOffers();

    @POST("get_categoryproduct.php")
    Observable<ModelProducts> getCategoryProduct(@Body ModelProducts user);

}
