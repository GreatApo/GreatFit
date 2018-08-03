package com.dinodevs.greatfitwatchface;

import android.content.Context;
import android.util.Log;

import com.dinodevs.greatfitwatchface.widget.BatteryWidget;
import com.dinodevs.greatfitwatchface.widget.CaloriesWidget;
import com.dinodevs.greatfitwatchface.widget.CirclesWidget;
import com.dinodevs.greatfitwatchface.widget.FloorWidget;
import com.dinodevs.greatfitwatchface.widget.HeartRateWidget;
import com.dinodevs.greatfitwatchface.widget.MainClock;
import com.dinodevs.greatfitwatchface.widget.GreatWidget;
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
    // Class variables
    private Context context;
    private boolean needRefreshSecond;
    public String greatfitParameters;

    public GreatFitSlpt() {
        super(
                new MainClock(),
                new CirclesWidget(),
                new HeartRateWidget(),
                new CaloriesWidget(),
                new FloorWidget(),
                new BatteryWidget(),
                new WeatherWidget(),
                new GreatWidget()
        );
    }

    @Override
    protected SlptLayout createClockLayout26WC() {
        SlptAbsoluteLayout result = new SlptAbsoluteLayout();
        for (SlptViewComponent component : clock.buildSlptViewComponent(this)) {
            result.add(component);
        }
        for (Widget widget : widgets) {
            for (SlptViewComponent component : widget.buildSlptViewComponent(this)) {
                result.add(component);
            }
        }

        //Log.w("DinoDevs-GreatFit", "Rebuild 26WC");

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

        //Log.w("DinoDevs-GreatFit", "Rebuild 8C");

        return result;
    }


    protected void initWatchFaceConfig() {
        Log.w("DinoDevs-GreatFit", "Initiating watchface");

        this.greatfitParameters = "";
        //this.getResources().getBoolean(R.bool.seconds)

        this.context = this.getApplicationContext();
        this.needRefreshSecond = Util.needSlptRefreshSecond(this.context);
        if (this.needRefreshSecond) {
            this.setClockPeriodSecond(true);
        }
    }
}