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

    public LiveData<ModelProducts> getcategoryproduct(ModelProducts catname) {
        final MutableLiveData<ModelProducts> response = new MutableLiveData<>();
        apiReqest.getCategoryProduct(catname)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                            if (result!=null)
                                response.postValue(result);
                            else
                                response.postValue(null); },
                        t -> {
                            response.postValue(null);
                            Log.d("MyError", "login: "+t.getMessage());

                        });

        return response;
    }


}
