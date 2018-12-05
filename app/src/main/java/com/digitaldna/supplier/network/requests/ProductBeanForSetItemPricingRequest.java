package com.digitaldna.supplier.network.requests;


import android.support.annotation.Nullable;
import android.util.Log;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductBeanForSetItemPricingRequest {
    @Nullable
    @JsonProperty("ProductID")
    private Integer mProductID;

    @Nullable
    @JsonProperty("ProductName")
    private String mProductName;

    @Nullable
    @JsonProperty("CurrencyID")
    private Integer mCurrencyID;

    @Nullable
    @JsonProperty("Currency")
    private String mCurrency;

    @Nullable
    @JsonProperty("Price")
    private String mPrice;

    @Nullable
    @JsonProperty("Price2")
    private String mPrice2;

    @Nullable
    @JsonProperty("IsActive")
    private boolean mIsActive;

    public void setmProductID(@Nullable Integer mProductID) {
        this.mProductID = mProductID;
    }

    public void setmProductName(@Nullable String mProductName) {
        this.mProductName = mProductName;
    }

    public void setmCurrencyID(@Nullable Integer mCurrencyID) {
        this.mCurrencyID = mCurrencyID;
    }

    public void setmCurrency(@Nullable String mCurrency) {
        this.mCurrency = mCurrency;
    }

    public void setmPrice(@Nullable String mPrice) {
        this.mPrice = mPrice;
    }

    public void setmPrice2(@Nullable String mcomingPrice2) {
        Log.i("SETTTT", "mcomingPrice2 " + mcomingPrice2);
        if(mcomingPrice2 == null){
            Log.i("SETTTT", "mcomingPrice2 is NULL " + mcomingPrice2);
            this.mPrice2 = null;
        } else {
            if(mcomingPrice2.equals("null")){
                this.mPrice2 = null;
            } else {
                this.mPrice2 = mcomingPrice2;
            }
            Log.i("SETTTT", "mcomingPrice2 is NOOOOT NULL " + mcomingPrice2);

        }

    }

    public void setmIsActive(@Nullable boolean mIsActive) {
        this.mIsActive = mIsActive;
    }

    @Nullable
    public String getPrice2() {
        return mPrice2;
    }

    @Nullable
    public Integer getmProductID() {
        return mProductID;
    }
}


