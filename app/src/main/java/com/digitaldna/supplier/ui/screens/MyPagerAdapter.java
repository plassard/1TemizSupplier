package com.digitaldna.supplier.ui.screens;

/**
 * Created by yevgen on 3/16/18.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Custom PagerAdapter class
 */
public class MyPagerAdapter extends FragmentPagerAdapter {
    private static final int NUMBER_OF_PAGES = 2;
    /**
     * Constructor
     * @param fm
     */
    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * Return fragment based on the position.
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return MainMenuFragment.newInstance("MainMenuFragment, Instance 1");
            case 1:
                return OrdersFragment.newInstance("OrdersFragment, Instance 1");
            default:
                return MainMenuFragment.newInstance("MainMenuFragment, Default");
        }
    }

    /**
     * Return the number of pages.
     * @return
     */
    @Override
    public int getCount() {
        return NUMBER_OF_PAGES;
    }


}