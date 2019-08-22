package com.dinodevs.greatfitwatchface.settings;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.SeekBar;

/**
 * Created by Kieron on 20/01/2018.
 */

public class IncrementalSetting extends BaseSetting {

    //Setting
    View.OnClickListener onClickLessListener;
    View.OnClickListener onClickMoreListener;
    String title;
    String subtitle;
    Drawable icon;
    String value;

    public IncrementalSetting(Drawable icon, String title, String subtitle, View.OnClickListener onClickListenerLess, View.OnClickListener onClickListenerMore, String current) {
        this.onClickLessListener = onClickListenerLess;
        this.onClickMoreListener = onClickListenerMore;
        this.title = title;
        this.subtitle = (subtitle==null)?"Current: "+current+" (Max:)":subtitle;
        this.icon = icon;
        this.value = current;
    }
}
