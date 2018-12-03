package com.digitaldna.supplier.ui.screens.orders;

import android.content.Context;
import android.widget.TextView;

import com.digitaldna.supplier.R;
import com.digitaldna.supplier.network.beans.ProductBean;
import com.digitaldna.supplier.network.beans.ProductGroupBean;

import java.io.Serializable;

/**
 * Created by egemendurmus on 26/06/16.
 */
public class SupplierProductPriceItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private String groupName;
    private String currency;
    private String price;
    private int count;
    private int productId;


    public SupplierProductPriceItem() {

    }

    public SupplierProductPriceItem(String groupName, String price, int count, int productId, String currency) {
        this.groupName = groupName;
        this.price = price;
        this.count = count;
        this.productId = productId;
        this.currency = currency;
    }


    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getPrice() {
        return price;
    }


    public String getCount() {
        return String.valueOf(count);
    }

    public void setCount(int value, TextView tvTotalItems, TextView tvTotalPrice, Context context) {
        count = value;
        int idOfGroup = 0;
        int amountOfSelectedItemsTotal = 0;
        double totalPrice = 0;
        for (ProductBean product : AddItemsActivity.selectedProducts) {
            if(product.getmProductID() == this.productId){
                product.setSelectedProductsQuantity(value);
                idOfGroup = product.getmProductGroupID();
            }
            amountOfSelectedItemsTotal = amountOfSelectedItemsTotal + product.getSelectedProductsQuantity();
            totalPrice = totalPrice + product.getmPrice() * product.getSelectedProductsQuantity();
        }

        //calculate items in group to display
        for (ProductGroupBean productGroup : AddItemsActivity.productGroups) {
            if(productGroup.getmProductGroupID() == idOfGroup){
                int amountOfSelectedItemsInCategory = 0;
                for (ProductBean product : AddItemsActivity.selectedProducts) {
                    if(product.getmProductGroupID() == idOfGroup) {
                        amountOfSelectedItemsInCategory = amountOfSelectedItemsInCategory + product.getSelectedProductsQuantity();
                    }
                }
                productGroup.setSelectedProductsCount(amountOfSelectedItemsInCategory);
            }
        }
        AddItemsActivity.productCategoryAdapter.notifyDataSetChanged();


        tvTotalItems.setText(amountOfSelectedItemsTotal + " " + context.getResources().getString(R.string.items));
        tvTotalPrice.setText(context.getResources().getString(R.string.add_item_format_total_price) + " " +  "₺" + totalPrice);
    }

    public int getProductId() {
        return productId;
    }

    public String getCurrency() {
        return currency;
    }

}