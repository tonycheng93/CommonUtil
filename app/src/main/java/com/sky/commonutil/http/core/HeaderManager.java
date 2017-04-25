package com.sky.commonutil.http.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tonycheng on 2017/4/25.
 */

public class HeaderManager {

    private final Map<String, String> mDefaultHeaders = new HashMap<>();

    private static class Holder {
        public static HeaderManager instance = new HeaderManager();
    }

    public static HeaderManager getInstance() {
        return Holder.instance;
    }
}
