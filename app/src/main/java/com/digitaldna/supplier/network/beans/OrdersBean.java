package com.digitaldna.supplier.network.beans;


import android.support.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrdersBean {
    @Nullable
    @JsonProperty("OrderStatusID")
    private Integer mOrderStatusID;

    @Nullable
    @JsonProperty("CourierImageURL")
    private String mCourierImageURL;

    @Nullable
    @JsonProperty("CustomerTitle")
    private String mCustomerTitle;

    @Nullable
    @JsonProperty("JobStartTime")
    private String mJobStartTime;

    @Nullable
    @JsonProperty("JobEndTime")
    private String mJobEndTime;

    @Nullable
    @JsonProperty("OrderJobDate")
    private String mOrderJobDate;

    @Nullable
    @JsonProperty("Location")
    private String mLocation;

    @Nullable
    @JsonProperty("OrderStatusText")
    private String mOrderStatusText;

    @Nullable
    @JsonProperty("OrderNumber")
    private String mOrderNumber;

    @Nullable
    @JsonProperty("OrderID")
    private Integer mOrderID;

    private boolean wasViewed = false;

    @Nullable
    public boolean setViewed() {
        wasViewed = true;
        return wasViewed;
    }

    @Nullable
    public boolean getWasViewed() {
        return wasViewed;
    }
    @Nullable
    public Integer getmOrderStatusID() {
        return mOrderStatusID;
    }

    @Nullable
    public Integer getOrderID() {
        return mOrderID;
    }

    @Nullable
    public String getCustomerTitle() {
        return mCustomerTitle;
    }

    @Nullable
    public String getLocation() {
        return mLocation;
    }

    @Nullable
    public String getOrderStatusText() {
        return mOrderStatusText;
    }

    @Nullable
    public String getOrderNumber() {
        return mOrderNumber;
    }
    @Nullable
    public String getCourierImageURL() {
        return mCourierImageURL;
    }

    @Nullable
    public String getJobStartTime() {
        return mJobStartTime.substring(0, 5);
    }

    @Nullable
    public String getJobEndTime() {
        return mJobEndTime.substring(0, 5);
    }

    @Nullable
    public String getOrderJobDate() {
        return mOrderJobDate;
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


