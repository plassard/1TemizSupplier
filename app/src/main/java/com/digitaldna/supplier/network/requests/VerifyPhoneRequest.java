package com.digitaldna.supplier.network.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VerifyPhoneRequest {

    @JsonProperty("CountryID")
    private int mCountryID;

    @JsonProperty("PhoneNumber")
    private String mPhoneNumber;

    @JsonProperty("VerificationCode")
    private String mVerificationCode;

    @JsonProperty("Ticket")
    private String mTicket;

    @JsonProperty("UserID")
    private String mUserID;

    public VerifyPhoneRequest(int countryID, String phoneNumber, String verifCode, String userId, String ticket) {
        mCountryID = countryID;
        mPhoneNumber = phoneNumber;
        mVerificationCode = verifCode;
        mUserID = userId;
        mTicket = ticket;
    }

}
