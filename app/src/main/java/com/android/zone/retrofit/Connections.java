package com.android.zone.retrofit;

import android.content.Context;
import android.text.TextUtils;

import com.android.zone.BuildConfig;
import com.android.zone.utils.LogUtil;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.OkUrlFactory;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import org.apache.http.conn.scheme.HostNameResolver;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * 连接处理类，主要提供OkHttpClient对象
 */
public final class Connections {

    private final static int CONN_TIMEOUT = 30000;
    private final static int READ_TIMEOUT = 30000;
    private static String userAgent;

    private static Singleton<OkHttpClient> SINGLE_CLIENT = new Singleton<OkHttpClient>() {
        @Override
        protected OkHttpClient create() {
            OkHttpClient client = new OkHttpClient();
            onOkHttpClientCreated(client);
            return client;
        }
    };

    private static Singleton<Retrofit> RETROFIT = new Singleton<Retrofit>() {
        @Override
        protected Retrofit create() {
            OkHttpClient client = getOkHttpClient();
            Retrofit.Builder builder = new Retrofit.Builder();

            String apiHost =BuildConfig.HOST;

            return builder.addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .baseUrl(apiHost)
                    .build();
        }
    };

    public static String getUserAgent() {
        return userAgent;
    }

    public static void setUserAgent(String ua) {
        userAgent = ua;
    }

    private static void supportHttps(OkHttpClient client) {
        try {
            TrustManager tm = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType)
                        throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType)
                        throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };

            SSLContext e = SSLContext.getInstance("TLS");
            e.init(null, new TrustManager[]{tm}, null);
            client.setSslSocketFactory(e.getSocketFactory());

        } catch (Exception e) {
            throw new RuntimeException("supportHttps failed", e);
        }
    }

    private static void onOkHttpClientCreated(OkHttpClient client) {
        client.setConnectTimeout(CONN_TIMEOUT, TimeUnit.MILLISECONDS);
        client.setReadTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS);
        supportHttps(client);

        List<Interceptor> interceptors = client.interceptors();
        interceptors.add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                String url = request.urlString();
                if (!url.startsWith(BuildConfig.HOST)) {
                    return chain.proceed(request);
                }

                Request.Builder builder = chain.request().newBuilder();
                try {
                    final String ua = getUserAgent();
                    if (!TextUtils.isEmpty(ua)) {
                        builder.header("User-Agent", ua);
                    }
                    String country = Locale.getDefault().getCountry();

                    String language = Locale.getDefault().getLanguage();

//                    builder.addHeader("country", Headers.valueEncoded(country))
//                            .addHeader("language", Headers.valueEncoded(language));

                    //添加header
                    Map<String, String> headers = Headers.createCommonHeaders();
                    if (headers != null) {
                        for (Map.Entry<String, String> entry : headers.entrySet()) {
                            builder.addHeader(entry.getKey(), entry.getValue());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return chain.proceed(builder.build());
            }
        });

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    LogUtil.d("OkHttp", message);
                }
            });

            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            interceptors.add(interceptor);
    }


    private static OkUrlFactory sFactory;

    public static HttpURLConnection openConnection(String url) throws MalformedURLException {
        if (sFactory == null) {
            sFactory = new OkUrlFactory(getOkHttpClient());
        }
        return sFactory.open(new URL(url));
    }

    public static OkHttpClient getOkHttpClient() {
        return SINGLE_CLIENT.get();
    }

    public static Retrofit getRetrofit() {
        return RETROFIT.get();
    }

    public static <T> T createService(Class<T> cls) {
        final Retrofit retrofit = getRetrofit();
        return retrofit.create(cls);
    }

}
