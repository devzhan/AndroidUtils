package com.android.zone.utils;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;

public class LogMonitor {

    private static LogMonitor sInstance = new LogMonitor();
    private HandlerThread mHandlerThread = new HandlerThread("log");
    private Handler mHandler;

    private LogMonitor() {
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());
    }

    private static Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            StringBuilder sb = new StringBuilder();
            StackTraceElement[] stackTrace = Looper.getMainLooper().getThread().getStackTrace();
            for (StackTraceElement s : stackTrace) {
                sb.append(s.toString() + "\n");
            }
            Log.e("TAG", sb.toString());
        }
    };

    public static LogMonitor getInstance() {
        return sInstance;
    }

    public void startMonitor() {
        mHandler.postDelayed(mRunnable, 1000);
    }

    public void removeMonitor() {
        mHandler.removeCallbacks(mRunnable);
    }


}
