package com.android.zone.retrofit;

/**
 */

public abstract class ResponseHandler<T> implements ProtocolHandler<T> {

    @Override
    public void onSuccess(Protocol<T> protocol) {
        success(protocol.data);
    }

    @Override
    public void onFailure(int fail_code, String msg) {
        fail(fail_code, msg);
    }

    protected abstract void success(T data);
    protected abstract void fail(int err, String extra_cause);
}
