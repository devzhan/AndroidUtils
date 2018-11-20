package com.android.zone.clipboard;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.zone.R;


public class TipViewController implements View.OnClickListener, View.OnTouchListener, ViewContainer.KeyEventHandler {

    private WindowManager mWindowManager;
    private Context mContext;
    private ViewContainer mWholeView;
    private View mContentView;
    private ViewDismissHandler mViewDismissHandler;
    private CharSequence mContent = "";
    private TextView mTextView;
    private boolean hasRemove;

    public TipViewController(Context application, CharSequence content) {
        mContext = application;
        mContent = content;
        mWindowManager = (WindowManager) application.getSystemService(Context.WINDOW_SERVICE);
    }

    public void setViewDismissHandler(ViewDismissHandler viewDismissHandler) {
        mViewDismissHandler = viewDismissHandler;
    }

    public void updateContent(CharSequence content) {
        mContent = content;
        mTextView.setText(mContent);
    }

    public void show() {
        hasRemove = false;
        ViewContainer view = (ViewContainer) View.inflate(mContext, R.layout.layout_tip_clipboard, null);

        // display content
        mTextView = (TextView) view.findViewById(R.id.pop_view_text);
        mTextView.setText(mContent);

        mWholeView = view;
        mContentView = view.findViewById(R.id.pop_view_content_view);

        // event listeners
        mContentView.setOnClickListener(this);
        mWholeView.setOnTouchListener(this);
        mWholeView.setKeyEventHandler(this);
        int w = WindowManager.LayoutParams.MATCH_PARENT;
        int h = WindowManager.LayoutParams.MATCH_PARENT;
        int flags = 0;
        int type = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //解决Android 7.1.1起不能再用Toast的问题（先解决crash）
            if(Build.VERSION.SDK_INT > 24){
                type = WindowManager.LayoutParams.TYPE_PHONE;
            }else{
                type = WindowManager.LayoutParams.TYPE_TOAST;
            }
        } else {
            type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(w, h, type, flags, PixelFormat.TRANSLUCENT);
        layoutParams.gravity = Gravity.TOP;
        layoutParams.alpha = 1;
        mWindowManager.addView(mWholeView, layoutParams);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!hasRemove){
                    autoRemovePoppedViewAndClear();
                }
            }
        },2000);
    }

    @Override
    public void onClick(View v) {
        removePoppedViewAndClear();
    }

    /**
     * 悬浮框定时消失
     */
    private void autoRemovePoppedViewAndClear() {
        hasRemove = true;
        if (mWindowManager != null && mWholeView != null) {
            mWindowManager.removeView(mWholeView);
        }
        if (mContentView!=null){
            mContentView.setOnClickListener(null);
        }
        if (mWholeView!=null){
            mWholeView.setOnTouchListener(null);
            mWholeView.setKeyEventHandler(null);
        }
    }

    private void removePoppedViewAndClear() {
        hasRemove = true;
        // remove view
        if (mWindowManager != null && mWholeView != null) {
            mWindowManager.removeView(mWholeView);
        }

        if (mViewDismissHandler != null) {
            mViewDismissHandler.onViewDismiss(Uri.parse(mContent.toString()));
        }

        // remove listeners
        mContentView.setOnClickListener(null);
        mWholeView.setOnTouchListener(null);
        mWholeView.setKeyEventHandler(null);
    }

    /**
     * touch the outside of the content view, remove the popped view
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        Rect rect = new Rect();
        mContentView.getGlobalVisibleRect(rect);
        if (!rect.contains(x, y)) {
            removePoppedViewAndClear();
        }
        return false;
    }

    @Override
    public void onKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            removePoppedViewAndClear();
        }
    }

    public interface ViewDismissHandler {
        void onViewDismiss(Uri uri);
    }
}
