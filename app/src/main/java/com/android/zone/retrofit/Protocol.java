package com.android.zone.retrofit;

import android.support.annotation.Keep;

/**
 * description:
 * author hui.zhu
 * date 2017/3/6
 * copyright TCL-MIG
 */
@Keep
public class Protocol<T> {
    public int code;
    public String msg;
    public T data;

    @Keep
    public static class Empty{}

}
