package com.example.sokolbazar.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sokolbazar.R;
import com.example.sokolbazar.adapter.AllCategoryAdapter;
import com.example.sokolbazar.adapter.AllProductAdapter;
import com.example.sokolbazar.adapter.OffersAdapter;
import com.example.sokolbazar.model.ModelProducts;
import com.example.sokolbazar.viewModel.ViewModelHome;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    ViewModelHome viewModelHome;
    AllCategoryAdapter allCategoryAdapter;
    List<ModelProducts> product;
    RecyclerView recyclerView;
    ImageView cartlogo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        initViewModel();

        recyclerView = findViewById(R.id.recyclerViewid);
        cartlogo = findViewById(R.id.cartidd);

        product = new ArrayList<>();
        allCategoryAdapter = new AllCategoryAdapter(product,this);
        initRecyclerViewAllproduct(allCategoryAdapter,recyclerView);
       
        
        cartlogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this,CartActivity.class);
                startActivity(intent);
            }
        });


    }
    private void initRecyclerViewAllproduct(RecyclerView.Adapter adapter, RecyclerView view) {
       // view.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        view.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        view.setAdapter(adapter);
        allCategoryAdapter.notifyDataSetChanged();

        datasetallproduct();
    }

    private void datasetallproduct() {

        Intent intent = getIntent();
        String category = intent.getStringExtra("category");

        ModelProducts products = new ModelProducts();
        products.setCategory(category);

        viewModelHome.getcategoryproducts(products).observe(this, new Observer<List<ModelProducts>>() {
            @Override
            public void onChanged(List<ModelProducts> item) {
                   product.addAll(item);
                allCategoryAdapter.notifyDataSetChanged();

            }
        });

    }

    private void initViewModel() {
        viewModelHome = new ViewModelProvider
                .AndroidViewModelFactory(getApplication())
                .create(ViewModelHome.class);
    }
}