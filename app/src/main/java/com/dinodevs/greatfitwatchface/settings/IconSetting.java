package com.dinodevs.greatfitwatchface.settings;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * Created by Kieron on 20/01/2018.
 */

public class IconSetting extends BaseSetting {

    //Setting with a click listener, two strings and an icon

    View.OnClickListener onClickListener;
    String title;
    String subtitle;
    Drawable icon;
    Integer color;

    public IconSetting(Drawable icon, String title, String subtitle, View.OnClickListener onClickListener, Integer color) {
        this.onClickListener = onClickListener;
        this.title = title;
        this.subtitle = subtitle;
        this.icon = icon;
        this.color = (color==null)? Color.parseColor("#ffffff") : color ;
    }
}
