package com.digitaldna.supplier.network.beans;


import android.support.annotation.Keep;

import com.digitaldna.supplier.network.beans.base.BaseJsonDataBean;
import com.digitaldna.supplier.network.deserializer.BeanDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = GetOrderDetailsBean.Deserializer.class)
public class GetOrderDetailsBean extends BaseJsonDataBean<OrderDetailsBean> {
    @Keep
    public GetOrderDetailsBean() {
    }

    public static final class Deserializer extends BeanDeserializer<OrderDetailsBean> {
        public Deserializer() {
            super(GetOrderDetailsBean.class, OrderDetailsBean.class);
        }
    }

}
