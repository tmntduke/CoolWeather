package com.example.tmnt.coolweather.Utils;

/**
 * Created by tmnt on 2016/2/3.
 */
public class StringUtils {
    public static String toStrings(byte[] bytes) {
        return new String(bytes, 0, bytes.length);
    }
}
