package com.digitaldna.supplier.network.beans;


import android.support.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CommentsBean {

    @Nullable
    @JsonProperty("OrderNumber")
    private String mOrderNumber;

    @Nullable
    @JsonProperty("CustomerFirstName")
    private String mCustomerFirstName;

    @Nullable
    @JsonProperty("CustomerLastName")
    private String mCustomerLastName;

    @Nullable
    @JsonProperty("CommentText")
    private String mCommentText;

    @Nullable
    @JsonProperty("CustomerImageURL")
    private String mCustomerImageURL;

    @Nullable
    public String getOrderNumber() {
        return mOrderNumber;
    }

    @Nullable
    public String getName() {
        return mCustomerFirstName + " " + mCustomerLastName;
    }

    @Nullable
    public String getCustomerImageURL() {
        return mCustomerImageURL;
    }

    @Nullable
    public String getCommentText() {
        return mCommentText;
    }

}


