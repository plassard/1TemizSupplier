package com.digitaldna.supplier.ui.screens.orders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.digitaldna.supplier.R;
import com.digitaldna.supplier.network.beans.OrdersBean;

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
        OrdersBean item = orders.get(position);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_order, null);
        }
        holder = new ViewHolder();


        //image
        //holder.imageView = (ImageView) convertView.findViewById(R.id.iv);

        //com.squareup.picasso:picasso:2.5.2 for downloadimage
      //  Picasso.with(parent.getContext()).load(item.getImageUrl()).resize(120, 102).into(holder.imageView);

/*
        //supplier name
        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setTypeface(LoginActivity.typeface);

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
        return convertView;
    }

    private static class ViewHolder {

        ImageView imageView;

    }
}
