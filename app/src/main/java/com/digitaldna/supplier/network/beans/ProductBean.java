package com.digitaldna.supplier.network.beans;


import android.support.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductBean {
    @Nullable
    @JsonProperty("ProductID")
    private Integer mProductID;

    @Nullable
    @JsonProperty("ProductGroupID")
    private Integer mProductGroupID;

    @Nullable
    @JsonProperty("ProductName")
    private String mProductName;

    @Nullable
    @JsonProperty("Price")
    private Double mPrice;

    @Nullable
    @JsonProperty("Price2")
    private Double mPrice2;

    @Nullable
    public Integer getmProductID() {
        return mProductID;
    }

    public void setmProductID(@Nullable Integer mProductID) {
        this.mProductID = mProductID;
    }

    @Nullable
    public Integer getmProductGroupID() {
        return mProductGroupID;
    }

    public void setmProductGroupID(@Nullable Integer mProductGroupID) {
        this.mProductGroupID = mProductGroupID;
    }

    public Double getmPrice() {
        return mPrice;
    }

    public void setmPrice(Double mPrice) {
        this.mPrice = mPrice;
    }

    @Nullable
    public Double getmPrice2() {
        return mPrice2;
    }

    @Nullable
    public void setmPrice2(Double mPrice2) {
        this.mPrice2 = mPrice2;
    }


    @Nullable
    public String getmProductName() {
        return mProductName;
    }

    public void setmProductName(@Nullable String mProductName) {
        this.mProductName = mProductName;
    }



    int mSelectedQuantity = 0;
    @Nullable
    public int getSelectedProductsQuantity() {
        return mSelectedQuantity;
    }

    public void setSelectedProductsQuantity(@Nullable Integer quantity) {
        this.mSelectedQuantity = quantity;
    }




}


