package com.dinodevs.greatfitwatchface;

import android.content.Context;

import com.dinodevs.greatfitwatchface.settings.LoadSettings;
import com.dinodevs.greatfitwatchface.widget.FloorWidget;
import com.dinodevs.greatfitwatchface.widget.MoonPhaseWidget;
import com.dinodevs.greatfitwatchface.widget.SportTodayDistanceWidget;
import com.dinodevs.greatfitwatchface.widget.SportTotalDistanceWidget;
import com.dinodevs.greatfitwatchface.widget.StepsWidget;
import com.huami.watch.watchface.AbstractSlptClock;

import com.dinodevs.greatfitwatchface.widget.BatteryWidget;
import com.dinodevs.greatfitwatchface.widget.HeartRateWidget;
import com.dinodevs.greatfitwatchface.widget.MainClock;
import com.dinodevs.greatfitwatchface.widget.CaloriesWidget;
import com.dinodevs.greatfitwatchface.widget.GreatWidget;
import com.dinodevs.greatfitwatchface.widget.WeatherWidget;


/**
 * Amazfit watch faces
 */

public class GreatFit extends AbstractWatchFace {
    Context context;
    public GreatFit() {
        super();
    }

    @Override
    public void onCreate() {
        context = this.getApplicationContext();

        // Load settings
        LoadSettings settings = new LoadSettings(context);

        this.clock = new MainClock(settings);

        if(settings.isHeartRate()) {
            this.widgets.add(new HeartRateWidget(settings));
        }
        if(settings.isStepsRate()) {
            this.widgets.add(new StepsWidget(settings));
        }
        if(settings.isTodayDistanceRate()) {
            this.widgets.add(new SportTodayDistanceWidget(settings));
        }
        if(settings.isTotalDistanceRate()) {
            this.widgets.add(new SportTotalDistanceWidget(settings));
        }
        if(settings.isCalories()) {
            this.widgets.add(new CaloriesWidget(settings));
        }
        if(settings.isFloor()) {
            this.widgets.add(new FloorWidget(settings));
        }
        if(settings.isBattery()) {
            this.widgets.add(new BatteryWidget(settings));
        }
        if(settings.isWeather()) {
            this.widgets.add(new WeatherWidget(settings));
        }
        if (settings.isMoonPhase()){
            this.widgets.add(new MoonPhaseWidget(settings));
        }
        if(settings.isGreat()) {
            this.widgets.add(new GreatWidget(settings));
        }

        status_bar(settings.status_bar, settings.status_barLeft, settings.status_barTop);

        super.onCreate();
    }

    private void status_bar(boolean isOn, int left, int top){
        // Show it or... show it off screen :P
        if(isOn) {
            notifyStatusBarPosition(
                    (float) left,
                    (float) top
            );
        }else{
            notifyStatusBarPosition(10.0F,10.0F);// not working
        }
    }

    @Override
    protected Class<? extends AbstractSlptClock> slptClockClass() {
        return GreatFitSlpt.class;
    }
}