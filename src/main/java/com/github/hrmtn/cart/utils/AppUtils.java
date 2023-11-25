package com.github.hrmtn.cart.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class AppUtils {

    static private DateTimeFormatter formatter;

    static {
        formatter = DateTimeFormatter
                .ISO_DATE_TIME
                .withLocale(Locale.US)
                .withLocale( Locale.FRANCE )
                .withZone(ZoneId.of("UTC"));
    }

    public static String formatDate(Instant instant) {
        return formatter.format(instant);
    }

}
