package com.digitaldna.supplier.network.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetOrderDetailsRequest extends BasicRequest {

    @JsonProperty("OrderID")
    private int mOrderID;

    public GetOrderDetailsRequest(Integer orderID, String userId, String ticket) {
        super(userId, ticket);
        mOrderID = orderID;
    }



}
