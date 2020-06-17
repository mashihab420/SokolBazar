package com.example.sokolbazar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.sokolbazar.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener , View.OnClickListener {
    private static String TAG="MainActivity";
    private ActivityMainBinding binding;
    FragmentHome fragmentHome;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.include.toolbar);

        actionBarDrawerToggle=new ActionBarDrawerToggle(this,
                binding.drawerLayout,binding.include.toolbar,R.string.open, R.string.close);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getColor(R.color.black));



        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        binding.navigationView.setNavigationItemSelectedListener(this);

        binding.hideNav.setOnClickListener(this);
        binding.additem.setOnClickListener(this);
        binding.category.setOnClickListener(this);
        binding.home.setOnClickListener(this);
        binding.logout.setOnClickListener(this);
        binding.profile.setOnClickListener(this);
        binding.setting.setOnClickListener(this);

        initFragmentHome();
    }

    private void initFragmentHome(){
        fragmentHome=new FragmentHome();
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(binding.include.contentMain.fragmentContainer.getId(),fragmentHome,"FragmentHome").commit();

    }

    @Override
    public void onClick(View v) {

        Log.d(TAG, "onClick: clicked");
        switch (v.getId()){
            case R.id.hide_nav:
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.additem:

                break;
            case R.id.profile:
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting:
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();
                break;
            case R.id.category:

                break;
            case R.id.logout:
                Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show();
                break;
            default:
                Log.d(TAG, "onClick: default");

        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }
}