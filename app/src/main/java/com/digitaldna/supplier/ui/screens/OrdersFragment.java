package com.digitaldna.supplier.ui.screens;


import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.digitaldna.supplier.R;
import com.digitaldna.supplier.widgets.SimpleOnTabSelectedListener;


public class OrdersFragment extends Fragment {
    /**
     * fields
     */
    private static OrdersFragment instance = null;
    /**
     * Create fragment view when paginated.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */



    /**
     * Returns new instance.
     *
     * @param text
     * @return
     */
    public static OrdersFragment newInstance(String text){

        if(instance == null){
            // new instance
            instance = new OrdersFragment();

            // sets data to bundle
            Bundle bundle = new Bundle();
            bundle.putString("msg", text);

            // set data to fragment
            instance.setArguments(bundle);

            return instance;
        } else {
            return instance;
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_orders, container, false);

        // TextView textView = (TextView) v.findViewById(R.id.tvFragSecond);
        //textView.setText("HHHHH");

        return v;
    }

    public OrdersFragment() {
        // Required empty public constructor
    }

    private TabLayout tlTabs;
    private ViewPager vpPager;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tlTabs = (TabLayout) view.findViewById(R.id.tl_tabs);
        vpPager = (ViewPager) view.findViewById(R.id.vp_pager);
        tlTabs.addOnTabSelectedListener(mSimpleOnTabSelectedListener);
        tlTabs.setupWithViewPager(vpPager);
        vpPager.setOffscreenPageLimit(2);
    }


    private final SimpleOnTabSelectedListener mSimpleOnTabSelectedListener = new SimpleOnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            changeTabTextColor(tab.getPosition(), R.color.textWhite, R.color.textWhite);
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            changeTabTextColor(tab.getPosition(), R.color.textCharcoal, R.color.textAshGrey);
        }
    };


    private void changeTabTextColor(int tabId, @ColorRes int tabTitleColor, @ColorRes int counterColor) {

        int count = tlTabs.getTabCount();
        if (tabId > count) {
            return;
        }
        TabLayout.Tab tab = tlTabs.getTabAt(tabId);
        if (tab != null) {
            View view = tab.getCustomView();
            if (view != null) {
                TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
                TextView tvCount = (TextView) view.findViewById(R.id.tv_count);
                if (tvTitle != null) {
                    tvTitle.setTextColor(ContextCompat.getColor(getContext(), tabTitleColor));
                }

                if (tvCount != null) {
                    tvCount.setTextColor(ContextCompat.getColor(getContext(), counterColor));
                }
            }
        }
    }

}
