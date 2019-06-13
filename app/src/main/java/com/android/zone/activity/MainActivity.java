package com.android.zone.activity;

import android.content.Intent;
import android.os.Environment;
import android.os.Looper;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Printer;
import android.view.View;
import android.widget.Button;

import com.android.zone.R;
import com.android.zone.retrofit.Connections;
import com.android.zone.utils.LogMonitor;
import com.android.zone.utils.LogUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private Button mButtonWebView;
    private Button mButtonCountDownLatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogUtil.i(TAG,"method is :%s","onCreate");
        //创建网络请求
//        Connections.createService()
        initView();
    }

    private void initView() {
        mButtonWebView = findViewById(R.id.bt_wv);
        mButtonWebView.setOnClickListener(this);
        mButtonCountDownLatch = findViewById(R.id.bt_countdownlatch);
        mButtonCountDownLatch.setOnClickListener(this);
        Looper.getMainLooper().setMessageLogging(new Printer() {
            private static final String START = ">>>>> Dispatching";
            private static final String END = "<<<<< Finished";

            @Override
            public void println(String x) {
                if (x.startsWith(START)) {
                    LogMonitor.getInstance().startMonitor();
                }
                if (x.startsWith(END)) {
                    LogMonitor.getInstance().removeMonitor();
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId ==R.id.bt_wv){
            Intent intent = new Intent(MainActivity.this,WebViewActivity.class);
            startActivity(intent);
        }else if (viewId ==R.id.bt_countdownlatch){
            LogUtil.i(TAG,"点击");
            try {
                Thread.sleep(20*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            Intent intent = new Intent(MainActivity.this,CountDownLatchActivity.class);
//            startActivity(intent);
        }
    }

    public void print() {
        BufferedReader bufferedReader = null;
        String tmp = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(new File(Environment.getExternalStorageDirectory() + "/anr.log")));
            while ((tmp = bufferedReader.readLine()) != null) {
                Log.i("wangchen", tmp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
