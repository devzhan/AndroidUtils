package com.android.zone.concurrency;

import com.android.zone.utils.LogUtil;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class Statistic implements Runnable {
    private static final String TAG ="CyclicBarrier";
    private CyclicBarrier mCyclicBarrier;

    public Statistic(CyclicBarrier cyclicBarrier) {
        mCyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        LogUtil.i(TAG, "mCyclicBarrier:  " + Thread.currentThread().getName() + "开始" + mCyclicBarrier.getNumberWaiting());
        try {
            long duration = (long) (Math.random() * 20);
            TimeUnit.SECONDS.sleep(duration);
            mCyclicBarrier.await();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LogUtil.i(TAG, "mCyclicBarrier:  " + Thread.currentThread().getName() + "结束");
    }
}
