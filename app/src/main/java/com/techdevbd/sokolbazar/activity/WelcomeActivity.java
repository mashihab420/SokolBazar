package com.techdevbd.sokolbazar.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.techdevbd.sokolbazar.MysharedPreferance;
import com.techdevbd.sokolbazar.R;

public class WelcomeActivity extends AppCompatActivity {
MysharedPreferance sharedPreferance;
TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        textView = findViewById(R.id.textView16);

        sharedPreferance = MysharedPreferance.getPreferences(getApplicationContext());

        String name = sharedPreferance.getName();
        String phone = sharedPreferance.getPhone();
        String address = sharedPreferance.getAddress();

        textView.setText(name+ "  "+phone+"  "+address);




    }

    public void logoutbutton(View view) {

        sharedPreferance.setName("none");
        sharedPreferance.setPhone("none");
        sharedPreferance.setAddress("none");

        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
         intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }
}