package com.digitaldna.supplier.network.beans;


import android.support.annotation.Keep;

import com.digitaldna.supplier.network.beans.base.BaseJsonDataBean;
import com.digitaldna.supplier.network.deserializer.BeanDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = GetVerifCodeBean.Deserializer.class)
public class GetVerifCodeBean extends BaseJsonDataBean<VerificationCodeBean> {
    @Keep
    public GetVerifCodeBean() {
    }

    public static final class Deserializer extends BeanDeserializer<VerificationCodeBean> {
        public Deserializer() {
            super(GetVerifCodeBean.class, VerificationCodeBean.class);
        }
    }

}
