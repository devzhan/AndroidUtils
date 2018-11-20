package com.android.zone.retrofit;

/**
 */

public abstract class SimpleResponseHandler extends ResponseHandler<Protocol.Empty> {

    @Override
    protected void success(Protocol.Empty data) {
        success();
    }

    protected abstract void success();

}
