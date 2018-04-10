package com.digitaldna.supplier.ui.screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitaldna.supplier.R;
import com.digitaldna.supplier.SplashActivity;
import com.digitaldna.supplier.utils.ImageToCircleTransform;
import com.digitaldna.supplier.utils.PrefProvider;
import com.squareup.picasso.Picasso;

public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ImageView ivProfilePicture = (ImageView)findViewById(R.id.iv_avatar);

        try {
           Picasso.with(this)
                   .load(PrefProvider.getProfilePictureURL(this))
                   .placeholder(R.drawable.svg_ic_commend_rating_blue_40dp)
                   .error(R.drawable.svg_ic_commend_rating_blue_40dp)
                   .transform(new ImageToCircleTransform())
                   .into(ivProfilePicture);
        }catch (Exception e) {
            Log.i("LLL", "Picasso exc " + e);
        }
        TextView tvSupplierTitle = (TextView)findViewById(R.id.tv_full_name);
        tvSupplierTitle.setText(PrefProvider.getSupplierTitle(this));

        TextView tvBalanceAmount = (TextView)findViewById(R.id.tv_balance_amount_settings);

        tvBalanceAmount.setText(getIntent().getExtras().getString("Earnings"));


        ImageView ivMenu = (ImageView)findViewById(R.id.iv_toolbar_menu);
        ivMenu.setOnClickListener(view -> {
            this.finish();
        });

        Button btnLogOut = (Button)findViewById(R.id.b_sign_out);
        btnLogOut.setOnClickListener(view -> {
            PrefProvider.logOut(this);
            Intent intent = new Intent(this, SplashActivity.class);
            startActivity(intent);
        });
    }
}
