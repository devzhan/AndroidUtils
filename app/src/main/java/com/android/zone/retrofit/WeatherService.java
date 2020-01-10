package com.android.zone.retrofit;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


/**
 * Author: shuangshuang.li
 * Date: 2019/03/26
 */
public interface WeatherService {
    public static final String SCORE_KEY ="api/v1/commonConfig/keys";
    @GET("v2/TAkhjf8d1nlSlspN/121.6544,25.1552/realtime.jsonp?callback=MYCALLBACK")
    Call<Protocol<String>> getWeather();
    @POST(SCORE_KEY)
    @FormUrlEncoded
    Call<Protocol<LanguageItem>> getScoreKeys(@FieldMap Map<String, String> map);





}


