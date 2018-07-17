package com.digitaldna.supplier.network.beans;


import android.support.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AcceptOrderData {

    @Nullable
    @JsonProperty("ProfilePictureURL")
    private String mProfilePictureURL;

    @Nullable
    @JsonProperty("CourierID")
    private Integer mCourierID;


    @Nullable
    public String getProfilePictureURL() {
        return mProfilePictureURL;
    }

    public void setProfilePictureURL(@Nullable String pictureURL) {
        mProfilePictureURL = pictureURL;
    }

    @Nullable
    public Integer getCourierID() {
        return mCourierID;
    }

}


