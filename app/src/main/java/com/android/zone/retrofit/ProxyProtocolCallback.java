package com.android.zone.retrofit;


public class ProxyProtocolCallback<T> extends ProtocolCallback<T> {

    private final ProtocolHandler<T> mHandler;

    public ProxyProtocolCallback(ProtocolHandler<T> handler) {
        this.mHandler = handler;
    }

    @Override
    public void onSuccess(Protocol<T> protocol) {
        if (mHandler != null)
            mHandler.onSuccess(protocol);
    }

    @Override
    public void onFailure(int fail_code, String msg) {
        if (mHandler != null)
            mHandler.onFailure(fail_code, msg);
    }
}
