package com.digitaldna.supplier.ui.screens.settings;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitaldna.supplier.R;
import com.digitaldna.supplier.network.NetworkAPIsInterface;
import com.digitaldna.supplier.network.RestClient;
import com.digitaldna.supplier.network.beans.GetLoginBean;
import com.digitaldna.supplier.network.requests.LoginRequest;
import com.digitaldna.supplier.network.requests.SetShopInfoPasswordRequest;
import com.digitaldna.supplier.network.requests.SetShopInformationRequest;
import com.digitaldna.supplier.ui.screens.SettingsActivity;
import com.digitaldna.supplier.ui.screens.authorization.ForgotPasswordActivity;
import com.digitaldna.supplier.utils.PrefProvider;

import org.w3c.dom.Text;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ChangePasswordActivity extends Activity {
    EditText etCurrentPassword, etNewPassword, etRepeatPassword;
    String currentPass;
    Context context;
    TextView tvClickForgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        etCurrentPassword = (EditText)findViewById(R.id.et_curren_password);
        etNewPassword = (EditText)findViewById(R.id.et_new_password1);
        etRepeatPassword = (EditText)findViewById(R.id.et_new_password2);
        tvClickForgot = (TextView) findViewById(R.id.tv_click_forgot);
        tvClickForgot.setOnClickListener(view -> {
            Intent intent = new Intent(this, ForgotPasswordActivity.class);
            startActivity(intent);
        });
        currentPass = PrefProvider.getPassword(this);
        context = this;
        Button btnSave = (Button)findViewById(R.id.b_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etCurrentPassword.getText().toString().isEmpty() || etRepeatPassword.getText().toString().isEmpty() || etNewPassword.getText().toString().isEmpty()){
                    final Dialog dialog = new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.setContentView(R.layout.dialog_info);
                    TextView text = (TextView) dialog.findViewById(R.id.textViewErrorMessage);
                    text.setText(context.getResources().getString(R.string.please_fill_in_all_fields));
                    Button dialogButton = (Button) dialog.findViewById(R.id.buttonOK);
                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                } else {

                if(etCurrentPassword.getText().toString().equals(currentPass)) {
                    if(etNewPassword.getText().toString().equals(etRepeatPassword.getText().toString())) {
                    SetShopInfoPasswordRequest setInfroRequest = new SetShopInfoPasswordRequest(etRepeatPassword.getText().toString(),
                            PrefProvider.getSupplierTitle(getApplicationContext()),
                            PrefProvider.getShopName(getApplicationContext()),
                            PrefProvider.getEmail(getApplicationContext()),
                            PrefProvider.getCountryID(getApplicationContext()),
                            PrefProvider.getPhoneNumber(getApplicationContext()),
                            PrefProvider.getGsmNumberCountryID(getApplicationContext()),
                            PrefProvider.getGsmNumber(getApplicationContext()),
                            PrefProvider.getEmail(getApplicationContext()),
                            PrefProvider.getTicket(context));
                        Log.i("SSSSSS", "did we here");
                    RestClient.getInstance().create(NetworkAPIsInterface.class).setPassword(setInfroRequest)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .filter(result -> result != null)
                            .subscribe(result -> handleResult(result), e -> handleError(e));
                    } else {
                        final Dialog dialog = new Dialog(context);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.setContentView(R.layout.dialog_info);
                        TextView text = (TextView) dialog.findViewById(R.id.textViewErrorMessage);
                        text.setText(context.getResources().getString(R.string.new_pass_not_match));
                        Button dialogButton = (Button) dialog.findViewById(R.id.buttonOK);
                        dialogButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }
                } else {
                    final Dialog dialog = new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.setContentView(R.layout.dialog_info);
                    TextView text = (TextView) dialog.findViewById(R.id.textViewErrorMessage);
                    text.setText(context.getResources().getString(R.string.current_password_is_wrong));
                    Button dialogButton = (Button) dialog.findViewById(R.id.buttonOK);
                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }

                }
            }
        });
        ImageView imageView = (ImageView)findViewById(R.id.iv_toolbar_close);
        imageView.setOnClickListener(view -> onBackPressed());
    }
    private void handleResult(GetLoginBean res){

        // PrefProvider.saveShopName(this, etShortName.getText().toString());
        //  PrefProvider.saveSupplierTitle(this, etFullShopName.getText().toString());
        Log.i("SSSSSS", "success" + res.toString());
        Log.i("SSSSSS", "success" + res.getData());
        PrefProvider.saveTicket(this, res.getData().getTicket());
        this.finish();

        /* Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);*/
    }
    private void handleError(Throwable t){
        Log.i("SSSSSS", "error" + t.getMessage());
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_info);
        TextView text = (TextView) dialog.findViewById(R.id.textViewErrorMessage);
        text.setText(t.getMessage());
        Button dialogButton = (Button) dialog.findViewById(R.id.buttonOK);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
