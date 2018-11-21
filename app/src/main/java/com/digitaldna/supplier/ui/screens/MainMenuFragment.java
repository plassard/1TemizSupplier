package com.digitaldna.supplier.ui.screens;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitaldna.supplier.R;
import com.digitaldna.supplier.network.NetworkAPIsInterface;
import com.digitaldna.supplier.network.RestClient;
import com.digitaldna.supplier.network.beans.GetCommentsBean;
import com.digitaldna.supplier.network.beans.GetOrdersBean;
import com.digitaldna.supplier.network.beans.GetSupplierSummaryBean;
import com.digitaldna.supplier.network.beans.OrdersBean;
import com.digitaldna.supplier.network.beans.SupplierSummaryBean;
import com.digitaldna.supplier.network.beans.base.BaseJsonBean;
import com.digitaldna.supplier.network.requests.BasicRequest;
import com.digitaldna.supplier.ui.screens.settings.CapacityActivity;
import com.digitaldna.supplier.ui.screens.settings.HolidaysActivity;
import com.digitaldna.supplier.ui.screens.settings.SettingsMenuActivity;
import com.digitaldna.supplier.ui.screens.settings.WorkingHoursActivity;
import com.digitaldna.supplier.utils.ImageToCircleTransform;
import com.digitaldna.supplier.utils.PrefProvider;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class MainMenuFragment extends Fragment {
    View vMenuOrders, vMenuEarnings, vMenuCommentsAndRating, vMenuStatistics, vMenuSettings;
    ImageView ivProfilePicture;


    private static MainMenuFragment instance = null;

    public static MainMenuFragment deleteInstance(){
        instance = null;
        return null;
    };

    public static MainMenuFragment newInstance(String text){
        Log.i("LLL", "MainMenuFragment newInstance" + text);
        Log.i("LLL", "MainMenuFragment instance" + instance);
        if(instance == null){
            // new instance
            instance = new MainMenuFragment();

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


    public MainMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        /*View v = inflater.inflate(R.layout.fragment_main_menu, container, false);

        return v;*/
        return inflater.inflate(R.layout.fragment_main_menu, container, false);
    }

    private Animation anim1, anim2, anim3, anim4, anim5;
    View viewMain;
    String earningsAmount;

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        Log.i("LLL", "MainMenuFragment onViewCreated " + PrefProvider.getProfilePictureURL(getContext()));
        vMenuOrders = v.findViewById(R.id.menu_orders);
        vMenuEarnings = v.findViewById(R.id.menu_earnings);
        vMenuCommentsAndRating = v.findViewById(R.id.menu_comments_and_ratings);
        vMenuStatistics = v.findViewById(R.id.menu_statistics);
        vMenuSettings = v.findViewById(R.id.menu_settings);
        ivProfilePicture = (ImageView)v.findViewById(R.id.iv_settings);

        try{
            Picasso.with(getContext())
                    .load(PrefProvider.getProfilePictureURL(getContext()))
                    .placeholder(R.drawable.svg_ic_commend_rating_blue_40dp)
                    .error(R.drawable.svg_ic_commend_rating_blue_40dp)
                    .transform(new ImageToCircleTransform())
                    .into(ivProfilePicture);
        }catch (Exception e) {}

        ViewPager pager = (ViewPager)getActivity().findViewById(R.id.viewPager);
        vMenuOrders.setOnClickListener(view -> {
            pager.setCurrentItem(1);
            PrefProvider.saveSeenOrderID(getContext(), futureLastSeenOrder);
        });
        Log.i("LLL", "MainMenuFragment view pager");

        vMenuEarnings.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), EarningsActivity.class);
            startActivity(intent);
        });

        vMenuCommentsAndRating.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), CommentsAndRatingActivity.class);
            startActivity(intent);
        });

        vMenuStatistics.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), StatisticsActivity.class);
            startActivity(intent);
        });

        vMenuSettings.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), SettingsMenuActivity.class);
            intent.putExtra("Earnings", earningsAmount);
            startActivity(intent);
        });



        anim1 = null;
        anim2 = null;
        anim1 = AnimationUtils.loadAnimation(getActivity(), R.anim.transparency_in);
        anim2 = AnimationUtils.loadAnimation(getActivity(), R.anim.transparency_in);
        anim3 = AnimationUtils.loadAnimation(getActivity(), R.anim.transparency_in);
        anim4 = AnimationUtils.loadAnimation(getActivity(), R.anim.transparency_in);
        anim5 = AnimationUtils.loadAnimation(getActivity(), R.anim.transparency_in);



        new Handler()
                .postDelayed(() -> {
                    vMenuOrders.startAnimation(anim1);
                    vMenuOrders.setVisibility(View.VISIBLE);
                }, 500);

        new Handler()
                .postDelayed(() -> {
                    vMenuEarnings.startAnimation(anim2);
                    vMenuEarnings.setVisibility(View.VISIBLE);
                }, 600);
        new Handler()
                .postDelayed(() -> {
                    vMenuCommentsAndRating.startAnimation(anim3);
                    vMenuCommentsAndRating.setVisibility(View.VISIBLE);
                }, 700);
        new Handler()
                .postDelayed(() -> {
                    vMenuStatistics.startAnimation(anim4);
                    vMenuStatistics.setVisibility(View.VISIBLE);
                }, 800);
        new Handler()
                .postDelayed(() -> {
                    vMenuSettings.startAnimation(anim5);
                    vMenuSettings.setVisibility(View.VISIBLE);
                }, 900);

        TextView textView = (TextView)vMenuSettings.findViewById(R.id.tv_email);
        String string = PrefProvider.getEmail(getContext());
        ivOrdersCount = (ImageView)vMenuOrders.findViewById(R.id.iv_orders_count);
        textView.setText(string);

        Log.i("LLL", "MainMenuFragment set text" + string);
        BasicRequest basicRequest = new BasicRequest(PrefProvider.getEmail(getContext()),
                PrefProvider.getTicket(getContext()));

        RestClient.getInstance().create(NetworkAPIsInterface.class).getSummary(basicRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(result -> result != null)
                .subscribe(result -> handleResult(result) , e -> handleError(e));

        BasicRequest ordersRequest = new BasicRequest(PrefProvider.getEmail(getContext()), PrefProvider.getTicket(getContext()));

        RestClient.getInstance().create(NetworkAPIsInterface.class).getSupplierOrders(ordersRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(result -> result != null)
                .map(GetOrdersBean::getData)
               /* .flatMap(s -> {
                    System.out.println();
                    List<OrdersBean> ordersBean = s;
                    ordersBean.add(s.get(0));
                    return Observable.just(s);
                })*/
                //.doOnEach(item -> Log.i("DDDD", "doOnEach " + item.toString()))
                //.doOnNext(item -> Log.i("DDDD", "doOnNext " + item.size()))
                .subscribe(result -> handleOrdersListResult(result) , e -> handleOrdersListError(e));


        Intent intent = new Intent(getContext(), HolidaysActivity.class);
        startActivity(intent);
    }

    int futureLastSeenOrder;

    private void handleOrdersListResult(List<OrdersBean> ordersBean){

        Log.i("DDDD", "ordersBean.size()" + ordersBean.size());
        int lastSeenOrder = PrefProvider.getSeenOrderID(getContext());
        int newOrdersCount = 0;
        futureLastSeenOrder = lastSeenOrder;
        //fill "all" array, first "fresh" orders that needs to be accepted within 30 minutes(countdown)
        for(int i = 0; i < ordersBean.size(); i++) {
            if (ordersBean.get(i).getOrderID() > lastSeenOrder){
                newOrdersCount++;
                if(futureLastSeenOrder < ordersBean.get(i).getOrderID()){
                    futureLastSeenOrder = ordersBean.get(i).getOrderID();
                }
            }
        }
        TextView tvOrdersCount = (TextView)vMenuOrders.findViewById(R.id.tv_orders_count);
        tvOrdersCount.setText(String.valueOf(ordersBean.size()));
        //tvOrdersCount.setText(String.valueOf(newOrdersCount));
        if(newOrdersCount > 0) {
            ivOrdersCount.setVisibility(View.VISIBLE);
            animateNewOrder();
        } else {
            ivOrdersCount.setVisibility(View.INVISIBLE);
        }
    }
    private void handleOrdersListError(Throwable t){
        Log.i("HANDLEE", "error" + BaseJsonBean.mStatusText);

    }


    private void handleResult(GetSupplierSummaryBean getSupplierSummaryBean){
        Log.i("LLL", "MainMenuFragment success");
        SupplierSummaryBean summaryBean = getSupplierSummaryBean.getData();
        Log.i("AAAA", summaryBean.getLateJobCount() + " " + summaryBean.getOnTimeJobCount());
        TextView tvOnTime = (TextView)vMenuOrders.findViewById(R.id.tv_on_time_count);
        TextView tvLate = (TextView)vMenuOrders.findViewById(R.id.tv_late_count);
        TextView tvBalanceAmount = (TextView)vMenuEarnings.findViewById(R.id.tv_balance_amount);
        TextView tvRating = (TextView)vMenuCommentsAndRating.findViewById(R.id.tv_rating);
        TextView tvCommentsCount = (TextView)vMenuCommentsAndRating.findViewById(R.id.tv_comments_count);

        tvOnTime.setText(String.valueOf(summaryBean.getOnTimeJobCount()));
        tvLate.setText(String.valueOf(summaryBean.getLateJobCount()));
        tvBalanceAmount.setText(getResources().getString(R.string.tr_lyra) + " " + String.valueOf(summaryBean.getEarnings()));
        tvRating.setText(String.valueOf(summaryBean.getAverageRating()));
        tvCommentsCount.setText(String.valueOf(summaryBean.getCommentCount()));

        earningsAmount = getResources().getString(R.string.tr_lyra) + " " + String.valueOf(summaryBean.getEarnings());




        Log.i("LLL", "MainMenuFragment success");

    }
    ImageView ivOrdersCount;
    public void animateNewOrder(){

        ImageView ivOrdersCountStable = (ImageView)vMenuOrders.findViewById(R.id.iv_orders_count_stable);


        Animation animCZoomInStable = AnimationUtils.loadAnimation(getActivity(), R.anim.circle_stable_zoom_in);
        Animation animCZoomOutStable = AnimationUtils.loadAnimation(getActivity(), R.anim.circle_stable_zoom_out);
        Animation animC = AnimationUtils.loadAnimation(getActivity(), R.anim.circle_attention);

        //animCZoomInStable.setFillAfter(true);
        animC.setFillAfter(true);
        animCZoomInStable.setAnimationListener((new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ivOrdersCountStable.startAnimation(animCZoomOutStable);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        }));

        animCZoomOutStable.setAnimationListener((new Animation.AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        animateNewOrder();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                }));
        ivOrdersCount.startAnimation(animC);
        ivOrdersCountStable.startAnimation(animCZoomInStable);

    }

    @Override
    public void onResume() {
        super.onResume();




    }

    private void handleError(Throwable t){

        Log.i("LLL", "MainMenuFragment onViewCreated");
        Log.i("LLL", "ERRRRRR "+ BaseJsonBean.mStatusText);
    }


}
