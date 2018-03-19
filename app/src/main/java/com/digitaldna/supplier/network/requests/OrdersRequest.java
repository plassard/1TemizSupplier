package com.digitaldna.supplier.network.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrdersRequest {

    @JsonProperty("UserID")
    private String mUserID;

    @JsonProperty("Ticket")
    private String mTicket;

    public OrdersRequest(String userId, String ticket) {
       // super(ticket, userId);
        mUserID = userId;
        mTicket = ticket;
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
