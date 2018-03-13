package com.digitaldna.supplier.network;


import com.digitaldna.supplier.network.beans.GetLoginBean;
import com.digitaldna.supplier.network.requests.LoginRequest;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/*
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;*/

public interface NetworkAPIsInterface {

    /**
     * Authorize with ticket
     * http://apidocs.1temiz.com/Method/Index/136
     * Api #50.2
     */
    //@Headers("Content-Type: application/x-www-form-urlencoded")
  /*  @POST(Urls.SUPPLIER + Urls.LOGIN)
    Observable<GetLoginBean> login(@NonNull @Body LoginRequest body);
*/

    @GET("coupons/")
    Observable<StoreCoupons> getCoupons(@Query("status") String status);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST(Urls.SUPPLIER +  "/" + Urls.LOGIN)
    Observable<GetLoginBean> login(@NonNull @Body LoginRequest body);


}
