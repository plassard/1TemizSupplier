package com.digitaldna.supplier.ui.screens.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.digitaldna.supplier.R;
import com.digitaldna.supplier.network.Urls;
import com.digitaldna.supplier.network.beans.CommentsBean;
import com.digitaldna.supplier.utils.ImageToCircleTransform;
import com.digitaldna.supplier.widgets.OrderStatusView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by yevgen on 3/19/18.
 */

public class CommentsListAdapter extends BaseAdapter {

    Context context;
    private List<CommentsBean> comments;


    public CommentsListAdapter(Context context, List<CommentsBean> comments) {
        this.comments = comments;
        this.context = context;
    }


    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public CommentsBean getItem(int position) {
        return comments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        CommentsBean commentItem = comments.get(position);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_comment, null);
        }
        holder = new ViewHolder();

        TextView tvCustomerName = (TextView) convertView.findViewById(R.id.tv_CustomerName);
        tvCustomerName.setText(commentItem.getName());

        TextView tvOrderNumber = (TextView) convertView.findViewById(R.id.tv_OrderNumber);
        tvOrderNumber.setText(context.getResources().getString(R.string.order) + " #" + commentItem.getOrderNumber());

        TextView tvCommentText = (TextView) convertView.findViewById(R.id.tv_CommentText);
        tvCommentText.setText(commentItem.getCommentText());

        ImageView ivCustomerPicture = (ImageView)convertView.findViewById(R.id.iv_avatar);
        try{
            Picasso.with(context)
                    .load(Urls.HOST_URL + "/" + commentItem.getCustomerImageURL())
                    .placeholder(R.drawable.svg_ic_clock_blue_24dp)
                    .error(R.drawable.svg_ic_clock_blue_24dp)
                    .transform(new ImageToCircleTransform())
                    .into(ivCustomerPicture);
        }catch (Exception e) {}

       // convertView.setOnClickListener(view -> openOrderDetailsScreen(orderItem));

        return convertView;
    }


    private static class ViewHolder {

        ImageView imageView;

    }
}
