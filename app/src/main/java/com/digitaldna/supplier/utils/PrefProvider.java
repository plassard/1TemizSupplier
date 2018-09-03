package com.digitaldna.supplier.utils;

import android.content.Context;
import android.content.SharedPreferences;



/**
 * Created by yevgen on 3/13/18.
 */

public class PrefProvider {
    static SharedPreferences mPreferences;

    static public void saveEmail(Context context, String email){
        mPreferences = context.getSharedPreferences(PreferencesContract.PREFS_NAME, Context.MODE_PRIVATE);
        mPreferences.edit()
                .putString(PreferencesContract.Supplier.EMAIL, email)
                .commit();
    }

    static public String getEmail(Context context) {
        mPreferences = context.getSharedPreferences(PreferencesContract.PREFS_NAME, Context.MODE_PRIVATE);
        return mPreferences.getString(PreferencesContract.Supplier.EMAIL, "");
    }

    static public void saveShopName(Context context, String shopName){
        mPreferences = context.getSharedPreferences(PreferencesContract.PREFS_NAME, Context.MODE_PRIVATE);
        mPreferences.edit()
                .putString(PreferencesContract.Supplier.SHOP_NAME, shopName)
                .commit();
    }

    static public String getShopName(Context context) {
        mPreferences = context.getSharedPreferences(PreferencesContract.PREFS_NAME, Context.MODE_PRIVATE);
        return mPreferences.getString(PreferencesContract.Supplier.SHOP_NAME, "");
    }

    static public void saveLanguage(Context context, String language){
        mPreferences = context.getSharedPreferences(PreferencesContract.PREFS_NAME, Context.MODE_PRIVATE);
        mPreferences.edit()
                .putString(PreferencesContract.Supplier.LANGUAGE, language)
                .commit();
    }

    static public void saveLanguageId(Context context, Integer languageId){
        mPreferences = context.getSharedPreferences(PreferencesContract.PREFS_NAME, Context.MODE_PRIVATE);
        mPreferences.edit()
                .putInt(PreferencesContract.Supplier.LANGUAGE_ID, languageId)
                .commit();
    }

    static public String getLanguage(Context context) {
        mPreferences = context.getSharedPreferences(PreferencesContract.PREFS_NAME, Context.MODE_PRIVATE);
        return mPreferences.getString(PreferencesContract.Supplier.LANGUAGE, "");
    }

    static public Integer getLanguageId(Context context) {
        mPreferences = context.getSharedPreferences(PreferencesContract.PREFS_NAME, Context.MODE_PRIVATE);
        return mPreferences.getInt(PreferencesContract.Supplier.LANGUAGE_ID, -1);
    }

    static public void saveProfilePictureURL(Context context, String url){
        mPreferences = context.getSharedPreferences(PreferencesContract.PREFS_NAME, Context.MODE_PRIVATE);
        mPreferences.edit()
                .putString(PreferencesContract.Supplier.PROFILE_PICTURE_URL, url)
                .commit();
    }

    static public String getProfilePictureURL(Context context) {
        mPreferences = context.getSharedPreferences(PreferencesContract.PREFS_NAME, Context.MODE_PRIVATE);
        return mPreferences.getString(PreferencesContract.Supplier.PROFILE_PICTURE_URL, "");
    }


    static public void saveSupplierTitle(Context context, String title){
        mPreferences = context.getSharedPreferences(PreferencesContract.PREFS_NAME, Context.MODE_PRIVATE);
        mPreferences.edit()
                .putString(PreferencesContract.Supplier.TITLE, title)
                .commit();
    }

    static public String getSupplierTitle(Context context) {
        mPreferences = context.getSharedPreferences(PreferencesContract.PREFS_NAME, Context.MODE_PRIVATE);
        return mPreferences.getString(PreferencesContract.Supplier.TITLE, "");
    }

    static public void saveTicket(Context context, String ticket){
        mPreferences = context.getSharedPreferences(PreferencesContract.PREFS_NAME, Context.MODE_PRIVATE);
        mPreferences.edit()
                .putString(PreferencesContract.Network.TICKET, ticket)
                .commit();
    }

    static public String getTicket(Context context) {
        mPreferences = context.getSharedPreferences(PreferencesContract.PREFS_NAME, Context.MODE_PRIVATE);
        return mPreferences.getString(PreferencesContract.Network.TICKET, "");
    }

    static public void saveCountryID(Context context, Integer CountryID){
        mPreferences = context.getSharedPreferences(PreferencesContract.PREFS_NAME, Context.MODE_PRIVATE);
        mPreferences.edit()
                .putInt(PreferencesContract.Supplier.COUNTRY_ID, CountryID)
                .commit();
    }

    static public Integer getCountryID(Context context) {
        mPreferences = context.getSharedPreferences(PreferencesContract.PREFS_NAME, Context.MODE_PRIVATE);
        return mPreferences.getInt(PreferencesContract.Supplier.COUNTRY_ID, -1);
    }

    static public void saveSeenOrderID(Context context, Integer CountryID){
        mPreferences = context.getSharedPreferences(PreferencesContract.PREFS_NAME, Context.MODE_PRIVATE);
        mPreferences.edit()
                .putInt(PreferencesContract.Supplier.LAST_SEEN_ORDER_ID, CountryID)
                .commit();
    }

    static public Integer getSeenOrderID(Context context) {
        mPreferences = context.getSharedPreferences(PreferencesContract.PREFS_NAME, Context.MODE_PRIVATE);
        return mPreferences.getInt(PreferencesContract.Supplier.LAST_SEEN_ORDER_ID, -1);
    }

    static public void saveGsmNumberCountryID(Context context, Integer CountryID){
        mPreferences = context.getSharedPreferences(PreferencesContract.PREFS_NAME, Context.MODE_PRIVATE);
        mPreferences.edit()
                .putInt(PreferencesContract.Supplier.GSM_NUMBER_COUNTRY_ID, CountryID)
                .commit();
    }

    static public Integer getGsmNumberCountryID(Context context) {
        mPreferences = context.getSharedPreferences(PreferencesContract.PREFS_NAME, Context.MODE_PRIVATE);
        return mPreferences.getInt(PreferencesContract.Supplier.GSM_NUMBER_COUNTRY_ID, -1);
    }


    static public void savePhoneNumber(Context context, String PhoneNumber){
        mPreferences = context.getSharedPreferences(PreferencesContract.PREFS_NAME, Context.MODE_PRIVATE);
        mPreferences.edit()
                .putString(PreferencesContract.Supplier.PHONE_NUMBER, PhoneNumber)
                .commit();
    }

    static public String getPhoneNumber(Context context) {
        mPreferences = context.getSharedPreferences(PreferencesContract.PREFS_NAME, Context.MODE_PRIVATE);
        return mPreferences.getString(PreferencesContract.Supplier.PHONE_NUMBER, "");
    }

    static public void saveGsmNumber(Context context, String PhoneNumber){
        mPreferences = context.getSharedPreferences(PreferencesContract.PREFS_NAME, Context.MODE_PRIVATE);
        mPreferences.edit()
                .putString(PreferencesContract.Supplier.GSM_NUMBER, PhoneNumber)
                .commit();
    }

    static public String getGsmNumber(Context context) {
        mPreferences = context.getSharedPreferences(PreferencesContract.PREFS_NAME, Context.MODE_PRIVATE);
        return mPreferences.getString(PreferencesContract.Supplier.GSM_NUMBER, "");
    }

    static public void logOut(Context context){
        mPreferences = context.getSharedPreferences(PreferencesContract.PREFS_NAME, Context.MODE_PRIVATE);
        mPreferences.edit()
                .clear()
                .commit();
    }

}
