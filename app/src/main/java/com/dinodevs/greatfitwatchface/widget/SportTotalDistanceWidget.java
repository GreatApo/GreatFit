package com.dinodevs.greatfitwatchface.widget;

import android.app.Service;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.dinodevs.greatfitwatchface.data.DataType;
import com.dinodevs.greatfitwatchface.data.TodayDistance;
import com.dinodevs.greatfitwatchface.data.TotalDistance;
import com.dinodevs.greatfitwatchface.resource.ResourceManager;
import com.dinodevs.greatfitwatchface.settings.LoadSettings;
import com.huami.watch.watchface.util.Util;
import com.ingenic.iwds.slpt.view.arc.SlptTodayDistanceArcAnglePicView;
import com.ingenic.iwds.slpt.view.core.SlptLinearLayout;
import com.ingenic.iwds.slpt.view.core.SlptPictureView;
import com.ingenic.iwds.slpt.view.core.SlptViewComponent;
import com.ingenic.iwds.slpt.view.sport.SlptTotalDistanceFView;
import com.ingenic.iwds.slpt.view.sport.SlptTotalDistanceLView;
import com.ingenic.iwds.slpt.view.utils.SimpleFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class SportTotalDistanceWidget extends AbstractWidget {
    private TotalDistance total_distanceData;
    private Paint total_distancePaint;
    private Paint ring;
    private Bitmap total_distanceIcon;
    private LoadSettings settings;
    private Service mService;

    // Constructor
    public SportTotalDistanceWidget(LoadSettings settings) {
        this.settings = settings;
    }

    // Screen-on init (runs once)
    public void init(Service service) {
        this.mService = service;
        
        this.total_distancePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.total_distancePaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), settings.font));
        this.total_distancePaint.setTextSize(settings.total_distanceFontSize);
        this.total_distancePaint.setColor(settings.total_distanceColor);
        this.total_distancePaint.setTextAlign( (settings.total_distanceAlignLeft) ? Paint.Align.LEFT : Paint.Align.CENTER );

        if(settings.total_distanceIcon){
            this.total_distanceIcon = Util.decodeImage(service.getResources(),"icons/"+settings.is_white_bg+"total_distance.png");
        }
    }

    // Value updater
    public void onDataUpdate(DataType type, Object value) {
        // total Sport's Distance class
        this.total_distanceData = (TotalDistance) value;
    }

    // Register update listeners
    public List<DataType> getDataTypes() { return Collections.singletonList(DataType.TOTAL_DISTANCE);}

    // Draw screen-on
    public void draw(Canvas canvas, float width, float height, float centerX, float centerY) {
        if (this.total_distanceData == null) {return;}

        if(settings.total_distanceIcon){
            canvas.drawBitmap(this.total_distanceIcon, settings.total_distanceIconLeft, settings.total_distanceIconTop, settings.mGPaint);
        }

        // total Sport's Distance widget
        String units = (settings.total_distanceUnits) ? " km" : "";// if units are enabled
        String text = String.format("%s", this.total_distanceData.getDistance())+units;
        canvas.drawText(text, settings.total_distanceLeft, settings.total_distanceTop, total_distancePaint);
    }

    // Screen-off (SLPT)
    public List<SlptViewComponent> buildSlptViewComponent(Service service) {
        return buildSlptViewComponent(service, false);
    }

    // Screen-off (SLPT) - Better screen quality
    public List<SlptViewComponent> buildSlptViewComponent(Service service, boolean better_resolution) {
        better_resolution = better_resolution && settings.better_resolution_when_raising_hand;
        List<SlptViewComponent> slpt_objects = new ArrayList<>();
        int tmp_left;

        // Do not show in SLPT (but show on raise of hand)
        boolean show_all = (!settings.clock_only_slpt || better_resolution);
        if (!show_all)
            return slpt_objects;

        // Show total Sport's Distance
        if(settings.total_distance>0){
            // Show or Not icon
            if (settings.total_distanceIcon) {
                SlptPictureView total_distanceIcon = new SlptPictureView();
                total_distanceIcon.setImagePicture( SimpleFile.readFileFromAssets(service, ( (better_resolution)?"26wc_":"slpt_" )+"icons/"+settings.is_white_bg+"total_distance.png") );
                total_distanceIcon.setStart(
                        (int) settings.total_distanceIconLeft,
                        (int) settings.total_distanceIconTop
                );
                slpt_objects.add(total_distanceIcon);
            }

            SlptPictureView dot = new SlptPictureView();
            dot.setStringPicture(".");
            SlptPictureView kilometer = new SlptPictureView();
            kilometer.setStringPicture(" km");

            SlptLinearLayout distance = new SlptLinearLayout();
            distance.add(new SlptTotalDistanceFView());
            distance.add(dot);
            distance.add(new SlptTotalDistanceLView());
            // Show or Not Units
            if(settings.today_distanceUnits) {
                distance.add(kilometer);
            }
            distance.setTextAttrForAll(
                    settings.total_distanceFontSize,
                    settings.total_distanceColor,
                    ResourceManager.getTypeFace(service.getResources(), settings.font)
            );
            // Position based on screen on
            distance.alignX = 2;
            distance.alignY = 0;
            tmp_left = (int) settings.total_distanceLeft;
            if(!settings.total_distanceAlignLeft) {
                // If text is centered, set rectangle
                distance.setRect(
                        (int) (2 * tmp_left + 640),
                        (int) (((float)settings.font_ratio/100)*settings.total_distanceFontSize)
                );
                tmp_left = -320;
            }
            distance.setStart(
                    tmp_left,
                    (int) (settings.total_distanceTop-((float)settings.font_ratio/100)*settings.total_distanceFontSize)
            );
            slpt_objects.add(distance);
        }
        
        return slpt_objects;
    }
}
