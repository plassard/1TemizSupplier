package com.digitaldna.supplier.ui.screens.settings.itempricing;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitaldna.supplier.R;
import com.digitaldna.supplier.network.beans.ProductPricingBean;

import java.util.List;

/**
 * Created by egemen.durmus on 14/08/16.
 * Adapter for category menu at the top of ItemPriceListActivity.
 */
public class ProductsForPricingAdapter extends RecyclerView
        .Adapter<ProductsForPricingAdapter
        .DataObjectHolder> {
    private static List<ProductPricingBean> productCategoryItems;
    Context ctx;
    FrameLayout flBottomContainer;

    public DataObjectHolder dataObjectHolder;
    public ProductsForPricingAdapter(List<ProductPricingBean> myDataset, FrameLayout frameLayout) {
        productCategoryItems = myDataset;
        flBottomContainer = frameLayout;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_in_pricing, parent, false);
        dataObjectHolder = new DataObjectHolder(view);

        ctx = (parent.getContext());
        return dataObjectHolder;
    }
    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        Log.i("jkljkkj", "productCategoryItems.size()" + productCategoryItems.size());
        Log.i("jkljkkj", "productCategoryItems.get(position).getmProductName())" +productCategoryItems.get(position).getmProductName());
        holder.label.setText(productCategoryItems.get(position).getmProductName());
        if(productCategoryItems.get(position).getmPrice() != null){
            holder.etPrice.setText(String.valueOf(productCategoryItems.get(position).getmPrice()));
        }

        holder.iconInfo.setOnClickListener(v -> {
            final Dialog dialog = new Dialog(ctx);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.dialog_info);
            TextView text = (TextView) dialog.findViewById(R.id.textViewErrorMessage);
            text.setText(ctx.getResources().getString(R.string.minimum_price) + " ₺" + productCategoryItems.get(position).getmRecommendedPrice()/* + "\n" +
                    ctx.getResources().getString(R.string.recommended_price) + " ₺" + productCategoryItems.get(position).getmRecommendedPrice()*/);
            Button dialogButton = (Button) dialog.findViewById(R.id.buttonOK);
            dialogButton.setOnClickListener(v1 -> {
                    dialog.dismiss();
                });
            dialog.show();
        });

        holder.etPrice.setInputType(InputType.TYPE_CLASS_NUMBER);
        holder.etPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ItemPricingActivity.changesMade = true;
                flBottomContainer.setVisibility(View.VISIBLE);
                try {
                    ItemPricingActivity.productsToDisplay.get(position).setmPrice(Double.valueOf(s.toString()));
                }catch (Exception e) {
                    ItemPricingActivity.productsToDisplay.get(position).setmPrice(0D);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return productCategoryItems.size();
    }


    public class DataObjectHolder extends RecyclerView.ViewHolder
           {
        public ImageView iconInfo;
        TextView label;
        EditText etPrice;
        public DataObjectHolder(View itemView) {
            super(itemView);
            label = (TextView) itemView.findViewById(R.id.tv_title);
            etPrice = (EditText) itemView.findViewById(R.id.et_price);
            iconInfo = (ImageView) itemView.findViewById(R.id.ivInfo);
        }


    }
}