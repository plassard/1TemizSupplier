package com.digitaldna.supplier.network.beans;


import android.support.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SupplierSuccessRate {
    @Nullable
    @JsonProperty("TotalOrderCount")
    private Double mTotalOrderCount;

    @Nullable
    @JsonProperty("CompletedOrderCount")
    private Double mCompletedOrderCount;

    @Nullable
    @JsonProperty("CanceledOrderCount")
    private Double mCanceledOrderCount;

    @Nullable
    @JsonProperty("CanceledRate")
    private Double mCanceledRate;

    @Nullable
    @JsonProperty("CompletedJobCount")
    private Double mCompletedJobCount;

    @Nullable
    @JsonProperty("OnTimeJobCount")
    private Double mOnTimeJobCount;

    @Nullable
    @JsonProperty("OnTimeRate")
    private Double mOnTimeRate;

    @Nullable
    @JsonProperty("LateJobCount")
    private Double mLateJobCount;

    @Nullable
    @JsonProperty("LateRate")
    private Double mLateRate;

    public int getTotalOrderCount() {
        return Integer.valueOf(mTotalOrderCount.intValue());
    }

    public Double getCompletedOrderCount() {
        return mCompletedOrderCount;
    }

    public int getCanceledOrderCount() {
        return Integer.valueOf(mCanceledOrderCount.intValue());
    }

    public Double getCanceledRate() {
        return mCanceledRate * 100;
    }

    public int getCompletedJobCount() {
        return Integer.valueOf(mCompletedJobCount.intValue());
    }

    public int getOnTimeJobCount() {
        return Integer.valueOf(mOnTimeJobCount.intValue());
    }

    public Double getOnTimeRate() {
        return mOnTimeRate;
    }

    public int getLateJobCount() {
        return Integer.valueOf(mLateJobCount.intValue());
    }

    public Double getLateRate() {
        return mLateRate;
    }
}


