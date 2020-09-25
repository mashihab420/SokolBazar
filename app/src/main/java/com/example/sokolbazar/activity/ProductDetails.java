package com.example.sokolbazar.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.sokolbazar.R;
import com.example.sokolbazar.databinding.ActivityProductDetailsBinding;

public class ProductDetails extends AppCompatActivity {

    ActivityProductDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //setContentView(R.layout.activity_product_details);

        Intent intent = getIntent();
        String name = intent.getStringExtra("pname");
        String price = intent.getStringExtra("pprice");
        String quantity = intent.getStringExtra("quantity");
        String urll = intent.getStringExtra("url");

        Glide
                .with(getApplicationContext())
                .load(urll)
                .centerCrop()
                .into(binding.productimgd);

        binding.productnamed.setText(name);
        binding.productpriced.setText(price);

        binding.cartidd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CartActivity.class);
                startActivity(intent);
            }
        });

        binding.backicond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDetails.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        binding.addItemIdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantity = binding.pquantitysd.getText().toString();

                int quantitys = Integer.parseInt(quantity);
                quantitys +=1;

                binding.pquantitysd.setText(""+quantitys);
            }
        });

        binding.minusproductd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantity = binding.pquantitysd.getText().toString();
                if (quantity.equals("1")){

                }else {
                    int quantitys = Integer.parseInt(quantity);
                    quantitys -=1;

                    binding.pquantitysd.setText(""+quantitys);
                }
            }
        });

    }
}