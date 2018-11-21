package com.digitaldna.supplier.network.beans;


import android.support.annotation.Keep;

import com.digitaldna.supplier.network.beans.base.BaseJsonDataBean;
import com.digitaldna.supplier.network.deserializer.BeanDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

@JsonDeserialize(using = GetHolidaysBean.Deserializer.class)
public class GetHolidaysBean extends BaseJsonDataBean<List<HolidayBean>> {
    @Keep
    public GetHolidaysBean() {
    }

    public static final class Deserializer extends BeanDeserializer<List<HolidayBean>> {
        public Deserializer() {
            super(GetHolidaysBean.class, Type.generic(List.class, HolidayBean.class));
        }
    }

}
