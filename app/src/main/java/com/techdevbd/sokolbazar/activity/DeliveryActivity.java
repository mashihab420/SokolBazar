package com.techdevbd.sokolbazar.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.techdevbd.sokolbazar.MysharedPreferance;
import com.techdevbd.sokolbazar.R;
import com.techdevbd.sokolbazar.databinding.ActivityDeliveryBinding;
import com.techdevbd.sokolbazar.databinding.ActivityMainBinding;
import com.techdevbd.sokolbazar.model.ModelCartRoom;
import com.techdevbd.sokolbazar.model.ModelOrderProduct;
import com.techdevbd.sokolbazar.model.ModelUsers;
import com.techdevbd.sokolbazar.repository.CartRepository;
import com.techdevbd.sokolbazar.retrofit.ApiClient;
import com.techdevbd.sokolbazar.retrofit.ApiInterface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryActivity extends AppCompatActivity {
    ActivityDeliveryBinding binding;
    Spinner spinner;
    String spinnertext;
    ApiInterface apiInterface;
    MysharedPreferance sharedPreferance;
    CartRepository repository;
    List<ModelCartRoom> arrayList;
    int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeliveryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        apiInterface = ApiClient.getApiInterface();
        sharedPreferance = MysharedPreferance.getPreferences(getApplicationContext());
        arrayList = new ArrayList<>();
        repository = new CartRepository(getApplicationContext());


        spinner = findViewById(R.id.spinnerid);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.pickatime, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio1:
                        binding.spinnerid.setVisibility(View.VISIBLE);

                        break;
                    case R.id.radio2:

                        binding.spinnerid.setVisibility(View.GONE);
                        break;
                }
            }
        });

        binding.spinnerid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnertext = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //for storage permission


        ActivityCompat.requestPermissions(DeliveryActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        ActivityCompat.requestPermissions(DeliveryActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);




    }

    public void paymentbtn(View view) {

        if (binding.radio1.isChecked()) {

            if (spinnertext.equals("Pick a time")) {
                Toast.makeText(this, "You Must Pick A  Time", Toast.LENGTH_SHORT).show();
            } else {




                repository.getAllData().observe(this, new Observer<List<ModelCartRoom>>() {
                    @Override
                    public void onChanged(List<ModelCartRoom> modelCartRooms) {


                        arrayList.addAll(modelCartRooms);

                        String ordernumberselfservice = getOrderNumberGenerator();

                        Intent intent = getIntent();
                        String subtotal = intent.getStringExtra("subtotall");
                        String discount = intent.getStringExtra("discountt");
                        int total = intent.getIntExtra("totall",0);

                        int totall = total+50;


                        String phone = sharedPreferance.getPhone();

                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy hh.mm.ss aa");
                        String formattedDate = dateFormat.format(new Date()).toString();

                        for (i = 0; i < arrayList.size(); i++) {

                            /*  String phone = sharedPreferance.getPhone();

                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy hh.mm.ss aa");
                            String formattedDate = dateFormat.format(new Date()).toString();
                           */


                            ModelOrderProduct modelOrderProduct = new ModelOrderProduct();
                            modelOrderProduct.setPhone(phone);
                            modelOrderProduct.setProduct_name(modelCartRooms.get(i).getP_name());
                            modelOrderProduct.setQuantity(Integer.parseInt(modelCartRooms.get(i).getQuantity()));
                            modelOrderProduct.setInvoice_number(ordernumberselfservice);
                            modelOrderProduct.setPrice(Integer.parseInt(modelCartRooms.get(i).getP_price()));
                            modelOrderProduct.setShop_name(modelCartRooms.get(i).getC_logo());
                            modelOrderProduct.setSubtotal(subtotal);
                            modelOrderProduct.setDiscount(discount);
                            modelOrderProduct.setTotal(""+totall);
                            modelOrderProduct.setImage_url(modelCartRooms.get(i).getUrl());
                            modelOrderProduct.setDelivery_time(spinnertext);
                            modelOrderProduct.setDelivery_type("Home Delivery");
                            modelOrderProduct.setEntry_time(formattedDate);


                            apiInterface.userOrderInsert(modelOrderProduct).enqueue(new Callback<ModelOrderProduct>() {
                                @Override
                                public void onResponse(Call<ModelOrderProduct> call, Response<ModelOrderProduct> response) {
                                    if (response.isSuccessful()) {

                                       /* Intent intent = new Intent(DeliveryActivity.this,ConfirmOrderActivity.class);
                                        intent.putExtra("order_id",ordernumberselfservice);
                                        intent.putExtra("phone",phone);
                                        intent.putExtra("delivery_type","Home Delivery");
                                        intent.putExtra("subtotall",subtotal);
                                        intent.putExtra("discountt",discount);
                                        intent.putExtra("totall",totall);
                                        intent.putExtra("orderdate",formattedDate);
                                        startActivity(intent);*/


                                    } else {
                                        Toast.makeText(DeliveryActivity.this, "Order Not Confirmed", Toast.LENGTH_SHORT).show();
                                    }
                                    //Toast.makeText(DeliveryActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(Call<ModelOrderProduct> call, Throwable t) {

                                    View parentLayout = findViewById(android.R.id.content);
                                    Snackbar mSnackBar = Snackbar.make(parentLayout, "Check the internet connection !", Snackbar.LENGTH_LONG);
                                    View view = mSnackBar.getView();
                                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
                                    params.gravity = Gravity.TOP;
                                    view.setLayoutParams(params);
                                    view.setBackgroundColor(Color.RED);
                                    TextView mainTextView = (TextView) (view).findViewById(R.id.snackbar_text);
                                    mainTextView.setTextColor(Color.WHITE);
                                    mSnackBar.show();

                                }
                            });



                        }


                         intent = new Intent(DeliveryActivity.this,ConfirmOrderActivity.class);
                        intent.putExtra("order_id",ordernumberselfservice);
                        intent.putExtra("phone",phone);
                        intent.putExtra("delivery_type","Home Delivery");
                        intent.putExtra("subtotall",subtotal);
                        intent.putExtra("discountt",discount);
                        intent.putExtra("totall",totall);
                        intent.putExtra("orderdate",formattedDate);
                        startActivity(intent);

                      // Toast.makeText(DeliveryActivity.this, "Order confirmed", Toast.LENGTH_SHORT).show();


                    }
                });



            }

        } else {



            repository.getAllData().observe(this, new Observer<List<ModelCartRoom>>() {
                @Override
                public void onChanged(List<ModelCartRoom> modelCartRooms) {


                    arrayList.addAll(modelCartRooms);

                    String ordernumberselfservice = getOrderNumberGenerator();

                    Intent intent = getIntent();
                    String subtotal = intent.getStringExtra("subtotall");
                    String discount = intent.getStringExtra("discountt");
                    int total = intent.getIntExtra("totall",0);

                    String phone = sharedPreferance.getPhone();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy      hh.mm.ss aa");
                    String formattedDate = dateFormat.format(new Date()).toString();

                    for (i = 0; i < arrayList.size(); i++) {



                        ModelOrderProduct modelOrderProduct = new ModelOrderProduct();
                        modelOrderProduct.setPhone(phone);
                        modelOrderProduct.setProduct_name(modelCartRooms.get(i).getP_name());
                        modelOrderProduct.setQuantity(Integer.parseInt(modelCartRooms.get(i).getQuantity()));
                        modelOrderProduct.setInvoice_number(ordernumberselfservice);
                        modelOrderProduct.setPrice(Integer.parseInt(modelCartRooms.get(i).getP_price()));
                        modelOrderProduct.setShop_name(modelCartRooms.get(i).getC_logo());
                        modelOrderProduct.setSubtotal(subtotal);
                        modelOrderProduct.setDiscount(discount);
                        modelOrderProduct.setTotal(""+total);
                        modelOrderProduct.setImage_url(modelCartRooms.get(i).getUrl());
                        modelOrderProduct.setDelivery_time("Any Time");
                        modelOrderProduct.setDelivery_type("Self Service");
                        modelOrderProduct.setEntry_time(formattedDate);

                        apiInterface.userOrderInsert(modelOrderProduct).enqueue(new Callback<ModelOrderProduct>() {
                            @Override
                            public void onResponse(Call<ModelOrderProduct> call, Response<ModelOrderProduct> response) {
                                if (response.isSuccessful()) {

                                    /*Intent intent = new Intent(DeliveryActivity.this,ConfirmOrderActivity.class);
                                    intent.putExtra("order_id",ordernumberselfservice);
                                    intent.putExtra("phone",phone);
                                    intent.putExtra("delivery_type","Self Service");
                                    intent.putExtra("subtotall",subtotal);
                                    intent.putExtra("discountt",discount);
                                    intent.putExtra("totall",total);
                                    intent.putExtra("orderdate",formattedDate);
                                    startActivity(intent);*/

                                } else {
                                    Toast.makeText(DeliveryActivity.this, "Order Not Confirmed", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onFailure(Call<ModelOrderProduct> call, Throwable t) {

                                View parentLayout = findViewById(android.R.id.content);
                                Snackbar mSnackBar = Snackbar.make(parentLayout, "Check the internet connection !", Snackbar.LENGTH_LONG);
                                View view = mSnackBar.getView();
                                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
                                params.gravity = Gravity.TOP;
                                view.setLayoutParams(params);
                                view.setBackgroundColor(Color.RED);
                                TextView mainTextView = (TextView) (view).findViewById(R.id.snackbar_text);
                                mainTextView.setTextColor(Color.WHITE);
                                mSnackBar.show();

                            }
                        });




                    }

                    intent = new Intent(DeliveryActivity.this,ConfirmOrderActivity.class);
                    intent.putExtra("order_id",ordernumberselfservice);
                    intent.putExtra("phone",phone);
                    intent.putExtra("delivery_type","Self Service");
                    intent.putExtra("subtotall",subtotal);
                    intent.putExtra("discountt",discount);
                    intent.putExtra("totall",total);
                    intent.putExtra("orderdate",formattedDate);
                    startActivity(intent);

                  //  Toast.makeText(DeliveryActivity.this, "Order confirmed", Toast.LENGTH_SHORT).show();


                }
            });


        }
    }

    public static String getOrderNumberGenerator() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }


}