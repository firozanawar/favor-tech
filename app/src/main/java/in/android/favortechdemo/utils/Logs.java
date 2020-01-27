package in.android.favortechdemo.utils;

import android.util.Log;

import in.android.favortechdemo.BuildConfig;

/**
 * The generic log class for application wide logging
 * Author: Inzimam Islam
 * Created: 23/01/2020
 * Modified: 24/01/2020
 */
public class Logs {

    public static final String TAG = "tag-ilma";
    private static final boolean ENABLE_LOGS = BuildConfig.DEBUG;

    private Logs() {
    }

    @SuppressWarnings("unused")
    public static void v(String tag, String msg) {
        if (ENABLE_LOGS) {
            Log.v(tag, msg);
        }
    }

    @SuppressWarnings("unused")
    public static void v(String tag, String msg, Exception e) {
        if (ENABLE_LOGS) {
            Log.v(tag, msg, e);
        }
    }

    @SuppressWarnings("unused")
    public static void v(String tag, String msg, OutOfMemoryError e) {
        if (ENABLE_LOGS) {
            Log.v(tag, msg, e);
        }
    }

    @SuppressWarnings("unused")
    public static boolean getIsLogsEnabled() {
        return ENABLE_LOGS;
    }

    @SuppressWarnings("unused")
    public static void reportException(Exception e) {
        if (ENABLE_LOGS) {
            Log.e("Exception", e.toString(), e);
        }
    }
}
