package com.dinodevs.greatfitwatchface;

import android.util.Log;

import com.huami.watch.watchface.AbstractSlptClock;
import com.ingenic.iwds.slpt.view.core.SlptAbsoluteLayout;
import com.ingenic.iwds.slpt.view.core.SlptLayout;
import com.ingenic.iwds.slpt.view.core.SlptViewComponent;

import java.util.Arrays;
import java.util.LinkedList;

import com.dinodevs.greatfitwatchface.widget.ClockWidget;
import com.dinodevs.greatfitwatchface.widget.Widget;

/**
 * Splt version of
 */

public abstract class AbstractWatchFaceSlpt extends AbstractSlptClock {

    final ClockWidget clock;
    final LinkedList<Widget> widgets = new LinkedList<>();

    protected AbstractWatchFaceSlpt(final ClockWidget clock, final Widget... widgets) {
        this.clock = clock;
        this.widgets.addAll(Arrays.asList(widgets));
    }

    public final SlptLayout createClockLayout() {
        SlptAbsoluteLayout result = new SlptAbsoluteLayout();
        for (SlptViewComponent component : clock.buildSlptViewComponent(this)) {
            result.add(component);
        }
        for (Widget widget : widgets) {
            for (SlptViewComponent component : widget.buildSlptViewComponent(this)) {
                result.add(component);
            }
        }


        Log.w("DarkThanosTest", "Rebuild Normal");

        return result;
    }
}
