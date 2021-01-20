package com.techdevbd.sokolbazar.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.techdevbd.sokolbazar.MysharedPreferance;
import com.techdevbd.sokolbazar.R;
import com.techdevbd.sokolbazar.adapter.CartAdapter;
import com.techdevbd.sokolbazar.adapter.OrdersAdapter;
import com.techdevbd.sokolbazar.model.ModelCartRoom;
import com.techdevbd.sokolbazar.model.ModelOrderProduct;
import com.techdevbd.sokolbazar.model.ModelOrdersRoom;
import com.techdevbd.sokolbazar.repository.CartRepository;
import com.techdevbd.sokolbazar.repository.OrdersRepository;
import com.techdevbd.sokolbazar.retrofit.ApiClient;
import com.techdevbd.sokolbazar.retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersActivity extends AppCompatActivity {
    List<ModelOrderProduct> arrayList;
    Context context;
    RecyclerView recyclerView;
    OrdersAdapter adapter;
    ApiInterface apiInterface;
    MysharedPreferance mysharedPreferance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        arrayList = new ArrayList<>();
        mysharedPreferance = MysharedPreferance.getPreferences(getApplicationContext());
        apiInterface = ApiClient.getApiInterface();
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        adapter = new OrdersAdapter(arrayList,getApplicationContext());
        recyclerView.setAdapter(adapter);

       /* repository = new OrdersRepository(getApplicationContext());

        repository.getAllData().observe(this, new Observer<List<ModelOrdersRoom>>() {
            @Override
            public void onChanged(List<ModelOrdersRoom> modelCartRooms) {

                arrayList.clear();
                arrayList.addAll(modelCartRooms);
                adapter.notifyDataSetChanged();



            }
        });*/
       String phonee = mysharedPreferance.getPhone();

        ModelOrderProduct modelOrderProduct = new ModelOrderProduct();
        modelOrderProduct.setPhone(phonee);
        apiInterface.getOrders(modelOrderProduct).enqueue(new Callback<List<ModelOrderProduct>>() {
            @Override
            public void onResponse(Call<List<ModelOrderProduct>> call, Response<List<ModelOrderProduct>> response) {
                arrayList.clear();
                arrayList.addAll(response.body());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ModelOrderProduct>> call, Throwable t) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void backbtniconorder(View view) {
        onBackPressed();
    }
}