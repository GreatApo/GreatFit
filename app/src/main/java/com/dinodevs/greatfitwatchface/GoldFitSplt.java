package com.dinodevs.greatfitwatchface;

import android.util.Log;

import com.dinodevs.greatfitwatchface.widget.BatteryWidget;
import com.dinodevs.greatfitwatchface.widget.CaloriesWidget;
import com.dinodevs.greatfitwatchface.widget.CirclesWidget;
import com.dinodevs.greatfitwatchface.widget.FloorWidget;
import com.dinodevs.greatfitwatchface.widget.HeartRateWidget;
import com.dinodevs.greatfitwatchface.widget.MainClock;
import com.dinodevs.greatfitwatchface.widget.TimeWidget;
import com.dinodevs.greatfitwatchface.widget.WeatherWidget;
import com.dinodevs.greatfitwatchface.widget.Widget;
import com.ingenic.iwds.slpt.view.core.SlptAbsoluteLayout;
import com.ingenic.iwds.slpt.view.core.SlptLayout;
import com.ingenic.iwds.slpt.view.core.SlptViewComponent;

/**
 * Splt version of the watch.
 */

public class GoldFitSplt extends AbstractWatchFaceSlpt {

    public GoldFitSplt() {
        super(
                new MainClock(),
                new HeartRateWidget(),
                new BatteryWidget(),
                new WeatherWidget()
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

        Log.w("DarkThanosTest", "Rebuild 26WC");

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

        Log.w("DarkThanosTest", "Rebuild 8C");

        return result;
    }
}