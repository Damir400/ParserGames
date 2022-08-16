package com.damir.parsing.common;

public class Helper {
    public static int tryParseInt(String value, int defaultVal) {
        try {
            return Integer.parseInt(value);
        }
        catch (NumberFormatException e) {
            return defaultVal;
        }
    }
}
