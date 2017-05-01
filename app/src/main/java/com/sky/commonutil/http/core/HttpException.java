package com.sky.commonutil.http.core;

/**
 * Created by tonycheng on 2017/4/26.
 */

public class HttpException extends RuntimeException {

    public static final int CODE_TIMEOUT = 1000;
    public static final int CODE_UNCONNECTED = 1001;
    public static final int CODE_MALFORMEDJSON = 1002;
    public static final int CODE_OTHER = 1003;
    public static final String CONNECT_EXCEPTION = "网络连接异常，请检查您的网络状态";
    public static final String SOCKET_TIMEOUT_EXCEPTION = "网络连接超时，请检查您的网络状态，稍后重试";
    public static final String MALFORMED_JSON_EXCEPTION = "数据解析错误";

    public HttpException(int errorCode, String message) {
        super(getHttpException(errorCode, message));
    }

    public HttpException(String message) {
        super(message);
    }

    private static String getHttpException(int code, String msg) {
        String message = "";
        switch (code) {
            default:
                message = msg;
        }
        return message;
    }
}
