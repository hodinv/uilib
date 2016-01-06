package com.hodinv.uilib.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by vhodin on 22.12.2015.
 * Helper for numbers parsing
 */
public class NumbersHelper {

    /**
     * Parse string toi double. Delimiter can be , or .
     *
     * @param doubleStr double string value
     * @return double value or null if error while parsing (null input also null)
     */
    @Nullable
    public static Double parseToDouble(@Nullable String doubleStr) {
        if (doubleStr == null) {
            return 0D;
        }
        try {
            return Double.parseDouble(doubleStr);
        } catch (NumberFormatException ex1) {
            try {
                return Double.parseDouble(doubleStr.replace(".", ","));
            } catch (NumberFormatException ex2) {
                try {
                    return Double.parseDouble(doubleStr.replace(",", "."));
                } catch (NumberFormatException ex3) {
                    // do nothing
                }
            }
        }
        return null;
    }

    /**
     * Parse string toi double. Delimiter can be , or .
     *
     * @param doubleStr double string value
     * @return double value or 0 if error while parsing (null input also 0)
     */
    @NonNull
    public static Double parseDoubleNotNull(@Nullable String doubleStr) {
        Double value = parseToDouble(doubleStr);
        return value != null ? value : 0D;
    }


}
