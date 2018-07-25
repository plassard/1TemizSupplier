package com.digitaldna.supplier.network.beans;


import android.support.annotation.Keep;

import com.digitaldna.supplier.network.beans.base.BaseJsonDataBean;
import com.digitaldna.supplier.network.deserializer.BeanDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

@JsonDeserialize(using = GetCommentsBean.Deserializer.class)
public class GetCommentsBean extends BaseJsonDataBean<List<CommentsBean>> {
    @Keep
    public GetCommentsBean() {
    }

    public static final class Deserializer extends BeanDeserializer<List<CommentsBean>> {
        public Deserializer() {
            super(GetCommentsBean.class, Type.generic(List.class, CommentsBean.class));
        }
    }

}
