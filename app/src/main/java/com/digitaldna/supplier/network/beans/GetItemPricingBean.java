package com.digitaldna.supplier.network.beans;


import android.support.annotation.Keep;

import com.digitaldna.supplier.network.beans.base.BaseJsonDataBean;
import com.digitaldna.supplier.network.deserializer.BeanDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = GetItemPricingBean.Deserializer.class)
public class GetItemPricingBean extends BaseJsonDataBean<ItemPricingBean> {
    @Keep
    public GetItemPricingBean() {
    }

    public static final class Deserializer extends BeanDeserializer<ItemPricingBean> {
        public Deserializer() {
            super(GetItemPricingBean.class, ItemPricingBean.class);
        }
    }

}
