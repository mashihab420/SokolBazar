package com.techdevbd.sokolbazar.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.techdevbd.sokolbazar.R;
import com.techdevbd.sokolbazar.SingleMediaScanner;

import java.io.File;
import java.io.FileNotFoundException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class ConfirmOrderActivity extends AppCompatActivity {

    TextView orderidTextview,qrcodeforshopping,ordernumber,invoiceid,deliverytypee;
    ImageView imageView;
    Button button;
    OutputStream outputStream;
    RelativeLayout relativeLayout = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        imageView = findViewById(R.id.imageView);
        ordernumber = findViewById(R.id.textView21);
        qrcodeforshopping = findViewById(R.id.textView24);
        invoiceid = findViewById(R.id.invoiceid);
        button = findViewById(R.id.button2);
        deliverytypee = findViewById(R.id.textView23);

        relativeLayout = findViewById(R.id.relativeLayout1);
        orderidTextview = findViewById(R.id.orderidtv);

        Intent intent = getIntent();
        String orderid = intent.getStringExtra("order_id");
        String phone = intent.getStringExtra("phone");
        String deliverytype = intent.getStringExtra("delivery_type");


        orderidTextview.setText("Order #"+orderid);
        ordernumber.setText("Order number #"+orderid);

        deliverytypee.setText(deliverytype);

        invoiceid.setText("Order number #"+orderid);

        if (deliverytype.equals("Self Service")){
            String text = orderid+","+phone;

            QRGEncoder qrgEncoder = new QRGEncoder(text,null, QRGContents.Type.TEXT,500);
            try{
                Bitmap qrbits = qrgEncoder.getBitmap();
                imageView.setImageBitmap(qrbits);

            }catch (Exception e)
            {
                e.printStackTrace();
            }
            qrcodeforshopping.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.VISIBLE);




        }else {
           relativeLayout.setVisibility(View.GONE);
            qrcodeforshopping.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);
            button.setVisibility(View.GONE);
        }



    }



    private void saveimage() {

        Intent intent = getIntent();
        String orderid = intent.getStringExtra("order_id");
        String phone = intent.getStringExtra("phone");

        Bitmap bitmap = Bitmap.createBitmap(relativeLayout.getWidth(), relativeLayout.getHeight(),
                Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);

        relativeLayout.draw(canvas);


        File filepath = Environment.getExternalStorageDirectory();
        File dir = new File(filepath.getAbsolutePath() + "/SokolBazar/");
        dir.mkdir();
        File file = new File(dir, orderid+"_"+phone+ ".jpg");


        try {
            outputStream = new FileOutputStream(file);
            new SingleMediaScanner(this, file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        Toast.makeText(getApplicationContext(), "QR Code Save to Internal Storage", Toast.LENGTH_SHORT).show();
        try {
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void savebtn(View view) {

        saveimage();
    }
}