package com.dinodevs.greatfitwatchface.widget;

import android.app.Service;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.dinodevs.greatfitwatchface.data.DataType;
import com.dinodevs.greatfitwatchface.data.Date;
import com.dinodevs.greatfitwatchface.resource.MoonPhase;
import com.dinodevs.greatfitwatchface.settings.LoadSettings;
import com.huami.watch.watchface.util.Util;
import com.ingenic.iwds.slpt.view.core.SlptPictureView;
import com.ingenic.iwds.slpt.view.core.SlptViewComponent;
import com.ingenic.iwds.slpt.view.utils.SimpleFile;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;


public class MoonPhaseWidget extends AbstractWidget {
    private Service mService;

    private Bitmap moonphaseImageIcon;
    private LoadSettings settings;
    private MoonPhase mf;
    private int lastDay;

    // Constructor
    public MoonPhaseWidget(LoadSettings settings) {
        this.settings = settings;

        mf = new MoonPhase();
        lastDay = -1;
    }

    // Screen-on init (runs once)
    @Override
    public void init(Service service) {
        this.mService = service;

        if (settings.moonphase > 0) {
            if (settings.moonphaseIcon) {
                int i = mf.getPhaseIndex();
                this.moonphaseImageIcon = getMoonphaseImageIcon(i);
            }
        }
    }

    private Bitmap getMoonphaseImageIcon(int i) {
        String name = "moon/moon"+Integer.toString(i)+".png";
        return Util.decodeImage(mService.getResources(),name);
    }

    // Register listener
    @Override
    public List<DataType> getDataTypes() {
        return Collections.singletonList(DataType.DATE);
    }

    // Updater
    @Override
    public void onDataUpdate(DataType type, Object value) {
        Date d =(Date)value;
        if (d.getDay() != lastDay) {
            lastDay=d.getDay();
            Calendar c = Calendar.getInstance();
            c.set(d.getYear(), d.getMonth(), lastDay);
            mf = new MoonPhase(c);
            int i = mf.getPhaseIndex();
            this.moonphaseImageIcon = getMoonphaseImageIcon(i);
        }
    }

    // Screen on
    @Override
    public void draw(Canvas canvas, float width, float height, float centerX, float centerY) {
        // Draw Text
        if(settings.moonphase>0 && moonphaseImageIcon != null) {
            canvas.drawBitmap(this.moonphaseImageIcon, settings.moonphaseIconLeft, settings.moonphaseIconTop, settings.mGPaint);
        }
    }

    // Screen-off (SLPT)
    @Override
    public List<SlptViewComponent> buildSlptViewComponent(Service service) {
        return buildSlptViewComponent(service, false);
    }

    // Screen-off (SLPT) - Better screen quality
    public List<SlptViewComponent> buildSlptViewComponent(Service service, boolean better_resolution) {
        better_resolution = better_resolution && settings.better_resolution_when_raising_hand;

        List<SlptViewComponent> slpt_objects = new ArrayList<>();

        // Do not show in SLPT (but show on raise of hand)
        boolean show_all = (!settings.clock_only_slpt || better_resolution);
        if (!show_all)
            return slpt_objects;

        this.mService = service;

        try  {
            // Get moon data
            int i = mf.getPhaseIndex();
            String filename = (better_resolution) ? String.format("26wc_moon/moon%d.png", i) : String.format("slpt_moon/" + settings.is_white_bg + "moon%d.png", i);

            SlptPictureView moonphaseIcon = new SlptPictureView();
            moonphaseIcon.setImagePicture(SimpleFile.readFileFromAssets(service, filename));
            moonphaseIcon.setStart(
                    (int) settings.moonphaseIconLeft,
                    (int) settings.moonphaseIconTop
            );
            slpt_objects.add(moonphaseIcon);

        } catch (Exception ex) {
            //Log.e(TAG,ex.toString());
        }

        return slpt_objects;
    }
}
