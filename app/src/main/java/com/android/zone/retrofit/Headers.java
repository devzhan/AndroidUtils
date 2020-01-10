package com.android.zone.retrofit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;


import com.android.zone.utils.LogUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * @author  by wenbiao.xie on 2017/3/3.
 */

public class Headers {

    private final static String TAG = "Headers";
    private final static String TOKEN_MAGIC = "hiyocus_tstream";

    public static void init(Context context) {
//        VERSION_CODE = ContextUtils.getVersionCode(context);
//        CHANNEL = ContextUtils.getMetaData(context, "CHANNEL");
//        IMSI = TelephonyManagerUtil.getSubscriberId(context);
//        IMEI = TelephonyManagerUtil.getIMEI(context);
//        MAC = getDeviceMac(context);
//
//        if (CHANNEL == null)
//            CHANNEL = "";
//
//        if (IMSI == null)
//            IMSI = "";
//
//        if (IMEI == null)
//            IMEI = "";
//
//        if (MAC == null)
//            MAC = "";

        String MAC = "";
        String IMEI = "";
        String IMSI = "";
        String CHANNEL = "";
        LogUtil.d(TAG, "channel: %s, imsi: %s, imei: %s, mac: %s", CHANNEL, IMSI, IMEI, MAC);
    }

//    private static String getDeviceMac(Context context) {
//        String mac = PrefsUtils.loadPrefString(context, PREF_DEVICE_MAC, null);
//        if (mac == null) {
//            mac = SystemUtils.getMacAddress(context);
//            if (!TextUtils.isEmpty(mac)) {
//                PrefsUtils.savePrefString(context, PREF_DEVICE_MAC, mac);
//            }
//        }
//
//        return mac;
//    }

    /**
     * 用户登录退出时，需要同步更新网络侧的用户信息，该方法负责同步用户信息
     * 当用户登录成功后，更新最新的用户ID与来源，如果用户登出，将清除相关信息
     * @param uid 用户ID
     * @param from 用户来源
     */
    public static void onAuthernicationUpdated(String uid, String from) {
        String UID = uid == null ? "" : uid;
        String USER_FROM = from == null ? "" : from;

        LogUtil.d(TAG, "UID: %s, from: %s", UID, USER_FROM);
    }


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


    static Map<String,String> createCommonHeaders(Context context) {

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


    /**
     * get time zone
     * @return
     */
    public static String getTimeZone(){
        TimeZone tz = TimeZone.getDefault();
        return  tz.getDisplayName(false, TimeZone.SHORT);
    }
}
