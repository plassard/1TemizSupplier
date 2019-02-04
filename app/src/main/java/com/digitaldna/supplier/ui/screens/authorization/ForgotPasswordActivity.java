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
import com.digitaldna.supplier.network.beans.EmptyBean;
import com.digitaldna.supplier.network.beans.GetEmptyBean;
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
//import retrofit2.converter.gson.GsonConverterFactory;


public class ForgotPasswordActivity extends Activity {
    String email;
    Retrofit retrofit;
    EditText etEmail;
    TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        etEmail = (EditText)findViewById(R.id.editTextPass);
        tvError = (TextView)findViewById(R.id.textViewPasswordError);

        etEmail.addTextChangedListener(new MyTextWatcher(etEmail));

        Button btnSend = (Button)findViewById(R.id.buttonNextPass);
        btnSend.setOnClickListener(view -> {
            if(validatePassword()){
                Log.i("LLL", "validated");
                connectToServer();
            } else {
                Log.i("LLL", "else");
            }

        });

    }


    private void connectToServer(){
        LoginRequest loginRequest = new LoginRequest(etEmail.getText().toString(),"", PrefProvider.getLanguageId(this));

        RestClient.getInstance().create(NetworkAPIsInterface.class).forgot(loginRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(result -> result != null)
                .subscribe(result -> handleResult(result) , e -> handleError(e));
    }



    private void handleResult(GetEmptyBean emptyBean){
        Log.i("LLL", "forgot succ");
        if(emptyBean.getStatusCode() == 100){
            finish();
        } else {
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.dialog_info);
            TextView text = (TextView) dialog.findViewById(R.id.textViewErrorMessage);
            text.setText(emptyBean.getStatusText());
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
        Log.i("LLL", "forgot error" + t);
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

    private boolean validateEmail() {
        String email = etEmail.getText().toString().trim();
        if (email.isEmpty()) {
            tvError.setText(getResources().getString(R.string.field_required));
            return false;
        } else if (!isValidEmail(email)) {
            tvError.setText(getResources().getString(R.string.mail_error));
            return false;
        } else {
            tvError.setText("");
        }
        return true;
    }

    boolean isValidEmail(String email) {
        return !android.text.TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean validatePassword() {
        String password = etEmail.getText().toString();
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
                    validateEmail();
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
