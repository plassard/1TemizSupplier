package com.digitaldna.supplier.network.requests;

import android.util.Log;

import com.digitaldna.supplier.network.Urls;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetVerificationCodeRequest {

    @JsonProperty("UserID")
    private String mUserID;

    @JsonProperty("VerificationType")
    private int mVerificationType;

    @JsonProperty("Ticket")
    private String mTicket;

    public GetVerificationCodeRequest(String userId, String ticket) {
        mUserID = userId;
        mVerificationType = 1;
        mTicket = ticket;
    }

}
