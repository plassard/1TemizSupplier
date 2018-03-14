package com.digitaldna.supplier.ui.screens;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.digitaldna.supplier.CustomPagerAdapter;
import com.digitaldna.supplier.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));

        View view = viewPager.getFocusedChild();

        View vMenuOrderHistory = view.findViewById(R.id.menu_orders);
        vMenuOrderHistory.setVisibility(View.GONE);

        /*View vMenuOrderHistory2 = viewPager.findViewById(R.id.menu_earnings);
        vMenuOrderHistory2.setBackgroundResource(R.drawable.logo);*/
    }
}
