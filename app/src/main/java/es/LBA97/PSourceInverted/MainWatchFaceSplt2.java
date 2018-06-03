package es.LBA97.PSourceInverted;

import android.util.Log;

import es.LBA97.PSourceInverted.widget.AmPmWidget;
import es.LBA97.PSourceInverted.widget.BatteryWidget;
import es.LBA97.PSourceInverted.widget.CaloriesWidget;
import es.LBA97.PSourceInverted.widget.CirclesWidget;
import es.LBA97.PSourceInverted.widget.FloorWidget;
import es.LBA97.PSourceInverted.widget.HeartRateWidget;
import es.LBA97.PSourceInverted.widget.MainClock;

import com.ingenic.iwds.slpt.view.core.SlptAbsoluteLayout;
import com.ingenic.iwds.slpt.view.core.SlptLayout;
import com.ingenic.iwds.slpt.view.core.SlptViewComponent;

import es.LBA97.PSourceInverted.widget.TimeWidget;
import es.LBA97.PSourceInverted.widget.WeatherWidget;
import es.LBA97.PSourceInverted.widget.Widget;

/**
 * Splt version of the watch.
 */

public class MainWatchFaceSplt2 extends AbstractWatchFaceSlpt {

    public MainWatchFaceSplt2() {
        super(
                new MainClock(),
                new CirclesWidget(),
                new HeartRateWidget(),
                new CaloriesWidget(),
                new BatteryWidget(),
                new FloorWidget(),
                new BatteryWidget(),
                new WeatherWidget(),
                new TimeWidget()
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