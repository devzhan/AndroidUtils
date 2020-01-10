package com.android.zone.rxandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.zone.R;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class RxAndroidActivity extends AppCompatActivity {

    public static final String TAG = "RxAndroidActivity";
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_android);
        textView = findViewById(R.id.tv);

        findViewById(R.id.bt_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download();
            }
        });
        findViewById(R.id.bt_jinshan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    public void download() {
        RxUtil rxUtil = new RxUtil();
        String path = "https://blog.csdn.net/u011315960/category_9289471.html";
        final Observable<String> bytes = rxUtil.downloadImage(path);
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String b) {
                Log.e(TAG, Thread.currentThread().getName());
                Log.e(TAG, "string is :" + b);
                textView.setText(b);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        bytes.subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
        bytes.subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

            }
        });
        Single<String> single = Single.create(new SingleOnSubscribe<String>() {

            /**
             * Called for each SingleObserver that subscribes.
             *
             * @param emitter the safe emitter instance, never null
             * @throws Exception on error
             */
            @Override
            public void subscribe(SingleEmitter<String> emitter) throws Exception {

            }
        });

//订阅观察者SingleObserver
        single.subscribe(new SingleObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(String s) {
                //相当于onNext和onComplete
            }

            @Override
            public void onError(Throwable e) {

            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {


                Observable.create(new ObservableOnSubscribe<String>() {
                    /**
                     * Called for each Observer that subscribes.
                     *
                     * @param emitter the safe emitter instance, never null
                     * @throws Exception on error
                     */
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        Log.e(TAG, "emitter===" + emitter);
                        Log.e(TAG, Thread.currentThread().getName());
                        emitter.onNext("AAAAAAAAA");

                    }
                }).subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.e(TAG, "s===" + s);
                        Log.e(TAG, Thread.currentThread().getName());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
            }
        }).start();
    }


}