package com.miui.cxmjava.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

public class HomeKeyUtil {

    private static final String TAG = "HomeKeyUtil";

    static final String SYSTEM_DIALOG_REASON_KEY = "reason";
    static final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";
    private static BroadcastReceiver sHomeReceiver = null;

    /**
     * 添加home的广播
     */
    public static void registerHomeKeyReceiver(Context context, final HomeKeyListener listener) {
        Log.d(TAG, "注册home的广播");
        sHomeReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                homeFinish(intent, context, listener);
            }
        };
        final IntentFilter homeFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);

        context.registerReceiver(sHomeReceiver, homeFilter);
    }

    /**
     * 注销home的广播
     *
     * @param context
     */
    public static void unregisterHomeKeyReceiver(Context context) {
        Log.d(TAG, "销毁home的广播");
        if (null != sHomeReceiver) {
            context.unregisterReceiver(sHomeReceiver);
            sHomeReceiver = null;
            Log.d(TAG, "已经注销了，不能再注销了");
        }
    }

    private static void homeFinish(Intent intent, Context context, HomeKeyListener listener) {

        String action = intent.getAction();

        if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {

            String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);

            if (reason != null && reason.equals(SYSTEM_DIALOG_REASON_HOME_KEY)) {

                if (listener != null) {
                    listener.homeKey();
                }
            }
        }
    }

    //回调接口， 当然可以自己自行处理
    public interface HomeKeyListener {
        void homeKey();
    }
}
