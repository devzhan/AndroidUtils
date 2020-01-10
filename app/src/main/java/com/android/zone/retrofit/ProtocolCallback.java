package com.android.zone.retrofit;


import com.android.zone.utils.LogUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class ProtocolCallback<T> implements Callback<Protocol<T>>, ProtocolHandler<T>  {
    private static final String TAG = "ProtocolCallback";

    @Override
    public void onResponse(Call<Protocol<T>> call, Response<Protocol<T>> response) {
        if (!response.isSuccessful()) {
            onFailure(0, null);
            return;
        }

        Protocol<T> data = response.body();
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
    public void onFailure(Call<Protocol<T>> call, Throwable t) {
        LogUtil.i(TAG, "onResponse failure err = %d, msg = %s", t, t.getMessage());
        onFailure(0, t.getMessage());
    }



    protected boolean isSuccessful(int data_code) {
        return (data_code == ProtocolError.PROTOCOL_SUCCESS
                || data_code == ProtocolError.PROTOCOL_SUCCESS2
        ||data_code == ProtocolError.PROTOCOL_SUCCESS3);
    }
}

