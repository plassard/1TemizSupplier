package com.digitaldna.supplier.ui.screens;

import android.app.Activity;
import android.os.Bundle;

import com.digitaldna.supplier.R;

public class OrderDetailsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        String orderID = getIntent().getExtras().getString("orderID");


    }
}
