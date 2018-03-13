package com.digitaldna.supplier;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.digitaldna.supplier.ui.screens.authorization.EnterEmailActivity;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Intent intent = new Intent(this, EnterEmailActivity.class);
        startActivity(intent);
    }
}
