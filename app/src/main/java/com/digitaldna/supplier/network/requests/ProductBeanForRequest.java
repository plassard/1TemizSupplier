package com.digitaldna.supplier.network.requests;


import android.support.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductBeanForRequest {
    @Nullable
    @JsonProperty("ProductID")
    private Integer mProductID;

    @Nullable
    @JsonProperty("ProductGroupID")
    private Integer mProductGroupID;

    @Nullable
    @JsonProperty("Quantity")
    private Integer mQuantity;

    @Nullable
    @JsonProperty("PriceIndex")
    private Integer mPriceIndex;

    public void setmProductID(@Nullable Integer mProductID) {
        this.mProductID = mProductID;
    }

    public void setmProductGroupID(@Nullable Integer mProductGroupID) {
        this.mProductGroupID = mProductGroupID;
    }

    public void setmQuantity(@Nullable Integer mQuantity) {
        this.mQuantity = mQuantity;
    }

    public void setmPriceIndex(@Nullable Integer mPriceIndex) {
        this.mPriceIndex = mPriceIndex;
    }
}


