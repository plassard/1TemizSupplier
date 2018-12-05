package com.digitaldna.supplier.ui.screens.settings;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.digitaldna.supplier.R;
import com.digitaldna.supplier.ui.screens.EarningsActivity;
import com.digitaldna.supplier.ui.screens.SettingsActivity;
import com.digitaldna.supplier.ui.screens.settings.itempricing.ItemPricingActivity;

public class SettingsMenuActivity extends Activity implements View.OnClickListener {
    View vMenuShopInfo, vMenuWorkHours, vMenuCapacity, vMenuHolidays, vMenuItemPricing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_menu);

        vMenuShopInfo = findViewById(R.id.menu_shopinfo);
        vMenuWorkHours = findViewById(R.id.menu_workinghours);
        vMenuCapacity = findViewById(R.id.menu_capacity);
        vMenuHolidays = findViewById(R.id.menu_holidays);
        vMenuItemPricing = findViewById(R.id.menu_item_pricing);

        vMenuShopInfo.setOnClickListener(this);
        vMenuWorkHours.setOnClickListener(this);
        vMenuCapacity.setOnClickListener(this);
        vMenuHolidays.setOnClickListener(this);
        vMenuItemPricing.setOnClickListener(this);

        ImageView ivMenu = (ImageView)findViewById(R.id.iv_toolbar_menu);
        ivMenu.setOnClickListener(view -> {
            this.finish();
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, SettingsActivity.class);
        switch(v.getId()) {
            case R.id.menu_shopinfo:
                //Play voicefile
                Log.i("HHHH", "click shop");
                intent = new Intent(this, SettingsActivity.class);
                break;
            case R.id.menu_workinghours:
                //Stop MediaPlayer
                intent = new Intent(this, WorkingHoursActivity.class);
                break;
            case R.id.menu_capacity:
                //Stop MediaPlayer
                intent = new Intent(this, CapacityActivity.class);
                break;
            case R.id.menu_holidays:
                //Stop MediaPlayer
                intent = new Intent(this, HolidaysActivity.class);
                break;
            case R.id.menu_item_pricing:
                //Stop MediaPlayer
                intent = new Intent(this, ItemPricingActivity.class);
                intent.putExtra("orderID", 780);
                break;
        }
        Log.i("HHHH", "click intent");
        startActivity(intent);
    }
}
