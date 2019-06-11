package com.android.zone.concurrency;

import com.android.zone.utils.LogUtil;

import java.util.concurrent.TimeUnit;

public class CountDownLatchdevalper implements Runnable {
    private static final String TAG = "CountDownLatch";
    private CountDownLatchProject mCountDownLatchProject;

    public CountDownLatchdevalper(CountDownLatchProject countDownLatchProject ) {
        this.mCountDownLatchProject = countDownLatchProject;
    }

    @Override
    public void run() {

        LogUtil.i(TAG, "" + Thread.currentThread().getName() + "开始开发.............");
        try {
            long duration = (long) (Math.random() * 20);
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mCountDownLatchProject.complete(Thread.currentThread().getName());

    }
}
