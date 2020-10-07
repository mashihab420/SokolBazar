package com.techdevbd.sokolbazar.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.techdevbd.sokolbazar.model.ModelProducts;
import com.techdevbd.sokolbazar.repository.RepositoryAllProduct;
import com.techdevbd.sokolbazar.repository.RepositoryCategory;
import com.techdevbd.sokolbazar.repository.RepositoryCategorysProduct;
import com.techdevbd.sokolbazar.repository.RepositoryOffers;

import java.util.List;

public class ViewModelHome extends AndroidViewModel {

    private RepositoryCategory repositoryCategory;
    private RepositoryOffers repositoryOffers;
    private RepositoryAllProduct repositoryAllProduct;
    private RepositoryCategorysProduct repositoryCategorysProduct;


    public ViewModelHome(@NonNull Application application) {
        super(application);

        if (repositoryCategory== null){
            repositoryCategory = new RepositoryCategory();
        }
        if (repositoryOffers== null){
            repositoryOffers = new RepositoryOffers();
        }

        if (repositoryAllProduct== null){
            repositoryAllProduct = new RepositoryAllProduct();
        }
        if (repositoryCategorysProduct== null){
            repositoryCategorysProduct = new RepositoryCategorysProduct();
        }
    }

    public LiveData<List<ModelProducts>> getCategories(){
        return repositoryCategory.getCategories();
    }

    public LiveData<List<ModelProducts>> getOffers(){
        return repositoryOffers.getOffers();
    }

    public LiveData<List<ModelProducts>> getProducts(){
        return repositoryAllProduct.getProducts();
    }

    public LiveData<List<ModelProducts>> getcategoryproducts(ModelProducts products) {
        return repositoryCategorysProduct.getcategoryproduct(products);
    }

}
