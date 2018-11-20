package com.android.zone.retrofit;


import com.android.zone.utils.LogUtil;

import retrofit.Call;

/**
 * Created by wenbiao.xie on 2017/3/21.
 */

public class ChainCall {
    private final static String TAG = "ChainCall";

    private static final int DEFAULT_SIZE = 4;
    private static final int MAX_RETRY_TIMES = 3;

    private Call[] mCalls;
    private ResponseHandler[] mHandlers;
    private ChainCall.Callback mCallback;
    /**
     * The number of elements or the size of the vector.
     */
    protected int mCallCount;
    private volatile boolean mExecuting;
    private int mExecuteIndex;
    private boolean mRetry;
    private int mLastRetryIndex = -1;
    private int mRetryTimes = 0;

    public ChainCall() {
        mCalls = new Call[DEFAULT_SIZE];
        mHandlers = new ResponseHandler[DEFAULT_SIZE];
        mCallCount = 0;
        mExecuteIndex = -1;
    }

    public <T> void enqueue(Call<Protocol<T>> call, ResponseHandler<T> handler) {
        if (isRunning()) {
            throw new IllegalStateException("cannot enqueue any call while executing");
        }

        if (mCalls.length == mCallCount) {
            growByHalf();
        }

        mCalls[mCallCount] = call;
        mHandlers[mCallCount] = handler;
        mCallCount++;
    }

    private <E> E[] newElementArray(int size) {
        return (E[]) new Object[size];
    }

    boolean isRunning() {
        return mExecuting;
    }

    public void setRetry(boolean retry) {
        this.mRetry = retry;
        if (retry) {
            mLastRetryIndex = -1;
            mRetryTimes = 0;
        }
    }

    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    public void execute(Callback callback) {
        if (mExecuting) {
            LogUtil.w(TAG, "already executing, ignore it!!");
            return;
        }

        if (mCallCount == 0 || mExecuteIndex >= mCallCount-1) {
            throw new IllegalStateException("no calls to execute or index outflow!");
        }

        mExecuting = true;
        setCallback(callback);
        nextCall();
    }

    private void nextCall() {
        final int index = ++mExecuteIndex;
        Call call = mCalls[index];
        ResponseHandler handler = mHandlers[index];
        call.enqueue(new ProxyProtocolCallback(new WrappedHandler(handler)));
    }

    private void doCall(int index) {
        Call call = mCalls[index];
        ResponseHandler handler = mHandlers[index];
        call.enqueue(new ProxyProtocolCallback(new WrappedHandler(handler)));
    }

    private void callSuccess() {
        LogUtil.v(TAG, "callSuccess");
        // all calls success
        if (mExecuteIndex >= mCallCount -1) {

            if (mCallback != null) {
                mCallback.success();
            }
            return;
        }

        nextCall();
    }

    private void callFailed(int err, String extra_cause) {
        LogUtil.v(TAG, "callFailed err=%d, cause=%s", err, extra_cause);
        if (mRetry) {
            if (mLastRetryIndex != mExecuteIndex)
                mRetryTimes = 0;

            mRetryTimes++;
            mLastRetryIndex = mExecuteIndex;

            if (mRetryTimes < MAX_RETRY_TIMES) {
                LogUtil.w(TAG, "retry call: %d, times: %d", mLastRetryIndex, mRetryTimes);
                doCall(mLastRetryIndex);
                return;
            }
        }

        if (mCallback != null) {
            mCallback.fail(mExecuteIndex, err, extra_cause);
        }
    }

    /**
     * JIT optimization
     */
    private void growByHalf() {
        int capacity = mCalls.length;
        int adding = capacity >> 1;

        Call[] newCalls = newElementArray(capacity + adding);
        System.arraycopy(mCalls, 0, newCalls, 0, capacity);
        mCalls = newCalls;

        ResponseHandler[] newHandlers = newElementArray(capacity + adding);
        System.arraycopy(mHandlers, 0, newHandlers, 0, capacity);
        mHandlers = newHandlers;
    }

    private class WrappedHandler<T> extends ResponseHandler<T> {

        final ResponseHandler<T> target;
        WrappedHandler(ResponseHandler<T> handler) {
            this.target = handler;
        }

        @Override
        protected void success(T data) {
            this.target.success(data);
            callSuccess();
        }

        @Override
        protected void fail(int err, String extra_cause) {
            this.target.fail(err, extra_cause);
            callFailed(err, extra_cause);
        }
    }

    public interface Callback {
        void success();
        void fail(int indexCall, int err, String msg);
    }

}
