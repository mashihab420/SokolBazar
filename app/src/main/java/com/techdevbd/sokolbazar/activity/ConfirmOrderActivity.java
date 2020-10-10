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
import com.techdevbd.sokolbazar.model.ModelCartRoom;
import com.techdevbd.sokolbazar.model.ModelOrdersRoom;
import com.techdevbd.sokolbazar.repository.CartRepository;
import com.techdevbd.sokolbazar.repository.OrdersRepository;

import java.io.File;
import java.io.FileNotFoundException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class ConfirmOrderActivity extends AppCompatActivity {

    TextView orderidTextview,qrcodeforshopping,ordernumber,invoiceid,deliverytypee,subtotall,discountt,totalll,deliverytaka;
    ImageView imageView,backiconbt;
    Button button,mainmenu;
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
        mainmenu = findViewById(R.id.mainmenu_when_homedelivery);
        deliverytypee = findViewById(R.id.textView23);
        backiconbt = findViewById(R.id.backicon);
        deliverytaka = findViewById(R.id.deliveryfeeid);

        relativeLayout = findViewById(R.id.relativeLayout1);
        orderidTextview = findViewById(R.id.orderidtv);

        subtotall = findViewById(R.id.subtotalid);
        discountt = findViewById(R.id.discountid);
        totalll = findViewById(R.id.totalid);

        Intent intent = getIntent();
        String orderid = intent.getStringExtra("order_id");
        String phone = intent.getStringExtra("phone");
        String deliverytype = intent.getStringExtra("delivery_type");

        String subtotal = intent.getStringExtra("subtotall");
        String discount = intent.getStringExtra("discountt");


        int total = intent.getIntExtra("totall",0);

        subtotall.setText(subtotal+" BDT");
        discountt.setText(discount+" BDT");
        totalll.setText(total+" BDT");


        if (deliverytype.equals("Home Delivery")){
            deliverytaka.setText("50 BDT");

        }else {
            deliverytaka.setText("0 BDT");

        }

        orderidTextview.setText("Order");
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

            button.setVisibility(View.VISIBLE);

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

        }


        backiconbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


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
        //Toast.makeText(getApplicationContext(), "QR Code Save to Internal Storage", Toast.LENGTH_SHORT).show();
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
        final OrdersRepository repository = new OrdersRepository(getApplicationContext());
        Intent intent = getIntent();
        String orderid = intent.getStringExtra("order_id");
        String phone = intent.getStringExtra("phone");
        String deliverytype = intent.getStringExtra("delivery_type");
        String datetime = intent.getStringExtra("orderdate");
        repository.insertSingleData(new ModelOrdersRoom(phone,orderid,datetime,deliverytype));
        intent = new Intent(ConfirmOrderActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = getIntent();
        String deliverytype = intent.getStringExtra("delivery_type");

        saveimage();

        final OrdersRepository repository = new OrdersRepository(getApplicationContext());

        String orderid = intent.getStringExtra("order_id");
        String phone = intent.getStringExtra("phone");

        String datetime = intent.getStringExtra("orderdate");
        repository.insertSingleData(new ModelOrdersRoom(phone,orderid,datetime,deliverytype));
        intent = new Intent(ConfirmOrderActivity.this,CartActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

        /*if (deliverytype.equals("Self Service")){

        }else {
            saveimage();
            final OrdersRepository repository = new OrdersRepository(getApplicationContext());

            String orderid = intent.getStringExtra("order_id");
            String phone = intent.getStringExtra("phone");

            String datetime = intent.getStringExtra("orderdate");
            repository.insertSingleData(new ModelOrdersRoom(phone,orderid,datetime,deliverytype));
            intent = new Intent(ConfirmOrderActivity.this,CartActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }*/


    }

    public void mainmenu_HD(View view) {

        Intent intent = new Intent(ConfirmOrderActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}