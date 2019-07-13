package com.dinodevs.greatfitwatchface.widget;

import android.app.Service;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

import com.dinodevs.greatfitwatchface.settings.LoadSettings;
import com.ingenic.iwds.slpt.view.arc.SlptArcAnglePicView;
import com.ingenic.iwds.slpt.view.arc.SlptPowerArcAnglePicView;
import com.ingenic.iwds.slpt.view.arc.SlptTodayDistanceArcAnglePicView;
import com.ingenic.iwds.slpt.view.arc.SlptTodayStepArcAnglePicView;
import com.ingenic.iwds.slpt.view.core.SlptLinearLayout;
import com.ingenic.iwds.slpt.view.core.SlptPictureView;
import com.ingenic.iwds.slpt.view.core.SlptViewComponent;
import com.ingenic.iwds.slpt.view.sport.SlptPowerNumView;
import com.ingenic.iwds.slpt.view.sport.SlptTodaySportDistanceFView;
import com.ingenic.iwds.slpt.view.sport.SlptTodaySportDistanceLView;
import com.ingenic.iwds.slpt.view.sport.SlptTodayStepNumView;
import com.ingenic.iwds.slpt.view.sport.SlptTotalDistanceFView;
import com.ingenic.iwds.slpt.view.sport.SlptTotalDistanceLView;
import com.ingenic.iwds.slpt.view.utils.SimpleFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.dinodevs.greatfitwatchface.R;
import com.dinodevs.greatfitwatchface.data.Battery;
import com.dinodevs.greatfitwatchface.data.DataType;
import com.dinodevs.greatfitwatchface.data.Steps;
import com.dinodevs.greatfitwatchface.data.TodayDistance;
import com.dinodevs.greatfitwatchface.data.TotalDistance;
import com.dinodevs.greatfitwatchface.resource.ResourceManager;

public class CirclesWidget extends AbstractWidget {


    private Battery batteryData;
    private Steps stepsData;
    private TodayDistance sportData;
    private TotalDistance roadData;
    private Float batterySweepAngle = null;
    private Float stepsSweepAngle = null;
    private Float sportSweepAngle = null;

    private Paint batteryPaint;
    private Paint stepsPaint;
    private Paint sportPaint;
    private Paint roadPaint;
    private Paint ring;
    private Paint circle;

    private Drawable overlay;

    private LoadSettings settings;

    public CirclesWidget(LoadSettings settings) {
        this.settings = settings;
    }

    @Override
    public void init(Service service) {
        settings.startAngleBattery = settings.startAngleBattery - 180;
        settings.startAngleSteps = settings.startAngleSteps - 180;
        settings.startAngleSport = settings.startAngleSport - 180;

        this.ring = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.ring.setStrokeCap(Paint.Cap.ROUND);
        this.ring.setStyle(Paint.Style.STROKE);
        this.ring.setStrokeWidth(settings.thickness);
        /* // Malvares small circles
        this.circle = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.circle.setColor(Color.BLACK);
        this.circle.setStrokeWidth(1f);
        this.circle.setStyle(Paint.Style.STROKE);
        */

        // Widgets text colors
        this.batteryPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.batteryPaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), settings.font));
        this.batteryPaint.setTextSize(settings.batteryFontSize);
        this.batteryPaint.setColor(settings.batteryTextColor);
        this.batteryPaint.setTextAlign( (settings.batteryAlignLeftBool) ? Paint.Align.LEFT : Paint.Align.CENTER );

        this.stepsPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.stepsPaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), settings.font));
        this.stepsPaint.setTextSize(settings.stepsFontSize);
        this.stepsPaint.setColor(settings.stepsTextColor);
        this.stepsPaint.setTextAlign( (settings.stepsAlignLeftBool) ? Paint.Align.LEFT : Paint.Align.CENTER );

        this.sportPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.sportPaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), settings.font));
        this.sportPaint.setTextSize(settings.todayDistanceFontSize);
        this.sportPaint.setColor(settings.todayDistanceTextColor);
        this.sportPaint.setTextAlign( (settings.todayDistanceAlignLeftBool) ? Paint.Align.LEFT : Paint.Align.CENTER );

        this.roadPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.roadPaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), settings.font));
        this.roadPaint.setTextSize(settings.totalDistanceFontSize);
        this.roadPaint.setColor(settings.totalDistanceTextColor);
        this.roadPaint.setTextAlign( (settings.totalDistanceAlignLeftBool) ? Paint.Align.LEFT : Paint.Align.CENTER );

        // Over circles layout
        this.overlay = service.getResources().getDrawable(R.drawable.over_circles_layout);
        this.overlay.setBounds(0, 0, 320, 300);

        // Fix angles
        if(settings.batteryCircleBool){
            // Steps
            settings.startAngleSteps = settings.startAngleSteps+3;
            settings.fullAngleSteps = settings.fullAngleSteps - 6;
            // Sports
            settings.startAngleSport = settings.startAngleSport+3;
            settings.fullAngleSport = settings.fullAngleSport - 6;
        }
        if(settings.stepCircleBool){
            // Sports
            settings.startAngleSport = settings.startAngleSport+3;
            settings.fullAngleSport = settings.fullAngleSport - 6;
        }
    }

    @Override
    public void draw(Canvas canvas, float width, float height, float centerX, float centerY) {
        // Circle progress bars
        int count = canvas.save();
        int radius = Math.round(Math.min(width / 2, height / 2));// + this.thickness + this.padding;
        RectF oval = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
        RectF oval2 = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
        RectF oval3 = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);

        // Rotate from 0 to 270
        canvas.rotate(90, centerX, centerY);

        // Draw full length circle (as a background/empty circle)
        if(settings.circlesBackgroundBool) {
            this.ring.setColor(settings.backgroundColour);
            if (settings.batteryCircleBool) {
                radius = radius - settings.padding - settings.thickness;
                oval = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
                canvas.drawArc(oval, settings.startAngleBattery, settings.fullAngleBattery, false, ring);
            }
            if (settings.stepCircleBool) {
                radius = radius - settings.padding - settings.thickness;
                oval2 = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
                canvas.drawArc(oval2, settings.startAngleSteps, settings.fullAngleSteps, false, ring);
            }
            if (settings.todayDistanceCircleBool) {
                radius = radius - settings.padding - settings.thickness;
                oval3 = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
                canvas.drawArc(oval3, settings.startAngleSport, settings.fullAngleSport, false, ring);
            }
        }

        // Draw circle up to the point we want
        // Battery
        if (this.batterySweepAngle != null && settings.batteryCircleBool) {
            this.ring.setColor((settings.sltp_circle_color>0)?settings.colorCodes[settings.sltp_circle_color-1]:settings.batteryColour);
            canvas.drawArc(oval, settings.startAngleBattery, batterySweepAngle, false, ring);
            /*
            //Malvarez small circles at the end
            float px = getPointX(oval, centerX, startAngleBattery, batterySweepAngle);
            float py = getPointY(oval, centerY, startAngleBattery, batterySweepAngle);
            canvas.drawCircle(px, py, this.thickness / 3f, circle);
            canvas.drawCircle(px, py, this.thickness / 6f, circle);
            */
        }
        // Steps
        if (this.stepsSweepAngle != null && settings.stepCircleBool) {
            this.ring.setColor((settings.sltp_circle_color>0)?settings.colorCodes[settings.sltp_circle_color-1]:settings.stepsColour);
            canvas.drawArc(oval2, settings.startAngleSteps, stepsSweepAngle, false, ring);
        }
        // Today distance
        if (this.sportSweepAngle != null && settings.todayDistanceCircleBool) {
            this.ring.setColor((settings.sltp_circle_color>0)?settings.colorCodes[settings.sltp_circle_color-1]:settings.sportColour);
            canvas.drawArc(oval3, settings.startAngleSport, sportSweepAngle, false, ring);
        }
        canvas.restoreToCount(count);

        // Over circles layout
        this.overlay.draw(canvas);

        // Show battery
        if (this.batteryData != null && settings.batteryBool) {
            String text = String.format("%02d%%", this.batteryData.getLevel() * 100 / this.batteryData.getScale());
            canvas.drawText(text, settings.batteryTextLeft, settings.batteryTextTop, batteryPaint);
        }

        // Show steps
        if (this.stepsData != null && settings.stepsBool) {
            String text = String.format("%s", this.stepsData.getSteps());
            canvas.drawText(text, settings.stepsTextLeft, settings.stepsTextTop, stepsPaint);
        }

        // Show today distance
        if (this.sportData != null && settings.todayDistanceBool) {
            String units = (settings.showDistanceUnits)?" km":"";
            String text = String.format("%.2f", this.sportData.getDistance());
            canvas.drawText(text+units, settings.todayDistanceTextLeft, settings.todayDistanceTextTop, sportPaint);
        }

        // Show total distance
        if(settings.totalDistanceBool){
            String units = (settings.showDistanceUnits)?" km":"";
            if (this.roadData != null) {
                String text = String.format("%.2f", this.roadData.getDistance());
                canvas.drawText(text+units, settings.totalDistanceTextLeft, settings.totalDistanceTextTop, roadPaint);
            }else{
                //String text = String.format("0.00 km");
                canvas.drawText("0.00"+units, settings.totalDistanceTextLeft, settings.totalDistanceTextTop, roadPaint);
            }
        }
    }

    @Override
    public void onDataUpdate(DataType type, Object value) {
        switch (type) {
            case STEPS:
                onSteps((Steps) value);
                break;
            case BATTERY:
                onBatteryData((Battery) value);
                break;
            case DISTANCE:
                onDistanceData((TodayDistance) value);
                break;
            case TOTAL_DISTANCE:
                onTotaldistanceData((TotalDistance) value);
                break;
        }
    }

    @Override
    public List<DataType> getDataTypes() {
        return Arrays.asList(DataType.BATTERY, DataType.STEPS, DataType.DISTANCE, DataType.TOTAL_DISTANCE);
    }

    // Update steps variables
    private void onSteps(Steps steps) {
        this.stepsData = steps;
        if (stepsData == null || stepsData.getTarget() == 0) {
            this.stepsSweepAngle = 0f;
        } else {
            this.stepsSweepAngle = Math.min(settings.fullAngleSteps, settings.fullAngleSteps * (stepsData.getSteps() / (float) stepsData.getTarget()));
        }
    }

    // Update battery variables
    private void onBatteryData(Battery battery) {
        this.batteryData = battery;
        if (batteryData == null) {
            this.batterySweepAngle = 0f;
        } else {
            float scale = batteryData.getLevel() / (float) batteryData.getScale();
            this.batterySweepAngle = Math.min(settings.fullAngleBattery, settings.fullAngleBattery * scale);
        }
    }

    // Update today distance variables
    private void onDistanceData(TodayDistance distance) {
        this.sportData = distance;
        if (sportData == null || sportData.getDistance() <= 0) {
            this.sportSweepAngle = 0f;
        } else {
            double scale = sportData.getDistance() / 3.0d;
            this.sportSweepAngle = (float) Math.min(settings.fullAngleSport, settings.fullAngleSport * scale);
        }
    }

    // Update total distance variables
    private void onTotaldistanceData (TotalDistance distance){
        this.roadData = distance;
    }

    // Malvarez small circles functions
    private float getPointX(RectF oval, float cx, float startAngle, float sweepAngle) {
        float width = oval.right - oval.left;
        return (float) (cx + (width / 2D) * Math.cos((sweepAngle + startAngle) * Math.PI / 180));
    }

    private float getPointY(RectF oval, float cy, float startAngle, float sweepAngle) {
        float height = oval.bottom - oval.top;
        return (float) (cy + (height / 2D) * Math.sin((sweepAngle + startAngle) * Math.PI / 180));
    }

    // SLTP mode (Screen off)
    @Override
    public List<SlptViewComponent> buildSlptViewComponent(Service service) {
        return buildSlptViewComponent(service, false);
    }

    public List<SlptViewComponent> buildSlptViewComponent(Service service, boolean better_resolution) {
        better_resolution = better_resolution && settings.better_resolution_when_raising_hand;

        // Variables
        List<SlptViewComponent> slpt_objects = new ArrayList<>();
        int tmp_left;

        // It's a bird, it's a plane... nope... it's a font.
        Typeface timeTypeFace = ResourceManager.getTypeFace(service.getResources(), settings.font);

        // Show battery
        if(settings.batteryBool){
            SlptLinearLayout power = new SlptLinearLayout();
            SlptPictureView percentage = new SlptPictureView();
            percentage.setStringPicture("%");
            power.add(new SlptPowerNumView());
            power.add(percentage);
            power.setTextAttrForAll(
                    settings.batteryFontSize,
                    service.getResources().getColor(R.color.battery_colour_slpt),
                    timeTypeFace
            );
            // Position based on screen on
            power.alignX = 2;
            power.alignY = 0;
            tmp_left = (int) settings.batteryTextLeft;
            if(!settings.batteryAlignLeftBool) {
                // If text is centered, set rectangle
                power.setRect(
                        (int) (2 * tmp_left + 640),
                        (int) settings.batteryFontSize
                );
                tmp_left = -320;
            }
            power.setStart(
                    tmp_left,
                    (int) (settings.batteryTextTop-((float)settings.font_ratio/100)*settings.batteryFontSize)
            );
            slpt_objects.add(power);
        }

        // Show steps (today)
        if(settings.stepsBool){
            SlptLinearLayout steps = new SlptLinearLayout();
            steps.add(new SlptTodayStepNumView());
            steps.setTextAttrForAll(
                    settings.stepsFontSize,
                    service.getResources().getColor(R.color.steps_colour_slpt),
                    timeTypeFace
            );
            // Position based on screen on
            steps.alignX = 2;
            steps.alignY = 0;
            tmp_left = (int) settings.stepsTextLeft;
            if(!settings.stepsAlignLeftBool) {
                // If text is centered, set rectangle
                steps.setRect(
                        (int) (2 * tmp_left + 640),
                        (int) settings.stepsFontSize
                );
                tmp_left = -320;
            }
            steps.setStart(
                    (int) tmp_left,
                    (int) (settings.stepsTextTop-((float)settings.font_ratio/100)*settings.stepsFontSize)
            );
            slpt_objects.add(steps);
        }


        // It's just a . get over it!
        SlptPictureView point = new SlptPictureView();
        point.setStringPicture(".");

        // set "km"
        SlptPictureView kilometer = new SlptPictureView();
        kilometer.setStringPicture(" km");

        // Show today distance
        if(settings.todayDistanceBool){
            SlptLinearLayout sport = new SlptLinearLayout();
            sport.add(new SlptTodaySportDistanceFView());
            sport.add(point);
            sport.add(new SlptTodaySportDistanceLView());
            // Show or Not Units
            if(settings.showDistanceUnits) {
                sport.add(kilometer);
            }
            sport.setTextAttrForAll(
                    settings.todayDistanceFontSize,
                    service.getResources().getColor(R.color.today_distance_colour_slpt),
                    timeTypeFace
            );
            // Position based on screen on
            sport.alignX = 2;
            sport.alignY = 0;
            tmp_left = (int) settings.todayDistanceTextLeft;
            if(!settings.todayDistanceAlignLeftBool) {
                // If text is centered, set rectangle
                sport.setRect(
                        (int) (2 * tmp_left + 640),
                        (int) settings.todayDistanceFontSize
                );
                tmp_left = -320;
            }
            sport.setStart(
                    (int) tmp_left,
                    (int) (settings.todayDistanceTextTop-((float)settings.font_ratio/100)*settings.todayDistanceFontSize)
            );
            slpt_objects.add(sport);
        }

        // Show total distance
        if(settings.totalDistanceBool){
            SlptLinearLayout road = new SlptLinearLayout();
            road.add(new SlptTotalDistanceFView());
            road.add(point);
            road.add(new SlptTotalDistanceLView());
            // Show or Not Units
            if(settings.showDistanceUnits) {
                road.add(kilometer);
            }
            road.setTextAttrForAll(
                    settings.totalDistanceFontSize,
                    service.getResources().getColor(R.color.total_distance_colour_slpt),
                    timeTypeFace
            );
            // Position based on screen on
            road.alignX = 2;
            road.alignY = 0;
            tmp_left = (int) settings.totalDistanceTextLeft;
            if(!settings.totalDistanceAlignLeftBool) {
                // If text is centered, set rectangle
                road.setRect(
                        (int) (2 * tmp_left + 640),
                        (int) settings.totalDistanceFontSize
                );
                tmp_left = -320;
            }
            road.setStart(
                    (int) tmp_left,
                    (int) (settings.totalDistanceTextTop-((float)settings.font_ratio/100)*settings.totalDistanceFontSize)
            );
            slpt_objects.add(road);
        }

        // CIRCLE PROGRESS BARS
        // Draw background image
        int temp_ring = 1;
        int count_widgets = 0;
        if (settings.batteryCircleBool) {
            count_widgets++;
        }
        if (settings.stepCircleBool) {
            count_widgets++;
        }
        if (settings.todayDistanceCircleBool) {
            count_widgets++;
        }
        if(settings.circlesBackgroundBool) {
            SlptPictureView ring_background = new SlptPictureView();
            if (count_widgets == 1) {
                ring_background.setImagePicture(SimpleFile.readFileFromAssets(service, "slpt_circles/ring1"+ ((better_resolution)?"_better":"") +"_splt_bg.png"));
            } else if (count_widgets == 2) {
                ring_background.setImagePicture(SimpleFile.readFileFromAssets(service, "slpt_circles/ring2_splt_bg.png"));
            } else {
                ring_background.setImagePicture(SimpleFile.readFileFromAssets(service, "slpt_circles/ring3_splt_bg.png"));
            }
            slpt_objects.add(ring_background);
        }

        // Battery
        if (settings.batteryCircleBool) {
            SlptPowerArcAnglePicView localSlptPowerArcAnglePicView = new SlptPowerArcAnglePicView();
            localSlptPowerArcAnglePicView.setImagePicture(SimpleFile.readFileFromAssets(service, "slpt_circles/ring1_splt"+settings.sltp_circle_color+".png"));
            localSlptPowerArcAnglePicView.setStart((int) service.getResources().getDimension(R.dimen.battery_circle_left), (int) service.getResources().getDimension(R.dimen.battery_circle_top));
            localSlptPowerArcAnglePicView.start_angle = settings.startAngleBattery;
            localSlptPowerArcAnglePicView.len_angle = service.getResources().getInteger(R.integer.battery_circle_len_angle);
            localSlptPowerArcAnglePicView.full_angle = settings.fullAngleBattery;
            slpt_objects.add(localSlptPowerArcAnglePicView);
            temp_ring = 2;
        }

        // Steps

        if(settings.stepCircleBool){
            SlptTodayStepArcAnglePicView localSlptTodayStepArcAnglePicView = new SlptTodayStepArcAnglePicView();
            localSlptTodayStepArcAnglePicView.setImagePicture(SimpleFile.readFileFromAssets(service, "slpt_circles/ring"+temp_ring+"_splt"+settings.sltp_circle_color+".png"));
            localSlptTodayStepArcAnglePicView.setStart((int) service.getResources().getDimension(R.dimen.steps_circle_left), (int) service.getResources().getDimension(R.dimen.steps_circle_top));
            localSlptTodayStepArcAnglePicView.start_angle = settings.startAngleSteps;
            localSlptTodayStepArcAnglePicView.len_angle = service.getResources().getInteger(R.integer.steps_circle_len_angle);
            localSlptTodayStepArcAnglePicView.full_angle = settings.fullAngleSteps;
            slpt_objects.add(localSlptTodayStepArcAnglePicView);
        }

        // Total distance
        if(settings.todayDistanceCircleBool) {
            temp_ring = count_widgets;
            SlptTodayDistanceArcAnglePicView localSlptTodayDistanceArcAnglePicView = new SlptTodayDistanceArcAnglePicView();
            localSlptTodayDistanceArcAnglePicView.setImagePicture(SimpleFile.readFileFromAssets(service, "slpt_circles/ring" + temp_ring + "_splt" + settings.sltp_circle_color + ".png"));
            localSlptTodayDistanceArcAnglePicView.setStart((int) service.getResources().getDimension(R.dimen.today_distance_circle_left), (int) service.getResources().getDimension(R.dimen.today_distance_circle_top));
            localSlptTodayDistanceArcAnglePicView.start_angle = settings.startAngleSport;
            localSlptTodayDistanceArcAnglePicView.len_angle = service.getResources().getInteger(R.integer.today_distance_circle_len_angle);
            localSlptTodayDistanceArcAnglePicView.full_angle = settings.fullAngleSport;
            slpt_objects.add(localSlptTodayDistanceArcAnglePicView);
        }

        return slpt_objects;
    }
}