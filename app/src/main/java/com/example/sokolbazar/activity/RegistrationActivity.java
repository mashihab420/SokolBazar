package com.example.sokolbazar.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.sokolbazar.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {
    EditText name,phone,address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        name = findViewById(R.id.editTextTextPersonName2);
        phone = findViewById(R.id.editTextTextPersonName3);
        address = findViewById(R.id.editTextTextPersonName4);
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

    /*
    public void check_data()
    {

        Model_Owner model_owner = new Model_Owner();
        model_owner.setOwner_phone(owner_phone.getText().toString());
        sign_up_progress.setVisibility(View.VISIBLE);
        create.setVisibility(View.GONE);

        apiInterface.check_data(owner_phone.getText().toString()).enqueue(new Callback<Model_Owner>() {
            @Override
            public void onResponse(Call<Model_Owner> call, Response<Model_Owner> response) {

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

                    sign_up_progress.setVisibility(View.GONE);

                    create.setVisibility(View.VISIBLE);
                }
                else {

                    sign_up_progress.setVisibility(View.GONE);
                    create.setVisibility(View.VISIBLE);
                    Intent intent=new Intent(getApplicationContext(), Verify_number.class);
                    intent.putExtra("owner_name",owner_name.getText().toString());
                    intent.putExtra("owner_phone",owner_phone.getText().toString());
                    intent.putExtra("owner_email",owner_email.getText().toString());
                    intent.putExtra("owner_campus",sharedPreferance.getcampus());
                    intent.putExtra("owner_dept",sharedPreferance.getdept());
                    startActivity(intent);

                }

            }

            @Override
            public void onFailure(Call<Model_Owner> call, Throwable t) {


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
             //   create.setVisibility(View.VISIBLE);


            }
        });


    }
*/
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

            //check_data();
        }

    }
}