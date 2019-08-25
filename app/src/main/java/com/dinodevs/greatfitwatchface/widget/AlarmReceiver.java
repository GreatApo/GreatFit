package com.dinodevs.greatfitwatchface.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;

import com.dinodevs.greatfitwatchface.GreatFit;
import com.dinodevs.greatfitwatchface.data.DataType;
import com.dinodevs.greatfitwatchface.data.Time;

import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {
    public static final int GREATWIDGET_CODE = 1234;
    public static final String REQUEST_CODE = "code";
    private static final String TAG = "DinoDevs-GreatFit";
    GreatWidget greatWidget;

    public void onReceive(Context context, Intent intent) {
        if (intent.getIntExtra(REQUEST_CODE, 0) != GREATWIDGET_CODE) {
            Log.e(TAG, "AlarmReceiver unknown request code!");
        } else {
            updateGreatWidget(context);
        }
    }

    private void updateGreatWidget(Context context) {
        Calendar now = Calendar.getInstance();
        int hours = now.get(Calendar.HOUR_OF_DAY);
        int minutes = now.get(Calendar.MINUTE);
        int seconds = now.get(Calendar.SECOND);
        aquireWakeLock(context);
        Log.i(TAG, String.format("AlarmReceiver onReceive %02d:%02d:%02d ", now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), now.get(Calendar.SECOND)));
        this.greatWidget = GreatFit.getGreatWidget();
        GreatWidget greatWidget = this.greatWidget;
        if (greatWidget != null) {
            //greatWidget.onDataUpdate(DataType.TIME, new Time(seconds, minutes, hours, -1));
            greatWidget.scheduleUpdate();
        } else {
            Log.e(TAG, "AlarmReceiver error getting widget instance!");
        }
    }

    private void aquireWakeLock(Context context) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        if (pm != null) {
            pm.newWakeLock(6, "GreatFit:GreatWidget").acquire(1000);
        } else {
            Log.e(TAG, "AlarmReceiver null PowerManager!");
        }
    }
}
