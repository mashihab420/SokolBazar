package com.techdevbd.sokolbazar.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.techdevbd.sokolbazar.R;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class QRCodeGeneratorActivity extends AppCompatActivity {
    TextView qrtext;
    Button bt;
    ImageView qrcodeimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_r_code_generator);
        qrtext = findViewById(R.id.editTextTextPersonName);
        qrcodeimg = findViewById(R.id.imageView);
        bt = findViewById(R.id.button);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String text = qrtext.getText().toString();

                 if (text.isEmpty())
                {
                    qrtext.setError("value Reqquired.");
                }else{
                    QRGEncoder qrgEncoder = new QRGEncoder(text,null, QRGContents.Type.TEXT,500);
                    try{
                        Bitmap qrbits = qrgEncoder.getBitmap();
                        qrcodeimg.setImageBitmap(qrbits);
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });

    }
}