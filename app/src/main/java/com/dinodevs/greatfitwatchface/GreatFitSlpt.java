package com.dinodevs.greatfitwatchface;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.dinodevs.greatfitwatchface.settings.LoadSettings;
import com.dinodevs.greatfitwatchface.widget.BatteryWidget;
import com.dinodevs.greatfitwatchface.widget.CaloriesWidget;
import com.dinodevs.greatfitwatchface.widget.FloorWidget;
import com.dinodevs.greatfitwatchface.widget.HeartRateWidget;
import com.dinodevs.greatfitwatchface.widget.MainClock;
import com.dinodevs.greatfitwatchface.widget.GreatWidget;
import com.dinodevs.greatfitwatchface.widget.MoonPhaseWidget;
import com.dinodevs.greatfitwatchface.widget.SportTodayDistanceWidget;
import com.dinodevs.greatfitwatchface.widget.SportTotalDistanceWidget;
import com.dinodevs.greatfitwatchface.widget.StepsWidget;
import com.dinodevs.greatfitwatchface.widget.WeatherWidget;
import com.dinodevs.greatfitwatchface.widget.Widget;
import com.huami.watch.watchface.util.Util;
import com.ingenic.iwds.slpt.view.core.SlptAbsoluteLayout;
import com.ingenic.iwds.slpt.view.core.SlptLayout;
import com.ingenic.iwds.slpt.view.core.SlptViewComponent;

/**
 * Splt version of the watch.
 */

public class GreatFitSlpt extends AbstractWatchFaceSlpt {
    Context context;
    public GreatFitSlpt() {
        super();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        context = this.getApplicationContext();

        // Load settings
        LoadSettings settings = new LoadSettings(context);

        this.clock = new MainClock(settings);

        // Disable all except clock in both SLPT modes
        //if(settings.clock_only_slpt)
        //    return super.onStartCommand(intent, flags, startId);

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
        if(settings.isMoonPhase()){
            this.widgets.add(new MoonPhaseWidget(settings));
        }
        if(settings.isGreat()) {
            this.widgets.add(new GreatWidget(settings));
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected SlptLayout createClockLayout26WC() {
        SlptAbsoluteLayout result = new SlptAbsoluteLayout();
        for (SlptViewComponent component : clock.buildSlptViewComponent(this, true)) {
            result.add(component);
        }
        for (Widget widget : widgets) {
            for (SlptViewComponent component : widget.buildSlptViewComponent(this, true)) {
                result.add(component);
            }
        }

        return result;
    }

    @Override
    protected SlptLayout createClockLayout8C() {
        SlptAbsoluteLayout result = new SlptAbsoluteLayout();
        for (SlptViewComponent component : clock.buildSlptViewComponent(this)) {
            result.add(component);
        }
        for (Widget widget : widgets) {
            for (SlptViewComponent component : widget.buildSlptViewComponent(this)) {
                result.add(component);
            }
        }

        return result;
    }

    protected void initWatchFaceConfig() {
        //Log.w("DinoDevs-GreatFit", "Initiating watchface");
    }

    @Override
    public boolean isClockPeriodSecond() {
        Context context = this.getApplicationContext();
        boolean needRefreshSecond = Util.needSlptRefreshSecond(context);
        if (needRefreshSecond) {
            this.setClockPeriodSecond(true);
        }
        return needRefreshSecond;
    }

}