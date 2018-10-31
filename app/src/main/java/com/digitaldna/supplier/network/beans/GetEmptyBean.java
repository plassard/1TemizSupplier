package com.digitaldna.supplier.network.beans;


import android.support.annotation.Keep;

import com.digitaldna.supplier.network.beans.base.BaseJsonDataBean;
import com.digitaldna.supplier.network.deserializer.BeanDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = GetEmptyBean.Deserializer.class)
public class GetEmptyBean extends BaseJsonDataBean<EmptyBean> {
    @Keep
    public GetEmptyBean() {
    }

    public static final class Deserializer extends BeanDeserializer<EmptyBean> {
        public Deserializer() {
            super(GetEmptyBean.class, EmptyBean.class);
        }
    }

}
