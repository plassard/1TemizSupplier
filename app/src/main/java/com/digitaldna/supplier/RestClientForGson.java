package com.digitaldna.supplier;

import android.annotation.SuppressLint;

import com.digitaldna.supplier.network.Urls;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Rest client model.
 */
public class RestClientForGson {

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Urls.HOST_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(createHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static ObjectMapper sMapper = createObjectMapper();

    private RestClientForGson() {
    }

    public static Retrofit getInstance() {
        return retrofit;
    }


    public static ObjectMapper getMapper() {
        return sMapper;
    }

    private static ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NONE);
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper;
    }

    @SuppressLint("CommitPrefEdits")
    private static OkHttpClient createHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
        return client;
    }


    private static void change(JsonNode parent, String fieldName, String newValue) {
        if (parent.has(fieldName)) {
            ((ObjectNode) parent).put(fieldName, newValue);
        }
        // Now, recursively invoke this method on all properties
        for (JsonNode child : parent) {
            change(child, fieldName, newValue);
        }
    }

    static String bodyToString(final RequestBody request) {
        Buffer buffer = null;
        try {
            buffer = new Buffer();
            if (request != null) {
                request.writeTo(buffer);
            } else {
                return "";
            }
            return buffer.readUtf8();
        } catch (final IOException e) {
            //LOG.e(e);
            return "";
        } finally {
            if (buffer != null) {
                buffer.flush();
                buffer.close();
            }
        }
    }

}
