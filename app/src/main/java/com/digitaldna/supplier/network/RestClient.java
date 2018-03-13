package com.digitaldna.supplier.network;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;

//import com.digitaldna.courier.BuildConfig;
//import com.digitaldna.courier.utils.preferences.PrefProvider;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
//import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

//import static com.digitaldna.supplier.network.NetworkContract.Base.TICKET_FIELD;

import okhttp3.RequestBody;
import okio.Buffer;
import java.io.IOException;
/**
 * Rest client model.
 */
public class RestClient {



    private static ObjectMapper sMapper = createObjectMapper();
    private static final Retrofit RETROFIT = new Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(JacksonConverterFactory.create(sMapper))
            .baseUrl(Urls.HOST_URL)
            .client(createHttpClient())
            .build();



    private RestClient() {
    }

    public static Retrofit getInstance() {
        return RETROFIT;
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
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
return client;

       /* OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    RequestBody requestBody = request.body();
                    String requestStr = bodyToString(requestBody);
                    if (!TextUtils.isEmpty(requestStr)) {
                        JsonNode node = getMapper().readTree(requestStr);
                        change(node, "Ticket", PrefProvider.getInstance().getTicket());

                        RequestBody body = RequestBody.create(requestBody.contentType(), node.toString());
                        request = request.newBuilder()
                                .delete(requestBody)
                                .post(body)
                                .build();

                    }
                    return chain.proceed(request);
                });

            final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpBuilder.addInterceptor(loggingInterceptor);


        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient=new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        return httpClient.build();*/
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
