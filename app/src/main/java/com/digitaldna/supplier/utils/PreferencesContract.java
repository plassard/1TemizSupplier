package com.digitaldna.supplier.utils;


/**
 * Shared preferences contract.
 */
final class PreferencesContract {

    static final String PREFS_NAME = "OneTemizCourier";

    private PreferencesContract() {
        //no instance
    }

    static final class Network {
        private static final String NETWORK_PREFIX = "network_";
        static final String TOKEN = NETWORK_PREFIX + "token";
        static final String TICKET = NETWORK_PREFIX + "ticket";
        static final String REFRESH_TOKEN = NETWORK_PREFIX + "refresh_token";
        static final String COUNTRY_CODE = NETWORK_PREFIX + "country_code";

        private Network() {
            //no instance
        }
    }

    static final class Supplier {
        private static final String USER_PREFIX = "supplier_";

        static final String SHOP_NAME = USER_PREFIX + "shop_name";
        static final String EMAIL = USER_PREFIX + "email";
        static final String LANGUAGE = USER_PREFIX + "language";
        static final String LANGUAGE_ID = USER_PREFIX + "language_id";
        static final String PROFILE_PICTURE_URL = USER_PREFIX + "profile_picture_url";
        static final String TITLE = USER_PREFIX + "title";
        static final String COUNTRY_ID = USER_PREFIX + "country_id";
        static final String PHONE_NUMBER = USER_PREFIX + "phone_number";

        static final String PUSH_TOKEN = USER_PREFIX + "push_token";
        static final String VERIFIED = USER_PREFIX + "verified";

        static final String MY_COUNTRY_ID = USER_PREFIX + "country_id";
        static final String MY_PHONE_AREA = USER_PREFIX + "phone_area";
        static final String MY_PHONE_CODE = USER_PREFIX + "phone_code";
        static final String MY_PHONE_NUMBER = USER_PREFIX + "phone_number";

        private Supplier() {
            //no instance
        }

    }

    static final class Location {
        private static final String LOCATION_PREFIX = "location_";
        static final String LATITUDE = LOCATION_PREFIX + "latitude";
        static final String LONGITUDE = LOCATION_PREFIX + "longitude";

        private Location() {
            //no instance
        }
    }

}
