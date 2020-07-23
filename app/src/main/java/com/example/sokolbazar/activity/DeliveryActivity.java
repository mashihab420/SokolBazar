package com.example.sokolbazar.activity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import com.example.sokolbazar.R;
import com.example.sokolbazar.databinding.ActivityDeliveryBinding;
import com.example.sokolbazar.databinding.ActivityMainBinding;

public class DeliveryActivity extends AppCompatActivity {
    ActivityDeliveryBinding binding;
    Toolbar toolbarr;
    TextView toolbarTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_delivery);

        binding= ActivityDeliveryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbarr=findViewById(R.id.toolbar);
        setSupportActionBar(toolbarr);

        toolbarTitle=findViewById(R.id.toolbarTitle);
        toolbarTitle.setText("Shihab");

        setSupportActionBar(binding.include.toolbar);



    }
}