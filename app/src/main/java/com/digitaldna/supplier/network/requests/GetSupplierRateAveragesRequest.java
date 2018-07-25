package com.digitaldna.supplier.network.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetSupplierRateAveragesRequest extends BasicRequest {

    @JsonProperty("Filter")
    private int mFilter;

    public GetSupplierRateAveragesRequest(String userId, String ticket) {
        super(userId, ticket);
        mFilter = 10;
    }



}
