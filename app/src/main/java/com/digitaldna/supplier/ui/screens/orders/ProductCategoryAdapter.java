package com.digitaldna.supplier.ui.screens.orders;

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
import com.digitaldna.supplier.utils.ImageToCircleTransform;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by egemen.durmus on 14/08/16.
 * Adapter for category menu at the top of ItemPriceListActivity.
 */
public class ProductCategoryAdapter extends RecyclerView
        .Adapter<ProductCategoryAdapter
        .DataObjectHolder> {
    public static int lastExpandedPosition = 8;
    private static List<ProductGroupBean> productCategoryItems;
    Context ctx;
    private Map<String, String> productCategoryMap = new HashMap<String, String>();
    private Map<String, Integer> productCategoryRedMap = new HashMap<String, Integer>();
    private MyClickListener myClickListener;
    public DataObjectHolder dataObjectHolder;
    public ProductCategoryAdapter(List<ProductGroupBean> myDataset) {
        productCategoryItems = myDataset;
    }
    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
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
        boolean xx = false;
        //holder.label.setTextColor(Color.parseColor("#abaeaf"));
        ProductGroupBean dd = productCategoryItems.get(position);
        int top_grup_ids = dd.getmProductCategoryID();
   //     productCategoryRedMap.put(dd.getId(), position);
     //   productCategoryMap.put(dd.getId(), top_grup_ids);
    //    productCategoryMap.put(dd.getId(), productCategoryItems.get(position).getDisableImageUrl());
        try {
            if(AddItemsActivity.selectedGroup == position) {
                Picasso.with(ctx)
                        .load(Urls.HOST_URL + "/" + productCategoryItems.get(position).getmGroupActiveImageURL())
                        .into(holder.icon);
            } else {
                Picasso.with(ctx)
                        .load(Urls.HOST_URL + "/" + productCategoryItems.get(position).getmGroupInactiveImageURL())
                        .into(holder.icon);
            }

          //  holder.icon.setImageBitmap(AddItemsActivity.inactiveImageMap.get(productCategoryItems.get(position).getGroup()));
        } catch (Exception ex) {
           // StaticMethods.writeExceptionLog(ctx, ex);
        }
    }
    @Override
    public int getItemCount() {
        return productCategoryItems.size();
    }
    public interface MyClickListener {
        void onItemClick(int position, View v);
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        public ImageView icon;
        SnapHelper snapHelper;
        TextView label;
        TextView count;
        public DataObjectHolder(View itemView) {
            super(itemView);
            label = (TextView) itemView.findViewById(R.id.tv_category_name);
            count = (TextView) itemView.findViewById(R.id.tv_group_product_count);
            icon = (ImageView) itemView.findViewById(R.id.iv_icon);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            Log.i("ITEMMM", "OnClick ");

            myClickListener.onItemClick(getPosition(), v);
            if (lastExpandedPosition != -1
                    && getPosition() != lastExpandedPosition) {
                //expListView.collapseGroup(lastExpandedPosition);
                ProductGroupBean xx = productCategoryItems.get(lastExpandedPosition);
                try {

                    Log.i("ITEMMM", "OnClick try");
                   // notifyItemChanged(lastExpandedPosition, changeImageColor(icon, label));

                    snapHelper = new LinearSnapHelper();
                   // snapHelper.attachToRecyclerView(ItemPriceListActivity.categoriesRecyclerView);
                } catch (Exception ex) {
                    Log.i("ITEMMM", "OnClick exc" + ex);
                   // StaticMethods.writeExceptionLog(ctx, ex);
                 //   if (getPosition() != 0)
                    //    notifyItemChanged(lastExpandedPosition, changeImageColor(icon, label));
                  //  else
                      //  notifyItemChanged(lastExpandedPosition, changeImageColor(icon, label));
                }
            }
            lastExpandedPosition = getPosition();
        }
        private Object changeImageColor(ImageView icon, TextView label) {
            try {
                //   icon.setImageBitmap(suplier_info.map_aktif.get(mDataset.get(getPosition()).get_pro_grup()));
              //  Picasso.with(ctx).load(SupplierInfoActivity.activeImageMap.get(productCategoryItems.get(getPosition()).getGroup())).into(icon);
                label.setTextColor(Color.RED);
            } catch (Exception ex) {
            //    StaticMethods.writeExceptionLog(ctx, ex);
              //  StaticMethods.showWarningWithoutRedirection(ctx);
            }
            return new DataObjectHolder(label);
        }
    }
}