package com.digitaldna.supplier.network.beans;


import android.support.annotation.Keep;

import com.digitaldna.supplier.network.beans.base.BaseJsonDataBean;
import com.digitaldna.supplier.network.deserializer.BeanDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = GetSupplierSuccessRate.Deserializer.class)
public class GetSupplierSuccessRate extends BaseJsonDataBean<SupplierSuccessRate> {
    @Keep
    public GetSupplierSuccessRate() {
    }

    public static final class Deserializer extends BeanDeserializer<SupplierSuccessRate> {
        public Deserializer() {
            super(GetSupplierSuccessRate.class, SupplierSuccessRate.class);
        }
    }

}
