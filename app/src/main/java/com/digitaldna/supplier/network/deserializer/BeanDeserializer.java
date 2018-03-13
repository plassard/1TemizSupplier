package com.digitaldna.supplier.network.deserializer;


import com.digitaldna.supplier.network.RestClient;
import com.digitaldna.supplier.network.beans.base.BaseJsonBean;
import com.digitaldna.supplier.network.beans.base.BaseJsonDataBean;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import rx.Observable;

public class BeanDeserializer<T> extends JsonDeserializer<BaseJsonDataBean<T>> {

    private final Class<? extends BaseJsonDataBean<T>> mClass;
    private final JavaType mTypeReference;

    public BeanDeserializer(final Class<? extends BaseJsonDataBean<T>> clazz,
                            final Type type) {
        mClass = clazz;
        mTypeReference = type.toJavaType();
    }

    public BeanDeserializer(final Class<? extends BaseJsonDataBean<T>> clazz,
                            final TypeReference<T> type) {
        mClass = clazz;
        mTypeReference = RestClient.getMapper().getTypeFactory().constructType(type);
    }

    public BeanDeserializer(final Class<? extends BaseJsonDataBean<T>> clazz,
                            final Class<T> type) {
        mClass = clazz;
        mTypeReference = RestClient.getMapper().getTypeFactory().constructType(type);
    }


    @Override
    public BaseJsonDataBean<T> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        try {
            final Constructor<? extends BaseJsonDataBean<T>> constructor = mClass.getConstructor();
            final BaseJsonDataBean<T> bean = constructor.newInstance();
            onBeanCreated(bean, p);
            return bean;
        } catch (NoSuchMethodException e) {
            throw new IOException(e);
        } catch (IllegalAccessException e) {
            throw new IOException(e);
        } catch (InvocationTargetException e) {
            throw new IOException(e);
        } catch (InstantiationException e) {
            throw new IOException(e);
        }
    }

    private void onBeanCreated(final BaseJsonDataBean<T> bean, final JsonParser p) throws IOException {
        final ObjectNode node = RestClient.getMapper().readValue(p, ObjectNode.class);

        if (node.has(BaseJsonDataBean.DATA)) {
            final String data = node.get(BaseJsonDataBean.DATA).asText();
            final T dataValue = RestClient.getMapper().readValue(data, mTypeReference);
            bean.setData(dataValue);
        }

        if (node.has(BaseJsonBean.DISPLAY_TYPE)) {
            bean.setDisplayType(node.get(BaseJsonBean.DISPLAY_TYPE).asLong());
        }

        if (node.has(BaseJsonBean.LEVEL)) {
            bean.setLevel(node.get(BaseJsonBean.LEVEL).asLong());
        }

        if (node.has(BaseJsonBean.STATUS_CODE)) {
            bean.setStatusCode(node.get(BaseJsonBean.STATUS_CODE).asLong());
        }

        if (node.has(BaseJsonBean.STATUS_SUB_CODE)) {
            bean.setStatusSubCode(node.get(BaseJsonBean.STATUS_SUB_CODE).asLong());
        }

        if (node.has(BaseJsonBean.STATUS_TEXT)) {
            bean.setStatusText(node.get(BaseJsonBean.STATUS_TEXT).asText());
        }
    }

    public static class Type {
        private final Class<?> mPrimaryType;
        private final Type[] mGenerics;

        private Type(Class<?> primaryType, Type... generics) {
            mPrimaryType = primaryType;
            mGenerics = generics;
        }

        public static Type of(Class<?> primaryType) {
            return new Type(primaryType);
        }

        public static Type generic(Class<?> primaryType, Type... generics) {
            return new Type(primaryType, generics);
        }

        public static Type generic(Class<?> primaryType, Class<?>... parameters) {
            return new Type(primaryType,
                    Observable.from(parameters)
                            .map(Type::of)
                            .toList()
                            .map(list -> list.toArray(new Type[list.size()]))
                            .toBlocking()
                            .first());
        }

        public JavaType toJavaType() {
            if (mGenerics.length == 0) {
                return RestClient.getMapper().getTypeFactory().constructType(mPrimaryType);
            }
            final JavaType[] arr = Observable.from(mGenerics)
                    .map(Type::toJavaType)
                    .toList()
                    .map(list -> list.toArray(new JavaType[list.size()]))
                    .toBlocking()
                    .first();
            return RestClient.getMapper().getTypeFactory().constructParametricType(mPrimaryType, arr);
        }
    }

}
