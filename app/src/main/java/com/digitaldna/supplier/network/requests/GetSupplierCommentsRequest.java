package com.digitaldna.supplier.network.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetSupplierCommentsRequest extends BasicRequest {

    @JsonProperty("Filter")
    private int mFilter;

    public GetSupplierCommentsRequest(String userId, String ticket) {
        super(userId, ticket);
        mFilter = 10;
    }



}
