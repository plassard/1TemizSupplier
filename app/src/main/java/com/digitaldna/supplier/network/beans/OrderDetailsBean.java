package com.digitaldna.supplier.network.beans;


import android.support.annotation.Nullable;
import android.util.Log;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class OrderDetailsBean {
    @Nullable
    @JsonProperty("OrderNumber")
    private String mOrderNumber;

    @Nullable
    @JsonProperty("OrderStatusID")
    private Integer mOrderStatusID;

    @Nullable
    @JsonProperty("OrderStatusText")
    private String mOrderStatusText;

    @Nullable
    @JsonProperty("PickUpDateDescription")
    private String mPickUpDateDescription;

    @Nullable
    @JsonProperty("DropOffDateDescription")
    private String mDropOffDateDescription;

    @Nullable
    @JsonProperty("TotalPrice")
    private Double mTotalPrice;


    @Nullable
    @JsonProperty("Couriers")
    private List<CouriersBean> mCouriers;

    @Nullable
    @JsonProperty("SelectedCourier")
    private Integer mSelectedCourier;

    @Nullable
    @JsonProperty("isPickUpLate")
    private Boolean misPickUpLate;

    @Nullable
    @JsonProperty("isDropOffLate")
    private Boolean misDropOffLate;


    @Nullable
    @JsonProperty("CustomerFirstName")
    private String mCustomerFirstName;

    @Nullable
    @JsonProperty("CustomerLastName")
    private String mCustomerLastName;

    @Nullable
    @JsonProperty("Location")
    private String mLocation;

    @Nullable
    @JsonProperty("AddressNote")
    private String mAddressNote;

    @Nullable
    @JsonProperty("PaymentTypeDesc")
    private String mPaymentType;

    @Nullable
    @JsonProperty("Discount")
    private Double mDiscount;


    @Nullable
    @JsonProperty("OrderDetails")
    private List<OrderProductBean> mProducts;

    @Nullable
    @JsonProperty("OrderNote")
    private String mOrderNote;

    @Nullable
    @JsonProperty("CustomerProfilePictureURL")
    private String mCustomerProfilePictureURL;

    public String getProfilePictureURL() {
        return mCustomerProfilePictureURL;
    }

    public String getOrderNote() {
        if(mOrderNote == null){
            mOrderNote = "No order note";
        }
        return mOrderNote;
    }

    public String getPaymentType() {
        if(mPaymentType == null){
            mPaymentType = "Pending";
        }
        return mPaymentType;
    }

    public String getCustomerName() {
        return mCustomerFirstName + " " + mCustomerLastName;
    }

    public String getAddress() {
        return mLocation;
    }

    public String getmAddressNote() {
        return mAddressNote;
    }


    public String getDiscount() {
        if(String.valueOf(mDiscount).equals("0.0")){
            return "0";
        }else {
            return String.valueOf(mDiscount) + "0";}
    }

    public String getmPickUpStatusText() {
        String text;
        if(mOrderStatusID < 400){
            text = "Pick up is not realized";
        } else {
            if(misPickUpLate){
                text = "Late";
            } else {
                text = "On time";
            }
        }
        return text;
    }

    public String getmDropOffStatusText() {
        String text;
        if(mOrderStatusID < 600){
            text = "drop off is not realized";
        } else {
            if(misPickUpLate){
                text = "late";
            } else {
                text = "on time";
            }
        }
        return text;
    }

    public String getmOrderStatusText() {
        return mOrderStatusText;
    }

    public String getPickUpDateDescription() {
        return mPickUpDateDescription;
    }

    public String getDropOffDateDescription() {
        return mDropOffDateDescription;
    }


    public String getmSelectedCourierName() {
        String selectedCourier = "";
        for(int i = 0; i < mCouriers.size(); i++){
            if(mCouriers.get(i).getCourierID().equals(mSelectedCourier)){
                selectedCourier = mCouriers.get(i).getCourierName();
            }
        }
        return selectedCourier;
    }

    public List<CouriersBean> getmCouriersList() {
        return mCouriers;
    }

    public List<OrderProductBean> getProductsList() {
        return mProducts;
    }

    public Double getTotalPrice() {
        if(mTotalPrice == null){
            mTotalPrice = 0.0;
        }
        return mTotalPrice;
    }

    public List<OrderProductBean> getProducts() {
        return mProducts;
    }

    public String getProductsString() {
        return mProducts.toString();
    }

    public void setProducts(List<OrderProductBean> products) {
        mProducts = products;
    }

    @Nullable
    public String getOrderNumber() {
        return mOrderNumber;
    }

}


