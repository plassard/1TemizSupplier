package com.digitaldna.supplier.ui.screens;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitaldna.supplier.R;
import com.digitaldna.supplier.utils.ImageToCircleTransform;
import com.digitaldna.supplier.utils.PrefProvider;
import com.squareup.picasso.Picasso;


public class MainMenuFragment extends Fragment {
    View vMenuOrders, vMenuEarnings, vMenuCommentsAndRating, vMenuStatistics, vMenuSettings;
    ImageView ivProfilePicture;


    private static MainMenuFragment instance = null;

    public static MainMenuFragment newInstance(String text){

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

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {

        vMenuOrders = v.findViewById(R.id.menu_orders);
        vMenuEarnings = v.findViewById(R.id.menu_earnings);
        vMenuCommentsAndRating = v.findViewById(R.id.menu_comments_and_ratings);
        vMenuStatistics = v.findViewById(R.id.menu_statistics);
        vMenuSettings = v.findViewById(R.id.menu_settings);
        ivProfilePicture = (ImageView)v.findViewById(R.id.iv_settings);

        Picasso.with(getContext())
                .load(PrefProvider.getProfilePictureURL(getContext()))
                .placeholder(R.drawable.svg_ic_commend_rating_blue_40dp)
                .error(R.drawable.svg_ic_commend_rating_blue_40dp)
                .transform(new ImageToCircleTransform())
                .into(ivProfilePicture);

        ViewPager pager = (ViewPager)getActivity().findViewById(R.id.viewPager);
        vMenuOrders.setOnClickListener(view -> {
            pager.setCurrentItem(1);
        });

        vMenuSettings.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), SettingsActivity.class);
            startActivity(intent);
        });

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

        textView.setText(string);


    }



}
