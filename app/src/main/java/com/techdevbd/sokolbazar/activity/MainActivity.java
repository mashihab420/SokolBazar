package com.techdevbd.sokolbazar.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.techdevbd.sokolbazar.MysharedPreferance;
import com.techdevbd.sokolbazar.R;
import com.techdevbd.sokolbazar.databinding.ActivityMainBinding;
import com.techdevbd.sokolbazar.fragment.FragmentHome;
import com.techdevbd.sokolbazar.model.ModelCartRoom;
import com.techdevbd.sokolbazar.model.ModelUsers;
import com.techdevbd.sokolbazar.repository.CartRepository;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener , View.OnClickListener {
    private static String TAG="MainActivity";
    private ActivityMainBinding binding;
    FragmentHome fragmentHome;
    int valu =0;
    Toolbar toolbarr;
    TextView toolbarTitle,cartQuantity;

    private ActionBarDrawerToggle actionBarDrawerToggle;
    MysharedPreferance sharedPreferance;
    CartRepository repository;
    Dialog dialog;
    boolean flip = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        toolbarr=findViewById(R.id.toolbar);
        setSupportActionBar(toolbarr);
        sharedPreferance = MysharedPreferance.getPreferences(getApplicationContext());
        cartQuantity = findViewById(R.id.cart_quantity_id);
        toolbarTitle=findViewById(R.id.toolbarTitle);
        toolbarTitle.setText("");



        setSupportActionBar(binding.include.toolbar);

        actionBarDrawerToggle=new ActionBarDrawerToggle(this,
                binding.drawerLayout,binding.include.toolbar,R.string.open, R.string.close);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getColor(R.color.black));


        dialog = new Dialog(this);
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        binding.navigationView.setNavigationItemSelectedListener(this);

        binding.hideNav.setOnClickListener(this);
        binding.ordersitem.setOnClickListener(this);
        binding.category.setOnClickListener(this);
        binding.home.setOnClickListener(this);
        binding.logout.setOnClickListener(this);
        binding.profile.setOnClickListener(this);
        binding.setting.setOnClickListener(this);

       String phone = sharedPreferance.getPhone();

       if (phone.equals("none")){
           binding.logout.setVisibility(View.GONE);
           binding.login.setVisibility(View.VISIBLE);
       }else {
           binding.logout.setVisibility(View.VISIBLE);
           binding.login.setVisibility(View.GONE);
       }
       
      binding.login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               binding.login.setVisibility(View.GONE);
               binding.logout.setVisibility(View.VISIBLE);
               Intent intent = new Intent(MainActivity.this,LoginActivity.class);
               startActivity(intent);
           }
       });


     /*  binding.logout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               if (flip==false){
                   binding.logout.setText("Log Out");
                   flip=true;
               }else {
                   binding.logout.setText("Login");
                   flip=false;
               }

           }
       });*/
      binding.logout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               binding.login.setVisibility(View.VISIBLE);
               binding.logout.setVisibility(View.GONE);

               sharedPreferance.setName("none");
               sharedPreferance.setPhone("none");
               sharedPreferance.setAddress("none");
               Toast.makeText(getApplicationContext(), "Logged Out", Toast.LENGTH_SHORT).show();

           }
       });

        initFragmentHome();


        //for cart quantity calculation start

        repository = new CartRepository(this);
        repository.getAllData().observe(this, new Observer<List<ModelCartRoom>>() {
            @Override
            public void onChanged(List<ModelCartRoom> modelCartRooms) {

                if (modelCartRooms.size()==0){
                    cartQuantity.setVisibility(View.GONE);
                }else {
                    cartQuantity.setVisibility(View.VISIBLE);
                    if(modelCartRooms.size()>99){
                        cartQuantity.setText("99+");
                    }else {
                        cartQuantity.setText(""+modelCartRooms.size());
                    }


                }

            }
        });

//for cart quantity calculation end




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.menu_cart,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: called");
        if(item.getItemId()==R.id.cartmenuid){

          /*  valu++;
            if (valu>0){
                cartQuantity.setVisibility(View.VISIBLE);
            }

            cartQuantity.setText(""+valu);

            if (valu>10){
                cartQuantity.setText("10+");
            }
            Toast.makeText(this, ""+valu, Toast.LENGTH_SHORT).show();*/
            Intent intent = new Intent(MainActivity.this, CartActivity.class);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
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
            case R.id.ordersitem:
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(MainActivity.this,OrdersActivity.class);
                startActivity(intent);
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
           /* case R.id.logout:
                sharedPreferance.setName("none");
                sharedPreferance.setPhone("none");
                sharedPreferance.setAddress("none");
                Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show();

                break;*/
            default:
                Log.d(TAG, "onClick: default");

        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }

    @Override
    public void onBackPressed() {

        openDialog();
    }

    private void openDialog() {
        dialog.setContentView(R.layout.dialog_main_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView imageViewClose = dialog.findViewById(R.id.imageView5);
        TextView no = dialog.findViewById(R.id.button6);
        TextView ok = dialog.findViewById(R.id.button7);

        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 dialog.dismiss();

            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              // onBackPressed();
               MainActivity.super.onBackPressed();
               dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
}