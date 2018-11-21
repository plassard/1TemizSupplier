package com.digitaldna.supplier.network.beans;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TimePeriodsWorkingHoursBean {
    @JsonProperty("StartTime")
    private String mStartTime;

    @JsonProperty("EndTime")
    private String mEndTime;

    @JsonProperty("MemberUsing")
    private boolean mMemberUsing;

    @JsonProperty("TimePeriodID")
    private Integer mTimePeriodID;

    @JsonProperty("PeriodDay")
    private Integer mPeriodDay;

    public String getmStartTime() {
        return mStartTime;
    }

    public void setmStartTime(String mStartTime) {
        this.mStartTime = mStartTime;
    }

    public String getmEndTime() {
        return mEndTime;
    }

    public void setmEndTime(String mEndTime) {
        this.mEndTime = mEndTime;
    }

    public boolean ismMemberUsing() {
        return mMemberUsing;
    }

    public void setMemberUsing(boolean mMemberUsing) {
        this.mMemberUsing = mMemberUsing;
    }

    public Integer getmTimePeriodID() {
        return mTimePeriodID;
    }

    public void setmTimePeriodID(Integer mTimePeriodID) {
        this.mTimePeriodID = mTimePeriodID;
    }

    public Integer getmPeriodDay() {
        return mPeriodDay;
    }

    public void setmPeriodDay(Integer mPeriodDay) {
        this.mPeriodDay = mPeriodDay;
    }
}
