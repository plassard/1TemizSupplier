package com.digitaldna.supplier.network.requests;



import com.digitaldna.supplier.network.beans.ProductBean;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SetOrderProductListRequest extends BasicRequest {

    @JsonProperty("OrderID")
    private Long mOrderJobId;

    @JsonProperty("Products")
    private List<ProductBeanForRequest> mOrderProductBeen;

    public SetOrderProductListRequest(String ticket, String userId, Long orderJobId, List<ProductBeanForRequest> orderProductBeen) {
        super(ticket, userId);
        mOrderJobId = orderJobId;
        mOrderProductBeen = orderProductBeen;
    }

    public Long getOrderJobId() {
        return mOrderJobId;
    }

    public void setOrderJobId(Long orderJobId) {
        mOrderJobId = orderJobId;
    }

    public List<ProductBeanForRequest> getOrderProductBeen() {
        return mOrderProductBeen;
    }

    public void setOrderProductBeen(List<ProductBeanForRequest> orderProductBeen) {
        mOrderProductBeen = orderProductBeen;
    }
}
