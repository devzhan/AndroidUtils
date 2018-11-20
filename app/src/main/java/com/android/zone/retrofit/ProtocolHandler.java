package com.android.zone.retrofit;


public interface ProtocolHandler<T> {

    void onSuccess(Protocol<T> protocol);

    void onFailure(int fail_code, String msg);
}
