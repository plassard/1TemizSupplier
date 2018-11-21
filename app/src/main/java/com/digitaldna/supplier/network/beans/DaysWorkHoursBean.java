package com.digitaldna.supplier.network.beans;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DaysWorkHoursBean {
    @JsonProperty("PeriodDay")
    private Long mPeriodDay;

    @JsonProperty("PeriodDayDesc")
    private String mPeriodDayDesc;

    @JsonProperty("TimePeriods")
    private List<TimePeriodsWorkingHoursBean> mTimePeriods;

    public String getmPeriodDayDesc() {
        return mPeriodDayDesc;
    }

    public void setmPeriodDayDesc(String mPeriodDayDesc) {
        this.mPeriodDayDesc = mPeriodDayDesc;
    }

    public List<TimePeriodsWorkingHoursBean> getmTimePeriods() {
        return mTimePeriods;
    }

    public void setmTimePeriods(List<TimePeriodsWorkingHoursBean> mTimePeriods) {
        this.mTimePeriods = mTimePeriods;
    }
}
