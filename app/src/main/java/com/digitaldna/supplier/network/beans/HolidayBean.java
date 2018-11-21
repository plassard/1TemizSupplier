package com.digitaldna.supplier.network.beans;


import android.support.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HolidayBean {

    @Nullable
    @JsonProperty("Date")
    private String mDate;

    @Nullable
    public String getDate() {
        return mDate;
    }

    public void setDate(@Nullable String mDate) {
        this.mDate = mDate;
    }
}


