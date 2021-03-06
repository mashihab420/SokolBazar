package com.techdevbd.sokolbazar.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.techdevbd.sokolbazar.R;
import com.techdevbd.sokolbazar.databinding.ActivityProductDetailsBinding;
import com.techdevbd.sokolbazar.model.ModelCartRoom;
import com.techdevbd.sokolbazar.repository.CartRepository;

import java.util.List;

public class ProductDetails extends AppCompatActivity {

    ActivityProductDetailsBinding binding;
    CartRepository repository;
    TextView cartQuantity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //setContentView(R.layout.activity_product_details);
        cartQuantity = findViewById(R.id.cart_quantity_id);
        Intent intent = getIntent();
        String name = intent.getStringExtra("pname");
        String price = intent.getStringExtra("pprice");
        String description = intent.getStringExtra("description");
        String quantity = intent.getStringExtra("quantity");
        String offers = intent.getStringExtra("offers");
        String urll = intent.getStringExtra("url");
        String shopname = intent.getStringExtra("shopname");
        String activitytag = intent.getStringExtra("activity");

        Glide
                .with(getApplicationContext())
                .load(urll)
                .centerCrop()
                .into(binding.productimgd);

        binding.productnamed.setText(name);
        binding.productpriced.setText(price);
        binding.productdis.setText(description);

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
               /* Intent intent = new Intent(ProductDetails.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();*/
               onBackPressed();
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

        binding.addtocartid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CartRepository repository = new CartRepository(getApplication());



                String quantity = binding.pquantitysd.getText().toString();
                String offers = intent.getStringExtra("offers");
                String urll = intent.getStringExtra("url");
                String shopname = intent.getStringExtra("shopname");

                repository.insertSingleData(new ModelCartRoom(name,price,quantity,offers,urll,shopname));
                Intent intent = new Intent(ProductDetails.this,CartActivity.class);
                startActivity(intent);
            }
        });



        repository = new CartRepository(this);
        repository.getAllData().observe(this, new Observer<List<ModelCartRoom>>() {
            @Override
            public void onChanged(List<ModelCartRoom> modelCartRooms) {

                if (modelCartRooms.size()==0){
                    cartQuantity.setVisibility(View.GONE);
                }else {
                    cartQuantity.setVisibility(View.VISIBLE);
                    if(modelCartRooms.size()>99){
                        cartQuantity.setText("99+");
                    }else {
                        cartQuantity.setText(""+modelCartRooms.size());
                    }


                }

            }
        });


    }
}