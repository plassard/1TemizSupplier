package com.digitaldna.supplier.ui.screens;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digitaldna.supplier.R;


public class MainMenuFragment extends Fragment {


    public MainMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_menu, container, false);
    }


   /* @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        View vMenuOrderHistory = view.findViewById(R.id.menu_orders);
        vMenuOrderHistory.setVisibility(View.GONE);

        View vMenuOrderHistory2 = view.findViewById(R.id.menu_earnings);
        vMenuOrderHistory2.setBackgroundResource(R.drawable.logo);
    }
*/


}
