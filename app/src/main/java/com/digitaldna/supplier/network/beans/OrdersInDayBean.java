package com.digitaldna.supplier.network.beans;


import android.support.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrdersInDayBean {
    @Nullable
    @JsonProperty("Orders")
    private Double mOrders;

    @Nullable
    @JsonProperty("CompletedOrders")
    private Double mCompletedOrders;

    @Nullable
    @JsonProperty("InCompletedOrders")
    private Double mInCompletedOrders;


    public int getOrders() {
        return Integer.valueOf(mOrders.intValue());
    }

    public int getCompletedOrders() {
        return Integer.valueOf(mCompletedOrders.intValue());
    }

    public int getInCompletedOrders() {
        return Integer.valueOf(mInCompletedOrders.intValue());
    }
}


