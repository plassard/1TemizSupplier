package com.digitaldna.supplier.network.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BasicRequest {

    @JsonProperty("UserID")
    private String mUserID;

    @JsonProperty("Ticket")
    private String mTicket;

    public BasicRequest(String userId, String ticket) {
       // super(ticket, userId);
        mUserID = userId;
        mTicket = ticket;
    }

}
