package com.digitaldna.supplier.ui.screens;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.digitaldna.supplier.R;


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


  /*  @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_orders, container, false);
    }*/

}
