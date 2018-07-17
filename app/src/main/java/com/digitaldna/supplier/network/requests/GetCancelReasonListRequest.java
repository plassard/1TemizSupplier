package com.digitaldna.supplier.network.requests;

import android.util.Log;

import com.digitaldna.supplier.network.Urls;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetCancelReasonListRequest {

    @JsonProperty("UserID")
    private String mUserID;

    @JsonProperty("Ticket")
    private String mTicket;


    public GetCancelReasonListRequest(String userId, String ticket) {
        mUserID = userId;
        mTicket = ticket;
    }

}
