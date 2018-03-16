package com.digitaldna.supplier.ui.screens.authorization;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

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


public class EnterEmailActivity extends Activity {
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_enter);

        EditText etEmail = (EditText)findViewById(R.id.editTextEmail);

        Button btnNext = (Button)findViewById(R.id.buttonNext);
        btnNext.setOnClickListener(view -> {
            connectToServer();
        });


    }

    private void handleResult(GetLoginBean getLoginBean){
        LoginSupplierBean loginSupplierBean = getLoginBean.getData();
        PrefProvider.saveEmail(this, loginSupplierBean.getEmail());

        String mProfilePictureUrl = loginSupplierBean.getProfilePictureURL();
        PrefProvider.saveProfilePictureURL(this, !TextUtils.isEmpty(mProfilePictureUrl) ? "" + Urls.HOST_URL + "/" + mProfilePictureUrl : "");

        PrefProvider.saveSupplierTitle(this, loginSupplierBean.getTitle());

        openMain();
    }
    private void handleError(Throwable t){
        Log.i("LLL", "ERRRRRR "+ BaseJsonBean.mStatusText);
    }

    private void openMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.transparency_in_screen, R.anim.transparency_out);
    }

    private void connectToServer(){
        LoginRequest loginRequest = new LoginRequest("eozturk782@gmail.com", "123456", PrefProvider.getLanguageId(this));

        RestClient.getInstance().create(NetworkAPIsInterface.class).login(loginRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(result -> result != null)
                .subscribe(result -> handleResult(result) , e -> handleError(e));
    }

}
