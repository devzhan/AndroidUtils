package com.android.zone.utils;


import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;

public class LogUtil {


    private static boolean debug = true;            // 是否记录日志

    public static boolean isDebug() {
        return debug;
    }


    private static String buildWholeMessage(String format, Object... args) {
        if (args == null || args.length == 0)
            return format;

        String msg = String.format(format, args);
        return msg;
    }

    public static void d(String tag, String format, Object... args) {
        if (debug) {
            Log.d(tag, buildWholeMessage(format, args));
        }
    }

    public static void i(String tag, String format, Object... args) {
        if (debug) {
            Log.i(tag, buildWholeMessage(format, args));
        }
    }

    public static void e(String tag, String format, Object... args) {
        if (debug) {
            Log.e(tag, buildWholeMessage(format, args));
        }
    }

    public static void e(String tag, Throwable e) {
        if (debug) {
            Log.e(tag, getStackTraceString(e));
        }
    }

    public static void v(String tag, String format, Object... args) {
        if (debug) {
            Log.v(tag, buildWholeMessage(format, args));
        }
    }

    public static void w(String tag, String format, Object... args) {
        if (debug) {
            Log.w(tag, buildWholeMessage(format, args));
        }
    }

    private static String getStackTraceString(Throwable tr) {
        if (tr == null) {
            return "";
        }
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tr.printStackTrace(pw);
        return sw.toString();
    }
}