package com.android.zone.lifecycle;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.zone.R;
import com.android.zone.utils.LogUtil;

public class LifecycleActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LifeObserver";
    private Button mButtonEmpty;
    private Button mButtonPost;
    private MutableLiveData mutableLiveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecycle);
        mButtonEmpty = findViewById(R.id.bt_empty);
        mButtonEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId()==R.id.bt_empty){
                    Intent i = new Intent(LifecycleActivity.this,EmptyActivity.class);
                    startActivity(i);
                }
            }
        });
        getLifecycle().addObserver(new LifeObserver());
        mButtonPost = findViewById(R.id.bt_post);
        mButtonPost.setOnClickListener(this);
        mutableLiveData = new MutableLiveData<>();

        mutableLiveData.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                LogUtil.i(TAG,"onChanged====="+integer);
            }
        });
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
        if (v.getId()==R.id.bt_post){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int number =0;
                    while (number<5){
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        number++;
                        mutableLiveData.postValue(number);
                        LogUtil.i(TAG,"post=====");

                    }
                }
            }).start();
        }
    }
}
