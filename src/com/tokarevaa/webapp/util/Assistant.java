package com.tokarevaa.webapp.util;

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
}
