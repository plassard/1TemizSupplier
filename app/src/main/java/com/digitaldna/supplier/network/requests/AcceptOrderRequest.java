package com.digitaldna.supplier.network.requests;

import com.digitaldna.supplier.network.Urls;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AcceptOrderRequest {

    @JsonProperty("OrderID")
    private int mOrderID;

    @JsonProperty("Ticket")
    private String mTicket;

    @JsonProperty("UserID")
    private String mUserID;

    public AcceptOrderRequest(String userId, String ticket, int orderID) {
        mUserID = userId;
        mTicket = ticket;
        mOrderID = orderID;
    }

}
