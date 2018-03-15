package com.digitaldna.supplier.ui.screens.authorization;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.digitaldna.supplier.R;
import com.digitaldna.supplier.network.NetworkAPIsInterface;

import com.digitaldna.supplier.network.Urls;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
//import retrofit2.converter.gson.GsonConverterFactory;


public class EnterPasswordActivity extends Activity {
    String email;
    Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_password);

        email = getIntent().getExtras().getString("email");
        EditText etPassword = (EditText)findViewById(R.id.editTextPass);

        Button btnNext = (Button)findViewById(R.id.buttonNextPass);
        btnNext.setOnClickListener(view -> {
            Log.i("EEE", email + etPassword.getText());
            connectToServer();
            /*Intent intent = new Intent(this, EnterPasswordActivity.class);
            intent.putExtra("email", etEmail.getText());
            startActivity(intent);*/
        });

        retrofit = new Retrofit.Builder()
                .baseUrl(Urls.HOST_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

   /* private void handleResult(StoreCoupons storeCoupons){
        Log.i("LLL", "store cop" + storeCoupons.toString());
    }*/
    private void handleError(Throwable t){
        Log.i("LLL", ""+ t.toString());
    }


    private void connectToServer(){
/*
retrofit.create(NetworkAPIsInterface.class).getCoupons("status")
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::handleResult, this::handleError);*/
    }
}
