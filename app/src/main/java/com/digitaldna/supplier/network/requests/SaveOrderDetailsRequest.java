package com.digitaldna.supplier.network.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SaveOrderDetailsRequest {

    @JsonProperty("UserID")
    private String mUserID;

    @JsonProperty("Ticket")
    private String mTicket;

    @JsonProperty("OrderID")
    private int mOrderID;

    @JsonProperty("CourierID")
    private int mCourierıd;

    public SaveOrderDetailsRequest(String userId, String ticket, Integer orderID, Integer Courierıd) {
       // super(ticket, userId);
        mUserID = userId;
        mTicket = ticket;
        mOrderID = orderID;
        mCourierıd = Courierıd;
    }



}
