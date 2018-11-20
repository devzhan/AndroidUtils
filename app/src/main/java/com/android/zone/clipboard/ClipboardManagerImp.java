package com.android.zone.clipboard;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public final class ClipboardManagerImp extends ClipboardManagerCompat {
    private static final String TAG = "ClipboardManager";
    private ClipboardManager mClipboardManager;

    ClipboardManager.OnPrimaryClipChangedListener mOnPrimaryClipChangedListener = new ClipboardManager.OnPrimaryClipChangedListener() {
        @Override
        public void onPrimaryClipChanged() {
            notifyPrimaryClipChanged();
        }
    };


    public ClipboardManagerImp(Context context) {
        mClipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
    }

    @Override
    public void addPrimaryClipChangedListener(OnPrimaryClipChangedListener listener) {
        super.addPrimaryClipChangedListener(listener);
        synchronized (mPrimaryClipChangedListeners) {
            if (mPrimaryClipChangedListeners.size() == 1) {
                mClipboardManager.addPrimaryClipChangedListener(mOnPrimaryClipChangedListener);
            }
        }
    }

    @Override
    public void removePrimaryClipChangedListener(OnPrimaryClipChangedListener listener) {
        super.removePrimaryClipChangedListener(listener);
        synchronized (mPrimaryClipChangedListeners) {
            if (mPrimaryClipChangedListeners.size() == 0) {
                mClipboardManager.removePrimaryClipChangedListener(mOnPrimaryClipChangedListener);
            }
        }
    }

    @Override
    public String getCopyText() {
        String content  = "";
        if (mClipboardManager.hasPrimaryClip()&&mClipboardManager.getPrimaryClip().getItemCount()>0){
            ClipData clipData = mClipboardManager.getPrimaryClip();
            ClipData.Item item = clipData.getItemAt(0);
            if (item.getText()!=null){
                content = item.getText().toString();
            }
        }
        return content;
    }
}
