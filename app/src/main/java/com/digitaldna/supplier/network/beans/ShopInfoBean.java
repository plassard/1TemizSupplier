package com.digitaldna.supplier.network.beans;


import android.support.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ShopInfoBean {
    @Nullable
    @JsonProperty("Title")
    private String mTitle;

    @Nullable
    @JsonProperty("Email")
    private String mEmail;

    @Nullable
    @JsonProperty("ProfilePictureURL")
    private String mProfilePictureURL;

    @Nullable
    @JsonProperty("Ticket")
    private String mTicket;

    @Nullable
    @JsonProperty("Language")
    private Integer mLanguageID;

    @Nullable
    @JsonProperty("CountryID")
    private Integer mCountryID;

    @Nullable
    @JsonProperty("GsmNumber")
    private String mGsmNumber;

    @Nullable
    @JsonProperty("GsmNumberCountryID")
    private int mGsmNumberCountryID;

    @Nullable
    @JsonProperty("PhoneNumber")
    private String mPhoneNumber;

    @Nullable
    @JsonProperty("AreaCode")
    private String mAreaCode;

    @Nullable
    @JsonProperty("isPhoneNumberVerified")
    private Boolean mIsPhoneNumberVerified;

    @Nullable
    @JsonProperty("ShopName")
    private String mShopName;

    @Nullable
    public String getShopName() {
        return mShopName;
    }

    public void setShopName(@Nullable String ShopName) {
        mShopName = ShopName;
    }

    @Nullable
    public String getTitle() {
        return mTitle;
    }

    public void setTitle(@Nullable String title) {
        mTitle = title;
    }

    @Nullable
    public String getEmail() {
        return mEmail;
    }

    public void setEmail(@Nullable String email) {
        mEmail = email;
    }



    @Nullable
    public String getProfilePictureURL() {
        return mProfilePictureURL;
    }

    public void setProfilePictureURL(@Nullable String pictureURL) {
        mProfilePictureURL = pictureURL;
    }

    @Nullable
    public String getTicket() {
        return mTicket;
    }

    public void setTicket(@Nullable String ticket) {
        mTicket = ticket;
    }

    @Nullable
    public Integer getCountryID() {
        return mCountryID;
    }

    @Nullable
    public Integer getGsmNumberCountryID() {
        return mGsmNumberCountryID;
    }

    @Nullable
    public String getGsmNumber() {
        return mGsmNumber;
    }

    @Nullable
    public Integer getLanguageID() {
        if(mLanguageID != null)
            return mLanguageID;
        else
            return 1;
    }


    @Nullable
    public String getPhoneNumber() {
        if(mAreaCode != null)
            return mAreaCode + mPhoneNumber;
        else
            return mPhoneNumber;
    }

    @Nullable
    public Boolean getIsPhoneNumberVerified() {
        return mIsPhoneNumberVerified;
    }

    public void setIsPhoneNumberVerified(@Nullable Boolean isPhoneNumberVerified) {
        mIsPhoneNumberVerified = isPhoneNumberVerified;
    }
}


