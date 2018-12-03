package com.digitaldna.supplier.network.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseWithSupplierIdRequest extends BasicRequest {

    @JsonProperty("SupplierID")
    private int mSupplierID;

    public BaseWithSupplierIdRequest(Integer id, String userId, String ticket) {
        super(userId, ticket);
        mSupplierID = id;
    }



}
