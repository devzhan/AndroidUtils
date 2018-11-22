package com.android.zone.view;


import android.graphics.Bitmap;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.android.zone.utils.LogUtil;

/**
 * WebChromeClient主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度等比如
 */
public class PageChromeClient extends WebChromeClient {
    private static final String TAG = "PAGEVIEW";
    @Override
    public void onReceivedIcon(WebView view, Bitmap icon) {
        super.onReceivedIcon(view, icon);
    }
    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        LogUtil.i(TAG,"onReceivedTitle title is :%s",title);
    }


}
