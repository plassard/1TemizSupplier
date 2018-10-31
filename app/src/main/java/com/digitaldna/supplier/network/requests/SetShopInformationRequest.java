package com.digitaldna.supplier.network.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SetShopInformationRequest extends BasicRequest {

    @JsonProperty("Platform")
    private int mPlatform = 65;

    @JsonProperty("Title")
    private String mTitle;

    @JsonProperty("ShopName")
    private String mShopName;

    @JsonProperty("Email")
    private String mEmail;

    @JsonProperty("GsmNumberCountryID")
    private int mGsmNumberCountryID;

    @JsonProperty("GsmNumber")
    private String mGsmNumber;

    @JsonProperty("PhoneNumberCountryID")
    private int mPhoneNumberCountryID;

    @JsonProperty("PhoneNumber")
    private String mPhoneNumber;

    @JsonProperty("DeleteCurrentProfilePicture")
    private boolean mDeleteCurrentProfilePicture = false;

    @JsonProperty("DeleteCurrentCoverPicture")
    private boolean mDeleteCurrentCoverPicture = false;

    public SetShopInformationRequest(String title, String ShopName, String email, int GSMcountryID, String GSMnumber, int countryID, String number, String userId, String ticket) {
        super(userId, ticket);
        mTitle = title;
        mShopName = ShopName;
        mEmail = email;
        mGsmNumberCountryID = GSMcountryID;
        mGsmNumber = GSMnumber;
        mPhoneNumberCountryID = countryID;
        mPhoneNumber = number;
    }



}
