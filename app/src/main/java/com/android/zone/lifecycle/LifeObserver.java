package com.android.zone.lifecycle;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

import com.android.zone.utils.LogUtil;

public class LifeObserver implements LifecycleObserver {
    private static final String TAG = "LifeObserver";


    // 使用注解  @OnLifecycleEvent 来表明该方法需要监听指定的生命周期事件
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void resumeListener() {
        LogUtil.i(TAG,"resumeListener");
    }
    // 使用注解  @OnLifecycleEvent 来表明该方法需要监听指定的生命周期事件
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void pauseListener() {
        LogUtil.i(TAG,"pauseListener");
    }

}
