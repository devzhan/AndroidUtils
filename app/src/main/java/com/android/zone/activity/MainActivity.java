package com.android.zone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Printer;
import android.view.View;
import android.widget.Button;

import com.android.zone.R;
import com.android.zone.eventbus.EvenBusSetupActivity;
import com.android.zone.lifecycle.LifecycleActivity;
import com.android.zone.retrofit.RetrofitActivity;
import com.android.zone.rxandroid.RxAndroidActivity;
import com.android.zone.utils.LogMonitor;
import com.android.zone.utils.LogUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private Button mButtonWebView;
    private Button mButtonCountDownLatch;
    private Button mButtonEventBus;
    private Button mButtonResult;
    private Button mButton;
    private Button mButtonRxAndroid;
    private Button mButtonRetrofit;
    private Button mButtonLifeCycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogUtil.i(TAG,"method is :%s","onCreate");
        //创建网络请求
        initView();
        handle();
        from();
    }

    private void from() {
        Integer from [] = {1,2,3,4,5,6,7,8,9};
        Observable observable = Observable.fromArray(from);
        observable.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer o) throws Exception {
                LogUtil.i(TAG,"accept is :%s",o.toString());
            }
        });



    }

    private void handle() {
        final Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {

            /**
             * Called for each Observer that subscribes.
             *
             * @param emitter the safe emitter instance, never null
             * @throws Exception on error
             */
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        });
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "对onSubscribe事件作出响应");

            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "对onNext事件作出响应"+integer.intValue());

            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "对onError事件作出响应");

            }

            @Override
            public void onComplete() {
                Log.d(TAG, "对onComplete事件作出响应");
            }
        };
        observable.subscribe(observer);
        Observable.create(new ObservableOnSubscribe<String>(){

            /**
             * Called for each Observer that subscribes.
             *
             * @param emitter the safe emitter instance, never null
             * @throws Exception on error
             */
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("1223");
                emitter.onComplete();
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Log.e(TAG,"s==="+s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void initView() {
        mButtonWebView = findViewById(R.id.bt_wv);
        mButtonWebView.setOnClickListener(this);
        mButtonCountDownLatch = findViewById(R.id.bt_countdownlatch);
        mButtonEventBus = findViewById(R.id.bt_eventbus);
        mButtonEventBus.setOnClickListener(this);
        mButtonCountDownLatch.setOnClickListener(this);
        mButtonResult = findViewById(R.id.bt_result);
        mButtonResult.setOnClickListener(this);
        mButtonRxAndroid = findViewById(R.id.bt_rx);
        mButtonRxAndroid.setOnClickListener(this);
        mButtonRetrofit = findViewById(R.id.bt_retrofit);
        mButtonRetrofit.setOnClickListener(this);
        mButtonLifeCycle = findViewById(R.id.bt_lifecycle);
        mButtonLifeCycle.setOnClickListener(this);

        Looper.getMainLooper().setMessageLogging(new Printer() {
            private static final String START = ">>>>> Dispatching";
            private static final String END = "<<<<< Finished";

            @Override
            public void println(String x) {
                if (x.startsWith(START)) {
                    LogMonitor.getInstance().startMonitor();
                }
                if (x.startsWith(END)) {
                    LogMonitor.getInstance().removeMonitor();
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId ==R.id.bt_wv){
            Intent intent = new Intent(MainActivity.this,WebViewActivity.class);
            startActivity(intent);
        }else if (viewId ==R.id.bt_countdownlatch){
            LogUtil.i(TAG,"点击");
            try {
                Thread.sleep(20*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else if (viewId ==R.id.bt_eventbus){
            Intent intent = new Intent(MainActivity.this, EvenBusSetupActivity.class);
            startActivity(intent);
        }else if (viewId== R.id.bt_result){
            Intent i = new Intent(this,OnResultActivity.class);
            startActivityForResult(i,10);
        }else if (viewId== R.id.bt_rx){
            Intent i = new Intent(this, RxAndroidActivity.class);
            startActivity(i);
        }else if (viewId== R.id.bt_retrofit){
            Intent i = new Intent(this, RetrofitActivity.class);
            startActivity(i);
        }else if (viewId== R.id.bt_lifecycle){
            Intent i = new Intent(this, LifecycleActivity.class);
            startActivity(i);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG,"requestCode==="+requestCode+"resultCode==="+resultCode);

    }

    public void print() {
        BufferedReader bufferedReader = null;
        String tmp = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(new File(Environment.getExternalStorageDirectory() + "/anr.log")));
            while ((tmp = bufferedReader.readLine()) != null) {
                Log.i("wangchen", tmp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
