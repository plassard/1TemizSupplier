package com.digitaldna.supplier.ui.screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

        Picasso.with(this)
                .load(PrefProvider.getProfilePictureURL(this))
                .placeholder(R.drawable.svg_ic_commend_rating_blue_40dp)
                .error(R.drawable.svg_ic_commend_rating_blue_40dp)
                .transform(new ImageToCircleTransform())
                .into(ivProfilePicture);

        TextView tvSupplierTitle = (TextView)findViewById(R.id.tv_full_name);
        tvSupplierTitle.setText(PrefProvider.getSupplierTitle(this));

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
