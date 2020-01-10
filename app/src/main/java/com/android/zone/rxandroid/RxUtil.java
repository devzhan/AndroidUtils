package com.android.zone.rxandroid;


import android.net.Uri;
import android.util.Log;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RxUtil {

    public Observable<String > downloadImage(final String path){
        final OkHttpClient okHttpClient = new OkHttpClient();

        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> emitter) throws Exception {
                if (!emitter.isDisposed()){
                    Request request = new Request.Builder().url(path).build();
                    okHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.e(RxAndroidActivity.TAG,e.getMessage());
                            emitter.onError(e);

                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Log.e(RxAndroidActivity.TAG,"code is ==="+response.code());
                                emitter.onNext(response.body().string());
                                emitter.onComplete();
                        }
                    });

                }
            }

        });
    };
}
