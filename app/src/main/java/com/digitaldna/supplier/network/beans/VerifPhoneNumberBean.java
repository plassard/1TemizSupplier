package com.digitaldna.supplier.network.beans;


import android.support.annotation.Keep;

import com.digitaldna.supplier.network.beans.base.BaseJsonDataBean;
import com.digitaldna.supplier.network.deserializer.BeanDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = VerifPhoneNumberBean.Deserializer.class)
public class VerifPhoneNumberBean extends BaseJsonDataBean<VerificationCodeBean> {
    @Keep
    public VerifPhoneNumberBean() {
    }

    public static final class Deserializer extends BeanDeserializer<VerificationCodeBean> {
        public Deserializer() {
            super(VerifPhoneNumberBean.class, VerificationCodeBean.class);
        }
    }

}
