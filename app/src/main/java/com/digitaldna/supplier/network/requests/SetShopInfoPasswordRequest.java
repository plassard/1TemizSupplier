package com.digitaldna.supplier.network.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SetShopInfoPasswordRequest extends SetShopInformationRequest {

    @JsonProperty("Password")
    private String mPassword;

    public SetShopInfoPasswordRequest(String password, String title, String ShopName, String email, int GSMcountryID, String GSMnumber, int countryID, String number, String userId, String ticket) {
        super(title, ShopName, email, countryID, number, GSMcountryID, GSMnumber, userId, ticket);
        mPassword = password;
    }



}
