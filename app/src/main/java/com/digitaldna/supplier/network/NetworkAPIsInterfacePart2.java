package com.digitaldna.supplier.network;


import com.digitaldna.supplier.network.beans.AcceptOrderBean;
import com.digitaldna.supplier.network.beans.EmptyBean;
import com.digitaldna.supplier.network.beans.GetCancelReasonsListBean;
import com.digitaldna.supplier.network.beans.GetCapacityBean;
import com.digitaldna.supplier.network.beans.GetCommentsBean;
import com.digitaldna.supplier.network.beans.GetEarningsBean;
import com.digitaldna.supplier.network.beans.GetEmptyBean;
import com.digitaldna.supplier.network.beans.GetHolidaysBean;
import com.digitaldna.supplier.network.beans.GetItemPricingBean;
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
import com.digitaldna.supplier.network.requests.AcceptOrderRequest;
import com.digitaldna.supplier.network.requests.BaseWithFilterRequest;
import com.digitaldna.supplier.network.requests.BaseWithGroupIDRequest;
import com.digitaldna.supplier.network.requests.BaseWithLangIdRequest;
import com.digitaldna.supplier.network.requests.BaseWithSupplierIdRequest;
import com.digitaldna.supplier.network.requests.BasicRequest;
import com.digitaldna.supplier.network.requests.GetOrderDetailsRequest;
import com.digitaldna.supplier.network.requests.GetVerificationCodeRequest;
import com.digitaldna.supplier.network.requests.LoginRequest;
import com.digitaldna.supplier.network.requests.RejectOrderRequest;
import com.digitaldna.supplier.network.requests.SaveOrderDetailsRequest;
import com.digitaldna.supplier.network.requests.SetItemPricingRequest;
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

public interface NetworkAPIsInterfacePart2 {

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST(Urls.SUPPLIER +  "/" + Urls.GET_ITEM_PRICING)
    Observable<GetItemPricingBean> getItemPricing(@NonNull @Body BaseWithGroupIDRequest body);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST(Urls.SUPPLIER +  "/" + Urls.SET_ITEM_PRICING)
    Observable<GetEmptyBean> setItemPricing(@NonNull @Body SetItemPricingRequest body);
}
