package com.android.zone.retrofit;

public abstract class ResponseHandler<T> implements ProtocolHandler<T> {

    @Override
    public void onSuccess(Protocol<T> protocol) {
        success(protocol.data);
    }

    @Override
    public void onFailure(int fail_code, String msg) {
        fail(fail_code, msg);
    }

    public abstract void success(T data);
    public abstract void fail(int err, String extra_cause);
}
