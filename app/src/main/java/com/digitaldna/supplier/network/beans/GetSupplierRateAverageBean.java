package com.digitaldna.supplier.network.beans;


import android.support.annotation.Keep;

import com.digitaldna.supplier.network.beans.base.BaseJsonDataBean;
import com.digitaldna.supplier.network.deserializer.BeanDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = GetSupplierRateAverageBean.Deserializer.class)
public class GetSupplierRateAverageBean extends BaseJsonDataBean<SupplierRateAverageBean> {
    @Keep
    public GetSupplierRateAverageBean() {
    }

    public static final class Deserializer extends BeanDeserializer<SupplierRateAverageBean> {
        public Deserializer() {
            super(GetSupplierRateAverageBean.class, SupplierRateAverageBean.class);
        }
    }

}
