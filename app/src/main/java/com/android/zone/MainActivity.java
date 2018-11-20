package com.android.zone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.zone.retrofit.Connections;
import com.android.zone.utils.LogUtil;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogUtil.i(TAG,"method is :%s","onCreate");
        //创建网络请求
//        Connections.createService()
    }
}
