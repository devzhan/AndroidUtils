package com.android.zone.retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.android.zone.R;
import com.android.zone.utils.LogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitActivity extends AppCompatActivity {
    private String TAG = "RetrofitActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        getWeather();
    }

    private void getWeather() {
        final WeatherService weatherService = RetrofitUtils.getInstance().createService(WeatherService.class);
//        Call<Protocol<String>> call= weatherService.getWeather();
//        call.enqueue(new Callback<Protocol<String>>() {
//            @Override
//            public void onResponse(Call<Protocol<String>> call, Response<Protocol<String>> response) {
//                LogUtil.e(TAG,"onResponse==="+response.toString());
//            }
//
//            @Override
//            public void onFailure(Call<Protocol<String>> call, Throwable t) {
//                LogUtil.e(TAG,"onFailure==="+t.toString());
//
//            }
//        });

        Locale locale = getResources().getConfiguration().locale;
        StringBuilder sbl = new StringBuilder(locale.getLanguage());
        ArrayList<String> pars = new ArrayList<>();
        String language = "";
        if (sbl != null) {
            language = sbl.toString();
        }
        ArrayList<String> keysList = new ArrayList<>();
        keysList.add("browser_boost_rate_content");
        keysList.add("browser_boost_rate_title");
        String keys = TextUtils.join("|", keysList);
        pars.add(keys);
        pars.add(language);
        String pkg = "com.hawk.android.browser";
        pars.add(pkg);
        pars.add("#87&$#1@90");
        String par = TextUtils.join("&", pars);
        String sing = MD5Utils.md5(par);
        final Map<String, String> params = new HashMap<>();
        params.put("keys", keys);
        params.put("pkg", pkg);
        params.put("language", language);
        params.put("sign", sing);
        final Call<Protocol<LanguageItem>> call = weatherService.getScoreKeys(params);

//        call.enqueue(new ProtocolCallback<LanguageItem>() {
//            @Override
//            public void onSuccess(Protocol<LanguageItem> protocol) {
//
//            }
//
//            @Override
//            public void onFailure(int fail_code, String msg) {
//
//            }
//        });

        Observable.create(new ObservableOnSubscribe<LanguageItem>() {
            /**
             * Called for each Observer that subscribes.
             *
             * @param emitter the safe emitter instance, never null
             * @throws Exception on error
             */
            @Override
            public void subscribe(final ObservableEmitter<LanguageItem> emitter) throws Exception {
                if (!emitter.isDisposed()){
                    call.enqueue(new ProxyProtocolCallback<>(new ResponseHandler<LanguageItem>() {
                        @Override
                        public void success(LanguageItem data) {
                            LogUtil.e(TAG, "success===" + data.toString());
                            emitter.onNext(data);
                            emitter.onComplete();
                        }

                        @Override
                        public void fail(int err, String extra_cause) {
                            LogUtil.e(TAG, "fail===" + extra_cause);
                            emitter.onError(null);
                        }
                    }));
                }
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<LanguageItem>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(LanguageItem languageItem) {
                LogUtil.e(TAG, "onNext===" + languageItem.toString());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }
}
