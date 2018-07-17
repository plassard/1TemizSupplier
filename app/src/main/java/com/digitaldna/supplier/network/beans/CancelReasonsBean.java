package com.digitaldna.supplier.network.beans;


import android.support.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CancelReasonsBean {
    @Nullable
    @JsonProperty("CancelReasonID")
    private Integer mCancelReasonID;

    @Nullable
    @JsonProperty("CancelReasonName")
    private String mCancelReasonName;

    @Nullable
    public Integer getCancelReasonID() {
        return mCancelReasonID;
    }

    @Nullable
    public String getCancelReasonName() {
        return mCancelReasonName;
    }

}


