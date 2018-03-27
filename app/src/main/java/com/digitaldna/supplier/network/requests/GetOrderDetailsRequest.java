package com.digitaldna.supplier.network.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetOrderDetailsRequest {

    @JsonProperty("UserID")
    private String mUserID;

    @JsonProperty("Ticket")
    private String mTicket;

    @JsonProperty("OrderID")
    private int mOrderID;


    public GetOrderDetailsRequest(String userId, String ticket, Integer orderID) {
       // super(ticket, userId);
        mUserID = userId;
        mTicket = ticket;
        mOrderID = orderID;
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
