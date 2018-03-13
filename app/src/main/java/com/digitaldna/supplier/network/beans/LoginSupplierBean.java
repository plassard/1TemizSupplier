package com.digitaldna.supplier.network.beans;


import android.support.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginSupplierBean {
    @Nullable
    @JsonProperty("UserID")
    private String mUserId;
    @Nullable
    @JsonProperty("Ticket")
    private String mTicket;
    @Nullable
    @JsonProperty("Language")
    private Long mLanguage;

    @Nullable
    @JsonProperty("Countdown")
    private Integer mCountdown;

    @Nullable
    @JsonProperty("isPhoneNumberVerified")
    private Boolean mIsPhoneNumberVerified;

    @Nullable
    public String getUserId() {
        return mUserId;
    }

    public void setUserId(@Nullable String userId) {
        mUserId = userId;
    }

    @Nullable
    public String getTicket() {
        return mTicket;
    }

    public void setTicket(@Nullable String ticket) {
        mTicket = ticket;
    }

    @Nullable
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
    }
}


