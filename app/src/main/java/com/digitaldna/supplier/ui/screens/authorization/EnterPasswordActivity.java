package com.digitaldna.supplier.ui.screens.authorization;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
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
import com.digitaldna.supplier.utils.PrefProvider;

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
    TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_password);

        email = getIntent().getExtras().getString("email");
        etPassword = (EditText)findViewById(R.id.editTextPass);
        tvError = (TextView)findViewById(R.id.textViewPasswordError);

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
        PrefProvider.saveEmail(this, loginSupplierBean.getEmail());
        PrefProvider.saveTicket(this, loginSupplierBean.getTicket());

        openMain();

        String mProfilePictureUrl = loginSupplierBean.getProfilePictureURL();
        PrefProvider.saveProfilePictureURL(this, !TextUtils.isEmpty(mProfilePictureUrl) ? "" + Urls.HOST_URL + "/" + mProfilePictureUrl : "");

        PrefProvider.saveSupplierTitle(this, loginSupplierBean.getTitle());
    }

    private void handleError(Throwable t){
        Log.i("LLL", "ERRRRRR "+ BaseJsonBean.mStatusText);
        Log.i("LLL", "ERRRRRR "+ BaseJsonBean.STATUS_TEXT);
    }

    private void openMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.transparency_in_screen, R.anim.transparency_out);
    }

    private boolean validatePassword() {
        String password = etPassword.getText().toString();
        if (password.trim().isEmpty() || password.length() < 6) {
            tvError.setText(getResources().getString(R.string.password_error));
            return false;
        } else {
            tvError.setText("");
            return true;
        }

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
}
