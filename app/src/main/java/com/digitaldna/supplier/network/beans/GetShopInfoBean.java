package com.digitaldna.supplier.network.beans;


import android.support.annotation.Keep;

import com.digitaldna.supplier.network.beans.base.BaseJsonDataBean;
import com.digitaldna.supplier.network.deserializer.BeanDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = GetShopInfoBean.Deserializer.class)
public class GetShopInfoBean extends BaseJsonDataBean<ShopInfoBean> {
    @Keep
    public GetShopInfoBean() {
    }

    public static final class Deserializer extends BeanDeserializer<ShopInfoBean> {
        public Deserializer() {
            super(GetShopInfoBean.class, ShopInfoBean.class);
        }
    }

}
