package com.digitaldna.supplier.network.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by yevgen on 3/27/18.
 */


public class OrderProductBean {
    @JsonProperty("ProductName")
    private String mProductName;

    @JsonProperty("TotalPrice")
    private Double mPrice;

    @JsonProperty("Currency")
    private String mCurrency;

    @JsonProperty("ProductImageURL")
    private String mProductImageUrl;

    @JsonProperty("ProductID")
    private Long mProductId;

    @JsonProperty("Quantity")
    private Double mQuantity;

    public Integer getPriceIndex() {
        return mPriceIndex;
    }

    @JsonProperty("PriceIndex")
    private Integer mPriceIndex;

    public String getProductName() {
        return mProductName;
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

    public String getProductImageUrl() {
        return mProductImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        mProductImageUrl = productImageUrl;
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
    }
}
