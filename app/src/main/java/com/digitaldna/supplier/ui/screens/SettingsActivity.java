package com.digitaldna.supplier.ui.screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.digitaldna.supplier.R;
import com.digitaldna.supplier.SplashActivity;
import com.digitaldna.supplier.network.NetworkAPIsInterface;
import com.digitaldna.supplier.network.RestClient;
import com.digitaldna.supplier.network.beans.GetLoginBean;
import com.digitaldna.supplier.network.beans.GetShopInfoBean;
import com.digitaldna.supplier.network.beans.LoginSupplierBean;
import com.digitaldna.supplier.network.requests.BasicRequest;
import com.digitaldna.supplier.network.requests.SetShopInformationRequest;
import com.digitaldna.supplier.ui.screens.settings.ChangeEmailActivity;
import com.digitaldna.supplier.ui.screens.settings.ChangeLanguageActivity;
import com.digitaldna.supplier.ui.screens.settings.ChangeNameActivity;
import com.digitaldna.supplier.ui.screens.settings.ChangePasswordActivity;
import com.digitaldna.supplier.ui.screens.settings.ChangePhoneActivity;
import com.digitaldna.supplier.utils.ImageToCircleTransform;
import com.digitaldna.supplier.utils.PrefProvider;
import com.squareup.picasso.Picasso;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SettingsActivity extends Activity {
    TextView tvSupplierTitle;
    TextView tvShopName;
    TextView tvEmail;
    TextView tvPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Log.i("consolesss", "ShopName" + PrefProvider.getShopName(this));
        Log.i("consolesss", "ShopName" + PrefProvider.getSupplierTitle(this));

        RelativeLayout rlShopName = (RelativeLayout)findViewById(R.id.rl_shopname_container);
        rlShopName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, ChangeNameActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout rlEmail = (RelativeLayout)findViewById(R.id.rv_email_container);
        rlEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, ChangeEmailActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout rlPhoneNumber = (RelativeLayout)findViewById(R.id.rl_phone_container);
        rlPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, ChangePhoneActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout rlLanugae = (RelativeLayout)findViewById(R.id.rlLanguage);
        rlLanugae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, ChangeLanguageActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout rlPassword = (RelativeLayout)findViewById(R.id.rlPassword);
        rlPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

        ImageView ivProfilePicture = (ImageView)findViewById(R.id.iv_avatar);

        try {
           Picasso.with(this)
                   .load(PrefProvider.getProfilePictureURL(this))
                   .placeholder(R.drawable.svg_ic_commend_rating_blue_40dp)
                   .error(R.drawable.svg_ic_commend_rating_blue_40dp)
                   .transform(new ImageToCircleTransform())
                   .into(ivProfilePicture);
        }catch (Exception e) {
            Log.i("LLL", "Picasso exc " + e);
        }
        tvSupplierTitle = (TextView)findViewById(R.id.tv_full_name);
        tvShopName = (TextView)findViewById(R.id.tv_shop_name);
        tvEmail = (TextView)findViewById(R.id.tv_email);
        tvPhone = (TextView)findViewById(R.id.tv_phonenumber);
        tvPhone.setText(PrefProvider.getPhoneNumber(this));

        Log.i("LANGGG", " " + PrefProvider.getLanguageId(this));
        TextView tvLanguage = (TextView)findViewById(R.id.tv_language);
        if(PrefProvider.getLanguageId(this) == 0){
            tvLanguage.setText("Türkçe");
        } else {
            tvLanguage.setText("English");
        }

        ImageView ivMenu = (ImageView)findViewById(R.id.iv_toolbar_menu);
        ivMenu.setOnClickListener(view -> {
            this.finish();
        });

        Button btnLogOut = (Button)findViewById(R.id.b_sign_out);
        btnLogOut.setOnClickListener(view -> {
            PrefProvider.logOut(this);
            Intent intent = new Intent(this, SplashActivity.class);
            startActivity(intent);
        });

        BasicRequest getInfoRequest = new BasicRequest(PrefProvider.getEmail(getApplicationContext()),
                PrefProvider.getTicket(getApplicationContext()));

        RestClient.getInstance().create(NetworkAPIsInterface.class).getShopInfo(getInfoRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(result -> result != null)
                .subscribe(result -> handleResult(result) , e -> handleError(e));
    }

    @Override
    protected void onResume() {
        super.onResume();
        tvSupplierTitle.setText(PrefProvider.getShopName(this));
        tvShopName.setText(PrefProvider.getSupplierTitle(this));
        tvEmail.setText(PrefProvider.getEmail(this));

    }

    private void handleResult(GetShopInfoBean res){
       // PrefProvider.saveA(this, res.getData().getAreaCode());
        Log.i("IIIII", res.getData().getPhoneNumber());
        Log.i("IIIII", " getGsmNumber " + res.getData().getGsmNumber());
        PrefProvider.savePhoneNumber(this, res.getData().getPhoneNumber());
        Log.i("IIIII", " 1 " + res.getData().getGsmNumber());
        PrefProvider.saveCountryID(this, res.getData().getCountryID());
        Log.i("IIIII", " 2 " + res.getData().getGsmNumber());
        PrefProvider.saveGsmNumberCountryID(this, res.getData().getGsmNumberCountryID());
        Log.i("IIIII", " 3 " + res.getData().getGsmNumber());
        PrefProvider.saveGsmNumber(this, res.getData().getGsmNumber());
        Log.i("IIIII", "PrefProvider.getPhoneNumber(this)" + PrefProvider.getPhoneNumber(this));

    }

    private void handleError(Throwable t){

    }
}
