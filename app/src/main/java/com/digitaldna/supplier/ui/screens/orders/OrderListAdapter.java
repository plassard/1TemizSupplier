package com.digitaldna.supplier.ui.screens.orders;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.digitaldna.supplier.R;
import com.digitaldna.supplier.network.Urls;
import com.digitaldna.supplier.network.beans.OrdersBean;
import com.digitaldna.supplier.ui.screens.OrderDetailsActivity;
import com.digitaldna.supplier.utils.ImageToCircleTransform;
import com.digitaldna.supplier.utils.PrefProvider;
import com.digitaldna.supplier.widgets.OrderStatusView;
import com.digitaldna.supplier.widgets.StatusParams;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by yevgen on 3/19/18.
 */

public class OrderListAdapter extends BaseAdapter {

    Context context;
    private List<OrdersBean> orders;


    public OrderListAdapter(Context context, List<OrdersBean> orders) {
        this.orders = orders;
        this.context = context;
    }


    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public OrdersBean getItem(int position) {
        return orders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        OrdersBean orderItem = orders.get(position);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_order, null);
        }
        holder = new ViewHolder();

        TextView tvCustomerTitle = (TextView) convertView.findViewById(R.id.tv_customer);
        tvCustomerTitle.setText(orderItem.getCustomerTitle());

        TextView tvDistrict = (TextView) convertView.findViewById(R.id.tv_district);
        tvDistrict.setText(orderItem.getLocation());

        TextView tvTime = (TextView) convertView.findViewById(R.id.tv_time);
        tvTime.setText(orderItem.getJobStartTime() + " - " + orderItem.getJobEndTime());

        TextView tvOrderNumber = (TextView) convertView.findViewById(R.id.tv_order);
        tvOrderNumber.setText(orderItem.getOrderNumber());

        ImageView ivProfileClock = (ImageView)convertView.findViewById(R.id.iv_clock);
        try{
            Picasso.with(context)
                    .load(Urls.HOST_URL + "/" + orderItem.getCourierImageURL())
                    .placeholder(R.drawable.svg_ic_clock_blue_24dp)
                    .error(R.drawable.svg_ic_clock_blue_24dp)
                    .transform(new ImageToCircleTransform())
                    .into(ivProfileClock);
        }catch (Exception e) {}

        StatusParams mStatusParams;
        OrderStatusView osStatus;
        osStatus = (OrderStatusView) convertView.findViewById(R.id.os_status);

        mStatusParams = new StatusParams(orderItem.getOrderStatusText(),
                orderItem.getmOrderStatusID() == 500 ?
                        R.drawable.bg_status_text_pick_up : R.drawable.bg_status_text_drop_off,
                R.drawable.svg_ic_circle_red_10dp,
                orderItem.getmOrderStatusID() == 500 ?
                        R.style.OrderStatusPickUp : R.style.OrderStatusDropOff);

        osStatus.setStatusParams(mStatusParams, !orderItem.getWasViewed());


        TextView tvOrderJobDate = (TextView) convertView.findViewById(R.id.tv_date);

        //we compare date to set "Today" if date is today
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"); // your format
        Date orderDate = null;
        try {
            orderDate = format.parse(orderItem.getOrderJobDate());
        } catch (ParseException e) {            e.printStackTrace();        }
        Date dateToday = new Date();
        dateToday.setHours(0);
        dateToday.setMinutes(0);
        dateToday.setSeconds(0);
        if(orderDate.after(dateToday)){
            tvOrderJobDate.setText(context.getResources().getString(R.string.today));
        } else {

            tvOrderJobDate.setText(orderDate.toLocaleString());
        }

        convertView.setOnClickListener(view -> openOrderDetailsScreen(orderItem));


     /*   LinearLayout linearLayout = (LinearLayout)convertView.findViewById(R.id.linLayOrderRow);
        if(position < 5){
            linearLayout.setVisibility(View.INVISIBLE);
        }
try {
    if (orders.equals(OrdersFragment.ordersToday)) {
        linOrderToday[position] = linearLayout;
    } else if (orders.equals(OrdersFragment.ordersThisWeek)) {
        linOrderWeek[position] = linearLayout;
    } else if (orders.equals(OrdersFragment.ordersThisMonth)) {
        linOrderMonth[position] = linearLayout;
    }
}catch (Exception e) {}*/



        return convertView;
    }

private void openOrderDetailsScreen(OrdersBean orderItem){
    Intent intent = new Intent(context, OrderDetailsActivity.class);
    intent.putExtra("orderID", orderItem.getOrderID());
    context.startActivity(intent);
}

    static LinearLayout[] linOrderToday = new LinearLayout[5];
    static LinearLayout[] linOrderWeek = new LinearLayout[5];
    static LinearLayout[] linOrderMonth = new LinearLayout[5];



    static public void startAnimation(Context context, int position){

        for(int i = 0; i < 5; i++){
            try {
                linOrderMonth[i].setVisibility(View.INVISIBLE);
                linOrderWeek[i].setVisibility(View.INVISIBLE);
                linOrderToday[i].setVisibility(View.INVISIBLE);
            }catch (Exception e){}
        }

        Animation anim1 = AnimationUtils.loadAnimation(context, R.anim.transparency_in);
        Animation anim2 = AnimationUtils.loadAnimation(context, R.anim.transparency_in);
        Animation anim3 = AnimationUtils.loadAnimation(context, R.anim.transparency_in);
        Animation anim4 = AnimationUtils.loadAnimation(context, R.anim.transparency_in);
        Animation anim5 = AnimationUtils.loadAnimation(context, R.anim.transparency_in);
try {
   switch (position){
       case 0:
               try{
                       linOrderToday[0].setVisibility(View.VISIBLE);
                       linOrderToday[0].startAnimation(anim1);} catch (Exception e){}

           new Handler()
                   .postDelayed(() -> {try{
                       linOrderToday[1].setVisibility(View.VISIBLE);
                       linOrderToday[1].startAnimation(anim2);} catch (Exception e){}
                   }, 200);
           new Handler()
                   .postDelayed(() -> {try{
                       linOrderToday[2].setVisibility(View.VISIBLE);
                       linOrderToday[2].startAnimation(anim3);} catch (Exception e){}
                   }, 400);
           new Handler()
                   .postDelayed(() -> {try{
                       linOrderToday[3].setVisibility(View.VISIBLE);
                       linOrderToday[3].startAnimation(anim4);} catch (Exception e){}
                   }, 600);
           new Handler()
                   .postDelayed(() -> {try{
                       linOrderToday[4].setVisibility(View.VISIBLE);
                       linOrderToday[4].startAnimation(anim5);} catch (Exception e){}
                   }, 800);

       case 1:
           try{
               linOrderWeek[0].setVisibility(View.VISIBLE);
               linOrderWeek[0].startAnimation(anim1);} catch (Exception e){}
           new Handler()
                   .postDelayed(() -> {try{
                       linOrderWeek[1].setVisibility(View.VISIBLE);
                       linOrderWeek[1].startAnimation(anim2);} catch (Exception e){}
                   }, 200);
           new Handler()
                   .postDelayed(() -> {try{
                       linOrderWeek[2].setVisibility(View.VISIBLE);
                       linOrderWeek[2].startAnimation(anim3);} catch (Exception e){}
                   }, 400);
           new Handler()
                   .postDelayed(() -> {try{
                       linOrderWeek[3].setVisibility(View.VISIBLE);
                       linOrderWeek[3].startAnimation(anim4);} catch (Exception e){}
                   }, 600);
           new Handler()
                   .postDelayed(() -> {try{
                       linOrderWeek[4].setVisibility(View.VISIBLE);
                       linOrderWeek[4].startAnimation(anim5);} catch (Exception e){}
                   }, 800);
       case 2:
           try{
               linOrderMonth[0].setVisibility(View.VISIBLE);
               linOrderMonth[0].startAnimation(anim1);} catch (Exception e){}
           new Handler()
                   .postDelayed(() -> {try{
                       linOrderMonth[1].setVisibility(View.VISIBLE);
                       linOrderMonth[1].startAnimation(anim2);} catch (Exception e){}
                   }, 200);
           new Handler()
                   .postDelayed(() -> {try{
                       linOrderMonth[2].setVisibility(View.VISIBLE);
                       linOrderMonth[2].startAnimation(anim3);} catch (Exception e){}
                   }, 400);
           new Handler()
                   .postDelayed(() -> {try{
                       linOrderMonth[3].setVisibility(View.VISIBLE);
                       linOrderMonth[3].startAnimation(anim4);} catch (Exception e){}
                   }, 600);
           new Handler()
                   .postDelayed(() -> {try{
                       linOrderMonth[4].setVisibility(View.VISIBLE);
                       linOrderMonth[4].startAnimation(anim5);} catch (Exception e){}
                   }, 800);
   }


}catch (Exception e) {}
    }

    private static class ViewHolder {

        ImageView imageView;

    }
}
