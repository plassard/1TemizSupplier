package com.digitaldna.supplier.network.beans;


import android.support.annotation.Keep;

import com.digitaldna.supplier.network.beans.base.BaseJsonDataBean;
import com.digitaldna.supplier.network.deserializer.BeanDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

@JsonDeserialize(using = GetProductPriceListBean.Deserializer.class)
public class GetProductPriceListBean extends BaseJsonDataBean<List<ProductBean>> {
    @Keep
    public GetProductPriceListBean() {
    }

    public static final class Deserializer extends BeanDeserializer<List<ProductBean>> {
        public Deserializer() {
            super(GetProductPriceListBean.class, Type.generic(List.class, ProductBean.class));
        }
    }

}
