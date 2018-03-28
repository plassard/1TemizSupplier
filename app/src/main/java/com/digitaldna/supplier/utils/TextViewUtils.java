package com.digitaldna.supplier.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class TextViewUtils {

    private static final NumberFormat mDecimalFormat = new DecimalFormat("0.#");

    private TextViewUtils() {
//        no instance
    }

    public static String formatDoubleValueForQuantity(Double inValue) {
        return mDecimalFormat.format(inValue);
    }


}