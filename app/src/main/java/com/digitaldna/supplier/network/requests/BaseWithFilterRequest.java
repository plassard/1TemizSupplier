package com.digitaldna.supplier.network.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseWithFilterRequest extends BasicRequest {

    @JsonProperty("Filter")
    private int mFilter;

    public BaseWithFilterRequest(String userId, String ticket) {
        super(userId, ticket);
        mFilter = 10;
    }



}
