package com.android.zone.retrofit;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 */

public class Headers {

    private static final String PREF_DEVICE_MAC = "DEVICE_MAC_CACHE";
    private final static String TAG = "Headers";
    private final static String TOKEN_MAGIC = "hiyocus_tstream";
    private static String MAC = "";
    private static String IMEI = "";
    private static String IMSI = "";
    private static String CHANNEL = "";
    private static int VERSION_CODE = 0;

    private static String UID = "";
    private static String USER_FROM = "";





    public static String valueEncoded(String value) {
        if (TextUtils.isEmpty(value))
            return "";

        String newValue = value.replace("\n", "");
        if (isASCII(newValue)) {
            return newValue;
        }

        try {
            return URLEncoder.encode(newValue, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    private static boolean isASCII(String text) {
        for (int i = 0, length = text.length(); i < length; i++) {
            char c = text.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                return false;
            }
        }

        return true;
    }


    public static Map<String,String> createCommonHeaders() {

//        long time = System.currentTimeMillis();
//        int versioncode = VERSION_CODE;
//        final String mac = MAC;
//        String device = TelephonyManagerUtil.getDeviceId(context);
//        String pkg = context.getPackageName();
//
        Map<String, String> results = new HashMap<>();
//        results.put("model", valueEncoded(Build.MODEL));
//        results.put("osVersion", Build.VERSION.SDK_INT + "");
//        results.put("channel", CHANNEL);
//        results.put("networkType", getNetworkType(context));
//        results.put("packagename", pkg);
//        results.put("appName", pkg);
//        results.put("timezone", valueEncoded(getTimeZone()));
//        results.put("deviceId", device);
//        results.put("time", time + "");
//        results.put("versionCode", versioncode + "");
//        results.put("imsi", IMSI);
//        results.put("imei", IMEI);
//        results.put("mac", mac==null?"":mac);
//        String token = calcToken(device, time);
//        results.put("token", token.toLowerCase());
//
//        results.put("uid", UID);
//        results.put("userFrom", USER_FROM);
        return results;
    }


}
