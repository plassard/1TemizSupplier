package com.digitaldna.supplier.network;


import com.digitaldna.supplier.network.beans.AcceptOrderBean;
import com.digitaldna.supplier.network.beans.GetCancelReasonsListBean;
import com.digitaldna.supplier.network.beans.GetLoginBean;
import com.digitaldna.supplier.network.beans.GetOrderDetailsBean;
import com.digitaldna.supplier.network.beans.GetOrdersBean;
import com.digitaldna.supplier.network.beans.GetSupplierRateAverageBean;
import com.digitaldna.supplier.network.beans.GetSupplierSummaryBean;
import com.digitaldna.supplier.network.beans.GetVerifCodeBean;
import com.digitaldna.supplier.network.beans.VerifPhoneNumberBean;
import com.digitaldna.supplier.network.requests.AcceptOrderRequest;
import com.digitaldna.supplier.network.requests.GetOrderDetailsRequest;
import com.digitaldna.supplier.network.requests.GetSupplierRateAveragesRequest;
import com.digitaldna.supplier.network.requests.GetVerificationCodeRequest;
import com.digitaldna.supplier.network.requests.LoginRequest;
import com.digitaldna.supplier.network.requests.BasicRequest;
import com.digitaldna.supplier.network.requests.RejectOrderRequest;
import com.digitaldna.supplier.network.requests.SaveOrderDetailsRequest;
import com.digitaldna.supplier.network.requests.VerifyPhoneRequest;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

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

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST(Urls.SUPPLIER +  "/" + Urls.LOGIN)
    Observable<GetLoginBean> login(@NonNull @Body LoginRequest body);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST(Urls.SUPPLIER +  "/" + Urls.ACCEPT_ORDER)
    Observable<AcceptOrderBean> acceptOrder(@NonNull @Body AcceptOrderRequest body);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST(Urls.SUPPLIER +  "/" + Urls.REJECT_ORDER)
    Observable<AcceptOrderBean> rejectOrder(@NonNull @Body RejectOrderRequest body);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST(Urls.PROPERTY +  "/" + Urls.GET_CANCEL_REASONS_LIST)
    Observable<GetCancelReasonsListBean> getCancelReasons(@NonNull @Body BasicRequest body);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST(Urls.MEMBERSHIP +  "/" + Urls.GET_VERIFICATION_CODE)
    Observable<GetVerifCodeBean> getVerifCode(@NonNull @Body GetVerificationCodeRequest body);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST(Urls.MEMBERSHIP +  "/" + Urls.VERIFY_USER_PHONE_NUMBER)
    Observable<VerifPhoneNumberBean> verifyPhoneNumber(@NonNull @Body VerifyPhoneRequest body);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST(Urls.SUPPLIER +  "/" + Urls.GET_ORDERS)
    Observable<GetOrdersBean> getSupplierOrders(@NonNull @Body BasicRequest body);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST(Urls.SUPPLIER +  "/" + Urls.GET_ORDER_DETAILS)
    Observable<GetOrderDetailsBean> getOrderDetails(@NonNull @Body GetOrderDetailsRequest body);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST(Urls.SUPPLIER +  "/" + Urls.GET_SUPPLIER_SUMMARY_INFO)
    Observable<GetSupplierSummaryBean> getSummary(@NonNull @Body BasicRequest body);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST(Urls.SUPPLIER +  "/" + Urls.RATE_AVERAGE)
    Observable<GetSupplierRateAverageBean> getRateAverage(@NonNull @Body GetSupplierRateAveragesRequest body);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST(Urls.SUPPLIER +  "/" + Urls.ACCEPT_ORDER)
    Observable<GetOrderDetailsBean> saveOrderDetails(@NonNull @Body SaveOrderDetailsRequest body);

}
