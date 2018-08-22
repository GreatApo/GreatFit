package com.dinodevs.greatfitwatchface.settings;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.SeekBar;

/**
 * Created by Kieron on 20/01/2018.
 */

public class SeekbarSetting extends BaseSetting {

    //Setting
    SeekBar.OnSeekBarChangeListener onChangeListener;
    String title;
    String subtitle;
    Drawable icon;
    Integer current;
    Integer max;

    public SeekbarSetting(Drawable icon, String title, SeekBar.OnSeekBarChangeListener onChangeListener, Integer current, Integer max) {
        this.onChangeListener = onChangeListener;
        this.title = title;
        this.subtitle = "Current: "+current+" (Max: "+max+")";
        this.icon = icon;
        this.current = current;
        this.max = max;
    }
}
