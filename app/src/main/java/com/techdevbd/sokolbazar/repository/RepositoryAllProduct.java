package com.techdevbd.sokolbazar.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.techdevbd.sokolbazar.model.ModelProducts;
import com.techdevbd.sokolbazar.retrofit.ApiClient;
import com.techdevbd.sokolbazar.retrofit.ApiInterface;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RepositoryAllProduct {

    public static final String TAG = "RepositoryAllProduct";

    private ApiInterface apiReqest;

    public RepositoryAllProduct() {
        apiReqest = ApiClient.getApiInterface();
    }

    public LiveData<List<ModelProducts>> getProducts(){
        final MutableLiveData<List<ModelProducts>> response = new MutableLiveData<>();
        apiReqest.getProducts() //it should be change to api interface
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ModelProducts>>() {
                    @Override
                    public void accept(List<ModelProducts> products) throws Exception {

                        response.postValue(products);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG,"Error: "+throwable.getMessage());
                    }
                });
        return response;
    }



}
