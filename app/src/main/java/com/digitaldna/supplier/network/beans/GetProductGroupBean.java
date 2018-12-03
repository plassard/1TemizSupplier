package com.digitaldna.supplier.network.beans;


import android.support.annotation.Keep;

import com.digitaldna.supplier.network.beans.base.BaseJsonDataBean;
import com.digitaldna.supplier.network.deserializer.BeanDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

@JsonDeserialize(using = GetProductGroupBean.Deserializer.class)
public class GetProductGroupBean extends BaseJsonDataBean<List<ProductGroupBean>> {
    @Keep
    public GetProductGroupBean() {
    }

    public static final class Deserializer extends BeanDeserializer<List<ProductGroupBean>> {
        public Deserializer() {
            super(GetProductGroupBean.class, Type.generic(List.class, ProductGroupBean.class));
        }
    }

}
