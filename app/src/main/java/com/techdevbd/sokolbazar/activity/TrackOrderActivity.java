package com.techdevbd.sokolbazar.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.techdevbd.sokolbazar.R;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class TrackOrderActivity extends AppCompatActivity {
    ImageView imageView;
    TextView ordernumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order);
        imageView = findViewById(R.id.imageView);
        ordernumber = findViewById(R.id.ordernumber_id);

        Intent intent = getIntent();
        String orderid = intent.getStringExtra("order_id");
        String phone = intent.getStringExtra("phone");
        String deliverytype = intent.getStringExtra("delivery_type");

        ordernumber.setText("#"+orderid);

        String text = orderid+","+phone;

        QRGEncoder qrgEncoder = new QRGEncoder(text,null, QRGContents.Type.TEXT,500);
        try{
            Bitmap qrbits = qrgEncoder.getBitmap();
            imageView.setImageBitmap(qrbits);

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}