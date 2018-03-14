package com.digitaldna.supplier.ui.screens.authorization;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.digitaldna.supplier.R;
import com.digitaldna.supplier.network.NetworkAPIsInterface;
import com.digitaldna.supplier.network.StoreCoupons;
import com.digitaldna.supplier.network.Urls;
import com.digitaldna.supplier.network.beans.GetLoginBean;
import com.digitaldna.supplier.network.requests.LoginRequest;
import com.digitaldna.supplier.ui.screens.MainActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
//import retrofit2.converter.gson.GsonConverterFactory;

public class EnterEmailActivity extends Activity {
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_enter);

        EditText etEmail = (EditText)findViewById(R.id.editTextEmail);

        Button btnNext = (Button)findViewById(R.id.buttonNext);
        btnNext.setOnClickListener(view -> {
            //connectToServer();
            openMain();
        });



        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Urls.HOST_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    private void handleResult(GetLoginBean getLoginBean){
        Log.i("LLL", "store " + getLoginBean.toString());
    }
    private void handleError(Throwable t){
        Log.i("LLL", "error "+ t.toString());
    }

    private void openMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void connectToServer(){
        LoginRequest loginRequest = new LoginRequest("taylan285@yandex.com", "123456");

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient=new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        retrofit.create(NetworkAPIsInterface.class).login(loginRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResult, this::handleError);
    }



}
