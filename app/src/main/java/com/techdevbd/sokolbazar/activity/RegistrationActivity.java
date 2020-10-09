package com.techdevbd.sokolbazar.activity;
import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.material.snackbar.Snackbar;
import com.techdevbd.sokolbazar.R;
import com.techdevbd.sokolbazar.model.ModelUsers;
import com.techdevbd.sokolbazar.retrofit.ApiClient;
import com.techdevbd.sokolbazar.retrofit.ApiInterface;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegistrationActivity extends AppCompatActivity {
    EditText name,phone,address,password;
    ApiInterface apiInterface;
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private ImageView imageView;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        name = findViewById(R.id.editTextTextPersonName2);
        phone = findViewById(R.id.editTextTextPersonName3);
        address = findViewById(R.id.editTextTextPersonName4);
        password = findViewById(R.id.editTextTextPersonName5);

        apiInterface = ApiClient.getApiInterface();


    }


    public static boolean validateLetters(String txt) {

        String regx = "[0-9]+\\.?";
        Pattern pattern = Pattern.compile(regx,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(txt);
        return matcher.find();

    }

    public static boolean isValidEmail(CharSequence get_owner_email  ) {


        return (Patterns.EMAIL_ADDRESS.matcher(get_owner_email).matches());
    }


    public void create_accountbt(View view) {

        if (name.getText().toString().isEmpty()|| validateLetters(name.getText().toString())) {

            name.setError("Invalid name");


        } else if (phone.getText().toString().isEmpty() ||phone.getText().toString().length()<11||phone.getText().toString().length()>11) {
            phone.setError("Invalid number");

        }

        else if (address.getText().toString().isEmpty()) {
            address.setError("Invalid Address");

        }



        else {

           check_data();
        }

    }

    private void check_data() {

        ModelUsers modelUsers = new ModelUsers();
        String phoneno = phone.getText().toString();
        String pass = password.getText().toString();
        modelUsers.setPhone(phoneno);
        modelUsers.setPassword(pass);


        apiInterface.check_data(phoneno).enqueue(new Callback<ModelUsers>() {
            @Override
            public void onResponse(Call<ModelUsers> call, Response<ModelUsers> response) {
                if (response.body().getResponse().equals("ok"))

                {

                    View parentLayout = findViewById(android.R.id.content);
                    Snackbar mSnackBar = Snackbar.make(parentLayout, "This number is already used. Please login", Snackbar.LENGTH_LONG);
                    View view = mSnackBar.getView();
                    FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
                    params.gravity = Gravity.TOP;
                    view.setLayoutParams(params);
                    view.setBackgroundColor(Color.RED);
                    TextView mainTextView = (TextView) (view).findViewById(R.id.snackbar_text);
                    mainTextView.setTextColor(Color.WHITE);
                    mSnackBar.show();

                    //sign_up_progress.setVisibility(View.GONE);

                    //create.setVisibility(View.VISIBLE);
                }else {

                   // sign_up_progress.setVisibility(View.GONE);
                  //  create.setVisibility(View.VISIBLE);
                    Intent intent = getIntent();
                    String subtotal = intent.getStringExtra("subtotall");
                    String discount = intent.getStringExtra("discountt");
                    int total = intent.getIntExtra("totall",0);

                    intent=new Intent(getApplicationContext(), Verify_NumberActivity.class);
                    intent.putExtra("name",name.getText().toString());
                    intent.putExtra("phone",phone.getText().toString());
                    intent.putExtra("password",password.getText().toString());
                    intent.putExtra("address",address.getText().toString());

                    intent.putExtra("subtotall", subtotal);
                    intent.putExtra("discountt", discount);
                    intent.putExtra("totall", total);

                    startActivity(intent);

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


               // sign_up_progress.setVisibility(View.GONE);
               // create.setVisibility(View.VISIBLE);
            }
        });




    }

    public void loginbutton(View view) {

        Intent intent = getIntent();
        String subtotal = intent.getStringExtra("subtotall");
        String discount = intent.getStringExtra("discountt");
        int total = intent.getIntExtra("totall",0);



        intent = new Intent(RegistrationActivity.this,LoginActivity.class);
        intent.putExtra("subtotall", subtotal);
        intent.putExtra("discountt", discount);
        intent.putExtra("totall", total);
        startActivity(intent);

    }
}