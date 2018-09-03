package com.digitaldna.supplier.network.requests;

import android.util.Log;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BaseWithFilterRequest extends BasicRequest {

    @JsonProperty("Filter")
    private int mFilter;

    @JsonProperty("StartDate")
    private String mStartDate;

    @JsonProperty("EndDate")
    private String mEndDate;

    public BaseWithFilterRequest(String userId, String ticket) {
        super(userId, ticket);
        mFilter = 10;
        mStartDate = "2017-01-01";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(Calendar.getInstance().getTime());
        Log.i("date", date);
        mEndDate = date;
    }



}
