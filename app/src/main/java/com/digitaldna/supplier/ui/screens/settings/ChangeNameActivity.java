package com.digitaldna.supplier.ui.screens.settings;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.digitaldna.supplier.R;
import com.digitaldna.supplier.network.NetworkAPIsInterface;
import com.digitaldna.supplier.network.RestClient;
import com.digitaldna.supplier.network.beans.GetLoginBean;
import com.digitaldna.supplier.network.requests.LoginRequest;
import com.digitaldna.supplier.network.requests.SetShopInformationRequest;
import com.digitaldna.supplier.utils.PrefProvider;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ChangeNameActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_name);

        EditText etShortName = (EditText)findViewById(R.id.et_short_name);
        EditText etFullShopName = (EditText)findViewById(R.id.et_new_shop_name);

        etShortName.setText(PrefProvider.getShopName(this));
        etFullShopName.setText(PrefProvider.getSupplierTitle(this));

        Button btnSave = (Button)findViewById(R.id.b_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetShopInformationRequest setInfroRequest = new SetShopInformationRequest("new title",
                        PrefProvider.getShopName(getApplicationContext()),
                        PrefProvider.getEmail(getApplicationContext()),
                        PrefProvider.getCountryID(getApplicationContext()),
                        PrefProvider.getPhoneNumber(getApplicationContext()),
                        PrefProvider.getEmail(getApplicationContext()),
                        PrefProvider.getTicket(getApplicationContext()));

                RestClient.getInstance().create(NetworkAPIsInterface.class).setShopInfo(setInfroRequest)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .filter(result -> result != null)
                        .subscribe(result -> handleResult(result) , e -> handleError(e));
            }
        });

    }
    private void handleResult(GetLoginBean res){

    }
    private void handleError(Throwable t){

    }
}
