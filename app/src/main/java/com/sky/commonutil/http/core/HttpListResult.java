package com.sky.commonutil.http.core;

import java.util.List;

/**
 * Created by tonycheng on 2017/4/23.
 */

public class HttpListResult<T> {
    public boolean error = false;
    public List<T> results;
}
