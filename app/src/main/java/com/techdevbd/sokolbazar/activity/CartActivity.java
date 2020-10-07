package com.techdevbd.sokolbazar.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.techdevbd.sokolbazar.MysharedPreferance;
import com.techdevbd.sokolbazar.R;
import com.techdevbd.sokolbazar.adapter.CartAdapter;
import com.techdevbd.sokolbazar.model.ModelCartRoom;
import com.techdevbd.sokolbazar.repository.CartRepository;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements OnDataSend{

    ConstraintLayout constraintLayout;
    TextView address,subtotal,total;
    ImageView emptyimage,backbutton;
    RecyclerView recyclerView;
    CartAdapter adapter;
    List<ModelCartRoom> arrayList;
    Context context;
    CartRepository repository;
    int num = 1;
    int my_taka =0;
MysharedPreferance sharedPreferance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        constraintLayout = findViewById(R.id.subtotallayout);
        address = findViewById(R.id.textView6);
        emptyimage = findViewById(R.id.empty_cartimg);
        backbutton = findViewById(R.id.backicon);
        subtotal = findViewById(R.id.textView12);
        total = findViewById(R.id.textView15);
        sharedPreferance = MysharedPreferance.getPreferences(getApplicationContext());
        arrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);


        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        adapter = new CartAdapter(getApplicationContext(),arrayList,repository,this);
        recyclerView.setAdapter(adapter);


        repository = new CartRepository(getApplicationContext());

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(CartActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();*/
               onBackPressed();
            }
        });

        repository.getAllData().observe(this, new Observer<List<ModelCartRoom>>() {
            @Override
            public void onChanged(List<ModelCartRoom> modelCartRooms) {

                arrayList.clear();
                arrayList.addAll(modelCartRooms);
                adapter.notifyDataSetChanged();

              //  Toast.makeText(getApplicationContext(),""+modelCartRooms.size(), Toast.LENGTH_SHORT).show();


                if (modelCartRooms.size() == 0){
                    constraintLayout.setVisibility(View.GONE);
                    emptyimage.setVisibility(View.VISIBLE);

                    emptyimage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            /*Intent intent = new Intent(CartActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();*/
                            onBackPressed();
                        }
                    });

                }else {
                    constraintLayout.setVisibility(View.VISIBLE);
                    emptyimage.setVisibility(View.GONE);
                }


            }
        });


      String useraddress =  sharedPreferance.getAddress();

      address.setText(useraddress);



    }


    @Override
    public void totalPrice(String value) {
        subtotal.setText(value);
        int subtotal = Integer.parseInt(value);

       int totall = subtotal;

      total.setText(""+totall);


    }

    public void confirmorderId(View view) {

        Intent intent = new Intent(getApplicationContext(),RegistrationActivity.class);
        startActivity(intent);
    }

    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CartActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }*/
}