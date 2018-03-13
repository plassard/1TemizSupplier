package com.digitaldna.supplier.network.beans;


import android.support.annotation.Keep;

import android.support.annotation.Keep;

import com.digitaldna.supplier.network.beans.LoginSupplierBean;
import com.digitaldna.supplier.network.deserializer.BeanDeserializer;
import com.digitaldna.supplier.network.beans.base.BaseJsonDataBean;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = GetLoginBean.Deserializer.class)
public class GetLoginBean extends BaseJsonDataBean<LoginSupplierBean> {
    @Keep
    public GetLoginBean() {
    }

    public static final class Deserializer extends BeanDeserializer<LoginSupplierBean> {
        public Deserializer() {
            super(GetLoginBean.class, LoginSupplierBean.class);
        }
    }

}
