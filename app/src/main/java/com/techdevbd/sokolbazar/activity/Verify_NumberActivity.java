package com.techdevbd.sokolbazar.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.VisualVoicemailService;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.techdevbd.sokolbazar.MysharedPreferance;
import com.techdevbd.sokolbazar.R;
import com.techdevbd.sokolbazar.model.ModelUsers;
import com.techdevbd.sokolbazar.retrofit.ApiClient;
import com.techdevbd.sokolbazar.retrofit.ApiInterface;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Verify_NumberActivity extends AppCompatActivity {


    TextView phone ;
    TextView et_varification;
    ImageView back;

    ApiInterface apiInterface;
    ProgressBar progressBar;

    MysharedPreferance sharedPreferance;
    private String mVerificationId;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify__number);

        phone=findViewById(R.id.tt_verify_number);
        progressBar=findViewById(R.id.progressBar_verify);
        et_varification=findViewById(R.id.sign_up_verify_code);


        apiInterface = ApiClient.getApiInterface();
        sharedPreferance = MysharedPreferance.getPreferences(getApplicationContext());
        phone.setText(getIntent().getStringExtra("phone"));
        mAuth = FirebaseAuth.getInstance();



        hideKeyboard(Verify_NumberActivity.this);
        progressBar.setVisibility(View.VISIBLE);

        sendVerificationCode(phone.getText().toString());






    }


    private void sendVerificationCode(String mobile) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+880" + mobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }


    //the callback to detect the verification status
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            String code = phoneAuthCredential.getSmsCode();

            if (code != null) {
                et_varification.setText(code);

                progressBar.setVisibility(View.VISIBLE);

                verifyVerificationCode(et_varification.getText().toString());

            }



        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(Verify_NumberActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            //storing the verification id that is sent to the user
            mVerificationId = s;
        }
    };



    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    private void verifyVerificationCode(String otp) {
        //creating the credential
        progressBar.setVisibility(View.VISIBLE);
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otp);

        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(Verify_NumberActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                            ModelUsers modelUsers =new ModelUsers();
                            modelUsers.setName(getIntent().getStringExtra("name"));
                            modelUsers.setPhone(getIntent().getStringExtra("phone"));
                            modelUsers.setPassword(getIntent().getStringExtra("password"));
                            modelUsers.setAddress(getIntent().getStringExtra("address"));


                            String name =  getIntent().getStringExtra("name");
                            String phone =  getIntent().getStringExtra("phone");
                            String address =  getIntent().getStringExtra("address");

                            sharedPreferance.setName(name);
                            sharedPreferance.setPhone(phone);
                            sharedPreferance.setAddress(address);

                            sendData(modelUsers);

                            Intent intent = getIntent();
                            String subtotal = intent.getStringExtra("subtotall");
                            String discount = intent.getStringExtra("discountt");
                            int total = intent.getIntExtra("totall",0);

                            intent = new Intent(Verify_NumberActivity.this,DeliveryActivity.class);
                            intent.putExtra("subtotall", subtotal);
                            intent.putExtra("discountt", discount);
                            intent.putExtra("totall", total);
                            startActivity(intent);


                        } else {


                            progressBar.setVisibility(View.GONE);
                            View parentLayout = findViewById(android.R.id.content);
                            Snackbar mSnackBar = Snackbar.make(parentLayout, "Invalid code !!", Snackbar.LENGTH_LONG);
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
                });
    }

    private void sendData(ModelUsers modelUsers) {

        apiInterface.addUsers(modelUsers).enqueue(new Callback<ModelUsers>() {
            @Override
            public void onResponse(Call<ModelUsers> call, Response<ModelUsers> response) {




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


    public void backregpage(View view) {
        onBackPressed();
    }
}