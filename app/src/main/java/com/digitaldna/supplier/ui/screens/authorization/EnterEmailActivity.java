package com.digitaldna.supplier.ui.screens.authorization;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.digitaldna.supplier.R;

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
            Intent intent = new Intent(this, EnterPasswordActivity.class);
            intent.putExtra("email", etEmail.getText().toString());
            startActivity(intent);
        });


    }







}
