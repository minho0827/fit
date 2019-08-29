package com.intellisyscorp.fitzme_android.utils;

import android.os.Build;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class UserAgentInterceptor implements Interceptor {

    public final String userAgent;

    private UserAgentInterceptor(String userAgent) {
        this.userAgent = userAgent;
    }


    public UserAgentInterceptor(String appName, String appVersion) {
        this(String.format(Locale.US,
                "%s/%s (Android %s; %s; %s %s; %s)",
                appName,
                appVersion,
                Build.VERSION.RELEASE,
                Build.MODEL,
                Build.BRAND,
                Build.DEVICE,
                Locale.getDefault().getLanguage()));
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("authorization", "Basic YXBwQGludGVsbGlzeXMuY28ua3I6Zml0em1lMTIzIQ==");
//        headerMap.put("autorization", "Basic " + JwtManager.getInstance().getAuthToken());
        headerMap.put("accept", "application/json");

        Request userAgentRequest = chain.request()
                .newBuilder()
                .headers(Headers.of(headerMap))
                .build();
        return chain.proceed(userAgentRequest);
    }
}