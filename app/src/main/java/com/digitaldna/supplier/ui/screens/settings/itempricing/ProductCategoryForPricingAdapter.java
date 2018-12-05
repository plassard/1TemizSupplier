package com.digitaldna.supplier.ui.screens.settings.itempricing;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitaldna.supplier.R;
import com.digitaldna.supplier.network.Urls;
import com.digitaldna.supplier.network.beans.ProductGroupBean;
import com.digitaldna.supplier.ui.screens.orders.AddItemsActivity;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by egemen.durmus on 14/08/16.
 * Adapter for category menu at the top of ItemPriceListActivity.
 */
public class ProductCategoryForPricingAdapter extends RecyclerView
        .Adapter<ProductCategoryForPricingAdapter
        .DataObjectHolder> {
    private static List<ProductGroupBean> productCategoryItems;
    Context ctx;

    public DataObjectHolder dataObjectHolder;
    public ProductCategoryForPricingAdapter(List<ProductGroupBean> myDataset) {
        productCategoryItems = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_product_group, parent, false);
        dataObjectHolder = new DataObjectHolder(view);

        ctx = (parent.getContext());
        return dataObjectHolder;
    }
    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.label.setText(productCategoryItems.get(position).getmGroupName());
        holder.count.setText(String.valueOf(productCategoryItems.get(position).getSelectedProductsCount()));

        try {
            if(ItemPricingActivity.selectedGroup == position) {
                Picasso.with(ctx)
                        .load(Urls.HOST_URL + "/" + productCategoryItems.get(position).getmGroupActiveImageURL())
                        .into(holder.icon);
            } else {
                Picasso.with(ctx)
                        .load(Urls.HOST_URL + "/" + productCategoryItems.get(position).getmGroupInactiveImageURL())
                        .into(holder.icon);
            }

        } catch (Exception ex) {
           // StaticMethods.writeExceptionLog(ctx, ex);
        }
    }
    @Override
    public int getItemCount() {
        return productCategoryItems.size();
    }


    public class DataObjectHolder extends RecyclerView.ViewHolder
           {
        public ImageView icon;
        TextView label;
        TextView count;
        public DataObjectHolder(View itemView) {
            super(itemView);
            label = (TextView) itemView.findViewById(R.id.tv_category_name);
            count = (TextView) itemView.findViewById(R.id.tv_group_product_count);
            icon = (ImageView) itemView.findViewById(R.id.iv_icon);
        }


    }
}