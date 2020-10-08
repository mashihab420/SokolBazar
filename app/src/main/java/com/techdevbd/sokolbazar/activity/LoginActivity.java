package com.techdevbd.sokolbazar.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.techdevbd.sokolbazar.MysharedPreferance;
import com.techdevbd.sokolbazar.R;
import com.techdevbd.sokolbazar.model.ModelUsers;
import com.techdevbd.sokolbazar.retrofit.ApiClient;
import com.techdevbd.sokolbazar.retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    
    EditText phone,pass;
    ApiInterface apiInterface;
    MysharedPreferance sharedPreferance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        phone = findViewById(R.id.editTextTextPersonName6);
        pass = findViewById(R.id.editTextTextPersonName7);
        apiInterface = ApiClient.getApiInterface();
        sharedPreferance = MysharedPreferance.getPreferences(getApplicationContext());
    }

    public void loginuserButton(View view) {
        
        String uphone = phone.getText().toString();
        String upass = pass.getText().toString();
     //   int num = Integer.parseInt(uphone);
        if (uphone.equals("")){

            phone.setError("Please enter valid number");

        }
        else if(upass.equals("")){
            pass.setError("Please enter password");
        }
        else {


            apiInterface.userlogin(uphone,upass).enqueue(new Callback<ModelUsers>() {
                @Override
                public void onResponse(Call<ModelUsers> call, Response<ModelUsers> response) {
                    if (response.body().getResponse().equals("ok"))
                    {
                        sharedPreferance.setName(response.body().getName());
                        sharedPreferance.setPhone(response.body().getPhone());
                        sharedPreferance.setAddress(response.body().getAddress());

                        Intent intent = new Intent(LoginActivity.this, DeliveryActivity.class);
                        // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }else {
                        View parentLayout = findViewById(android.R.id.content);
                        Snackbar mSnackBar = Snackbar.make(parentLayout, "Incorrect Phone number or Password!", Snackbar.LENGTH_LONG);
                        View view = mSnackBar.getView();
                        FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
                        params.gravity = Gravity.TOP;
                        view.setLayoutParams(params);
                        view.setBackgroundColor(Color.RED);
                        TextView mainTextView = (TextView) (view).findViewById(R.id.snackbar_text);
                        mainTextView.setTextColor(Color.WHITE);
                        mSnackBar.show();
                    }


                }

                @Override
                public void onFailure(Call<ModelUsers> call, Throwable t) {

                    View parentLayout = findViewById(android.R.id.content);
                    Snackbar mSnackBar = Snackbar.make(parentLayout, "Check the internet connection !", Snackbar.LENGTH_LONG);
                    View view = mSnackBar.getView();
                    FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
                    params.gravity = Gravity.TOP;
                    view.setLayoutParams(params);
                    view.setBackgroundColor(Color.RED);
                    TextView mainTextView = (TextView) (view).findViewById(R.id.snackbar_text);
                    mainTextView.setTextColor(Color.WHITE);
                    mSnackBar.show();
                }
            });


        }
        
        
        
    }
}