package com.digitaldna.supplier.network.beans.base;

import android.support.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Base bean for responses
 */

public abstract class BaseJsonBean {
    public static final String DISPLAY_TYPE = "DisplayType";
    public static final String LEVEL = "Level";
    public static final String STATUS_CODE = "StatusCode";
    public static final String STATUS_SUB_CODE = "StatusSubCode";
    public static final String STATUS_TEXT = "StatusText";

    @Nullable
    @JsonProperty(DISPLAY_TYPE)
    private Long mDisplayType;
    @Nullable
    @JsonProperty(LEVEL)
    private Long mLevel;
    @Nullable
    @JsonProperty(STATUS_CODE)
    private Long mStatusCode;
    @Nullable
    @JsonProperty(STATUS_SUB_CODE)
    private Long mStatusSubCode;
    @Nullable
    @JsonProperty(STATUS_TEXT)
    static public String mStatusText;

    @Nullable
    public Long getDisplayType() {
        return mDisplayType;
    }

    public void setDisplayType(@Nullable Long displayType) {
        mDisplayType = displayType;
    }

    @Nullable
    public Long getLevel() {
        return mLevel;
    }

    public void setLevel(@Nullable Long level) {
        mLevel = level;
    }

    @Nullable
    public Long getStatusCode() {
        return mStatusCode;
    }

    public void setStatusCode(@Nullable Long statusCode) {
        mStatusCode = statusCode;
    }

    @Nullable
    public Long getStatusSubCode() {
        return mStatusSubCode;
    }

    public void setStatusSubCode(@Nullable Long statusSubCode) {
        mStatusSubCode = statusSubCode;
    }

    @Nullable
    public String getStatusText() {
        return mStatusText;
    }

    public void setStatusText(@Nullable String statusText) {
        mStatusText = statusText;
    }

    @Override
    public String toString() {
        return "BaseJsonBean{" +
                "mDisplayType=" + mDisplayType +
                ", mLevel=" + mLevel +
                ", mStatusCode=" + mStatusCode +
                ", mStatusSubCode=" + mStatusSubCode +
                ", mStatusText='" + mStatusText + '\'' +
                '}';
    }
}
