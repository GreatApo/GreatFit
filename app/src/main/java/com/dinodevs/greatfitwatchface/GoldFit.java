package com.dinodevs.greatfitwatchface;

import com.dinodevs.greatfitwatchface.widget.BatteryWidget;
import com.dinodevs.greatfitwatchface.widget.CaloriesWidget;
import com.dinodevs.greatfitwatchface.widget.CirclesWidget;
import com.dinodevs.greatfitwatchface.widget.FloorWidget;
import com.dinodevs.greatfitwatchface.widget.HeartRateWidget;
import com.dinodevs.greatfitwatchface.widget.MainClock;
import com.dinodevs.greatfitwatchface.widget.TimeWidget;
import com.dinodevs.greatfitwatchface.widget.WeatherWidget;
import com.huami.watch.watchface.AbstractSlptClock;


/**
 * Amazfit watch faces
 */

public class GoldFit extends AbstractWatchFace {

    public GoldFit() {
        super(
                new MainClock(),
                new HeartRateWidget(),
                new BatteryWidget(),
                new WeatherWidget()
        );
    }

    @Override
    protected Class<? extends AbstractSlptClock> slptClockClass() {
        return GreatFitSplt.class;
    }
}