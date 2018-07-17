package com.digitaldna.supplier.network.beans;


import android.support.annotation.Keep;

import com.digitaldna.supplier.network.beans.base.BaseJsonDataBean;
import com.digitaldna.supplier.network.deserializer.BeanDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

@JsonDeserialize(using = GetCancelReasonsListBean.Deserializer.class)
public class GetCancelReasonsListBean extends BaseJsonDataBean<List<CancelReasonsBean>> {
    @Keep
    public GetCancelReasonsListBean() {
    }

    public static final class Deserializer extends BeanDeserializer<List<CancelReasonsBean>> {
        public Deserializer() {
            super(GetCancelReasonsListBean.class, Type.generic(List.class, CancelReasonsBean.class));
        }
    }

}
