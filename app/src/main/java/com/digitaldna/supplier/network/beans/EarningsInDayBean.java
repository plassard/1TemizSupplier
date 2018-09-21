package com.digitaldna.supplier.network.beans;


import android.support.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EarningsInDayBean {
    @Nullable
    @JsonProperty("Date")
    private String mDate;

    @Nullable
    @JsonProperty("TotalEarning")
    private Double mTotalEarning;

    @Nullable
    @JsonProperty("InCurrency")
    private String mCurrency;


    public Double getEarnings() {
        if(!mTotalEarning.equals(null))
            return mTotalEarning;
        else
            return 0D;
    }

    public String getDate() {
        return mDate;
    }

   /* public int getDay2() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); // your format
        int day = Integer.valueOf(mDate.substring(9, 11));

        Date dateToday = new Date();
        Calendar calendarWithTodayDate = Calendar.getInstance();
        calendarWithTodayDate.setTime(dateToday);
        int thisMonthNumber = calendarWithTodayDate.get(Calendar.MONTH);
        int thisWeekOfYearNumber = calendarWithTodayDate.get(Calendar.WEEK_OF_YEAR);
    }*/
    public int getMonth() {
        if(!mDate.equals("TOTAL"))
            return Integer.valueOf(mDate.substring(5, 7));
        else
            return 0;
    }

    public int getYear() {
        if(!mDate.equals("TOTAL"))
            return Integer.valueOf(mDate.substring(0, 4));
        else
            return 0;
    }

    public int getDay() {
        if(!mDate.equals("TOTAL"))
            return Integer.valueOf(mDate.substring(8, 10));
        else
            return 0;
    }
}


