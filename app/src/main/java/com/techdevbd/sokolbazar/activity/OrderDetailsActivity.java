package com.techdevbd.sokolbazar.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.techdevbd.sokolbazar.R;
import com.techdevbd.sokolbazar.adapter.OrderDetailsAdapter;
import com.techdevbd.sokolbazar.model.ModelCartRoom;
import com.techdevbd.sokolbazar.model.ModelOrderProduct;
import com.techdevbd.sokolbazar.repository.CartRepository;
import com.techdevbd.sokolbazar.retrofit.ApiClient;
import com.techdevbd.sokolbazar.retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailsActivity extends AppCompatActivity implements OnDataSend{
    TextView price;
    RecyclerView recyclerView;
    ApiInterface apiInterface;
    OrderDetailsAdapter orderDetailsAdapter;
    List<ModelOrderProduct> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        recyclerView = findViewById(R.id.recyclerView);
        price = findViewById(R.id.totalorderprice);



        productList = new ArrayList<>();
        orderDetailsAdapter = new OrderDetailsAdapter(productList,getApplicationContext(),this);
        initRecyclerViewAllproduct(orderDetailsAdapter, recyclerView);




    }
    private void initRecyclerViewAllproduct(RecyclerView.Adapter adapter, RecyclerView view) {
        view.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        view.setAdapter(adapter);

        datasetallproduct();
    }

    private void datasetallproduct(){
        apiInterface = ApiClient.getApiInterface();

        ModelOrderProduct modelOrderProduct = new ModelOrderProduct();
        modelOrderProduct.setInvoice_number(""+getIntent().getStringExtra("order_id"));
        modelOrderProduct.setPhone(""+getIntent().getStringExtra("phone"));

        apiInterface.getOrderDetails(modelOrderProduct).enqueue(new Callback<List<ModelOrderProduct>>() {
            @Override
            public void onResponse(Call<List<ModelOrderProduct>> call, Response<List<ModelOrderProduct>> response) {
                productList.addAll(response.body());
                orderDetailsAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<ModelOrderProduct>> call, Throwable t) {

            }
        });



    }

    public void backbtniconorder(View view) {

        onBackPressed();

    }

    @Override
    public void totalPrice(String subtotal, String discount) {

    }

    @Override
    public void orderprice(String subtotal, String total, String discount) {
        int dis = Integer.parseInt(discount);
        int totall = Integer.parseInt(subtotal)-dis+50;
        price.setText(totall+" BDT");
    }
}