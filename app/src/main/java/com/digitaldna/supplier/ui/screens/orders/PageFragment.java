package com.digitaldna.supplier.ui.screens.orders;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;

import com.digitaldna.supplier.R;
import com.digitaldna.supplier.ui.screens.OrdersFragment;


public class PageFragment extends Fragment {
    private static final String ARG_PAGE_NUMBER = "page_number";
    private ListView lvOrders;

    public PageFragment() {
    }

    public static PageFragment newInstance(int page) {
        PageFragment fragment = new PageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE_NUMBER, page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = this.getView();
        lvOrders = (ListView) view.findViewById(R.id.lvOrders);
        Animation anim1 = AnimationUtils.loadAnimation(getContext(), R.anim.transparency_in);
        lvOrders.setAnimation(anim1);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page_layout, container, false);

        int page = getArguments().getInt(ARG_PAGE_NUMBER, -1);
        //we can delete this
        //TextView txt = (TextView) rootView.findViewById(R.id.page_number_label);
        //txt.setText(String.format("Page %d", page) /*+ OrdersFragment.ordersBeanList.get(page).getJobEndTime()*/);

        lvOrders = (ListView) rootView.findViewById(R.id.lvOrders);
        OrderListAdapter ordersAdapter = null;
        if(page == 1){
            ordersAdapter = new OrderListAdapter(getContext(), OrdersFragment.ordersToday);
        } else if(page == 2){
            ordersAdapter = new OrderListAdapter(getContext(), OrdersFragment.ordersThisWeek);
        } else if(page == 3){
            ordersAdapter = new OrderListAdapter(getContext(), OrdersFragment.ordersThisMonth);
        }
        lvOrders.setAdapter(ordersAdapter);

        /*lvOrders.setOnItemClickListener(new AdapterView.OnItemClickListener() {

        }*/

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

}