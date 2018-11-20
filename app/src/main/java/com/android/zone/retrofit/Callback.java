package com.android.zone.retrofit;

/**
 */

public interface Callback<P> {
    void onSuccess();
    void onFailed(int err);
}
