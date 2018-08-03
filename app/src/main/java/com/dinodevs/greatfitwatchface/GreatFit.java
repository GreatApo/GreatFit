package com.dinodevs.greatfitwatchface;

import com.dinodevs.greatfitwatchface.widget.Widget;
import com.huami.watch.watchface.AbstractSlptClock;

import com.dinodevs.greatfitwatchface.widget.BatteryWidget;
import com.dinodevs.greatfitwatchface.widget.CirclesWidget;
import com.dinodevs.greatfitwatchface.widget.HeartRateWidget;
import com.dinodevs.greatfitwatchface.widget.MainClock;
import com.dinodevs.greatfitwatchface.widget.CaloriesWidget;
import com.dinodevs.greatfitwatchface.widget.FloorWidget;
import com.dinodevs.greatfitwatchface.widget.GreatWidget;
import com.dinodevs.greatfitwatchface.widget.WeatherWidget;
import com.ingenic.iwds.slpt.view.core.SlptViewComponent;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


/**
 * Amazfit watch faces
 */

public class GreatFit extends AbstractWatchFace {

    public GreatFit() {
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
    protected Class<? extends AbstractSlptClock> slptClockClass() {
        return GreatFitSlpt.class;
    }
}