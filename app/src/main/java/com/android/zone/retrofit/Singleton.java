package com.android.zone.retrofit;

/**
 * 单例控制类
 * Created by wenbiao.xie on 2015/12/24.
 */
public abstract class Singleton<T> {
    private T mInstance;
    protected abstract T create();

    public final T get() {
        synchronized (this) {
            if (mInstance == null) {
                mInstance = create();
            }

            return mInstance;
        }
    }
}
