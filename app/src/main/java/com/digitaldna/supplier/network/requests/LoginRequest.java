package com.digitaldna.supplier.network.requests;

import android.support.annotation.Nullable;
import android.util.Log;

import com.digitaldna.supplier.network.Urls;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginRequest {

    @JsonProperty("UserID")
    private String mUserID;

    @JsonProperty("Password")
    private String mPassword;

    @JsonProperty("Platform")
    private int mPlatform = Urls.PLATFORM;



    public LoginRequest(String userId, String password) {
       // super(ticket, userId);
        mUserID = userId;
        mPassword = password;

        Log.i("LLL4", "userId " + userId);
        Log.i("LLL4", "password " + password);
    }

 /*   public Long getCountryId() {
        return mUserID;
    }

    public void setCountryId(Long countryId) {
        mCountryId = countryId;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }*/

}
