package com.android.zone.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.zone.R;
import com.android.zone.retrofit.Connections;
import com.android.zone.utils.LogUtil;

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
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId ==R.id.bt_wv){
            Intent intent = new Intent(MainActivity.this,WebViewActivity.class);
            startActivity(intent);
        }else if (viewId ==R.id.bt_countdownlatch){
            Intent intent = new Intent(MainActivity.this,CountDownLatchActivity.class);
            startActivity(intent);
        }
    }
}
