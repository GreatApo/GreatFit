package com.dinodevs.greatfitwatchface.widget;

import android.app.Service;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import com.dinodevs.greatfitwatchface.data.Steps;
import com.dinodevs.greatfitwatchface.data.DataType;
import com.dinodevs.greatfitwatchface.resource.ResourceManager;
import com.dinodevs.greatfitwatchface.settings.LoadSettings;
import com.huami.watch.watchface.util.Util;
import com.ingenic.iwds.slpt.view.arc.SlptTodayStepArcAnglePicView;
import com.ingenic.iwds.slpt.view.core.SlptLinearLayout;
import com.ingenic.iwds.slpt.view.core.SlptPictureView;
import com.ingenic.iwds.slpt.view.core.SlptViewComponent;
import com.ingenic.iwds.slpt.view.sport.SlptTodayStepNumView;
import com.ingenic.iwds.slpt.view.utils.SimpleFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class StepsWidget extends AbstractWidget {
    private Steps stepsData;
    private Paint stepsPaint;
    private Paint ring;

    private Bitmap icon;

    private Float stepsSweepAngle = 0f;
    private Integer angleLength;
    
    private LoadSettings settings;
    private Service mService;

    // Constructor
    public StepsWidget(LoadSettings settings) {
        this.settings = settings;
        if(settings.stepsProgClockwise==1) {
            this.angleLength = (settings.stepsProgEndAngle<settings.stepsProgStartAngle)? 360-(settings.stepsProgStartAngle-settings.stepsProgEndAngle) : settings.stepsProgEndAngle - settings.stepsProgStartAngle;
        }else{
            this.angleLength = (settings.stepsProgEndAngle>settings.stepsProgStartAngle)? 360-(settings.stepsProgStartAngle-settings.stepsProgEndAngle) : settings.stepsProgEndAngle - settings.stepsProgStartAngle;
        }
    }

    // Screen-on init (runs once)
    public void init(Service service) {
        this.mService = service;

        if(settings.steps>0){
            this.stepsPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            this.stepsPaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), settings.font));
            this.stepsPaint.setTextSize(settings.stepsFontSize);
            this.stepsPaint.setColor(settings.stepsColor);
            this.stepsPaint.setTextAlign( (settings.stepsAlignLeft) ? Paint.Align.LEFT : Paint.Align.CENTER );

            if(settings.stepsIcon){
                this.icon = Util.decodeImage(mService.getResources(),"icons/"+settings.is_white_bg+"steps.png");
            }
        }

        if(settings.stepsProg>0 && settings.stepsProgType==0){
            this.ring = new Paint(Paint.ANTI_ALIAS_FLAG);
            this.ring.setStrokeCap(Paint.Cap.ROUND);
            this.ring.setStyle(Paint.Style.STROKE);
            this.ring.setStrokeWidth(settings.stepsProgThickness);
        }
    }

    // Value updater
    public void onDataUpdate(DataType type, Object value) {
        // Steps class
        this.stepsData = (Steps) value;
        // Bar angle
        this.stepsSweepAngle = (this.stepsData == null)? 0f : this.angleLength*((float) (this.stepsData.getSteps()>this.stepsData.getTarget()?this.stepsData.getTarget():this.stepsData.getSteps())/this.stepsData.getTarget());
    }

    // Register update listeners
    public List<DataType> getDataTypes() {
        return Collections.singletonList(DataType.STEPS);
    }

    // Draw screen-on
    public void draw(Canvas canvas, float width, float height, float centerX, float centerY) {
        if (this.stepsData == null) {return;}
        
        // Steps widget
        if(settings.steps>0){
            // Draw icon
            if(settings.stepsIcon){
                canvas.drawBitmap(this.icon, settings.stepsIconLeft, settings.stepsIconTop, settings.mGPaint);
            }

            String text = String.format("%s", this.stepsData.getSteps());
            canvas.drawText(text, settings.stepsLeft, settings.stepsTop, stepsPaint);
        }

        // Steps circle progress bar
        if(settings.stepsProg>0 && settings.stepsProgType==0) {
            int count = canvas.save();

            // Rotate canvas to 0 degrees = 12 o'clock
            canvas.rotate(-90, centerX, centerY);

            // Define circle
            float radius = settings.stepsProgRadius - settings.stepsProgThickness;
            RectF oval = new RectF(settings.stepsProgLeft - radius, settings.stepsProgTop - radius, settings.stepsProgLeft + radius, settings.stepsProgTop + radius);

            // Background
            if(settings.stepsProgBgBool) {
                this.ring.setColor(Color.parseColor("#999999"));
                canvas.drawArc(oval, settings.stepsProgStartAngle, this.angleLength, false, ring);
            }

            this.ring.setColor(settings.colorCodes[settings.stepsProgColorIndex]);
            canvas.drawArc(oval, settings.stepsProgStartAngle, this.stepsSweepAngle, false, ring);

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

        // Show steps
        if(settings.steps>0){
            // Show or Not icon
            if (settings.stepsIcon) {
                SlptPictureView stepsIcon = new SlptPictureView();
                stepsIcon.setImagePicture( SimpleFile.readFileFromAssets(service, ( (better_resolution)?"26wc_":"slpt_" )+"icons/"+settings.is_white_bg+"steps.png") );
                stepsIcon.setStart(
                        (int) settings.stepsIconLeft,
                        (int) settings.stepsIconTop
                );
                slpt_objects.add(stepsIcon);
            }

            SlptLinearLayout steps = new SlptLinearLayout();
            steps.add(new SlptTodayStepNumView());
            steps.setTextAttrForAll(
                    settings.stepsFontSize,
                    settings.stepsColor,
                    ResourceManager.getTypeFace(service.getResources(), settings.font)
            );
            // Position based on screen on
            steps.alignX = 2;
            steps.alignY = 0;
            tmp_left = (int) settings.stepsLeft;
            if(!settings.stepsAlignLeft) {
                // If text is centered, set rectangle
                steps.setRect(
                        (int) (2 * tmp_left + 640),
                        (int) (((float)settings.font_ratio/100)*settings.stepsFontSize)
                );
                tmp_left = -320;
            }
            steps.setStart(
                    tmp_left,
                    (int) (settings.stepsTop-((float)settings.font_ratio/100)*settings.stepsFontSize)
            );
            slpt_objects.add(steps);
        }
        
        // Steps image
        if(settings.stepsProg>0 && settings.stepsProgType==1) {
            // Image
            // todo
            //SlptTodayStepPicGroupView
        }

        // steps bar
        if(settings.stepsProg>0 && settings.stepsProgType==0){
            // Draw background image
            if(settings.stepsProgBgBool) {
                SlptPictureView ring_background = new SlptPictureView();
                ring_background.setImagePicture(SimpleFile.readFileFromAssets(service, ((settings.isVerge())?"verge_":( (better_resolution)?"":"slpt_" ))+"circles/ring1_bg.png"));
                ring_background.setStart((int) (settings.stepsProgLeft-settings.stepsProgRadius), (int) (settings.stepsProgTop-settings.stepsProgRadius));
                slpt_objects.add(ring_background);
            }

            SlptTodayStepArcAnglePicView stepsArcAnglePicView = new SlptTodayStepArcAnglePicView();
            stepsArcAnglePicView.setImagePicture(SimpleFile.readFileFromAssets(service, ((settings.isVerge())?"verge_":( (better_resolution)?"":"slpt_" ))+settings.stepsProgSlptImage));
            stepsArcAnglePicView.setStart((int) (settings.stepsProgLeft-settings.stepsProgRadius), (int) (settings.stepsProgTop-settings.stepsProgRadius));
            stepsArcAnglePicView.start_angle = (settings.stepsProgClockwise==1)? settings.stepsProgStartAngle : settings.stepsProgEndAngle;
            //stepsArcAnglePicView.start_angle = settings.stepsProgStartAngle;
            stepsArcAnglePicView.len_angle = 0;
            stepsArcAnglePicView.full_angle = (settings.stepsProgClockwise==1)? this.angleLength : -this.angleLength;
            stepsArcAnglePicView.draw_clockwise = settings.stepsProgClockwise;
            slpt_objects.add(stepsArcAnglePicView);
        }
        
        return slpt_objects;
    }
}
