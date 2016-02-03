package com.example.tmnt.coolweather.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tmnt on 2016/2/3.
 */
public class SpiltUtils {
    public static Map<String, String> returnProvince(String result) {
        //List<String> frist = new ArrayList<>();
        String[] second = null;
        Map<String, String> map = new HashMap<>();
        String[] fristString = result.split("\\,");
        for (int i = 0; i < fristString.length; i++) {
            second = fristString[i].split("\\|");
            map.put(second[1], second[0]);
        }
        return map;
    }
}
