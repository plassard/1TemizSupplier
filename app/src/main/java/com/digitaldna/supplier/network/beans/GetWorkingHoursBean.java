package com.digitaldna.supplier.network.beans;


import android.support.annotation.Keep;

import com.digitaldna.supplier.network.beans.base.BaseJsonDataBean;
import com.digitaldna.supplier.network.deserializer.BeanDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

@JsonDeserialize(using = GetWorkingHoursBean.Deserializer.class)
public class GetWorkingHoursBean extends BaseJsonDataBean<List<WorkingHoursBean>> {
    @Keep
    public GetWorkingHoursBean() {
    }

    public static final class Deserializer extends BeanDeserializer<List<WorkingHoursBean>> {
        public Deserializer() {
            super(GetWorkingHoursBean.class, Type.generic(List.class, WorkingHoursBean.class));
        }
    }

}
