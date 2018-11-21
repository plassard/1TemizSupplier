package com.digitaldna.supplier.network.beans;


import android.support.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class CapacityDayBean {

    @Nullable
    @JsonProperty("DayName")
    private String mDayName;

    @Nullable
    @JsonProperty("Date")
    private String mDate;

    @JsonProperty("TimePeriods")
    private List<TimePeriodsBean> mTimePeriods;

    @Nullable
    public String getDayName() {
        return mDayName;
    }

    @Nullable
    public String getDate() {
        return mDate;
    }

    public List<TimePeriodsBean> getTimePeriods() {
        return mTimePeriods;
    }


}


