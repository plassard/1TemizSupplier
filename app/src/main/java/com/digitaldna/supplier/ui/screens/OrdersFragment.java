package com.digitaldna.supplier.ui.screens;


import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitaldna.supplier.R;
import com.digitaldna.supplier.network.NetworkAPIsInterface;
import com.digitaldna.supplier.network.RestClient;
import com.digitaldna.supplier.network.beans.GetOrdersBean;
import com.digitaldna.supplier.network.beans.OrdersBean;
import com.digitaldna.supplier.network.beans.base.BaseJsonBean;
import com.digitaldna.supplier.network.requests.OrdersRequest;
import com.digitaldna.supplier.ui.screens.orders.TabsPagerAdapter;
import com.digitaldna.supplier.utils.PrefProvider;
import com.digitaldna.supplier.widgets.OrderStatusView;
import com.digitaldna.supplier.widgets.SimpleOnTabSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class OrdersFragment extends Fragment {
    private OrderStatusView osStatus;
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

        //osStatus = (OrderStatusView) v.findViewById(R.id.os_status);
        // TextView textView = (TextView) v.findViewById(R.id.tvFragSecond);
        //textView.setText("HHHHH");

        return v;
    }

    public OrdersFragment() {
        // Required empty public constructor
    }

    private TabLayout tlTabs;
    private ViewPager vpPager;
    private TextView tvToolbarTitle;
    private ImageView ivToolbarLeft;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tlTabs = (TabLayout) view.findViewById(R.id.tl_tabs);
        vpPager = (ViewPager) view.findViewById(R.id.vp_pager);
        tvToolbarTitle = (TextView) view.findViewById(R.id.tv_toolbar_title);
        ivToolbarLeft = (ImageView) view.findViewById(R.id.iv_toolbar_left);
        tlTabs.addOnTabSelectedListener(mSimpleOnTabSelectedListener);



       /* tlTabs.setupWithViewPager(vpPager);
        vpPager.setOffscreenPageLimit(2);*/

        OrdersRequest ordersRequest = new OrdersRequest("eozturk782@gmail.com", PrefProvider.getTicket(getContext()));

        RestClient.getInstance().create(NetworkAPIsInterface.class).getSupplierOrders(ordersRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(result -> result != null)
                .map(GetOrdersBean::getData)
                .subscribe(result -> handleResult(result) , e -> handleError(e));

        tvToolbarTitle.setText(getText(R.string.orders));
        ivToolbarLeft.setImageResource(R.drawable.svg_ic_menu_black_24dp);

        ivToolbarLeft.setOnClickListener(v -> {
           /* if (mMainView != null) {
                MainActivity.useVpMenuPager = false;
                mMainView.openMainMenu();
            }*/
        });

    }
    public static List<OrdersBean> ordersToday;
    public static List<OrdersBean> ordersThisWeek;
    public static List<OrdersBean> ordersThisMonth;

    private void handleResult(List<OrdersBean> ordersBean){
        ordersToday = new ArrayList<OrdersBean>();
        ordersThisWeek = new ArrayList<OrdersBean>();
        ordersThisMonth = new ArrayList<OrdersBean>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"); // your format
        Date dateToday = new Date();
        dateToday.setHours(0);
        dateToday.setMinutes(0);
        dateToday.setSeconds(0);

        Date dateThisMonth;
        Calendar c = Calendar.getInstance();
        c.setTime(dateToday);
        c.add(Calendar.MONTH, -1);
        dateThisMonth = c.getTime();

        Date dateThisWeek;
        c = Calendar.getInstance();
        c.setTime(dateToday);
        c.add(Calendar.WEEK_OF_YEAR, -1);
        dateThisWeek = c.getTime();

        Date orderDate = null;
        for(int i = 0; i < ordersBean.size(); i++){

            try {
                orderDate = format.parse(ordersBean.get(i).getOrderJobDate());
            } catch (Exception e) { e.printStackTrace();Log.i("LLL", "ERRRRRR " + "ParseException " + i + " " + e); }

            try{
                if(orderDate.after(dateThisMonth)){
                    ordersThisMonth.add(ordersBean.get(i));
                }
                if(orderDate.after(dateThisWeek)){
                    ordersThisWeek.add(ordersBean.get(i));
                }
                if(orderDate.after(dateToday)){
                    ordersToday.add(ordersBean.get(i));
                }
            }catch (Exception e) {Log.i("LLL", "ERRRRRR " + "add exc " + e);}
        }

        try {
            Log.i("LLL", "ERRRRRR " + "ordersThisMonth " + ordersThisMonth.size());
            Log.i("LLL", "ERRRRRR " + "ordersThisWeek " + ordersThisWeek.size());
            Log.i("LLL", "ERRRRRR " + "ordersToday " + ordersToday.size());
        }catch (Exception e) {Log.i("LLL", "ERRRRRR " + "ordersToday exc" + e);}

        TabsPagerAdapter adapter = new TabsPagerAdapter(getFragmentManager());

        vpPager.setAdapter(adapter);
        tlTabs.setupWithViewPager(vpPager);

        /* LoginSupplierBean loginSupplierBean = getLoginBean.getData();
        PrefProvider.saveEmail(this, loginSupplierBean.getEmail());

        String mProfilePictureUrl = loginSupplierBean.getProfilePictureURL();
        PrefProvider.saveProfilePictureURL(this, !TextUtils.isEmpty(mProfilePictureUrl) ? "" + Urls.HOST_URL + "/" + mProfilePictureUrl : "");

        PrefProvider.saveSupplierTitle(this, loginSupplierBean.getTitle());
        PrefProvider.saveTicket(this, loginSupplierBean.getTicket());

        openMain();*/
    }
    private void handleError(Throwable t){
        Log.i("LLL", "ERRRRRR "+ BaseJsonBean.mStatusText);
    }

    private final SimpleOnTabSelectedListener mSimpleOnTabSelectedListener = new SimpleOnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            changeTabTextColor(tab.getPosition(), R.color.textWhite, R.color.textWhite);
           // OrderListAdapter.startAnimation(getContext());
            //OrderListAdapter.startAnimation(getContext(), tab.getPosition());
            Log.i("LLL", "posoi" +  tab.getPosition());
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
