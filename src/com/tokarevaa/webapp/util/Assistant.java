package com.tokarevaa.webapp.util;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class Assistant {
    public static int getObjectHash(Object o) {
        return (o == null) ? 0 : o.hashCode();
    }

    public static boolean isObjectEqualsBasic(Object o1, Object o2) {
        if (o1 == o2) {
            return true;
        }
        return o2 != null && o1.getClass() == o2.getClass();
    }

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/yyyy");
    private static final String DATE_NOW_TEXT = "н.в.";

    public static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static LocalDate parseDate(String date) {
        if (isEmpty(date) || date.equals("-") || date.equals(DATE_NOW_TEXT)) {
            return LocalDate.MAX;
        }
        YearMonth newDate = YearMonth.parse(date, DATE_FORMATTER);
        return LocalDate.of(newDate.getYear(), newDate.getMonthValue(), 1);
    }

    public static String formatDate(LocalDate date) {
        return date == null ? "" : (date.isEqual(LocalDate.MAX) ? DATE_NOW_TEXT : date.format(DATE_FORMATTER));
    }
}