package com.example.droid.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by ss on 2/26/2016.
 */
public class StringUtil {

    public static String formatCurrencyByLocale(Locale locale, double currency) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getCurrencyInstance(locale);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        symbols.setCurrencySymbol(""); // Don't use null.
        formatter.setDecimalFormatSymbols(symbols);
        return formatter.format(currency);
    }

    public static String subString(String sStatus) {
        int idx = sStatus.lastIndexOf("|");
        return sStatus.substring(0, idx);
    }

    public static String subStringLable(String sVariant) {
        int idx = sVariant.lastIndexOf(":");
        return sVariant.substring(0, idx+1);
    }

    public static String subStringDetail(String sVariant) {
        int idx = sVariant.lastIndexOf(":");
        return sVariant.substring(idx+1);
    }

    public static <T extends Enum<T>> T getEnumFromString(Class<T> c, String string) {
        if( c != null && string != null ) {
            try {
                return Enum.valueOf(c, string.trim());
            } catch(IllegalArgumentException ex) {
            }
        }
        return null;
    }

}
