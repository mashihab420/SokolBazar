package com.techdevbd.sokolbazar.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.techdevbd.sokolbazar.R;
import com.techdevbd.sokolbazar.adapter.AllProductAdapter;
import com.techdevbd.sokolbazar.adapter.SeeAllProductAdapter;
import com.techdevbd.sokolbazar.databinding.ActivitySeeAllProductBinding;
import com.techdevbd.sokolbazar.model.ModelProducts;
import com.techdevbd.sokolbazar.viewModel.ViewModelHome;

import java.util.ArrayList;
import java.util.List;

public class SeeAllProductActivity extends AppCompatActivity {

    ActivitySeeAllProductBinding binding;
    private SeeAllProductAdapter seeAllProductAdapter;
    List<ModelProducts> products;
    private ViewModelHome viewModelHome;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all_product);
        binding = ActivitySeeAllProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initViewModel();
        products = new ArrayList<>();
        seeAllProductAdapter = new SeeAllProductAdapter(products, SeeAllProductActivity.this);
        initRecyclerViewAllproduct(seeAllProductAdapter, binding.recyclerView);


    }

    private void initRecyclerViewAllproduct(RecyclerView.Adapter adapter, RecyclerView view) {
       // view.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        view.setLayoutManager(new GridLayoutManager(this,3,GridLayoutManager.VERTICAL,false));

        view.setAdapter(adapter);
        seeAllProductAdapter.notifyDataSetChanged();

        datasetallproduct();
    }

    private void datasetallproduct() {

        //todo get All product

        viewModelHome.getProducts().observe(this, new Observer<List<ModelProducts>>() {
            @Override
            public void onChanged(List<ModelProducts> employees) {
                products.addAll(employees);
                seeAllProductAdapter.notifyDataSetChanged();

                //  Toast.makeText(getContext(), ""+allOffer.get(0).getTitle(), Toast.LENGTH_SHORT).show();


            }
        });

    }

    private void initViewModel() {
        viewModelHome = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(ViewModelHome.class);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void btnback(View view) {
        onBackPressed();
    }
}