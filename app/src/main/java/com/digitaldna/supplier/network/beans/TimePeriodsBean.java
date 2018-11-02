package com.digitaldna.supplier.network.beans;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TimePeriodsBean {
    @JsonProperty("StartTime")
    private String mStartTime;

    @JsonProperty("EndTime")
    private String mEndTime;

    @JsonProperty("MemberFull")
    private boolean mMemberFull;

    @JsonProperty("TimePeriodID")
    private Integer mTimePeriodID;



    public String getEndTime() {
        return mEndTime.substring(0, 5);
    }
    public String getStartTime() {
        return mStartTime.substring(0, 5);
    }

    public void setStartTime(String startTime) {
        mStartTime = startTime;
    }

    public boolean getMemberFull() {
        return mMemberFull;
    }

    public Integer getTimePeriodID() {
        return mTimePeriodID;
    }
}
