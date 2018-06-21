package com.digitaldna.supplier.ui.screens.authorization;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;

import com.digitaldna.supplier.R;

import retrofit2.Retrofit;


public class EnterEmailActivity extends Activity {
    Retrofit retrofit;
    EditText etEmail;
    TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_enter);

        etEmail = (EditText)findViewById(R.id.editTextEmail);
        tvError = (TextView)findViewById(R.id.textViewLoginError);
        Button btnNext = (Button)findViewById(R.id.buttonNext);

        etEmail.addTextChangedListener(new MyTextWatcher(etEmail));

        btnNext.setOnClickListener(view -> {
            if(validateEmail()) {
                Intent intent = new Intent(this, EnterPasswordActivity.class);
                intent.putExtra("email", etEmail.getText().toString());
                startActivity(intent);
            };
        });


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

    private class MyTextWatcher implements TextWatcher {
        private View view;
        private MyTextWatcher(View view) {
            this.view = view;
        }
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            switch (view.getId()) {
                case R.id.editTextEmail:
                    validateEmail();
                    break;
            }
        }
        public void afterTextChanged(Editable editable) {
        }
    }

}
