package com.techdevbd.sokolbazar.retrofit;


import com.techdevbd.sokolbazar.model.ModelOrderProduct;
import com.techdevbd.sokolbazar.model.ModelProducts;
import com.techdevbd.sokolbazar.model.ModelUsers;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {



    @GET("get_category.php")
    Observable<List<ModelProducts>> getCategories();

    @GET("get_products.php")
    Observable<List<ModelProducts>> getProducts();

    @GET("get_offers.php")
    Observable<List<ModelProducts>> getOffers();

    @POST("get_categoryproduct.php")
    Observable<List<ModelProducts>> getCategoryProduct(@Body ModelProducts user);


    @GET("check_data_before_create.php")
    Call<ModelUsers> check_data(@Query("phone") String phone );

    @POST("insert_user_info.php")
    Call<ModelUsers> addUsers(@Body ModelUsers modelUsers);

    @POST("update_user_info.php")
    Call<ModelUsers> updateUserInfo(@Body ModelUsers modelUsers);

    @POST("user_order_insert.php")
    Call<ModelOrderProduct> userOrderInsert(@Body ModelOrderProduct modelOrderProduct);


    @GET("userlogin.php")
    Call<ModelUsers> userlogin(@Query("phone") String phone ,@Query("password") String pass);

}
