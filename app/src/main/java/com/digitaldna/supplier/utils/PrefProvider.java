package com.digitaldna.supplier.utils;

/**
 * Created by yevgen on 3/13/18.
 */

public class PrefProvider {




    public static PrefProvider getInstance() {
        return Loader.sInstance;
    }

    /**
     * Get ticket
     *
     * @return api ticket
     */
    public String getTicket() {
     //   return mPreferences.getString(PreferencesContract.Network.TICKET, "");
        return "test";
    }

    private static final class Loader {
        private static final PrefProvider sInstance = new PrefProvider();

        private Loader() throws IllegalAccessException {
            throw new IllegalAccessException("Loader class");
        }
    }
}
