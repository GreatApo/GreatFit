package es.LBA97.PSourceInverted;

import com.huami.watch.watchface.AbstractSlptClock;

import es.LBA97.PSourceInverted.widget.AmPmWidget;
import es.LBA97.PSourceInverted.widget.BatteryWidget;
import es.LBA97.PSourceInverted.widget.CirclesWidget;
import es.LBA97.PSourceInverted.widget.HeartRateWidget;
import es.LBA97.PSourceInverted.widget.MainClock;
import es.LBA97.PSourceInverted.widget.CaloriesWidget;
import es.LBA97.PSourceInverted.widget.FloorWidget;
import es.LBA97.PSourceInverted.widget.TimeWidget;
import es.LBA97.PSourceInverted.widget.WeatherWidget;


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