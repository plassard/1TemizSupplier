package com.digitaldna.supplier;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.digitaldna.supplier.ui.screens.MainActivity;
import com.digitaldna.supplier.ui.screens.authorization.EnterEmailActivity;
import com.digitaldna.supplier.utils.PrefProvider;

import java.util.Locale;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //here we check default langauge of the phone at first launch (when it was not defined/changed by user in app)



    }

    @Override
    protected void onResume() {
        super.onResume();

        if(PrefProvider.getLanguageId(this) == -1){
            if (Locale.getDefault().getLanguage().equals("tr")  || Locale.getDefault().getLanguage().equals("TR")) {
                PrefProvider.saveLanguage(this, "tr");
                PrefProvider.saveLanguageId(this, 0);
            } else {
                PrefProvider.saveLanguage(this, "en");
                PrefProvider.saveLanguageId(this, 1);
            }
        }
        if(PrefProvider.getEmail(this).equals(null) || PrefProvider.getEmail(this).equals("")) {
            Intent intent = new Intent(this, EnterEmailActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.transparency_in_screen, R.anim.transparency_out);
        }
    }
}
