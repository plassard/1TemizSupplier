package com.digitaldna.supplier.network;


import com.digitaldna.supplier.network.beans.AcceptOrderBean;
import com.digitaldna.supplier.network.beans.EmptyBean;
import com.digitaldna.supplier.network.beans.EmptyNoDataBean;
import com.digitaldna.supplier.network.beans.GetCancelReasonsListBean;
import com.digitaldna.supplier.network.beans.GetCapacityBean;
import com.digitaldna.supplier.network.beans.GetCommentsBean;
import com.digitaldna.supplier.network.beans.GetEarningsBean;
import com.digitaldna.supplier.network.beans.GetEmptyBean;
import com.digitaldna.supplier.network.beans.GetHolidaysBean;
import com.digitaldna.supplier.network.beans.GetLoginBean;
import com.digitaldna.supplier.network.beans.GetOrderDetailsBean;
import com.digitaldna.supplier.network.beans.GetOrdersBean;
import com.digitaldna.supplier.network.beans.GetProductGroupBean;
import com.digitaldna.supplier.network.beans.GetProductPriceListBean;
import com.digitaldna.supplier.network.beans.GetShopInfoBean;
import com.digitaldna.supplier.network.beans.GetStatisticOrdersBean;
import com.digitaldna.supplier.network.beans.GetSupplierRateAverageBean;
import com.digitaldna.supplier.network.beans.GetSupplierSuccessRate;
import com.digitaldna.supplier.network.beans.GetSupplierSummaryBean;
import com.digitaldna.supplier.network.beans.GetVerifCodeBean;
import com.digitaldna.supplier.network.beans.GetWorkingHoursBean;
import com.digitaldna.supplier.network.beans.VerifPhoneNumberBean;
import com.digitaldna.supplier.network.beans.base.BaseJsonBean;
import com.digitaldna.supplier.network.beans.base.BaseJsonDataBean;
import com.digitaldna.supplier.network.requests.AcceptOrderRequest;
import com.digitaldna.supplier.network.requests.BaseWithGroupIDRequest;
import com.digitaldna.supplier.network.requests.BaseWithLangIdRequest;
import com.digitaldna.supplier.network.requests.BaseWithSupplierIdRequest;
import com.digitaldna.supplier.network.requests.GetOrderDetailsRequest;
import com.digitaldna.supplier.network.requests.BaseWithFilterRequest;
import com.digitaldna.supplier.network.requests.GetVerificationCodeRequest;
import com.digitaldna.supplier.network.requests.LoginRequest;
import com.digitaldna.supplier.network.requests.BasicRequest;
import com.digitaldna.supplier.network.requests.RejectOrderRequest;
import com.digitaldna.supplier.network.requests.SaveOrderDetailsRequest;
import com.digitaldna.supplier.network.requests.SetOrderProductListRequest;
import com.digitaldna.supplier.network.requests.SetShopInfoPasswordRequest;
import com.digitaldna.supplier.network.requests.SetShopInformationRequest;
import com.digitaldna.supplier.network.requests.StartDateEndDateRequest;
import com.digitaldna.supplier.network.requests.VerifyPhoneRequest;
import com.google.gson.JsonObject;

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


    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST(Urls.SUPPLIER +  "/" + Urls.LOGIN)
    Observable<GetLoginBean> login(@NonNull @Body LoginRequest body);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST(Urls.MEMBERSHIP +  "/" + Urls.FORGOT)
    Observable<GetEmptyBean> forgot(@NonNull @Body LoginRequest body);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST(Urls.SUPPLIER +  "/" + Urls.SET_SHOP_INFO)
    Observable<GetLoginBean> setShopInfo(@NonNull @Body SetShopInformationRequest body);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST(Urls.SUPPLIER +  "/" + Urls.SET_SHOP_INFO)
    Observable<GetLoginBean> setPassword(@NonNull @Body SetShopInfoPasswordRequest body);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST(Urls.SUPPLIER +  "/" + Urls.GET_SHOP_INFO)
    Observable<GetShopInfoBean> getShopInfo(@NonNull @Body BasicRequest body);

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
    @POST(Urls.SUPPLIER +  "/" + Urls.GET_CAPACITY)
    Observable<GetCapacityBean> getCapacityFullSettings(@NonNull @Body BasicRequest body);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST(Urls.SUPPLIER +  "/" + Urls.SET_CAPACITY)
    Observable<GetEmptyBean> setCapacityFullSettings(@Body JsonObject body);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST(Urls.SUPPLIER +  "/" + Urls.GET_WORKING_HOURS)
    Observable<GetWorkingHoursBean> getWorkingHours(@NonNull @Body BasicRequest body);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST(Urls.SUPPLIER +  "/" + Urls.SET_WORKING_HOURS)
    Observable<GetEmptyBean> setWorkingHours(@Body JsonObject body);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST(Urls.SUPPLIER +  "/" + Urls.GET_SUPPLIER_HOLIDAYS)
    Observable<GetHolidaysBean> getSupplierHolidays(@NonNull @Body BasicRequest body);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST(Urls.SUPPLIER +  "/" + Urls.ADD_SUPPLIER_HOLIDAYS)
    Observable<GetEmptyBean> addSupplierHolidays(@Body JsonObject body);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST(Urls.SUPPLIER +  "/" + Urls.DELETE_SUPPLIER_HOLIDAYS)
    Observable<GetEmptyBean> deleteSupplierHolidays(@Body JsonObject body);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST(Urls.SUPPLIER +  "/" + Urls.GET_ORDER_DETAILS)
    Observable<GetOrderDetailsBean> getOrderDetails(@NonNull @Body GetOrderDetailsRequest body);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST(Urls.SUPPLIER +  "/" + Urls.GET_SUPPLIER_SUMMARY_INFO)
    Observable<GetSupplierSummaryBean> getSummary(@NonNull @Body BasicRequest body);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST(Urls.SUPPLIER +  "/" + Urls.RATE_AVERAGE)
    Observable<GetSupplierRateAverageBean> getRateAverage(@NonNull @Body BaseWithFilterRequest body);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST(Urls.SUPPLIER +  "/" + Urls.SUPPLIER_COMMENTS)
    Observable<GetCommentsBean> getSupplierComments(@NonNull @Body BaseWithFilterRequest body);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST(Urls.SUPPLIER +  "/" + Urls.STATISTICS)
    Observable<GetSupplierSuccessRate> getStatistics(@NonNull @Body BaseWithFilterRequest body);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST(Urls.SUPPLIER +  "/" + Urls.SUPPLIER_ORDERS)
    Observable<GetStatisticOrdersBean> getStatisticsOnOrders(@NonNull @Body BaseWithFilterRequest body);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST(Urls.SUPPLIER +  "/" + Urls.ACCEPT_ORDER)
    Observable<GetOrderDetailsBean> saveOrderDetails(@NonNull @Body SaveOrderDetailsRequest body);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST(Urls.SUPPLIER +  "/" + Urls.SET_LANGUAGE)
    Observable<EmptyBean> changeLanguage(@NonNull @Body BaseWithLangIdRequest body);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST(Urls.SUPPLIER +  "/" + Urls.SUPPLIER_EARNINGS)
    Observable<GetEarningsBean> getEarnings(@NonNull @Body StartDateEndDateRequest body);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST(Urls.PRODUCT +  "/" + Urls.GET_PRODUCT_GROUP_LIST)
    Observable<GetProductGroupBean> getProductGroupList(@NonNull @Body BasicRequest body);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST(Urls.PRODUCT +  "/" + Urls.GET_PRODUCT_PRICE_LIST)
    Observable<GetProductPriceListBean> getProductPriceList(@NonNull @Body BaseWithSupplierIdRequest body);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST(Urls.SUPPLIER +  "/" + Urls.SET_ORDER_PRODUCT_LIST)
    Observable<GetEmptyBean> setOrderProductList(@NonNull @Body SetOrderProductListRequest body);

 /*   @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST(Urls.SUPPLIER +  "/" + Urls.GET_ITEM_PRICING)
    Observable<GetProductPriceListBean> getItemPricing(@NonNull @Body BaseWithGroupIDRequest body);*/
}
