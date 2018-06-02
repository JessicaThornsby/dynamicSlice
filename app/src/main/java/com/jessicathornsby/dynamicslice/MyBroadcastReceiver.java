package com.jessicathornsby.dynamicslice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static com.jessicathornsby.dynamicslice.MainActivity.clickCount;
import static com.jessicathornsby.dynamicslice.MainActivity.updateClickCount;

public class MyBroadcastReceiver extends BroadcastReceiver {

    public static String ACTION_CHANGE_COUNT = "com.jessicathornsby.slicetesting.ACTION_CHANGE_COUNT";
    public static String EXTRA_COUNT_VALUE = "com.jessicathornsby.slicetesting.EXTRA_COUNT_VALUE";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (ACTION_CHANGE_COUNT.equals(action) && intent.getExtras() != null) {
            int newValue = intent.getExtras().getInt(EXTRA_COUNT_VALUE, clickCount);
            updateClickCount(context, newValue);
        }
    }

}
