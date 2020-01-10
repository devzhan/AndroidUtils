package com.android.zone.retrofit;


/**
 * Created by wenbiao.xie on 2017/3/14.
 */

public abstract class SimpleResponseHandler extends ResponseHandler<Protocol.Empty> {

    @Override
    public void success(Protocol.Empty data) {
        success();
    }

    public abstract void success();

}
