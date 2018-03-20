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

    static public void logOut(Context context){
        mPreferences = context.getSharedPreferences(PreferencesContract.PREFS_NAME, Context.MODE_PRIVATE);
        mPreferences.edit()
                .clear()
                .commit();
    }

}
