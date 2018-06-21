package com.digitaldna.supplier.network.requests;

import android.support.annotation.Nullable;
import android.util.Log;

import com.digitaldna.supplier.network.Urls;
import com.digitaldna.supplier.utils.PrefProvider;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginRequest {

    @JsonProperty("UserID")
    private String mUserID;

    @JsonProperty("Password")
    private String mPassword;

    @JsonProperty("Platform")
    private int mPlatform = Urls.PLATFORM;

    @JsonProperty("Language")
    private Integer mLanguage;

    public LoginRequest(String userId, String password, Integer langId) {
       // super(ticket, userId);
        mUserID = userId;
        mPassword = password;
        mLanguage = langId;
    }

}
