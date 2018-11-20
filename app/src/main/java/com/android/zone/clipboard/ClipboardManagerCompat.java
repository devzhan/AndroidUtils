package com.android.zone.clipboard;

import android.content.Context;

import java.util.ArrayList;

public abstract class ClipboardManagerCompat {

    protected final ArrayList<OnPrimaryClipChangedListener> mPrimaryClipChangedListeners
            = new ArrayList<OnPrimaryClipChangedListener>();

    public static ClipboardManagerCompat create(Context context) {
        return new ClipboardManagerImp(context);
    }

    public void addPrimaryClipChangedListener(OnPrimaryClipChangedListener listener) {
        synchronized (mPrimaryClipChangedListeners) {
            mPrimaryClipChangedListeners.add(listener);
        }
    }

    protected final void notifyPrimaryClipChanged() {
        synchronized (mPrimaryClipChangedListeners) {
            for (int i = 0; i < mPrimaryClipChangedListeners.size(); i++) {
                mPrimaryClipChangedListeners.get(i).onPrimaryClipChanged();
            }
        }
    }

    public void removePrimaryClipChangedListener(OnPrimaryClipChangedListener listener) {
        synchronized (mPrimaryClipChangedListeners) {
            mPrimaryClipChangedListeners.remove(listener);
        }
    }

    public abstract String getCopyText();

    public interface OnPrimaryClipChangedListener {
        void onPrimaryClipChanged();
    }
}
