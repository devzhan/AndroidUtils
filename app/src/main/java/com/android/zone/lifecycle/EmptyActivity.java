package com.android.zone.lifecycle;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.android.zone.R;
import com.android.zone.utils.LogUtil;

public class EmptyActivity extends Activity implements LifecycleOwner, View.OnClickListener {

    private LifecycleRegistry lifecycleRegistry;
    private static final String TAG = "LifeObserver";
    private MutableLiveData<Integer> mutableLiveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
        lifecycleRegistry = new LifecycleRegistry(this);
        lifecycleRegistry.addObserver(new LifeObserver());
        lifecycleRegistry.markState(Lifecycle.State.CREATED);

    }

    /**
     * Returns the Lifecycle of the provider.
     *
     * @return The lifecycle of the provider.
     */
    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return lifecycleRegistry;
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.i(TAG,"onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.i(TAG,"onPause");
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

    }
}
