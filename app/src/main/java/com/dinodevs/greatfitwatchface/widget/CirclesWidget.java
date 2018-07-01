package com.dinodevs.greatfitwatchface.widget;

import android.app.Service;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

import com.huami.watch.watchface.util.Util;
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

    private Paint ring;
    private Paint circle;

    private Paint batteryPaint;
    private Paint stepsPaint;
    private Paint sportPaint;
    private Paint roadPaint;
    private int backgroundColour;
    private boolean circlesBackgroundBool;
    private boolean batteryBool;
    private boolean stepsBool;
    private boolean todayDistanceBool;
    private boolean totalDistanceBool;
    private boolean batteryAlignLeftBool;
    private boolean stepsAlignLeftBool;
    private boolean todayDistanceAlignLeftBool;
    private boolean totalDistanceAlignLeftBool;

    private int batteryColour;
    private int stepsColour;
    private int sportColour;
    private int thickness;
    private int padding;

    private Battery batteryData;
    private Float batterySweepAngle = null;

    private Steps stepsData;
    private Float stepsSweepAngle = null;

    private TodayDistance sportData;
    private Float sportSweepAngle = null;

    private TotalDistance roadData;

    private float startAngleBattery = 30;
    private float arcSizeBattery = 360 - startAngleBattery - startAngleBattery;

    private float startAngleSteps = startAngleBattery + 3;
    private float arcSizeSteps = 360 - startAngleSteps - startAngleSteps;

    private float startAngleSport = 30;
    private float arcSizeSport = 360 - 2*startAngleSport;

    private boolean batteryCircleBool;
    private boolean stepCircleBool;
    private boolean todayDistanceCircleBool;

    private float batteryTextLeft;
    private float batteryTextTop;
    private float stepsTextLeft;
    private float stepsTextTop;
    private float sportTextLeft;
    private float sportTextTop;
    private float roadTextLeft;
    private float roadTextTop;

    private boolean showUnits;
    private Drawable background;

    @Override
    public void init(Service service) {
        // Get widget text show/hide booleans
        this.batteryBool = service.getResources().getBoolean(R.bool.battery);
        this.stepsBool = service.getResources().getBoolean(R.bool.steps);
        this.todayDistanceBool = service.getResources().getBoolean(R.bool.today_distance);
        this.totalDistanceBool = service.getResources().getBoolean(R.bool.total_distance);

        // Get circles show/hide booleans
        this.batteryCircleBool = service.getResources().getBoolean(R.bool.battery_circle);
        this.stepCircleBool = service.getResources().getBoolean(R.bool.steps_circle);
        this.todayDistanceCircleBool = service.getResources().getBoolean(R.bool.today_distance_circle);
        this.circlesBackgroundBool = service.getResources().getBoolean(R.bool.circles_background);

        // Circles variables
        this.startAngleBattery = service.getResources().getInteger(R.integer.battery_circle_start_angle) - 180;
        this.arcSizeBattery = service.getResources().getInteger(R.integer.battery_circle_full_angle);

        this.startAngleSteps = service.getResources().getInteger(R.integer.steps_circle_start_angle) - 180;
        this.arcSizeSteps = service.getResources().getInteger(R.integer.steps_circle_full_angle);

        this.startAngleSport = service.getResources().getInteger(R.integer.today_distance_circle_start_angle) - 180;
        this.arcSizeSport = service.getResources().getInteger(R.integer.today_distance_circle_full_angle);

        this.thickness = (int) service.getResources().getDimension(R.dimen.circles_thickness);
        this.padding = (int) service.getResources().getDimension(R.dimen.circles_padding);

        // Get circles colors
        this.backgroundColour = service.getResources().getColor(R.color.circles_background);
        this.batteryColour = service.getResources().getColor(R.color.battery_circle_colour);
        this.stepsColour = service.getResources().getColor(R.color.steps_circle_colour);
        this.sportColour = service.getResources().getColor(R.color.today_distance_circle_colour);

        this.ring = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.ring.setStrokeCap(Paint.Cap.ROUND);
        this.ring.setStyle(Paint.Style.STROKE);
        this.ring.setStrokeWidth(this.thickness);
        /* // Malvares small circles
        this.circle = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.circle.setColor(Color.BLACK);
        this.circle.setStrokeWidth(1f);
        this.circle.setStyle(Paint.Style.STROKE);
        */

        // Aling left true or false (false= align center)
        this.batteryAlignLeftBool = service.getResources().getBoolean(R.bool.battery_left_align);
        this.stepsAlignLeftBool = service.getResources().getBoolean(R.bool.steps_left_align);
        this.todayDistanceAlignLeftBool = service.getResources().getBoolean(R.bool.today_distance_left_align);
        this.totalDistanceAlignLeftBool = service.getResources().getBoolean(R.bool.total_distance_left_align);

        // Widgets text colors
        this.batteryPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.batteryPaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE));
        this.batteryPaint.setTextSize(service.getResources().getDimension(R.dimen.steps_font_size));
        this.batteryPaint.setColor(service.getResources().getColor(R.color.battery_colour));
        this.batteryPaint.setTextAlign( (this.batteryAlignLeftBool) ? Paint.Align.LEFT : Paint.Align.CENTER );

        this.stepsPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.stepsPaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE));
        this.stepsPaint.setTextSize(service.getResources().getDimension(R.dimen.steps_font_size));
        this.stepsPaint.setColor(service.getResources().getColor(R.color.steps_colour));
        this.stepsPaint.setTextAlign( (this.stepsAlignLeftBool) ? Paint.Align.LEFT : Paint.Align.CENTER );

        this.sportPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.sportPaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE));
        this.sportPaint.setTextSize(service.getResources().getDimension(R.dimen.today_distance_font_size));
        this.sportPaint.setColor(service.getResources().getColor(R.color.today_distance_colour));
        this.sportPaint.setTextAlign( (this.todayDistanceAlignLeftBool) ? Paint.Align.LEFT : Paint.Align.CENTER );

        this.roadPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.roadPaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE));
        this.roadPaint.setTextSize(service.getResources().getDimension(R.dimen.total_distance_font_size));
        this.roadPaint.setColor(service.getResources().getColor(R.color.total_distance_colour));
        this.roadPaint.setTextAlign( (this.totalDistanceAlignLeftBool) ? Paint.Align.LEFT : Paint.Align.CENTER );

        // Text positions
        this.batteryTextLeft = service.getResources().getDimension(R.dimen.battery_text_left);
        this.batteryTextTop = service.getResources().getDimension(R.dimen.battery_text_top);
        this.stepsTextLeft = service.getResources().getDimension(R.dimen.steps_text_left);
        this.stepsTextTop = service.getResources().getDimension(R.dimen.steps_text_top);
        this.sportTextLeft = service.getResources().getDimension(R.dimen.today_distance_text_left);
        this.sportTextTop = service.getResources().getDimension(R.dimen.today_distance_text_top);
        this.roadTextLeft = service.getResources().getDimension(R.dimen.total_distance_text_left);
        this.roadTextTop = service.getResources().getDimension(R.dimen.total_distance_text_top);

        // Show units boolean
        this.showUnits = service.getResources().getBoolean(R.bool.distance_units);

        // Over circles layout
        this.background = service.getResources().getDrawable(R.drawable.over_circles_layout);
        this.background.setBounds(0, 0, 320, 300);

        // Fix angles
        if(this.batteryCircleBool){
            // Steps
            startAngleSteps = startAngleSteps+3;
            arcSizeSteps = arcSizeSteps - 6;
            // Sports
            startAngleSport = startAngleSport+3;
            arcSizeSport = arcSizeSport - 6;
        }
        if(this.stepCircleBool){
            // Sports
            startAngleSport = startAngleSport+3;
            arcSizeSport = arcSizeSport - 6;
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
        if(this.circlesBackgroundBool) {
            this.ring.setColor(this.backgroundColour);
            if (this.batteryCircleBool) {
                radius = radius - this.padding - this.thickness;
                oval = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
                canvas.drawArc(oval, startAngleBattery, arcSizeBattery, false, ring);
            }
            if (this.stepCircleBool) {
                radius = radius - this.padding - this.thickness;
                oval2 = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
                canvas.drawArc(oval2, startAngleSteps, arcSizeSteps, false, ring);
            }
            if (this.todayDistanceCircleBool) {
                radius = radius - this.padding - this.thickness;
                oval3 = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
                canvas.drawArc(oval3, startAngleSport, arcSizeSport, false, ring);
            }
        }

        // Draw circle up to the point we want
        // Battery
        if (this.batterySweepAngle != null && this.batteryCircleBool) {
            this.ring.setColor(this.batteryColour);
            canvas.drawArc(oval, startAngleBattery, batterySweepAngle, false, ring);
            /*
            //Malvarez small circles at the end
            float px = getPointX(oval, centerX, startAngleBattery, batterySweepAngle);
            float py = getPointY(oval, centerY, startAngleBattery, batterySweepAngle);
            canvas.drawCircle(px, py, this.thickness / 3f, circle);
            canvas.drawCircle(px, py, this.thickness / 6f, circle);
            */
        }
        // Steps
        if (this.stepsSweepAngle != null && this.stepCircleBool) {
            this.ring.setColor(this.stepsColour);
            canvas.drawArc(oval2, startAngleSteps, stepsSweepAngle, false, ring);
        }
        // Today distance
        if (this.sportSweepAngle != null && this.todayDistanceCircleBool) {
            this.ring.setColor(this.sportColour);
            canvas.drawArc(oval3, startAngleSport, sportSweepAngle, false, ring);
        }
        canvas.restoreToCount(count);

        // Over circles layout
        this.background.draw(canvas);

        // Show battery
        if (this.batteryData != null && this.batteryBool) {
            String text = String.format("%02d%%", this.batteryData.getLevel() * 100 / this.batteryData.getScale());
            canvas.drawText(text, batteryTextLeft, batteryTextTop, batteryPaint);
        }

        // Show steps
        if (this.stepsData != null && this.stepsBool) {
            String text = String.format("%s", this.stepsData.getSteps());
            canvas.drawText(text, stepsTextLeft, stepsTextTop, stepsPaint);
        }

        // Show today distance
        if (this.sportData != null && this.todayDistanceBool) {
            String units = (showUnits)?" km":"";
            String text = String.format("%.2f", this.sportData.getDistance());
            canvas.drawText(text+units, sportTextLeft, sportTextTop, sportPaint);
        }

        // Show total distance
        if(this.totalDistanceBool){
            String units = (showUnits)?" km":"";
            if (this.roadData != null) {
                String text = String.format("%.2f", this.roadData.getDistance());
                canvas.drawText(text+units, roadTextLeft, roadTextTop, roadPaint);
            }else{
                //String text = String.format("0.00 km");
                canvas.drawText("0.00"+units, roadTextLeft, roadTextTop, roadPaint);
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
            this.stepsSweepAngle = Math.min(arcSizeSteps, arcSizeSteps * (stepsData.getSteps() / (float) stepsData.getTarget()));
        }
    }

    // Update battery variables
    private void onBatteryData(Battery battery) {
        this.batteryData = battery;
        if (batteryData == null) {
            this.batterySweepAngle = 0f;
        } else {
            float scale = batteryData.getLevel() / (float) batteryData.getScale();
            this.batterySweepAngle = Math.min(arcSizeBattery, arcSizeBattery * scale);
        }
    }

    // Update today distance variables
    private void onDistanceData(TodayDistance distance) {
        this.sportData = distance;
        if (sportData == null || sportData.getDistance() <= 0) {
            this.sportSweepAngle = 0f;
        } else {
            double scale = sportData.getDistance() / 3.0d;
            this.sportSweepAngle = (float) Math.min(arcSizeSport, arcSizeSport * scale);
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
        // Variables
        this.batteryCircleBool = service.getResources().getBoolean(R.bool.battery_circle);
        this.stepCircleBool = service.getResources().getBoolean(R.bool.steps_circle);
        this.todayDistanceCircleBool = service.getResources().getBoolean(R.bool.today_distance_circle);
        int sltp_circle_color = service.getResources().getInteger(R.integer.sltp_circle_color);
        int tmp_left;

        // It's a bird, it's a plane... nope... it's a font.
        Typeface timeTypeFace = ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE);

        // Show battery
        SlptLinearLayout power = new SlptLinearLayout();
        SlptPictureView percentage = new SlptPictureView();
        percentage.setStringPicture("%");
        power.add(new SlptPowerNumView());
        power.add(percentage);
        power.setTextAttrForAll(
                service.getResources().getDimension(R.dimen.battery_font_size),
                service.getResources().getColor(R.color.battery_colour_slpt),
                timeTypeFace
        );
        // Position based on screen on
        power.alignX = 2;
        power.alignY=0;
        tmp_left = (int) service.getResources().getDimension(R.dimen.battery_text_left);
        if(!service.getResources().getBoolean(R.bool.battery_left_align)) {
            // If text is centered, set rectangle
            power.setRect(
                    (int) (2 * tmp_left + 640),
                    (int) (service.getResources().getDimension(R.dimen.battery_font_size))
            );
            tmp_left = -320;
        }
        power.setStart(
                tmp_left,
                (int) (service.getResources().getDimension(R.dimen.battery_text_top)-((float)service.getResources().getInteger(R.integer.font_ratio)/100)*service.getResources().getDimension(R.dimen.battery_font_size))
        );
        // Hide if disabled
        if(!service.getResources().getBoolean(R.bool.battery)){power.show=false;}

        // Show steps (today)
        SlptLinearLayout steps = new SlptLinearLayout();
        steps.add(new SlptTodayStepNumView());
        steps.setTextAttrForAll(
                service.getResources().getDimension(R.dimen.steps_font_size),
                service.getResources().getColor(R.color.steps_colour_slpt),
                timeTypeFace
        );
        // Position based on screen on
        steps.alignX = 2;
        steps.alignY=0;
        tmp_left = (int) service.getResources().getDimension(R.dimen.steps_text_left);
        if(!service.getResources().getBoolean(R.bool.steps_left_align)) {
            // If text is centered, set rectangle
            steps.setRect(
                    (int) (2 * tmp_left + 640),
                    (int) (service.getResources().getDimension(R.dimen.steps_font_size))
            );
            tmp_left = -320;
        }
        steps.setStart(
                (int) tmp_left,
                (int) (service.getResources().getDimension(R.dimen.steps_text_top)-((float)service.getResources().getInteger(R.integer.font_ratio)/100)*service.getResources().getDimension(R.dimen.steps_font_size))
        );
        // Hide if disabled
        if(!service.getResources().getBoolean(R.bool.steps)){steps.show=false;}


        // It's just a . get over it!
        SlptPictureView point = new SlptPictureView();
        point.setStringPicture(".");

        // set "km"
        SlptPictureView kilometer = new SlptPictureView();
        kilometer.setStringPicture(" km");

        // Show today distance
        SlptLinearLayout sport = new SlptLinearLayout();
        sport.add(new SlptTodaySportDistanceFView());
        sport.add(point);
        sport.add(new SlptTodaySportDistanceLView());
        // Show or Not Units
        if(service.getResources().getBoolean(R.bool.distance_units)) {
            sport.add(kilometer);
        }
        sport.setTextAttrForAll(
                service.getResources().getDimension(R.dimen.today_distance_font_size),
                service.getResources().getColor(R.color.today_distance_colour_slpt),
                timeTypeFace
        );
        // Position based on screen on
        sport.alignX = 2;
        sport.alignY=0;
        tmp_left = (int) service.getResources().getDimension(R.dimen.today_distance_text_left);
        if(!service.getResources().getBoolean(R.bool.today_distance_left_align)) {
            // If text is centered, set rectangle
            sport.setRect(
                    (int) (2 * tmp_left + 640),
                    (int) (service.getResources().getDimension(R.dimen.today_distance_font_size))
            );
            tmp_left = -320;
        }
        sport.setStart(
                (int) tmp_left,
                (int) (service.getResources().getDimension(R.dimen.today_distance_text_top)-((float)service.getResources().getInteger(R.integer.font_ratio)/100)*service.getResources().getDimension(R.dimen.today_distance_font_size))
        );
        // Hide if disabled
        if(!service.getResources().getBoolean(R.bool.today_distance)){sport.show=false;}


        // Show total distance
        SlptLinearLayout road = new SlptLinearLayout();
        road.add(new SlptTotalDistanceFView());
        road.add(point);
        road.add(new SlptTotalDistanceLView());
        // Show or Not Units
        if(service.getResources().getBoolean(R.bool.distance_units)) {
            road.add(kilometer);
        }
        road.setTextAttrForAll(
                service.getResources().getDimension(R.dimen.total_distance_font_size),
                service.getResources().getColor(R.color.total_distance_colour_slpt),
                timeTypeFace
        );
        // Position based on screen on
        road.alignX = 2;
        road.alignY=0;
        tmp_left = (int) service.getResources().getDimension(R.dimen.total_distance_text_left);
        if(!service.getResources().getBoolean(R.bool.total_distance_left_align)) {
            // If text is centered, set rectangle
            road.setRect(
                    (int) (2 * tmp_left + 640),
                    (int) (service.getResources().getDimension(R.dimen.total_distance_font_size))
            );
            tmp_left = -320;
        }
        road.setStart(
                (int) tmp_left,
                (int) (service.getResources().getDimension(R.dimen.total_distance_text_top)-((float)service.getResources().getInteger(R.integer.font_ratio)/100)*service.getResources().getDimension(R.dimen.total_distance_font_size))
        );
        // Hide if disabled
        if(!service.getResources().getBoolean(R.bool.total_distance)){road.show=false;}

        // Circle bars
        // Draw background image
        int count_widgets = 0;
        if(this.batteryCircleBool){count_widgets++;}
        if(this.stepCircleBool){count_widgets++;}
        if(this.todayDistanceCircleBool){count_widgets++;}

        SlptPictureView ring_background = new SlptPictureView();
        if(count_widgets==1) {
            ring_background.setImagePicture(SimpleFile.readFileFromAssets(service, "slpt_circles/ring1_splt_bg.png"));
        }else if(count_widgets==2) {
            ring_background.setImagePicture(SimpleFile.readFileFromAssets(service, "slpt_circles/ring2_splt_bg.png"));
        }else{
            ring_background.setImagePicture(SimpleFile.readFileFromAssets(service, "slpt_circles/ring3_splt_bg.png"));
        }
        if(!service.getResources().getBoolean(R.bool.circles_background)){ring_background.show=false;}

        // Battery
        SlptPowerArcAnglePicView localSlptPowerArcAnglePicView = new SlptPowerArcAnglePicView();
        localSlptPowerArcAnglePicView.setImagePicture(SimpleFile.readFileFromAssets(service, "slpt_circles/ring1_splt"+sltp_circle_color+".png"));
        localSlptPowerArcAnglePicView.setStart((int) service.getResources().getDimension(R.dimen.battery_circle_left), (int) service.getResources().getDimension(R.dimen.battery_circle_top));
        localSlptPowerArcAnglePicView.start_angle = service.getResources().getInteger(R.integer.battery_circle_start_angle);
        localSlptPowerArcAnglePicView.len_angle = service.getResources().getInteger(R.integer.battery_circle_len_angle);
        localSlptPowerArcAnglePicView.full_angle = service.getResources().getInteger(R.integer.battery_circle_full_angle);
        if(!this.batteryCircleBool){localSlptPowerArcAnglePicView.show=false;}

        // Steps
        int temp_ring = 1;
        if(this.batteryCircleBool){temp_ring = 2;}
        SlptTodayStepArcAnglePicView localSlptTodayStepArcAnglePicView = new SlptTodayStepArcAnglePicView();
        localSlptTodayStepArcAnglePicView.setImagePicture(SimpleFile.readFileFromAssets(service, "slpt_circles/ring"+temp_ring+"_splt"+sltp_circle_color+".png"));
        localSlptTodayStepArcAnglePicView.setStart((int) service.getResources().getDimension(R.dimen.steps_circle_left), (int) service.getResources().getDimension(R.dimen.steps_circle_top));
        localSlptTodayStepArcAnglePicView.start_angle = service.getResources().getInteger(R.integer.steps_circle_start_angle);
        localSlptTodayStepArcAnglePicView.len_angle = service.getResources().getInteger(R.integer.steps_circle_len_angle);
        localSlptTodayStepArcAnglePicView.full_angle = service.getResources().getInteger(R.integer.steps_circle_full_angle);
        if(!this.stepCircleBool){localSlptTodayStepArcAnglePicView.show=false;}

        // Total distance
        if(count_widgets>0){temp_ring = count_widgets;}
        SlptTodayDistanceArcAnglePicView localSlptTodayDistanceArcAnglePicView = new SlptTodayDistanceArcAnglePicView();
        localSlptTodayDistanceArcAnglePicView.setImagePicture(SimpleFile.readFileFromAssets(service, "slpt_circles/ring"+temp_ring+"_splt"+sltp_circle_color+".png"));
        localSlptTodayDistanceArcAnglePicView.setStart((int) service.getResources().getDimension(R.dimen.today_distance_circle_left), (int) service.getResources().getDimension(R.dimen.today_distance_circle_top));
        localSlptTodayDistanceArcAnglePicView.start_angle = service.getResources().getInteger(R.integer.today_distance_circle_start_angle);
        localSlptTodayDistanceArcAnglePicView.len_angle = service.getResources().getInteger(R.integer.today_distance_circle_len_angle);
        localSlptTodayDistanceArcAnglePicView.full_angle = service.getResources().getInteger(R.integer.today_distance_circle_full_angle);
        if(!this.todayDistanceCircleBool){localSlptTodayDistanceArcAnglePicView.show=false;}

        return Arrays.asList(new SlptViewComponent[]{steps, sport, road, power, ring_background, localSlptPowerArcAnglePicView, localSlptTodayStepArcAnglePicView, localSlptTodayDistanceArcAnglePicView});
    }
}