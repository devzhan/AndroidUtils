package com.android.zone.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class PageWebView extends WebView {
    private static final String TAG = "PAGEVIEW";
    private static final String ANDROID_USERAGENT = "Mozilla/5.0 (Linux; U; " +
            "Android 2.2; en-us; Nexus One Build/FRF91) AppleWebKit/533.1 " +
            "(KHTML, like Gecko) Version/4.0 Mobile Safari/533.1";
    private WebSettings webSettings;
    private PageWebClient pageWebClient;

    public PageWebView(Context context) {
        super(context);
        init();
    }



    public PageWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    private void init() {
        webSettings = getSettings();
        webSettings.setUserAgentString(ANDROID_USERAGENT);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//把html中的内容放大webview等宽的一列中
        webSettings.setJavaScriptEnabled(true);//支持js
        webSettings.setBuiltInZoomControls(true); // 显示放大缩小
        webSettings.setSupportZoom(true); // 可以缩放
        webSettings.setAppCacheEnabled(true); //启用应用缓存
        webSettings.setDomStorageEnabled(true); //启用或禁用DOM缓存。
        webSettings.setDatabaseEnabled(true); //启用或禁用DOM缓存。
        pageWebClient = new PageWebClient();
        setWebViewClient(pageWebClient);



    }


}
