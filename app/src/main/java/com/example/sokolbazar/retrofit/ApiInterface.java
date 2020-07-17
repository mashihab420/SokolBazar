package com.example.sokolbazar.retrofit;


import com.example.sokolbazar.model.Employee;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("get_data.php/")
    Observable<List<Employee>> getCategories();

}
