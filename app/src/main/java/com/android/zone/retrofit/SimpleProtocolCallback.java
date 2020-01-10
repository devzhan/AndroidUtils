package com.android.zone.retrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by wenbiao.xie on 2017/3/14.
 */

public class SimpleProtocolCallback implements Callback<SimpleProtocol> {
    private static final String TAG = "SimpleProtocolCallback";
    private final SimpleResponseHandler mHandler;
    public SimpleProtocolCallback() {
        this(null);
    }
    public SimpleProtocolCallback(SimpleResponseHandler handler) {
        this.mHandler = handler;
    }




    protected boolean isSuccessful(int data_code) {
        return (data_code == ProtocolError.PROTOCOL_SUCCESS
                || data_code == ProtocolError.PROTOCOL_SUCCESS2);
    }

    protected void onSuccess(SimpleProtocol protocol) {
        if (mHandler != null)
            mHandler.success();
    }

    protected void onFailure(int fail_code, String msg) {
        if (mHandler != null)
            mHandler.fail(fail_code, msg);
    }

    @Override
    public void onResponse(Call<SimpleProtocol> call, Response<SimpleProtocol> response) {
        if (!response.isSuccessful()) {
            onFailure(0, null);
            return;
        }

        SimpleProtocol data = response.body();
        onSuccess(data);
    }

    @Override
    public void onFailure(Call<SimpleProtocol> call, Throwable t) {

        onFailure(0, t.getMessage());
    }
}
