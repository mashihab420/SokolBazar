package com.example.sokolbazar.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.sokolbazar.R;
import com.example.sokolbazar.adapter.AllCategoryAdapter;
import com.example.sokolbazar.adapter.AllProductAdapter;
import com.example.sokolbazar.model.ModelProducts;
import com.example.sokolbazar.viewModel.ViewModelHome;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    ViewModelHome viewModelHome;
    AllCategoryAdapter allCategoryAdapter;
    List<ModelProducts> product;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        recyclerView = findViewById(R.id.recyclerViewid);

        product = new ArrayList<>();
      //  allCategoryAdapter = new AllProductAdapter(product, getApplicationContext());
        initRecyclerViewAllproduct(allCategoryAdapter, recyclerView);

        initViewModel();









    }
    private void initRecyclerViewAllproduct(RecyclerView.Adapter adapter, RecyclerView view) {
        view.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        view.setAdapter(adapter);
        allCategoryAdapter.notifyDataSetChanged();

        datasetallproduct();
    }

    private void datasetallproduct() {

        Intent intent = getIntent();
        String category = intent.getStringExtra("category");

        ModelProducts products = new ModelProducts();
        products.setId(category);
        viewModelHome.getcategoryproducts(products).observe(this, new Observer<ModelProducts>() {
            @Override
            public void onChanged(ModelProducts products) {
             //   products.addAll(products);
               // allProductAdapter.notifyDataSetChanged();

            }
        });

    }

    private void initViewModel() {
        viewModelHome = new ViewModelProvider
                .AndroidViewModelFactory(getApplication())
                .create(ViewModelHome.class);
    }
}