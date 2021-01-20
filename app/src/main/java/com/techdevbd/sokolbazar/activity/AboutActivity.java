package com.techdevbd.sokolbazar.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.techdevbd.sokolbazar.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    public void backbtnicon(View view) {
        onBackPressed();
    }

    public void btncredits(View view) {
        Intent intent = new Intent(AboutActivity.this,CreditsActivity.class);
        startActivity(intent);
    }
}