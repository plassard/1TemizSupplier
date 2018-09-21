package com.digitaldna.supplier.network.beans;


import android.support.annotation.Keep;

import com.digitaldna.supplier.network.beans.base.BaseJsonDataBean;
import com.digitaldna.supplier.network.deserializer.BeanDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

@JsonDeserialize(using = GetEarningsBean.Deserializer.class)
public class GetEarningsBean extends BaseJsonDataBean<List<EarningsInDayBean>> {
    @Keep
    public GetEarningsBean() {
    }

    public static final class Deserializer extends BeanDeserializer<List<EarningsInDayBean>> {
        public Deserializer() {
            super(GetEarningsBean.class, Type.generic(List.class, EarningsInDayBean.class));
        }
    }

}
