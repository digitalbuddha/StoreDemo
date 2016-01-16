package com.digitalbuddha.daodemo.base;

import android.support.annotation.NonNull;

import com.digitalbuddha.daodemo.util.NetworkStatus;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import javax.inject.Inject;

/**
 * Created by 206847 on 1/16/16.
 */

public class CacheInterceptor implements Interceptor {
    private NetworkStatus networkStatus;

    @Inject
    public CacheInterceptor(NetworkStatus networkStatus) {
        this.networkStatus = networkStatus;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request.Builder request = originalRequest.newBuilder();
        if (originalRequest.header("fresh") != null) {
            request.cacheControl(CacheControl.FORCE_NETWORK);
        }
        Response response = chain.proceed(request.build());
        return response.newBuilder()
                .removeHeader("Pragma")
                .removeHeader("Cache-Control")
                .header("Cache-Control", cacheControl())
                .build();
    }

    @NonNull
    private String cacheControl() {
        String cacheHeaderValue;
        if (networkStatus.isOnGoodConnection()) {
            cacheHeaderValue = "public, max-age=2419200";
        } else {
            cacheHeaderValue = "public, only-if-cached, max-stale=2419200";
        }
        return cacheHeaderValue;
    }
}
