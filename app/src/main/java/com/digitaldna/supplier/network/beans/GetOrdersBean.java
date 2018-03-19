package com.digitaldna.supplier.network.beans;


import android.support.annotation.Keep;

import com.digitaldna.supplier.network.beans.base.BaseJsonDataBean;
import com.digitaldna.supplier.network.deserializer.BeanDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

@JsonDeserialize(using = GetOrdersBean.Deserializer.class)
public class GetOrdersBean extends BaseJsonDataBean<List<OrdersBean>> {
    @Keep
    public GetOrdersBean() {
    }

    public static final class Deserializer extends BeanDeserializer<List<OrdersBean>> {
        public Deserializer() {
            super(GetOrdersBean.class, Type.generic(List.class, OrdersBean.class));
        }
    }

}
