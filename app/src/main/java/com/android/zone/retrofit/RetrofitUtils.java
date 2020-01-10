package com.android.zone.retrofit;
import android.text.TextUtils;

import com.android.zone.BuildConfig;
import com.android.zone.utils.LogUtil;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * 连接处理类，主要提供OkHttpClient对象
 * Created by wenbiao.xie on 2016/6/8.
 */
public final class RetrofitUtils {


    public static final String BASE_URL = "http://platform-test2.tclclouds.com/";
    private static final int DEFAULT_TIMEOUT = 10;
    private static Retrofit retrofit;
    private static RetrofitUtils INSTANCE;

    private RetrofitUtils(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        if(retrofit == null){
            synchronized (RetrofitUtils.class){
                if(INSTANCE == null){
                    retrofit = new Retrofit.Builder()
                            .client(builder.build())
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .baseUrl(BASE_URL)
                            .build();
                }
            }
        }

    }

    public static RetrofitUtils getInstance(){
        if(INSTANCE == null){
            synchronized (RetrofitUtils.class){
                if(INSTANCE == null){
                    INSTANCE = new RetrofitUtils();
                }
            }
        }
        return INSTANCE;
    }
    public  <T> T createService(Class<T> cls) {
        return retrofit.create(cls);
    }

}
