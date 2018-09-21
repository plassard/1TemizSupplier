package com.digitaldna.supplier.network.requests;

import android.util.Log;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class StartDateEndDateRequest extends BasicRequest {

    @JsonProperty("Filter")
    private int mFilter;

    @JsonProperty("StartDate")
    private String mStartDate;

    @JsonProperty("EndDate")
    private String mEndDate;

    public StartDateEndDateRequest(String userId, String ticket, String startDate, String endDate) {
        super(userId, ticket);
        mFilter = 10;
        mStartDate = startDate;

        mEndDate = endDate;
    }



}
