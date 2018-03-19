package com.digitaldna.supplier.ui.screens.orders;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page_layout, container, false);

        TextView txt = (TextView) rootView.findViewById(R.id.page_number_label);
        int page = getArguments().getInt(ARG_PAGE_NUMBER, -1);
        txt.setText(String.format("Page %d", page) /*+ OrdersFragment.ordersBeanList.get(page).getJobEndTime()*/);
        lvOrders = (ListView) rootView.findViewById(R.id.lvOrders);
        OrderListAdapter ordersAdapter = new OrderListAdapter(getContext(), OrdersFragment.ordersThisWeek);
        lvOrders.setAdapter(ordersAdapter);

        /*lvOrders.setOnItemClickListener(new AdapterView.OnItemClickListener() {

        }*/

        return rootView;
    }
}