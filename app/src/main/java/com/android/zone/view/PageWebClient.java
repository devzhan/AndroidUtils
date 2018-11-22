package com.android.zone.view;

import android.graphics.Bitmap;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.zone.utils.LogUtil;

/**
 * WebViewClient主要帮助WebView处理各种通知、请求事件的
 */
public class PageWebClient extends WebViewClient {
    private static final String TAG = "PAGEVIEW";
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);//当前webview内的网页任然在该webview 跳转
        return true;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        LogUtil.i(TAG,"onPageStarted");
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        LogUtil.i(TAG,"onPageFinished");
    }

    @Override
    public boolean onRenderProcessGone(WebView view, RenderProcessGoneDetail detail) {
        LogUtil.i(TAG,"onRenderProcessGone");
        return super.onRenderProcessGone(view, detail);
    }

    @Override
    public void onPageCommitVisible(WebView view, String url) {
        LogUtil.i(TAG,"onPageCommitVisible");
        super.onPageCommitVisible(view, url);
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        LogUtil.i(TAG,"onLoadResource url is :%s",url);
        view.loadUrl("JavaScript:function mFunc(){[].forEach.call(document.querySelectorAll('div[id^=\"kp_box\"]'), function(div) { div.parentElement.remove()});}mFunc();");

        super.onLoadResource(view, url);
    }
}
