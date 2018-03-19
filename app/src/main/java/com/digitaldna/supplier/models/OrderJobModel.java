package com.digitaldna.supplier.models;



import android.os.Parcel;
import android.os.Parcelable;
public class OrderJobModel {

    public static final int STATUS_PICK_UP = 10;
    public static final int STATUS_DROP_OFF = 20;
    public static final Parcelable.Creator<OrderJobModel> CREATOR = new Parcelable.Creator<OrderJobModel>() {

        @Override
        public OrderJobModel createFromParcel(Parcel source) {
            return new OrderJobModel(source);
        }

        @Override
        public OrderJobModel[] newArray(int size) {
            return new OrderJobModel[size];
        }
    };
    private Long mOrderJobId;
    private Integer mOrderJobStatusId;
    private String mOrderJobStatusText;
    private Integer mJobTypeId;
    private String mJobType;
    private String mJobDateDescription;
    private String mOrderNumber;
    private String mJobDate;
    private String mTimePeriod;
    private String mCustomer;
    private String mDistrict;
    private Boolean mIsViewed;


    public OrderJobModel() {
    }

    protected OrderJobModel(Parcel in) {
        this.mOrderJobId = (Long) in.readValue(Long.class.getClassLoader());
        this.mOrderJobStatusId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.mOrderJobStatusText = in.readString();
        this.mJobTypeId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.mJobType = in.readString();
        this.mJobDateDescription = in.readString();
        this.mOrderNumber = in.readString();
        this.mJobDate = in.readString();
        this.mTimePeriod = in.readString();
        this.mCustomer = in.readString();
        this.mDistrict = in.readString();
        this.mIsViewed = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }


    public int describeContents() {
        return 0;
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.mOrderJobId);
        dest.writeValue(this.mOrderJobStatusId);
        dest.writeString(this.mOrderJobStatusText);
        dest.writeValue(this.mJobTypeId);
        dest.writeString(this.mJobType);
        dest.writeString(this.mJobDateDescription);
        dest.writeString(this.mOrderNumber);
        dest.writeString(this.mJobDate);
        dest.writeString(this.mTimePeriod);
        dest.writeString(this.mCustomer);
        dest.writeString(this.mDistrict);
        dest.writeValue(this.mIsViewed);
    }


    public Long getId() {
        return mOrderJobId;
    }


    public void setId(Long id) {
        mOrderJobId = id;
    }

    public Long getOrderJobId() {
        return mOrderJobId;
    }

    public void setOrderJobId(Long orderJobId) {
        mOrderJobId = orderJobId;
    }

    public Integer getOrderJobStatusId() {
        return mOrderJobStatusId;
    }

    public void setOrderJobStatusId(Integer orderJobStatusId) {
        mOrderJobStatusId = orderJobStatusId;
    }

    public String getOrderJobStatusText() {
        return mOrderJobStatusText;
    }

    public void setOrderJobStatusText(String orderJobStatusText) {
        mOrderJobStatusText = orderJobStatusText;
    }

    public Integer getJobTypeId() {
        return mJobTypeId;
    }

    public void setJobTypeId(Integer jobTypeId) {
        mJobTypeId = jobTypeId;
    }

    public String getJobType() {
        return mJobType;
    }

    public void setJobType(String jobType) {
        mJobType = jobType;
    }

    public String getJobDateDescription() {
        return mJobDateDescription;
    }

    public void setJobDateDescription(String jobDateDescription) {
        mJobDateDescription = jobDateDescription;
    }

    public String getOrderNumber() {
        return mOrderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        mOrderNumber = orderNumber;
    }

    public String getJobDate() {
        return mJobDate;
    }

    public void setJobDate(String jobDate) {
        mJobDate = jobDate;
    }

    public String getTimePeriod() {
        return mTimePeriod;
    }

    public void setTimePeriod(String timePeriod) {
        mTimePeriod = timePeriod;
    }

    public String getCustomer() {
        return mCustomer;
    }

    public void setCustomer(String customer) {
        mCustomer = customer;
    }

    public String getDistrict() {
        return mDistrict;
    }

    public void setDistrict(String district) {
        mDistrict = district;
    }

    public Boolean getViewed() {
        return mIsViewed;
    }

    public void setViewed(Boolean viewed) {
        mIsViewed = viewed;
    }

    /*public String getOrderStatusText(Context context) {
        return context.getString(mJobTypeId == STATUS_PICK_UP ? R.string.order_status_pick_up : R.string.order_status_drop_off);
    }*/
}
