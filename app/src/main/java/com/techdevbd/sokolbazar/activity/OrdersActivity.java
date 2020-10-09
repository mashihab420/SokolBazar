package com.techdevbd.sokolbazar.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.techdevbd.sokolbazar.R;
import com.techdevbd.sokolbazar.adapter.CartAdapter;
import com.techdevbd.sokolbazar.adapter.OrdersAdapter;
import com.techdevbd.sokolbazar.model.ModelCartRoom;
import com.techdevbd.sokolbazar.model.ModelOrderProduct;
import com.techdevbd.sokolbazar.model.ModelOrdersRoom;
import com.techdevbd.sokolbazar.repository.CartRepository;
import com.techdevbd.sokolbazar.repository.OrdersRepository;

import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends AppCompatActivity {
    List<ModelOrdersRoom> arrayList;
    Context context;
    RecyclerView recyclerView;
    OrdersAdapter adapter;
    OrdersRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        arrayList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        adapter = new OrdersAdapter(arrayList,getApplicationContext());
        recyclerView.setAdapter(adapter);

        repository = new OrdersRepository(getApplicationContext());

        repository.getAllData().observe(this, new Observer<List<ModelOrdersRoom>>() {
            @Override
            public void onChanged(List<ModelOrdersRoom> modelCartRooms) {

                arrayList.clear();
                arrayList.addAll(modelCartRooms);
                adapter.notifyDataSetChanged();




                /*if (modelCartRooms.size() == 0){
                    constraintLayout.setVisibility(View.GONE);
                    emptyimage.setVisibility(View.VISIBLE);

                    emptyimage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            *//*Intent intent = new Intent(CartActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();*//*
                            onBackPressed();
                        }
                    });

                }else {
                    constraintLayout.setVisibility(View.VISIBLE);
                    emptyimage.setVisibility(View.GONE);
                }*/


            }
        });
    }
}