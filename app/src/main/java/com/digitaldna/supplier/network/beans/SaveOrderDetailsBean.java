package com.digitaldna.supplier.network.beans;


import android.support.annotation.Keep;

import com.digitaldna.supplier.network.beans.base.BaseJsonDataBean;
import com.digitaldna.supplier.network.deserializer.BeanDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = SaveOrderDetailsBean.Deserializer.class)
public class SaveOrderDetailsBean extends BaseJsonDataBean<OrderDetailsBean> {
    @Keep
    public SaveOrderDetailsBean() {
    }

    public static final class Deserializer extends BeanDeserializer<OrderDetailsBean> {
        public Deserializer() {
            super(SaveOrderDetailsBean.class, OrderDetailsBean.class);
        }
    }

}
