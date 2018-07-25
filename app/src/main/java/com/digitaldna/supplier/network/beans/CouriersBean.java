package com.digitaldna.supplier.network.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by yevgen on 3/27/18.
 */


public class CouriersBean {
    @JsonProperty("CourierName")
    private String mCourierName;

    @JsonProperty("CourierID")
    private Integer mCourierID;

    @JsonProperty("isPhoneNumberVerified")
    private boolean misPhoneNumberVerified;

    public String getCourierName() {
        return mCourierName;
    }

    public Integer getCourierID() {
        return mCourierID;
    }

    public boolean getPhoneVerified() {
        return misPhoneNumberVerified;
    }

    /*@JsonProperty("UnitID")
    private Long mUnitId;
    @JsonProperty("Unit")
    private String mUnit;
    @JsonProperty("Price")
    private Double mPrice;
    @JsonProperty("Currency")
    private String mCurrency;
    @JsonProperty("GroupImageURL")
    private String mGroupImageUrl;
    @JsonProperty("ProductImageURL")
    private String mProductImageUrl;
    @JsonProperty("ProductGroupID")
    private Long mProductGroupId;
    @JsonProperty("ProductID")
    private Long mProductId;
    @JsonProperty("Quantity")
    private Double mQuantity;


    public void setProductName(String productName) {
        mProductName = productName;
    }

    public Long getUnitId() {
        return mUnitId;
    }

    public void setUnitId(Long unitId) {
        mUnitId = unitId;
    }

    public String getUnit() {
        return mUnit;
    }

    public void setUnit(String unit) {
        mUnit = unit;
    }

    public Double getPrice() {
        return mPrice;
    }

    public void setPrice(Double price) {
        mPrice = price;
    }

    public String getCurrency() {
        return mCurrency;
    }

    public void setCurrency(String currency) {
        mCurrency = currency;
    }

    public String getGroupImageUrl() {
        return mGroupImageUrl;
    }

    public void setGroupImageUrl(String groupImageUrl) {
        mGroupImageUrl = groupImageUrl;
    }

    public String getProductImageUrl() {
        return mProductImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        mProductImageUrl = productImageUrl;
    }

    public Long getProductGroupId() {
        return mProductGroupId;
    }

    public void setProductGroupId(Long productGroupId) {
        mProductGroupId = productGroupId;
    }

    public Long getProductId() {
        return mProductId;
    }

    public void setProductId(Long productId) {
        mProductId = productId;
    }

    public Double getQuantity() {
        return mQuantity;
    }

    public void setQuantity(Double quantity) {
        mQuantity = quantity;
    }*/
}
