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

    public Double getTotalOrderCount() {
        return mTotalOrderCount;
    }

    public Double getCompletedOrderCount() {
        return mCompletedOrderCount;
    }

    public Double getCanceledOrderCount() {
        return mCanceledOrderCount;
    }

    public Double getCanceledRate() {
        return mCanceledRate;
    }

    public Double getCompletedJobCount() {
        return mCompletedJobCount;
    }

    public Double getOnTimeJobCount() {
        return mOnTimeJobCount;
    }

    public Double getOnTimeRate() {
        return mOnTimeRate;
    }

    public Double getLateJobCount() {
        return mLateJobCount;
    }

    public Double getLateRate() {
        return mLateRate;
    }
}


