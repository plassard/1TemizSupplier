package com.digitaldna.supplier.network.beans;


import android.support.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class WorkingHoursBean {

    @Nullable
    @JsonProperty("GroupID")
    private int mGroupID;

    @Nullable
    @JsonProperty("GroupName")
    private String mGroupName;

    @JsonProperty("Days")
    private List<DaysWorkHoursBean> mDays;

    @Nullable
    public int getmGroupID() {
        return mGroupID;
    }

    public void setmGroupID(@Nullable int mGroupID) {
        this.mGroupID = mGroupID;
    }

    @Nullable
    public String getmGroupName() {
        return mGroupName;
    }

    public void setmGroupName(@Nullable String mGroupName) {
        this.mGroupName = mGroupName;
    }

    public List<DaysWorkHoursBean> getmDays() {
        return mDays;
    }

    public void setmDays(List<DaysWorkHoursBean> mDays) {
        this.mDays = mDays;
    }
}


