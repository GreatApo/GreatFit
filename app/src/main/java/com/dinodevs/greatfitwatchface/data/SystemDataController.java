package com.dinodevs.greatfitwatchface.data;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SystemDataController {
    private static final String WEATHER_KEY = "WeatherInfo";

    @Nullable
    private static SystemDataController sInstance;

    private final List<SystemDataChangeListener> mListeners = new ArrayList<>();
    private final ContentObserver mContentObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange, Uri uri) {
            for (SystemDataChangeListener listener : mListeners) {
                listener.onWeatherChanged();
            }
        }
    };

    public interface SystemDataChangeListener {
        void onWeatherChanged();
    }

    public static void addSystemDataChangeListener(Context context,
            SystemDataChangeListener listener) {
        if (sInstance == null) {
            sInstance = new SystemDataController(context);
        }
        sInstance.mListeners.add(listener);
    }

    public static void removeSystemDataChangeListener(SystemDataChangeListener listener) {
        if (sInstance == null) {
            return;
        }
        sInstance.mListeners.remove(listener);
    }

    private SystemDataController(Context context) {
        // Use Settings.System.CONTENT_URI instead of WEATHER_KEY if you interested in catching
        // notification about several events. Make filtering in ContentObserver.onChange.
        context.getApplicationContext().getContentResolver().registerContentObserver(Uri.parse(
                "content://settings/system/" + WEATHER_KEY), false, mContentObserver);
    }
}
