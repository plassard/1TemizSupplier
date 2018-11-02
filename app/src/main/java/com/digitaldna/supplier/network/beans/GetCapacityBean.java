package com.digitaldna.supplier.network.beans;


import android.support.annotation.Keep;

import com.digitaldna.supplier.network.beans.base.BaseJsonDataBean;
import com.digitaldna.supplier.network.deserializer.BeanDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

@JsonDeserialize(using = GetCapacityBean.Deserializer.class)
public class GetCapacityBean extends BaseJsonDataBean<List<CapacityDayBean>> {
    @Keep
    public GetCapacityBean() {
    }

    public static final class Deserializer extends BeanDeserializer<List<CapacityDayBean>> {
        public Deserializer() {
            super(GetCapacityBean.class, Type.generic(List.class, CapacityDayBean.class));
        }
    }

}
