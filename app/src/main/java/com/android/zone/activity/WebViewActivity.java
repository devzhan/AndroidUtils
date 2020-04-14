package com.android.zone.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import com.android.zone.R;
import com.android.zone.view.PageWebView;

public class WebViewActivity extends AppCompatActivity {

    private PageWebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        initView();
    }

    private void initView() {
//        String url = "https://blog.csdn.net/lanxingfeifei/article/details/52045082";
        String url = "http://music.163.com/m/song?id=1400256289&market=baiduqk";

        mWebView = findViewById(R.id.webview);
        mWebView.loadUrl(url);
        int a =1^2;
        Log.i("AA","a==="+a);
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()){
            mWebView.goBack();
        }else {
            super.onBackPressed();
        }
    }
}
