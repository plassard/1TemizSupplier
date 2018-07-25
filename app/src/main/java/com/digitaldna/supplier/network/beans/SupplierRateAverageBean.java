package com.digitaldna.supplier.network.beans;


import android.support.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SupplierRateAverageBean {
    @Nullable
    @JsonProperty("AverageRate")
    private Double mAverageRate;

    @Nullable
    @JsonProperty("R1")
    private Double mR1;

    @Nullable
    @JsonProperty("R2")
    private Double mR2;

    @Nullable
    @JsonProperty("R3")
    private Double mR3;

    @Nullable
    @JsonProperty("R4")
    private Double mR4;

    @Nullable
    @JsonProperty("R5")
    private Double mR5;


    public Double getAverageRate() {
        if(mAverageRate == null){
            mAverageRate = 0.0;
        }
        return mAverageRate;
    }

    public Double getR1() {
        return mR1;
    }

    public Double getR2() {
        return mR2;
    }

    public Double getR3() {
        return mR3;
    }

    public Double getR4() {
        return mR4;
    }

    public Double getR5() {
        return mR5;
    }
}


