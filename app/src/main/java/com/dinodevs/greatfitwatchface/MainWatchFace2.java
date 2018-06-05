package com.dinodevs.greatfitwatchface;

import com.huami.watch.watchface.AbstractSlptClock;

import com.dinodevs.greatfitwatchface.widget.BatteryWidget;
import com.dinodevs.greatfitwatchface.widget.CirclesWidget;
import com.dinodevs.greatfitwatchface.widget.HeartRateWidget;
import com.dinodevs.greatfitwatchface.widget.MainClock;
import com.dinodevs.greatfitwatchface.widget.CaloriesWidget;
import com.dinodevs.greatfitwatchface.widget.FloorWidget;
import com.dinodevs.greatfitwatchface.widget.TimeWidget;
import com.dinodevs.greatfitwatchface.widget.WeatherWidget;


/**
 * Amazfit watch faces
 */

public class MainWatchFace2 extends AbstractWatchFace {

    public MainWatchFace2() {
        super(
                new MainClock(),
                new CirclesWidget(),
                new HeartRateWidget(),
                new CaloriesWidget(),
                new FloorWidget(),
                new BatteryWidget(),
                new WeatherWidget(),
                new TimeWidget()
        );
    }

    @Override
    protected Class<? extends AbstractSlptClock> slptClockClass() {
        return MainWatchFaceSplt2.class;
    }
}