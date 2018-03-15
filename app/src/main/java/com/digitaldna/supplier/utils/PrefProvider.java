package com.digitaldna.supplier.utils;

import android.annotation.SuppressLint;
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
                .putString(PreferencesContract.User.EMAIL, email)
                .commit();
    }

    static public String getEmail(Context context) {
        mPreferences = context.getSharedPreferences(PreferencesContract.PREFS_NAME, Context.MODE_PRIVATE);
        return mPreferences.getString(PreferencesContract.User.EMAIL, "");
    }



}
