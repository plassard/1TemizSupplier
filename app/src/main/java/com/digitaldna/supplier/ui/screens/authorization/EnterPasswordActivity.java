package com.digitaldna.supplier.ui.screens.authorization;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.digitaldna.supplier.R;
import com.digitaldna.supplier.network.NetworkAPIsInterface;
import com.digitaldna.supplier.network.RestClient;
import com.digitaldna.supplier.network.Urls;
import com.digitaldna.supplier.network.beans.GetLoginBean;
import com.digitaldna.supplier.network.beans.LoginSupplierBean;
import com.digitaldna.supplier.network.beans.base.BaseJsonBean;
import com.digitaldna.supplier.network.requests.LoginRequest;
import com.digitaldna.supplier.ui.screens.MainActivity;
import com.digitaldna.supplier.ui.screens.MainMenuFragment;
import com.digitaldna.supplier.ui.screens.OrdersFragment;
import com.digitaldna.supplier.utils.PrefProvider;

import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
//import retrofit2.converter.gson.GsonConverterFactory;


public class EnterPasswordActivity extends Activity {
    String email;
    Retrofit retrofit;
    EditText etPassword;
    TextView tvError, tvForgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_password);

        email = getIntent().getExtras().getString("email");
        etPassword = (EditText)findViewById(R.id.editTextPass);
        tvError = (TextView)findViewById(R.id.textViewPasswordError);
        tvForgot = (TextView)findViewById(R.id.textViewForgot);
        etPassword.addTextChangedListener(new MyTextWatcher(etPassword));

        Button btnNext = (Button)findViewById(R.id.buttonNextPass);
        btnNext.setOnClickListener(view -> {
            if(validatePassword()){
                Log.i("LLL", "validated");
                connectToServer();
            } else {
                Log.i("LLL", "else");
            }

        });

        tvForgot.setOnClickListener(view -> {
            Intent intent = new Intent(this, ForgotPasswordActivity.class);
            startActivity(intent);
        });

    }


    private void connectToServer(){
        LoginRequest loginRequest = new LoginRequest(email, etPassword.getText().toString(), PrefProvider.getLanguageId(this));

        RestClient.getInstance().create(NetworkAPIsInterface.class).login(loginRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(result -> result != null)
                .subscribe(result -> handleResult(result) , e -> handleError(e));
    }



    private void handleResult(GetLoginBean getLoginBean){
        Log.i("LLL", "handleResult" + getLoginBean.toString());
        LoginSupplierBean loginSupplierBean = getLoginBean.getData();

        if(loginSupplierBean.getCurrentPage() == 0) {
            PrefProvider.savePassword(this, etPassword.getText().toString());
            Log.i("LLL", "handleResult loginSupplierBean.getIsPhoneNumberVerified()" + loginSupplierBean.getIsPhoneNumberVerified());
            PrefProvider.saveEmail(this, loginSupplierBean.getEmail());
            PrefProvider.saveTicket(this, loginSupplierBean.getTicket());
            String mProfilePictureUrl = loginSupplierBean.getProfilePictureURL();
            PrefProvider.saveProfilePictureURL(this, !TextUtils.isEmpty(mProfilePictureUrl) ? "" + Urls.HOST_URL + "/" + mProfilePictureUrl : "");
            PrefProvider.saveSupplierTitle(this, loginSupplierBean.getTitle());
            PrefProvider.saveShopName(this, loginSupplierBean.getShopName());

            PrefProvider.saveCountryID(this, loginSupplierBean.getCountryID());
            PrefProvider.savePhoneNumber(this, loginSupplierBean.getPhoneNumber());

            PrefProvider.saveLanguageId(this, loginSupplierBean.getLanguageID());
            PrefProvider.saveSupplierID(this, loginSupplierBean.getSupplierID());
            if (loginSupplierBean.getLanguageID() == 0)
                setLanguage("TR");
            else
                setLanguage("EN");

            if (loginSupplierBean.getIsPhoneNumberVerified())
                openMain();
            else {
                Intent intent = new Intent(this, SmsVerificationActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.transparency_in_screen, R.anim.transparency_out);
            }
        } else {
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.dialog_info);
            TextView text = (TextView) dialog.findViewById(R.id.textViewErrorMessage);
            text.setText(this.getResources().getString(R.string.registration_not_finished));
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

    private void handleError(Throwable t){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_info);
        TextView text = (TextView) dialog.findViewById(R.id.textViewErrorMessage);
        text.setText(BaseJsonBean.mStatusText);
        Button dialogButton = (Button) dialog.findViewById(R.id.buttonOK);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        Log.i("LLL", "ERRRRRR "+ BaseJsonBean.mStatusText);
        Log.i("LLL", "ERRRRRR "+ BaseJsonBean.STATUS_TEXT);
    }

    private void openMain(){
        MainMenuFragment.deleteInstance();
        OrdersFragment.deleteInstance();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.transparency_in_screen, R.anim.transparency_out);
    }

    private boolean validatePassword() {
        String password = etPassword.getText().toString();
        if (password.trim().isEmpty() /*|| password.length() < 6*/) {
            tvError.setText(getResources().getString(R.string.password_error));
            return false;
        } else {
            tvError.setText("");
            return true;
        }
       //return true;

    }

    private class MyTextWatcher implements TextWatcher {
        private View view;
        private MyTextWatcher(View view) {
            this.view = view;
        }
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            switch (view.getId()) {
                case R.id.editTextPass:
                    validatePassword();
                    break;
            }
        }
        public void afterTextChanged(Editable editable) {
        }
    }

    public void setLanguage(String language){
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        PrefProvider.saveLanguage(this, language);
    }
}
