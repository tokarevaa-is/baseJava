package com.tokarevaa.webapp.util;

public class DataParser {
    public static String extractArray(String str) {
        return str.substring(str.indexOf("[") + 1, str.lastIndexOf("]"));
    }

    public static String[] parseArray(String str) {
        String[] arrayElements = str.split("}(, )?");
        for (int i = 0; i < arrayElements.length; i++) {
            arrayElements[i] = arrayElements[i].substring(1);
        }

        return arrayElements;
    }
}
