package com.digitaldna.supplier.network.beans.base;


import android.support.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class BaseJsonDataBean<T> extends BaseJsonBean {
    public static final String DATA = "Data";
    @Nullable
    @JsonProperty(DATA)
    private T mData;

    @Nullable
    public T getData() {
        return mData;
    }

    public void setData(@Nullable T data) {
        mData = data;
    }

}