package com.digitaldna.supplier.ui.screens.orders;

import android.content.Context;
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
import com.digitaldna.supplier.network.beans.OrdersBean;
import com.digitaldna.supplier.widgets.OrderStatusView;
import com.digitaldna.supplier.widgets.StatusParams;

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



        //image
        //holder.imageView = (ImageView) convertView.findViewById(R.id.iv);

        //com.squareup.picasso:picasso:2.5.2 for downloadimage
      //  Picasso.with(parent.getContext()).load(item.getImageUrl()).resize(120, 102).into(holder.imageView);

/*


        if(item.getmTitle().length() > 33){
            lblListHeader.setTextSize(12);
        }
        Log.i("TTT", "leng " + item.getmTitle().length());

        if(item.getmTitle().length() > 33){
            Log.i("TTT", "shortening ");
            String str =item.getmTitle();
            try{
                str = str.substring(0, 50); } catch (Exception e){}
            lblListHeader.setText(str);
        }else {
            lblListHeader.setText(item.getmTitle());
        }
        //distance
        TextView distances = (TextView) convertView
                .findViewById(R.id.textView14);
        TextView distances1 = (TextView) convertView
                .findViewById(R.id.textView13);

        distances.setTypeface(LoginActivity.typeface);
        distances1.setTypeface(LoginActivity.typeface);

        distances.setTextColor(ContextCompat.getColor(context, R.color.text_gri));
        distances.setText(" " + item.getmdistance() + " " + item.getCurrency().toLowerCase());


        //price
        TextView price = (TextView) convertView
                .findViewById(R.id.textView17);
        price.setTypeface(LoginActivity.typeface);


        Float f = Float.parseFloat(item.getmprice());
        price.setText(context.getResources().getString(R.string.price_range_adapter) + " " + String.format("%.2f", f) + " " + item.getDistanceUnit());

        //rating
        TextView rating = (TextView) convertView
                .findViewById(R.id.textView16);
        rating.setTypeface(LoginActivity.typeface);
        rating.setText(item.getmrating().substring(0, 3));


        //opennow
        TextView opennow = (TextView) convertView
                .findViewById(R.id.textView18);
        opennow.setTypeface(LoginActivity.typeface);
        opennow.setText(item.getOpenNowStr());

        //organic

        TextView organic = (TextView) convertView
                .findViewById(R.id.textView128);
        organic.setTypeface(LoginActivity.typeface);
        organic.setText(item.getPropertyName());


        if (item.getmrating().equals("nul")) {
            rating.setText(context.getResources().getString(R.string.null_rating));
            rating.setBackground(context.getResources().getDrawable(R.drawable.transparent_select));

            int heights = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 58, context.getResources().getDisplayMetrics());
            rating.getLayoutParams().width = heights;
            SupplierInfoActivity.rating = item.getmrating();
        }
        SupplierInfoActivity.title = item.getmTitle();
        SupplierInfoActivity.distance = item.getmdistance();
        SupplierInfoActivity.rating = item.getmrating();
        SupplierInfoActivity.workhoursJson = item.getworkarray();
        SupplierInfoActivity.profilPicUrl = item.getImageUrl();
        SupplierInfoActivity.isLiked = item.isFavourite();
        SupplierInfoActivity.commentCount = item.getCommentCount();
        HttpPost.memberId = item.getMemberId();
*/


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
