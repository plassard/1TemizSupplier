package com.digitaldna.supplier.network.beans;


import android.support.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductGroupBean {
    @Nullable
    @JsonProperty("ProductCategoryID")
    private Integer mProductCategoryID;

    @Nullable
    @JsonProperty("ProductGroupID")
    private Integer mProductGroupID;

    @Nullable
    @JsonProperty("GroupName")
    private String mGroupName;

    @Nullable
    @JsonProperty("GroupActiveImageURL")
    private String mGroupActiveImageURL;

    @Nullable
    @JsonProperty("GroupInactiveImageURL")
    private String mGroupInactiveImageURL;


    @Nullable
    @JsonProperty("ProductCount")
    private Integer mProductCount;


    @Nullable
    public Integer getmProductCategoryID() {
        return mProductCategoryID;
    }

    public void setmProductCategoryID(@Nullable Integer mProductCategoryID) {
        this.mProductCategoryID = mProductCategoryID;
    }

    @Nullable
    public Integer getmProductGroupID() {
        return mProductGroupID;
    }

    public void setmProductGroupID(@Nullable Integer mProductGroupID) {
        this.mProductGroupID = mProductGroupID;
    }

    @Nullable
    public String getmGroupName() {
        return mGroupName;
    }

    public void setmGroupName(@Nullable String mGroupName) {
        this.mGroupName = mGroupName;
    }

    @Nullable
    public String getmGroupActiveImageURL() {
        return mGroupActiveImageURL;
    }

    public void setmGroupActiveImageURL(@Nullable String mGroupActiveImageURL) {
        this.mGroupActiveImageURL = mGroupActiveImageURL;
    }

    @Nullable
    public String getmGroupInactiveImageURL() {
        return mGroupInactiveImageURL;
    }

    public void setmGroupInactiveImageURL(@Nullable String mGroupInactiveImageURL) {
        this.mGroupInactiveImageURL = mGroupInactiveImageURL;
    }

    @Nullable
    public Integer getmProductCount() {
        return mProductCount;
    }

    public void setmProductCount(@Nullable Integer mProductCount) {
        this.mProductCount = mProductCount;
    }


    int mSelectedProductsCount = 0;
    @Nullable
    public int getSelectedProductsCount() {
        return mSelectedProductsCount;
    }
    public void setSelectedProductsCount(@Nullable Integer mSelectedProductsCount) {
        this.mSelectedProductsCount = mSelectedProductsCount;
    }

    double mSelectedTotalPriceInGroup = 0;
    @Nullable
    public double getSelectedTotalPriceInGroup() {
        return mSelectedTotalPriceInGroup;
    }
    public void setSelectedTotalPriceInGroup(@Nullable Double mSelected) {
        this.mSelectedTotalPriceInGroup = mSelected;
    }
}


