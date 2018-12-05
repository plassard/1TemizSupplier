package com.digitaldna.supplier.network.beans;


import android.support.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductPricingBean {
    @Nullable
    @JsonProperty("ProductID")
    private Integer mProductID;

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
    @JsonProperty("MinimumPrice")
    private Double mMinimumPrice;

    @Nullable
    @JsonProperty("MinimumPrice2")
    private Double mMinimumPrice2;


    @Nullable
    @JsonProperty("MaximumPrice")
    private Double mMaximumPrice;

    @Nullable
    @JsonProperty("MaximumPrice2")
    private Double mMaximumPrice2;



    @Nullable
    @JsonProperty("RecommendedPrice")
    private Double mRecommendedPrice;

    @Nullable
    @JsonProperty("RecommendedPrice2")
    private Double mRecommendedPrice2;

    @Nullable
    public Double getmMinimumPrice() {
        return mMinimumPrice;
    }

    @Nullable
    public Double getmMinimumPrice2() {
        return mMinimumPrice2;
    }

    @Nullable
    public Double getmMaximumPrice() {
        return mMaximumPrice;
    }

    @Nullable
    public Double getmMaximumPrice2() {
        return mMaximumPrice2;
    }

    @Nullable
    public Double getmRecommendedPrice() {
        return mRecommendedPrice;
    }

    @Nullable
    public Double getmRecommendedPrice2() {

        return mRecommendedPrice2;
    }

    @Nullable
    public Integer getmProductID() {
        return mProductID;
    }

    public void setmProductID(@Nullable Integer mProductID) {
        this.mProductID = mProductID;
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


    public void setmMinimumPrice(@Nullable Double mMinimumPrice) {
        this.mMinimumPrice = mMinimumPrice;
    }

    public void setmMaximumPrice(@Nullable Double mMaximumPrice) {
        this.mMaximumPrice = mMaximumPrice;
    }

    public void setmRecommendedPrice(@Nullable Double mRecommendedPrice) {
        this.mRecommendedPrice = mRecommendedPrice;
    }
}


