package com.android.zone.eventbus;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.zone.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class EventBusSendMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus_send_message);
        findViewById(R.id.bt_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                EventMessage eventMessage = new EventMessage();
//                eventMessage.setName("这是EventBus 发送的消息");
//                EventBus.getDefault().post(eventMessage);
//                finish();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sentMessage();
                    }
                }).start();

            }
        });


    }
    private void sentMessage(){
        EventMessage eventMessage = new EventMessage();
        eventMessage.setName("这是EventBus 发送的消息");
        EventBus.getDefault().post(eventMessage);
        finish();
    }

}
