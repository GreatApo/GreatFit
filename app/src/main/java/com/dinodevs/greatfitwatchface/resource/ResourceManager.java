package com.dinodevs.greatfitwatchface.resource;

import android.content.res.Resources;
import android.graphics.Typeface;

import com.ingenic.iwds.slpt.view.core.SlptLayout;
import com.ingenic.iwds.slpt.view.core.SlptNumView;
import com.ingenic.iwds.slpt.view.core.SlptViewComponent;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

/**
 * Resource manager for caching purposes
 */
public class ResourceManager {

    public enum Font {
        FONT_FILE("fonts/font.ttf")/*, BEBAS_NEUE("fonts/BebasNeue.otf")*/;  // More fonts can go here

        private final String path;

        Font(String path) {
            this.path = path;
        }
    }


    private static Map<Font, Typeface> TYPE_FACES = new EnumMap<>(Font.class);

    public static Typeface getTypeFace(final Resources resources, final Font font) {
        Typeface typeface = TYPE_FACES.get(font);
        if (typeface == null) {
            typeface = Typeface.createFromAsset(resources.getAssets(), font.path);
            TYPE_FACES.put(font, typeface);
        }
        return typeface;
    }
}
