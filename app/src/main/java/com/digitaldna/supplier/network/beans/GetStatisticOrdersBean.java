package com.digitaldna.supplier.network.beans;


import android.support.annotation.Keep;

import com.digitaldna.supplier.network.beans.base.BaseJsonDataBean;
import com.digitaldna.supplier.network.deserializer.BeanDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

@JsonDeserialize(using = GetStatisticOrdersBean.Deserializer.class)
public class GetStatisticOrdersBean extends BaseJsonDataBean<List<OrdersInDayBean>> {
    @Keep
    public GetStatisticOrdersBean() {
    }

    public static final class Deserializer extends BeanDeserializer<List<OrdersInDayBean>> {
        public Deserializer() {
            super(GetStatisticOrdersBean.class, Type.generic(List.class, OrdersInDayBean.class));
        }
    }

}
