package com.android.zone.eventbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.zone.R;
import com.android.zone.utils.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class EvenBusSetupActivity extends AppCompatActivity {

    private static final String TAG = "EvenBusSetupActivity";
    private TextView mTextReceive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_even_bus_setup);
        mTextReceive = findViewById(R.id.tv_message);
        mTextReceive.setText("接收到的消息是：");
        findViewById(R.id.bt_to_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EvenBusSetupActivity.this,EventBusSendMessageActivity.class);
                startActivity(intent);
            }
        });
        registerEventBus();
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onEventMessageReceived(EventMessage eventMessage) {
        LogUtil.i(TAG,"onMessageEventPost message name is: %s",eventMessage.getName());
        mTextReceive.setText("接收到的消息是："+eventMessage.getName());
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterEventBus();
    }
    private void registerEventBus() {
        EventBus.getDefault().register(this);
    }
    private void unregisterEventBus() {
        EventBus.getDefault().unregister(this);
    }
}
