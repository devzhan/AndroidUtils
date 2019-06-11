package com.android.zone.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.android.zone.R;
import com.android.zone.concurrency.CountDownLatchProject;
import com.android.zone.concurrency.CountDownLatchdevalper;
import com.android.zone.concurrency.Statistic;
import com.android.zone.utils.LogUtil;

import java.util.concurrent.CyclicBarrier;

public class CountDownLatchActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);
        findViewById(R.id.bt_countdownlatch).setOnClickListener(this);
        findViewById(R.id.bt_cyclicbarrier).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId==R.id.bt_countdownlatch){
            CountDownLatchProject project = new CountDownLatchProject(10);
            new Thread(project).start();
            for (int i = 0; i < 10; i++) {
                CountDownLatchdevalper count = new CountDownLatchdevalper(project);
                new Thread(count).start();
            }
        }else if (viewId == R.id.bt_cyclicbarrier){
            CyclicBarrier cyclicBarrier = new CyclicBarrier(3, new Runnable() {
                @Override
                public void run() {
                    LogUtil.i("CyclicBarrier","前面任务做完，处理接下来的事物");
                }
            });
            for (int i = 0; i < 3; i++) {
                new Thread(new Statistic(cyclicBarrier)).start();

        }
        }
    }
}
