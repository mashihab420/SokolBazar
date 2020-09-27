package com.example.sokolbazar.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sokolbazar.model.ModelProducts;
import com.example.sokolbazar.retrofit.ApiClient;
import com.example.sokolbazar.retrofit.ApiInterface;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RepositoryCategorysProduct {

    public static final String TAG = "RepositoryAllProduct";

    private ApiInterface apiReqest;

    public RepositoryCategorysProduct() {
        apiReqest = ApiClient.getApiInterface();
    }

    public LiveData<List<ModelProducts>> getcategoryproduct(ModelProducts modelProducts){

        final MutableLiveData<List<ModelProducts>> response = new MutableLiveData<>();
        apiReqest.getCategoryProduct(modelProducts)
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
                                   Log.d(TAG,""+throwable.getMessage());
                               }
                           });

        return response;
    }


}
