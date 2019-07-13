package com.dinodevs.greatfitwatchface.widget;

import android.app.Service;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.dinodevs.greatfitwatchface.data.DataType;
import com.dinodevs.greatfitwatchface.data.TodayDistance;
import com.dinodevs.greatfitwatchface.resource.ResourceManager;
import com.dinodevs.greatfitwatchface.settings.LoadSettings;
import com.huami.watch.watchface.util.Util;
import com.ingenic.iwds.slpt.view.arc.SlptTodayDistanceArcAnglePicView;
import com.ingenic.iwds.slpt.view.core.SlptLinearLayout;
import com.ingenic.iwds.slpt.view.core.SlptPictureView;
import com.ingenic.iwds.slpt.view.core.SlptViewComponent;
import com.ingenic.iwds.slpt.view.sport.SlptTodaySportDistanceFView;
import com.ingenic.iwds.slpt.view.sport.SlptTodaySportDistanceLView;
import com.ingenic.iwds.slpt.view.utils.SimpleFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class SportTodayDistanceWidget extends AbstractWidget {
    private TodayDistance today_distanceData;
    private Paint today_distancePaint;
    private Paint ring;
    private Bitmap today_distanceIcon;
    private Float today_distanceSweepAngle = 0f;
    private Integer angleLength;

    private LoadSettings settings;
    private Service mService;

    // Constructor
    public SportTodayDistanceWidget(LoadSettings settings) {
        this.settings = settings;
        this.angleLength = settings.today_distanceProgEndAngle - settings.today_distanceProgStartAngle;
    }

    // Screen-on init (runs once)
    public void init(Service service) {
        this.mService = service;
        
        if(settings.today_distance>0){
            this.today_distancePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            this.today_distancePaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), settings.font));
            this.today_distancePaint.setTextSize(settings.today_distanceFontSize);
            this.today_distancePaint.setColor(settings.today_distanceColor);
            this.today_distancePaint.setTextAlign( (settings.today_distanceAlignLeft) ? Paint.Align.LEFT : Paint.Align.CENTER );

            if(settings.today_distanceIcon){
                this.today_distanceIcon = Util.decodeImage(service.getResources(),"icons/"+settings.is_white_bg+"today_distance.png");
            }
        }

        if(settings.today_distanceProg>0 && settings.today_distanceProgType==0){
            this.ring = new Paint(Paint.ANTI_ALIAS_FLAG);
            this.ring.setStrokeCap(Paint.Cap.ROUND);
            this.ring.setStyle(Paint.Style.STROKE);
            this.ring.setStrokeWidth(settings.today_distanceProgThickness);
        }
    }

    // Value updater
    public void onDataUpdate(DataType type, Object value) {
        // Today Sport's Distance class
        this.today_distanceData = (TodayDistance) value;
        // Bar angle
        this.today_distanceSweepAngle = (this.today_distanceData == null)? 0f : Math.min( this.angleLength, this.angleLength*(today_distanceData.getDistance() / 3.0f) ) ;
    }

    // Register update listeners
    public List<DataType> getDataTypes() {
        return Collections.singletonList(DataType.DISTANCE);
    }

    // Draw screen-on
    public void draw(Canvas canvas, float width, float height, float centerX, float centerY) {
        if (this.today_distanceData == null) {return;}
        
        // Today Sport's Distance widget
        if(settings.today_distance>0){
            if(settings.today_distanceIcon){
                canvas.drawBitmap(this.today_distanceIcon, settings.today_distanceIconLeft, settings.today_distanceIconTop, settings.mGPaint);
            }
            String units = (settings.today_distanceUnits) ? " km" : "";// if units are enabled
            String text = String.format("%s", this.today_distanceData.getDistance())+units;
            canvas.drawText(text, settings.today_distanceLeft, settings.today_distanceTop, today_distancePaint);
        }

        // Today Sport's Distance bar
        if(settings.today_distanceProg>0 && settings.today_distanceProgType==0) {
            // Circle progress bars
            int count = canvas.save();
            int radius = Math.round(Math.min(width / 2, height / 2));// + this.thickness + this.padding;
            RectF oval = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
            // Rotate from 0 to 270
            canvas.rotate(90, centerX, centerY);

            radius = radius - (int) settings.today_distanceProgThickness;
            oval = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);

            // Background
            if(settings.today_distanceProgBgBool) {
                this.ring.setColor(Color.parseColor("#999999"));
                canvas.drawArc(oval, settings.today_distanceProgStartAngle, settings.today_distanceProgEndAngle, false, ring);
            }

            this.ring.setColor(settings.colorCodes[settings.today_distanceProgColorIndex]);
            canvas.drawArc(oval, settings.today_distanceProgStartAngle, today_distanceSweepAngle, false, ring);

            canvas.restoreToCount(count);
        }
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

        // Show Today Sport's Distance
        if(settings.today_distance>0){
            // Show or Not icon
            if (settings.today_distanceIcon) {
                SlptPictureView today_distanceIcon = new SlptPictureView();
                today_distanceIcon.setImagePicture( SimpleFile.readFileFromAssets(service, ( (better_resolution)?"26wc_":"slpt_" )+"icons/"+settings.is_white_bg+"today_distance.png") );
                today_distanceIcon.setStart(
                        (int) settings.today_distanceIconLeft,
                        (int) settings.today_distanceIconTop
                );
                slpt_objects.add(today_distanceIcon);
            }

            SlptPictureView dot = new SlptPictureView();
            dot.setStringPicture(".");
            SlptPictureView kilometer = new SlptPictureView();
            kilometer.setStringPicture(" km");

            SlptLinearLayout distance = new SlptLinearLayout();
            distance.add(new SlptTodaySportDistanceFView());
            distance.add(dot);
            distance.add(new SlptTodaySportDistanceLView());
            // Show or Not Units
            if(settings.today_distanceUnits) {
                distance.add(kilometer);
            }
            distance.setTextAttrForAll(
                    settings.today_distanceFontSize,
                    settings.today_distanceColor,
                    ResourceManager.getTypeFace(service.getResources(), settings.font)
            );
            // Position based on screen on
            distance.alignX = 2;
            distance.alignY = 0;
            tmp_left = (int) settings.today_distanceLeft;
            if(!settings.today_distanceAlignLeft) {
                // If text is centered, set rectangle
                distance.setRect(
                        (int) (2 * tmp_left + 640),
                        (int) (((float)settings.font_ratio/100)*settings.today_distanceFontSize)
                );
                tmp_left = -320;
            }
            distance.setStart(
                    tmp_left,
                    (int) (settings.today_distanceTop-((float)settings.font_ratio/100)*settings.today_distanceFontSize)
            );
            slpt_objects.add(distance);
        }
        
        // Today Sport's Distance images
        if(settings.today_distanceProg>0 && settings.today_distanceProgType==1) {
            // Image
            // todo
            //SlptTodayDistancePicGroupView
        }

        // Today Sport's Distance bar
        if(settings.today_distanceProg>0 && settings.today_distanceProgType==0){
            // Draw background image
            if(settings.today_distanceProgBgBool) {
                SlptPictureView ring_background = new SlptPictureView();
                ring_background.setImagePicture(SimpleFile.readFileFromAssets(service, ((settings.isVerge())?"verge_":( (better_resolution)?"":"slpt_" ))+"circles/ring1__bg.png"));
                slpt_objects.add(ring_background);
            }

            SlptTodayDistanceArcAnglePicView today_distanceArcAnglePicView = new SlptTodayDistanceArcAnglePicView();
            today_distanceArcAnglePicView.setImagePicture(SimpleFile.readFileFromAssets(service, ((settings.isVerge())?"verge_":( (better_resolution)?"":"slpt_" ))+settings.today_distanceProgSlptImage));
            today_distanceArcAnglePicView.setStart((int) settings.today_distanceProgLeft, (int) settings.today_distanceProgTop);
            today_distanceArcAnglePicView.start_angle = settings.today_distanceProgStartAngle;
            today_distanceArcAnglePicView.len_angle = 0;
            today_distanceArcAnglePicView.full_angle = settings.today_distanceProgEndAngle;
            today_distanceArcAnglePicView.draw_clockwise = settings.today_distanceProgClockwise;
            slpt_objects.add(today_distanceArcAnglePicView);
        }
        
        return slpt_objects;
    }
}
