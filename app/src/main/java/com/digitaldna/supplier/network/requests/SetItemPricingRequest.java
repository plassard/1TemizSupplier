package com.digitaldna.supplier.network.requests;



import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SetItemPricingRequest extends BasicRequest {


    @JsonProperty("Products")
    private List<ProductBeanForSetItemPricingRequest> mOrderProductBeen;

    public SetItemPricingRequest(String ticket, String userId, List<ProductBeanForSetItemPricingRequest> orderProductBeen) {
        super(ticket, userId);
        mOrderProductBeen = orderProductBeen;
    }


    public List<ProductBeanForSetItemPricingRequest> getOrderProductBeen() {
        return mOrderProductBeen;
    }

    public void setOrderProductBeen(List<ProductBeanForSetItemPricingRequest> orderProductBeen) {
        mOrderProductBeen = orderProductBeen;
    }
}
