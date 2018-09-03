package com.digitaldna.supplier.ui.screens.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.digitaldna.supplier.R;
import com.digitaldna.supplier.network.NetworkAPIsInterface;
import com.digitaldna.supplier.network.RestClient;
import com.digitaldna.supplier.network.beans.GetLoginBean;
import com.digitaldna.supplier.network.requests.LoginRequest;
import com.digitaldna.supplier.network.requests.SetShopInformationRequest;
import com.digitaldna.supplier.ui.screens.SettingsActivity;
import com.digitaldna.supplier.ui.screens.authorization.SmsVerificationActivity;
import com.digitaldna.supplier.utils.PrefProvider;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ChangePhoneActivity extends Activity {
    EditText etPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phone);

        etPhone = (EditText)findViewById(R.id.et_phone);

        etPhone.setText(PrefProvider.getPhoneNumber(this));

        Button btnSave = (Button)findViewById(R.id.b_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetShopInformationRequest setInfroRequest = new SetShopInformationRequest(PrefProvider.getSupplierTitle(getApplicationContext()),
                        PrefProvider.getShopName(getApplicationContext()),
                        PrefProvider.getEmail(getApplicationContext()),
                        PrefProvider.getGsmNumberCountryID(getApplicationContext()),
                        etPhone.getText().toString(),
                        PrefProvider.getEmail(getApplicationContext()),
                        PrefProvider.getTicket(getApplicationContext()));

                RestClient.getInstance().create(NetworkAPIsInterface.class).setShopInfo(setInfroRequest)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .filter(result -> result != null)
                        .subscribe(result -> handleResult(result) , e -> handleError(e));
            }
        });
        ImageView imageView = (ImageView)findViewById(R.id.iv_toolbar_close);
        imageView.setOnClickListener(view -> onBackPressed());
    }
    private void handleResult(GetLoginBean res){
        PrefProvider.saveTicket(this, res.getData().getTicket());
        PrefProvider.savePhoneNumber(this, etPhone.getText().toString());

        // PrefProvider.saveShopName(this, etShortName.getText().toString());
        //  PrefProvider.saveSupplierTitle(this, etFullShopName.getText().toString());
        Log.i("SSSSSS", "success" + res.getData().getTicket());

        this.finish();

        Intent intent = new Intent(this, SmsVerificationActivity.class);
        startActivity(intent);
    }
    private void handleError(Throwable t){
        Log.i("SSSSSS", "error" + t.getMessage());
    }
}
