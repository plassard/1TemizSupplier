package com.digitaldna.supplier.network.beans;


import android.support.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SupplierSummaryBean {
    @Nullable
    @JsonProperty("OnTimeJobCount")
    private Integer mOnTimeJobCount;

    @Nullable
    @JsonProperty("LateJobCount")
    private Integer mLateJobCount;

    @Nullable
    @JsonProperty("Earnings")
    private Double mEarnings;

    @Nullable
    @JsonProperty("RatingCount")
    private Integer mRatingCount;

    @Nullable
    @JsonProperty("AverageRating")
    private Double mAverageRating;

    @Nullable
    @JsonProperty("CommentCount")
    private Integer mCommentCount;


    public Integer getOnTimeJobCount() {
        return mOnTimeJobCount;
    }

    public Integer getLateJobCount() {
        return mLateJobCount;
    }

    public Double getEarnings() {
        return mEarnings;
    }


    public Integer getRatingCount() {
        return mRatingCount;
    }


    public Double getAverageRating() {
        return mAverageRating;
    }


    public Integer getCommentCount() {
        return mCommentCount;
    }





}


