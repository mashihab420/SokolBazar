package com.example.sokolbazar.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.sokolbazar.R;
import com.example.sokolbazar.model.ModelProducts;
import com.example.sokolbazar.viewModel.ViewModelHome;

public class CategoryActivity extends AppCompatActivity {

    ViewModelHome viewModelHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        initViewModel();
        Intent intent = getIntent();
        String category = intent.getStringExtra("category");


        ModelProducts products = new ModelProducts();
        products.setId(category);

        viewModelHome.getLoginResponse(products).observe(this, new Observer<ModelProducts>() {
            @Override
            public void onChanged(ModelProducts products) {
                if (products != null) {
                    Toast.makeText(CategoryActivity.this, "" + products.getPName(), Toast.LENGTH_SHORT).show();
                    /*CategoryActivity.this.startActivity(new Intent(CategoryActivity.this, MainActivity.class));
                    CategoryActivity.this.finish();*/
                }

            }
        });
    }

    private void initViewModel() {
        viewModelHome = new ViewModelProvider
                .AndroidViewModelFactory(getApplication())
                .create(ViewModelHome.class);
    }
}