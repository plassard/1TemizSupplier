package com.digitaldna.supplier.network.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RejectOrderRequest {

    @JsonProperty("OrderID")
    private int mOrderID;

    @JsonProperty("CancelReasonID")
    private int mCancelReasonID;

    @JsonProperty("Explanation")
    private String mExplanation;

    @JsonProperty("Ticket")
    private String mTicket;

    @JsonProperty("UserID")
    private String mUserID;

    public RejectOrderRequest(String userId, String ticket, int orderID, int cancelReason, String explanation) {
        mUserID = userId;
        mTicket = ticket;
        mOrderID = orderID;
        mCancelReasonID = cancelReason;
        mExplanation = explanation;
    }

}
