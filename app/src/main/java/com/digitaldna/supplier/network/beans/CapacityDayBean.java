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



    @JsonProperty("TimePeriods")
    private List<TimePeriodsBean> mTimePeriods;

    @Nullable
    public String getDayName() {
        return mDayName;
    }

    public List<TimePeriodsBean> getTimePeriods() {
        return mTimePeriods;
    }


}


