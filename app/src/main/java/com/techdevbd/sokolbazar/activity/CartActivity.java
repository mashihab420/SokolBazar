package com.techdevbd.sokolbazar.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.techdevbd.sokolbazar.MysharedPreferance;
import com.techdevbd.sokolbazar.R;
import com.techdevbd.sokolbazar.adapter.CartAdapter;
import com.techdevbd.sokolbazar.model.ModelCartRoom;
import com.techdevbd.sokolbazar.model.ModelUsers;
import com.techdevbd.sokolbazar.repository.CartRepository;
import com.techdevbd.sokolbazar.retrofit.ApiClient;
import com.techdevbd.sokolbazar.retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity implements OnDataSend{

    ConstraintLayout constraintLayout;
    TextView address,subtotal,total,discount;
    ImageView emptyimage,backbutton;
    RecyclerView recyclerView;
    CartAdapter adapter;
    List<ModelCartRoom> arrayList;
    Context context;
    CartRepository repository;
    int num = 1;
    int my_taka =0;
    int totall;
    String sub;
    int totalll;
    String discounttaka;
    int dis=0;
    Dialog dialog;
    ApiInterface apiInterface;
    MysharedPreferance sharedPreferance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        dialog = new Dialog(this);
        constraintLayout = findViewById(R.id.subtotallayout);
        address = findViewById(R.id.textView6);
        emptyimage = findViewById(R.id.empty_cartimg);
        backbutton = findViewById(R.id.backicon);
        subtotal = findViewById(R.id.textView12);
        discount = findViewById(R.id.textView13);
        total = findViewById(R.id.textView15);

        apiInterface = ApiClient.getApiInterface();
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
    public void totalPrice(String value,String discountt) {
        subtotal.setText(value+" BDT");
        discount.setText(discountt+" BDT");
        int subtotal = Integer.parseInt(value);

        int subtotall = Integer.parseInt(value);
       dis = Integer.parseInt(discountt);
       discounttaka = discountt;

      int disprice =  subtotall - dis;

       totall = disprice;

      total.setText(totall+" BDT");



      sub = value;
      totalll = disprice;


    }

    public void confirmorderId(View view) {

        String name = sharedPreferance.getName();




        if (name.equals("none")){
            Intent intent = new Intent(getApplicationContext(),RegistrationActivity.class);
            intent.putExtra("subtotall", sub);
            intent.putExtra("discountt", discounttaka);
            intent.putExtra("totall", totalll);
            intent.putExtra("activity","cart");
            startActivity(intent);
        }else {

            Intent intent = new Intent(getApplicationContext(),DeliveryActivity.class);

            intent.putExtra("subtotall", sub);
            intent.putExtra("discountt", discounttaka);
            intent.putExtra("totall", totalll);
            startActivity(intent);

        }


    }

    public void editAddressBtn(View view) {
        String phone = sharedPreferance.getPhone();

        if (phone.equals("none")){
            Toast.makeText(getApplicationContext(), "You must login to edit address!", Toast.LENGTH_SHORT).show();
        }else {

            openDialog();

        }

    }

    private void openDialog() {
        dialog.setContentView(R.layout.dialog_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        
        ImageView imageViewClose = dialog.findViewById(R.id.imageView2);
        TextView update = dialog.findViewById(R.id.button3);
        EditText editText = dialog.findViewById(R.id.editTextTextPersonName8);
        
        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // dialog.dismiss();
                String addresss = editText.getText().toString();
                String phone = sharedPreferance.getPhone();
                sharedPreferance.setAddress(addresss);
                address.setText(addresss);
                ModelUsers modelUsers = new ModelUsers();
                modelUsers.setPhone(phone);
                modelUsers.setAddress(addresss);

                apiInterface.updateUserInfo(modelUsers).enqueue(new Callback<ModelUsers>() {
                    @Override
                    public void onResponse(Call<ModelUsers> call, Response<ModelUsers> response) {
                        Toast.makeText(CartActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<ModelUsers> call, Throwable t) {

                    }
                });



            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }



    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CartActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }*/
}