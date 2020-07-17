package com.example.sokolbazar.repository;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sokolbazar.model.Employee;
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

    public LiveData<List<Employee>> getCategories(){
        final MutableLiveData<List<Employee>> response = new MutableLiveData<>();
        apiReqest.getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Employee>>() {
                               @Override
                               public void accept(List<Employee> employees) throws Exception {

                                   if (employees !=null)
                                   {
                                       response.postValue(employees);
                                   }else
                                   {
                                       response.postValue(null);
                                   }

                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   Log.d("Repositorycat","Error: "+throwable.getMessage());
                               }
                           });
        return response;
    }
}
