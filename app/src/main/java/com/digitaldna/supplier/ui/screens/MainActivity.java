package com.digitaldna.supplier.ui.screens;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.digitaldna.supplier.R;


public class MainActivity extends FragmentActivity
        implements ViewPager.OnPageChangeListener{

    /**
     * fields
     */
    private static final int NUMBER_OF_PAGES = 2;
    ViewPager pager;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        pager.addOnPageChangeListener(this);

    }

    /*************************************************************
     * Listeners for ViewPager
     *************************************************************/
    /**
     * When the current page is scrolled
     * @param position
     * @param v
     * @param i
     */
    @Override
    public void onPageScrolled(int position, float v, int i) {

    }

    /**
     * When a new page becomes selected
     * @param position
     */
    @Override
    public void onPageSelected(int position) {

    }

    /**
     * When the pager is automatically setting to the current page
     * @param position
     */
    @Override
    public void onPageScrollStateChanged(int position) {

    }


    /**
     * Custom PagerAdapter class
     */
    private class MyPagerAdapter extends FragmentPagerAdapter{

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


}