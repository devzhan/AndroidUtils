package com.android.zone.concurrency;

import com.android.zone.utils.LogUtil;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchProject implements Runnable {
    private static final String TAG = "CountDownLatch";
    private CountDownLatch mCountDownLatch;

    public CountDownLatchProject(int number) {
        mCountDownLatch = new CountDownLatch(number);
    }
    public void complete(String name){
        LogUtil.i(TAG, name+"完成---------");
        mCountDownLatch.countDown();
    }
    @Override
    public void run() {
        LogUtil.i(TAG, "项目开始---------");
        try {
            mCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LogUtil.i(TAG, "项目上线---------其他业务进行");

    }
}
