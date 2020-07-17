package com.example.sokolbazar.retrofit;


import com.example.sokolbazar.model.Employee;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @GET("photos/")
    Observable<List<Employee>> getCategories();

}
