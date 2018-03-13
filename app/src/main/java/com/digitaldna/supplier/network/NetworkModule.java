package com.digitaldna.supplier.network;

//import com.digitaldna.supplier.network.requests.LoginRequest;

//import rx.Observable;

/**
 * Created by yevgen on 3/13/18.
 */

public class NetworkModule {


 /*   public static NetworkModule getInstance() {
        return NetworkProvider.Loader.NETWORK_PROVIDER;
    }

    public static AuthorizationModule getAuthorization() {
        return getInstance().mAuthorizationModule;
    }*/


    /*@Override
    public Observable<LoginUserBean> login(
            String token, String userId, Long countryId, String phoneNumber, String password, String deviceToken, int languageID) {

        LoginRequest request = new LoginRequest(token, userId, countryId, phoneNumber, password, deviceToken, languageID);

        return mApiAuthorization
                .login(request)
                .compose(NetworkErrorUtils.rxParseServerError())
                .map(GetLoginUserBean::getData)
                .onErrorResumeNext(NetworkErrorUtils.instance().rxParseError());
    }*/
}
