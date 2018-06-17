package com.dinodevs.greatfitwatchface;

import com.dinodevs.greatfitwatchface.settings.APsettings;
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

public class GreatFit extends AbstractWatchFace {

    private APsettings settings;

    public static boolean[] bool_settings = new boolean[]{
            false,//seconds
            true,//indicator
            false,//date
            true,//day
            false,//month
            false,//month_as_text
            true,//three_letters_month_if_text
            false,//year
            true,//week_name
            true,//three_letters_day_if_text
            false,//calories_units
            false,//distance_units
            false,//heart_rate_units
            false,//temperature_units
            false,//flashing_indicator
            false,//flashing_heart_rate_icon
            true,//status_bar
            false,//battery_circle
            false,//steps_circle
            false,//today_distance_circle
            false,//circles_background
            true,//battery
            true,//steps
            false,//today_distance
            false,//total_distance
            true,//calories
            false,//heart_rate
            false,//temperature
            false,//weather_image
            false//floor
    };

    public int[] pos_settings = new int[]{
            //hours x,y,font-size
            118, 210, 190,
            //seconds x,y,font-size
            0, 0, 0,
            //minutes x,y,font-size
            210, 178, 130,
            //Day Name x,y,font-size
            210, 178, 130
    };

    public GreatFit() {
        super(
                new MainClock(),
                new CirclesWidget(),
                new HeartRateWidget(),
                new CaloriesWidget(),
                new BatteryWidget(),
                new FloorWidget(),
                new WeatherWidget()
        );
    }

    @Override
    protected Class<? extends AbstractSlptClock> slptClockClass() {
        return GreatFitSplt.class;
    }
}