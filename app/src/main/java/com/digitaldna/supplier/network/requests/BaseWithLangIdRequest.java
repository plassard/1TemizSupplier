package com.digitaldna.supplier.network.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseWithLangIdRequest extends BasicRequest {

    @JsonProperty("Language")
    private int mLanguage;

    public BaseWithLangIdRequest(Integer langID, String userId, String ticket) {
        super(userId, ticket);
        mLanguage = langID;
    }



}
