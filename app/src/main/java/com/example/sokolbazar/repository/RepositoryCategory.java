package com.example.sokolbazar.repository;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sokolbazar.model.Employee;
import com.example.sokolbazar.model.ModelProducts;
import com.example.sokolbazar.retrofit.ApiInterface;
import com.example.sokolbazar.model.ModelCategory;
import com.example.sokolbazar.retrofit.ApiClient;
import com.orhanobut.logger.LogAdapter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RepositoryCategory {
    private ApiInterface apiReqest;

    public RepositoryCategory() {
        apiReqest = ApiClient.getApiInterface();
    }

    public LiveData<List<ModelProducts>> getCategories(){
        final MutableLiveData<List<ModelProducts>> response = new MutableLiveData<>();
        apiReqest.getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ModelProducts>>() {
                               @Override
                               public void accept(List<ModelProducts> products) throws Exception {
                                   Log.d("errordipu", "accept: paisi size="+products.size());
                                   /*if (employees !=null)
                                   {
                                       response.postValue(employees);
                                   }else
                                   {
                                       response.postValue(null);
                                   }*/
                                   response.postValue(products);

                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   Log.d("errordipu","Error: "+throwable.getMessage());
                               }
                           });
        return response;
    }
}
