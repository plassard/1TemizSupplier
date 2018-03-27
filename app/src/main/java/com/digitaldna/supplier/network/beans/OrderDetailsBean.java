package com.digitaldna.supplier.network.beans;


import android.support.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class OrderDetailsBean {
    @Nullable
    @JsonProperty("OrderNumber")
    private String mOrderNumber;


    @Nullable
    @JsonProperty("OrderStatusText")
    private String mOrderStatusText;


    @Nullable
    @JsonProperty("TotalPrice")
    private Double mTotalPrice;

    @Nullable
    @JsonProperty("Products")
    private List<OrderProductBean> mProducts;

 /*   @Nullable
    @JsonProperty("ProfilePictureURL")
    private String mProfilePictureURL;

    @Nullable
    @JsonProperty("Ticket")
    private String mTicket;*/

    public String getmOrderStatusText() {
        return mOrderStatusText;
    }

    public Double getTotalPrice() {
        return mTotalPrice;
    }

    public List<OrderProductBean> getProducts() {
        return mProducts;
    }

    public String getProductsString() {
        return mProducts.toString();
    }

    public void setProducts(List<OrderProductBean> products) {
        mProducts = products;
    }

    /*@Nullable
    @JsonProperty("Countdown")
    private Integer mCountdown;

    @Nullable
    @JsonProperty("isPhoneNumberVerified")
    private Boolean mIsPhoneNumberVerified;*/

    @Nullable
    public String getOrderNumber() {
        return mOrderNumber;
    }



   /* @Nullable
    public Long getLanguage() {
        return mLanguage;
    }

    public void setLanguage(@Nullable Long language) {
        mLanguage = language;
    }

    @Nullable
    public Integer getCountdown() {
        return mCountdown;
    }

    public void setCountdown(@Nullable Integer countdown) {
        mCountdown = countdown;
    }

    @Nullable
    public Boolean getIsPhoneNumberVerified() {
        return mIsPhoneNumberVerified;
    }

    public void setIsPhoneNumberVerified(@Nullable Boolean isPhoneNumberVerified) {
        mIsPhoneNumberVerified = isPhoneNumberVerified;
    }*/
}


