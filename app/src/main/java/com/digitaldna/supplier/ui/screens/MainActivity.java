package com.digitaldna.supplier.ui.screens;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.digitaldna.supplier.R;


public class MainActivity extends FragmentActivity
        implements ViewPager.OnPageChangeListener{

    /**
     * fields
     */

    public ViewPager pager;

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

   public void setPager(){
        pager.setCurrentItem(1);
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





}