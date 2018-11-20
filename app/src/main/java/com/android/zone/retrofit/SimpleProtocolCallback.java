package com.android.zone.retrofit;

import com.android.zone.utils.LogUtil;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
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

    @Override
    public void onResponse(Response<SimpleProtocol> response, Retrofit retrofit) {
        if (!response.isSuccess()) {
            int err = httpStatusToNetworkError(response.code());
            onFailure(err, null);
            return;
        }

        SimpleProtocol data = response.body();
        if (data == null) {
            onFailure(ProtocolError.PROTOCOL_NONE_BODY, null);
            return;
        }

        if (!isSuccessful(data.code)) {
            onFailure(data.code, data.msg);
            return;
        }

        LogUtil.i(TAG, "onResponse success code = %d, msg = %s", data.code, data.msg);
        onSuccess(data);
    }

    @Override
    public void onFailure(Throwable t) {
        int err = NetworkError.FAIL_UNKNOWN;
        if (t instanceof SocketTimeoutException) {
            err = NetworkError.SOCKET_TIMEOUT;
        }

        else if (t instanceof InterruptedIOException) {
            err = NetworkError.CANCEL;
        }

        else if (t instanceof IOException) {
            if ("Canceled".equals(t.getMessage()))
                err = NetworkError.CANCEL;
            else
                err = NetworkError.FAIL_IO_ERROR;
        }

        LogUtil.i(TAG, "onResponse failure err = %d, msg = %s", err, t.getMessage());
        onFailure(err, t.getMessage());
    }

    protected int httpStatusToNetworkError(int status) {
        return NetworkError.toNetworkErrorWithHttpStatus(status);
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
}
