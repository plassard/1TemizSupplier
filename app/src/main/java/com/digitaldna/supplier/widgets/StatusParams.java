package com.digitaldna.supplier.widgets;


public class StatusParams {
    private String mStatus;
    private int mStatusBackgroundResId;
    private int mNotificationStatusResId;
    //    Sets the text color, size, style, hint color, and highlight color
    private int mStatusStyleResId;

    public StatusParams(String status, int statusBackgroundResId, int notificationStatusResId, int statusStyleResId) {
        mStatus = status;
        mStatusBackgroundResId = statusBackgroundResId;
        mNotificationStatusResId = notificationStatusResId;
        mStatusStyleResId = statusStyleResId;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public int getStatusBackgroundResId() {
        return mStatusBackgroundResId;
    }

    public void setStatusBackgroundResId(int statusBackgroundResId) {
        mStatusBackgroundResId = statusBackgroundResId;
    }

    public int getNotificationStatusResId() {
        return mNotificationStatusResId;
    }

    public void setNotificationStatusResId(int notificationStatusResId) {
        mNotificationStatusResId = notificationStatusResId;
    }

    public int getStatusStyleResId() {
        return mStatusStyleResId;
    }

    public void setStatusStyleResId(int statusStyleResId) {
        mStatusStyleResId = statusStyleResId;
    }
}
