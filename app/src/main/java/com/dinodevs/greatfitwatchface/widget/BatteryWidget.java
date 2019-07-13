package com.dinodevs.greatfitwatchface.widget;

import android.app.Service;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import com.dinodevs.greatfitwatchface.settings.LoadSettings;
import com.huami.watch.watchface.util.Util;
import com.ingenic.iwds.slpt.view.arc.SlptPowerArcAnglePicView;
import com.ingenic.iwds.slpt.view.core.SlptBatteryView;
import com.ingenic.iwds.slpt.view.core.SlptLinearLayout;
import com.ingenic.iwds.slpt.view.core.SlptPictureView;
import com.ingenic.iwds.slpt.view.core.SlptViewComponent;
import com.ingenic.iwds.slpt.view.sport.SlptPowerNumView;
import com.ingenic.iwds.slpt.view.utils.SimpleFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.dinodevs.greatfitwatchface.data.Battery;
import com.dinodevs.greatfitwatchface.data.DataType;
import com.dinodevs.greatfitwatchface.resource.ResourceManager;


public class BatteryWidget extends AbstractWidget {
    private Battery batteryData;
    private Paint batteryPaint;
    private Paint ring;

    private Float batterySweepAngle = 0f;
    private Integer angleLength;

    private Bitmap batteryIcon;
    private Bitmap icon;

    private Integer tempBattery=0;

    private LoadSettings settings;
    private Service mService;

    // Constructor
    public BatteryWidget(LoadSettings settings) {
        this.settings = settings;

        if(!(settings.batteryProg>0 && settings.batteryProgType==0)){return;}
        if(settings.batteryProgClockwise==1) {
            this.angleLength = (settings.batteryProgEndAngle < settings.batteryProgStartAngle) ? 360 - (settings.batteryProgStartAngle - settings.batteryProgEndAngle) : settings.batteryProgEndAngle - settings.batteryProgStartAngle;
        }else{
            this.angleLength = (settings.batteryProgEndAngle > settings.batteryProgStartAngle) ? 360 - (settings.batteryProgStartAngle - settings.batteryProgEndAngle) : settings.batteryProgEndAngle - settings.batteryProgStartAngle;
        }
    }

    // Screen-on init (runs once)
    public void init(Service service) {
        this.mService = service;

        // Battery percent element
        if(settings.battery_percent>0){
            if(settings.battery_percentIcon){
                this.icon = Util.decodeImage(mService.getResources(),"icons/"+settings.is_white_bg+"battery.png");
            }

            this.batteryPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            this.batteryPaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), settings.font));
            this.batteryPaint.setTextSize(settings.battery_percentFontSize);
            this.batteryPaint.setColor(settings.battery_percentColor);
            this.batteryPaint.setTextAlign( (settings.battery_percentAlignLeft) ? Paint.Align.LEFT : Paint.Align.CENTER );
        }

        // Battery as images
        if(settings.batteryProg>0 && settings.batteryProgType==1) {
            this.batteryIcon = Util.decodeImage(mService.getResources(),"battery/battery0.png");
        }

        // Progress Bar Circle
        if(settings.batteryProg>0 && settings.batteryProgType==0){
            this.ring = new Paint(Paint.ANTI_ALIAS_FLAG);
            this.ring.setStrokeCap(Paint.Cap.ROUND);
            this.ring.setStyle(Paint.Style.STROKE);
            this.ring.setStrokeWidth(settings.batteryProgThickness);
        }
    }

    // Value updater
    public void onDataUpdate(DataType type, Object value) {
        // Battery class
        this.batteryData = (Battery) value;

        if(this.batteryData == null){
            return;
        }

        // Bar angle
        if(settings.batteryProg>0 && settings.batteryProgType==0) {
            this.batterySweepAngle = this.angleLength * (this.batteryData.getLevel() / (float) this.batteryData.getScale());
        }

        // Battery Image
        if( this.tempBattery == this.batteryData.getLevel()/10 || !(settings.batteryProg>0 && settings.batteryProgType==1)){
            return;
        }
        this.tempBattery = this.batteryData.getLevel()/10;

        this.batteryIcon = Util.decodeImage(mService.getResources(),"battery/battery"+this.tempBattery+".png");
    }

    // Register update listeners
    public List<DataType> getDataTypes() {
        return Collections.singletonList(DataType.BATTERY);
    }

    // Draw screen-on
    public void draw(Canvas canvas, float width, float height, float centerX, float centerY) {
        if (this.batteryData == null) {return;}

        // Battery % widget
        if(settings.battery_percent>0){
            if(settings.battery_percentIcon){
                canvas.drawBitmap(this.icon, settings.battery_percentIconLeft, settings.battery_percentIconTop, settings.mGPaint);
            }

            String text = Integer.toString(this.batteryData.getLevel() * 100 / this.batteryData.getScale())+"%";
            canvas.drawText(text, settings.battery_percentLeft, settings.battery_percentTop, batteryPaint);
        }

        // Battery Progress Image
        if(settings.batteryProg>0 && settings.batteryProgType==1) {
            canvas.drawBitmap(this.batteryIcon, settings.batteryProgLeft, settings.batteryProgTop, settings.mGPaint);
        }

        // Battery bar
        if(settings.batteryProg>0 && settings.batteryProgType==0) {
            int count = canvas.save();

            // Rotate canvas to 0 degrees = 12 o'clock
            canvas.rotate(-90, centerX, centerY);

            // Define circle
            float radius = settings.batteryProgRadius - settings.batteryProgThickness;
            RectF oval = new RectF(settings.batteryProgLeft - radius, settings.batteryProgTop - radius, settings.batteryProgLeft + radius, settings.batteryProgTop + radius);

            // Background
            if(settings.batteryProgBgBool) {
                this.ring.setColor(Color.parseColor("#999999"));
                canvas.drawArc(oval, settings.batteryProgStartAngle, this.angleLength, false, ring);
            }

            this.ring.setColor(settings.colorCodes[settings.batteryProgColorIndex]);
            canvas.drawArc(oval, settings.batteryProgStartAngle, this.batterySweepAngle, false, ring);

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

        // Do not show in SLPT (but show on raise of hand)
        boolean show_all = (!settings.clock_only_slpt || better_resolution);
        if (!show_all)
            return slpt_objects;

        int tmp_left;

        // Show battery
        if(settings.battery_percent>0){
            // Show or Not icon
            if (settings.battery_percentIcon) {
                SlptPictureView battery_percentIcon = new SlptPictureView();
                battery_percentIcon.setImagePicture( SimpleFile.readFileFromAssets(service, ( (better_resolution)?"":"slpt_" )+"icons/"+settings.is_white_bg+"battery.png") );
                battery_percentIcon.setStart(
                        (int) settings.battery_percentIconLeft,
                        (int) settings.battery_percentIconTop
                );
                slpt_objects.add(battery_percentIcon);
            }

            SlptLinearLayout power = new SlptLinearLayout();
            SlptPictureView percentage = new SlptPictureView();
            percentage.setStringPicture("%");
            power.add(new SlptPowerNumView());
            power.add(percentage);
            power.setTextAttrForAll(
                    settings.battery_percentFontSize,
                    settings.battery_percentColor,
                    ResourceManager.getTypeFace(service.getResources(), settings.font)
            );
            // Position based on screen on
            power.alignX = 2;
            power.alignY = 0;
            tmp_left = (int) settings.battery_percentLeft;
            if(!settings.battery_percentAlignLeft) {
                // If text is centered, set rectangle
                power.setRect(
                        (int) (2 * tmp_left + 640),
                        (int) (((float)settings.font_ratio/100)*settings.battery_percentFontSize)
                );
                tmp_left = -320;
            }
            power.setStart(
                    tmp_left,
                    (int) (settings.battery_percentTop-((float)settings.font_ratio/100)*settings.battery_percentFontSize)
            );
            slpt_objects.add(power);
        }

        // Battery as images
        if(settings.batteryProg>0 && settings.batteryProgType==1) {
            int battery_steps = 11;
            byte[][] arrayOfByte = new byte[battery_steps][];
            for (int i = 0; i < arrayOfByte.length; i++) {
                arrayOfByte[i] = SimpleFile.readFileFromAssets(service, String.format(((better_resolution) ? "" : "slpt_") + "battery/battery%d.png", i));
            }
            SlptBatteryView localSlptBatteryView = new SlptBatteryView(battery_steps);
            localSlptBatteryView.setImagePictureArray(arrayOfByte);
            localSlptBatteryView.setStart((int) settings.batteryProgLeft, (int) settings.batteryProgTop);
            slpt_objects.add(localSlptBatteryView);
        }

        // Battery bar
        if(settings.batteryProg>0 && settings.batteryProgType==0){
            // Draw background image
            if(settings.batteryProgBgBool) {
                SlptPictureView ring_background = new SlptPictureView();
                ring_background.setImagePicture(SimpleFile.readFileFromAssets(service, ((settings.isVerge())?"verge_":( (better_resolution)?"":"slpt_" ))+"circles/ring1_bg.png"));
                ring_background.setStart((int) (settings.batteryProgLeft-settings.batteryProgRadius), (int) (settings.batteryProgTop-settings.batteryProgRadius));
                slpt_objects.add(ring_background);
            }

            SlptPowerArcAnglePicView localSlptPowerArcAnglePicView = new SlptPowerArcAnglePicView();
            localSlptPowerArcAnglePicView.setImagePicture(SimpleFile.readFileFromAssets(service, ((settings.isVerge())?"verge_":( (better_resolution)?"":"slpt_" ))+settings.batteryProgSlptImage));
            localSlptPowerArcAnglePicView.setStart((int) (settings.batteryProgLeft-settings.batteryProgRadius), (int) (settings.batteryProgTop-settings.batteryProgRadius));
            localSlptPowerArcAnglePicView.start_angle = (settings.batteryProgClockwise==1)? settings.batteryProgStartAngle : settings.batteryProgEndAngle;
            localSlptPowerArcAnglePicView.len_angle = 0;
            localSlptPowerArcAnglePicView.full_angle = (settings.batteryProgClockwise==1)? this.angleLength : -this.angleLength;
            localSlptPowerArcAnglePicView.draw_clockwise = settings.batteryProgClockwise;
            slpt_objects.add(localSlptPowerArcAnglePicView);
        }

        return slpt_objects;
    }
}
