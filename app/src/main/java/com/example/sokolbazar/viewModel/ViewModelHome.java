package com.example.sokolbazar.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sokolbazar.model.Employee;
import com.example.sokolbazar.model.ModelCategory;
import com.example.sokolbazar.repository.RepositoryCategory;

import java.util.List;

public class ViewModelHome extends AndroidViewModel {

    private RepositoryCategory repositoryCategory;


    public ViewModelHome(@NonNull Application application) {
        super(application);

        if (repositoryCategory== null){
            repositoryCategory = new RepositoryCategory();
        }
    }

    public LiveData<List<Employee>> getCategories(){
        return repositoryCategory.getCategories();
    }

}
