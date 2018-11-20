package com.android.zone.retrofit;

import android.support.annotation.Keep;

@Keep
public class Protocol<T> {
    public int code;
    public String msg;
    public T data;

    @Keep
    public static class Empty{}

}
