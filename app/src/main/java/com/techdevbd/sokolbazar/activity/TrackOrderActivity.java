package com.techdevbd.sokolbazar.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.techdevbd.sokolbazar.R;
import com.techdevbd.sokolbazar.databinding.ActivityMainBinding;
import com.techdevbd.sokolbazar.databinding.ActivityTrackOrderBinding;
import com.techdevbd.sokolbazar.model.ModelOrderProduct;
import com.techdevbd.sokolbazar.model.ModelUsers;
import com.techdevbd.sokolbazar.retrofit.ApiClient;
import com.techdevbd.sokolbazar.retrofit.ApiInterface;

import java.util.List;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrackOrderActivity extends AppCompatActivity {
    ImageView imageView;
    TextView ordernumber, readytopick, readytopickd_details, qrinvoiceid;
    ApiInterface apiInterface;
    SwipeRefreshLayout swipeRefreshLayout;
    ActivityTrackOrderBinding binding;
    String orderid;
    String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_track_order);
        binding = ActivityTrackOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        swipeRefreshLayout = findViewById(R.id.sqipeRefreshId);
        imageView = findViewById(R.id.imageView);
        readytopick = findViewById(R.id.textView37);
        readytopickd_details = findViewById(R.id.textView41);
        ordernumber = findViewById(R.id.ordernumber_id);
        qrinvoiceid = findViewById(R.id.invoiceid);
        apiInterface = ApiClient.getApiInterface();
        Intent intent = getIntent();
        orderid = intent.getStringExtra("order_id");
        String phone = intent.getStringExtra("phone");
        String deliverytype = intent.getStringExtra("delivery_type");
        String orderotpnum = intent.getStringExtra("orders_otp");

        ordernumber.setText("#" + orderid);

        //   String text = orderid + "," + phone;

       /* QRGEncoder qrgEncoder = new QRGEncoder(text, null, QRGContents.Type.TEXT, 500);
        try {
            Bitmap qrbits = qrgEncoder.getBitmap();
            imageView.setImageBitmap(qrbits);

        } catch (Exception e) {
            e.printStackTrace();
        }*/

        if (deliverytype.equals("Home Delivery")) {
            ordernumber.setText("Order OTP#" + orderotpnum);
            readytopick.setText("Ready to Delivered");
            readytopickd_details.setText("Your order is ready for Delivered.");
            String text = orderotpnum;
            qrinvoiceid.setText("Order OTP #"+text);
            QRGEncoder qrgEncoder = new QRGEncoder(text, null, QRGContents.Type.TEXT, 500);
            try {
                Bitmap qrbits = qrgEncoder.getBitmap();
                imageView.setImageBitmap(qrbits);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ordernumber.setText("Order Number#" + orderid);
            readytopick.setText("Ready to Pickup");
            readytopickd_details.setText("Your order is ready for pickup.");

            String text = orderid + "," + phone;
            qrinvoiceid.setText("Order Number #"+orderid);
            QRGEncoder qrgEncoder = new QRGEncoder(text, null, QRGContents.Type.TEXT, 500);
            try {
                Bitmap qrbits = qrgEncoder.getBitmap();
                imageView.setImageBitmap(qrbits);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        getData();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void getData() {
        ModelOrderProduct modelOrderProduct = new ModelOrderProduct();
        modelOrderProduct.setInvoice_number(orderid);

        apiInterface.get_order_status(modelOrderProduct).enqueue(new Callback<List<ModelOrderProduct>>() {
            @Override
            public void onResponse(Call<List<ModelOrderProduct>> call, Response<List<ModelOrderProduct>> response) {
                // Toast.makeText(TrackOrderActivity.this, ""+response.body().get(0).getOrderStatus(), Toast.LENGTH_SHORT).show();

                status = response.body().get(0).getOrderStatus();


                if (status.equals("recevied")) {

                    //  binding.imageCircleGreen2.setVisibility(View.VISIBLE);
                    binding.imageCircleBlue1.setVisibility(View.VISIBLE);

                    binding.textView35.setTextColor(Color.parseColor("#1C5F95"));
                    binding.textView38.setTextColor(Color.parseColor("#1C5F95"));


                }

                if (status.equals("confirmed")) {

                    binding.imageCircleGreen2.setVisibility(View.VISIBLE);
                    binding.imageLineGreen2.setVisibility(View.VISIBLE);
                    binding.imageCircleBlue2.setVisibility(View.VISIBLE);

                    binding.textView35.setTextColor(Color.parseColor("#595959"));
                    binding.textView38.setTextColor(Color.parseColor("#595959"));

                    binding.textView36.setTextColor(Color.parseColor("#1C5F95"));
                    binding.textView40.setTextColor(Color.parseColor("#1C5F95"));


                } else if (status.equals("preparing")) {

                    binding.imageCircleGreen2.setVisibility(View.VISIBLE);
                    binding.imageLineGreen2.setVisibility(View.VISIBLE);
                    binding.imageCircleGreen3.setVisibility(View.VISIBLE);
                    binding.imageCircleBlue4.setVisibility(View.VISIBLE);
                    binding.imageLineGreen3.setVisibility(View.VISIBLE);


                    binding.textView35.setTextColor(Color.parseColor("#595959"));
                    binding.textView38.setTextColor(Color.parseColor("#595959"));

                    binding.textView36.setTextColor(Color.parseColor("#595959"));
                    binding.textView40.setTextColor(Color.parseColor("#595959"));

                    binding.textView37.setTextColor(Color.parseColor("#1C5F95"));
                    binding.textView41.setTextColor(Color.parseColor("#1C5F95"));


                } else if (status.equals("ready")) {

                    binding.imageCircleGreen2.setVisibility(View.VISIBLE);
                    binding.imageLineGreen2.setVisibility(View.VISIBLE);
                    binding.imageCircleGreen3.setVisibility(View.VISIBLE);
                    binding.imageVerify.setVisibility(View.VISIBLE);
                    binding.imageLineGreen3.setVisibility(View.VISIBLE);


                    binding.textView35.setTextColor(Color.parseColor("#595959"));
                    binding.textView38.setTextColor(Color.parseColor("#595959"));

                    binding.textView36.setTextColor(Color.parseColor("#595959"));
                    binding.textView40.setTextColor(Color.parseColor("#595959"));

                    binding.textView37.setTextColor(Color.parseColor("#FE6268"));
                    binding.textView41.setTextColor(Color.parseColor("#FE6268"));


                } else if (status.equals("delivered")) {

                    binding.imageCircleGreen2.setVisibility(View.VISIBLE);
                    binding.imageLineGreen2.setVisibility(View.VISIBLE);
                    binding.imageCircleGreen3.setVisibility(View.VISIBLE);
                    binding.imageVerify.setVisibility(View.VISIBLE);
                    binding.imageLineGreen3.setVisibility(View.VISIBLE);


                    binding.textView35.setTextColor(Color.parseColor("#595959"));
                    binding.textView38.setTextColor(Color.parseColor("#595959"));

                    binding.textView36.setTextColor(Color.parseColor("#595959"));
                    binding.textView40.setTextColor(Color.parseColor("#595959"));

                    binding.textView37.setTextColor(Color.parseColor("#FE6268"));
                    binding.textView41.setTextColor(Color.parseColor("#FE6268"));

                    // binding.textView32.setText("Order Delivered");


                }


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

    public void backbtnicon(View view) {
        onBackPressed();
    }
}