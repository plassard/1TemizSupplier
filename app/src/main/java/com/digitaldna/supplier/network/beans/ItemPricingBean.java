package com.digitaldna.supplier.network.beans;


import android.content.Context;
import android.support.annotation.Nullable;

import com.digitaldna.supplier.R;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ItemPricingBean {
    @Nullable
    @JsonProperty("ProductGroupName")
    private String mProductGroupName;

    @Nullable
    public String getProductGroupName() {
        return mProductGroupName;
    }

    @Nullable
    @JsonProperty("Products")
    private List<ProductPricingBean> mProducts;


    @Nullable
    public List<ProductPricingBean> getProducts() {
        return mProducts;
    }
}


