package com.dinodevs.greatfitwatchface.widget;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextPaint;
import android.util.Log;

import com.dinodevs.greatfitwatchface.AbstractWatchFace;
import com.dinodevs.greatfitwatchface.settings.LoadSettings;
import com.huami.watch.watchface.util.Util;
import com.ingenic.iwds.slpt.view.arc.SlptArcAnglePicView;
import com.ingenic.iwds.slpt.view.arc.SlptTodayDistanceArcAnglePicView;
import com.ingenic.iwds.slpt.view.core.SlptLinearLayout;
import com.ingenic.iwds.slpt.view.core.SlptPictureView;
import com.ingenic.iwds.slpt.view.core.SlptViewComponent;
import com.ingenic.iwds.slpt.view.sport.SlptLastHeartRateView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import com.dinodevs.greatfitwatchface.R;
import com.dinodevs.greatfitwatchface.data.DataType;
import com.dinodevs.greatfitwatchface.data.HeartRate;
import com.dinodevs.greatfitwatchface.resource.ResourceManager;
import com.ingenic.iwds.slpt.view.utils.SimpleFile;

public class HeartRateWidget extends AbstractWidget {

    private TextPaint textPaint;
    private HeartRate heartRate;
    private Float heart_rateSweepAngle=0f;
    private Integer lastSlptUpdateHeart_rate = 0;
    private Integer angleLength;
    private float maxHeartRate = 200f;
    private Paint ring;
    private Bitmap heart_rateIcon;
    private Bitmap heart_rate_flashingIcon;
    private LoadSettings settings;
    private Service mService;

    // Constructor
    public HeartRateWidget(LoadSettings settings) {
        this.settings = settings;
        
        if(!(settings.heart_rateProg>0 && settings.heart_rateProgType==0)){return;}
        if(settings.heart_rateProgClockwise==1) {
            this.angleLength = (settings.heart_rateProgEndAngle < settings.heart_rateProgStartAngle) ? 360 - (settings.heart_rateProgStartAngle - settings.heart_rateProgEndAngle) : settings.heart_rateProgEndAngle - settings.heart_rateProgStartAngle;
        }else{
            this.angleLength = (settings.heart_rateProgEndAngle > settings.heart_rateProgStartAngle) ? 360 - (settings.heart_rateProgStartAngle - settings.heart_rateProgEndAngle) : settings.heart_rateProgEndAngle - settings.heart_rateProgStartAngle;
        }
    }

    // Screen-on init (runs once)
    @Override
    public void init(Service service) {
        this.mService = service;

        this.textPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        this.textPaint.setColor(settings.heart_rateColor);
        this.textPaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), settings.font));
        this.textPaint.setTextSize(settings.heart_rateFontSize);
        this.textPaint.setTextAlign( (settings.heart_rateAlignLeft) ? Paint.Align.LEFT : Paint.Align.CENTER );

        if(settings.heart_rateIcon){
            this.heart_rateIcon = Util.decodeImage(service.getResources(),"icons/"+settings.is_white_bg+"heart_rate.png");
            this.heart_rate_flashingIcon = Util.decodeImage(service.getResources(),"icons/"+settings.is_white_bg+"heart_rate_flashing.png");
        }
        
        // Progress Bar Circle
        if(settings.heart_rateProg>0 && settings.heart_rateProgType==0){
            this.ring = new Paint(Paint.ANTI_ALIAS_FLAG);
            this.ring.setStrokeCap(Paint.Cap.ROUND);
            this.ring.setStyle(Paint.Style.STROKE);
            this.ring.setStrokeWidth(settings.heart_rateProgThickness);
        }
    }

    // Register update listeners
    @Override
    public List<DataType> getDataTypes() {
        return Collections.singletonList(DataType.HEART_RATE);
    }

    // Value updater
    @Override
    public void onDataUpdate(DataType type, Object value) {
        // Heart rate class
        this.heartRate = (HeartRate) value;

        // Bar angle
        //this.heartRateSweepAngle = (this.heartRate == null)? 0f : Math.min( this.angleLength, this.angleLength*(heartRate.getHeartRate()/this.maxHeartRate) ) ;
        // Bar angle
        if(settings.heart_rateProg>0 && settings.heart_rateProgType==0 && heartRate!=null && heartRate.getHeartRate()>25 ) {
            this.heart_rateSweepAngle = this.angleLength * Math.min(heartRate.getHeartRate()/this.maxHeartRate,1f);

            //Log.w("DinoDevs-GreatFit", "Heart rate update: "+heartRate.getHeartRate()+", Sweep angle:"+ heart_rateSweepAngle+", %"+(Math.abs(heartRate.getHeartRate()-this.lastSlptUpdateHeart_rate)/this.maxHeartRate));

            if(Math.abs(heartRate.getHeartRate()-this.lastSlptUpdateHeart_rate)/this.maxHeartRate>0.05){
                this.lastSlptUpdateHeart_rate = heartRate.getHeartRate();
                // Save the value to get it on the new slpt service
                SharedPreferences sharedPreferences = mService.getSharedPreferences(mService.getPackageName()+"_settings", Context.MODE_PRIVATE);
                sharedPreferences.edit().putInt( "temp_heart_rate", this.lastSlptUpdateHeart_rate).apply();
                // Restart slpt
                ((AbstractWatchFace) this.mService).restartSlpt();
            }
        }
    }

    // Draw screen-on
    @Override
    public void draw(Canvas canvas, float width, float height, float centerX, float centerY) {
        // Draw heart rate element
        if(settings.heart_rate>0) {
            // Draw icon
            if(settings.heart_rateIcon){
                // Draw flashing heart icon
                if(settings.flashing_heart_rate_icon) {
                    Calendar calendar = Calendar.getInstance();
                    if (calendar.get(Calendar.SECOND) % 2 == 1) {
                        canvas.drawBitmap(this.heart_rate_flashingIcon, settings.heart_rateIconLeft, settings.heart_rateIconTop, settings.mGPaint);
                    }else{
                        canvas.drawBitmap(this.heart_rateIcon, settings.heart_rateIconLeft, settings.heart_rateIconTop, settings.mGPaint);
                    }
                }else{
                    canvas.drawBitmap(this.heart_rateIcon, settings.heart_rateIconLeft, settings.heart_rateIconTop, settings.mGPaint);
                }
            }

            // if units are enabled
            String units = (settings.heart_rateUnits) ? " bpm" : "";
            // Draw Heart rate
            String text = (heartRate == null || heartRate.getHeartRate() < 25) ? "--" : heartRate.getHeartRate() + units;
            canvas.drawText(text, settings.heart_rateLeft, settings.heart_rateTop, textPaint);
        }

        // heart_rate bar
        if(settings.heart_rateProg>0 && settings.heart_rateProgType==0) {
            int count = canvas.save();

            // Rotate canvas to 0 degrees = 12 o'clock
            canvas.rotate(-90, centerX, centerY);

            // Define circle
            float radius = settings.heart_rateProgRadius - settings.heart_rateProgThickness;
            RectF oval = new RectF(settings.heart_rateProgLeft - radius, settings.heart_rateProgTop - radius, settings.heart_rateProgLeft + radius, settings.heart_rateProgTop + radius);

            // Background
            if(settings.heart_rateProgBgBool) {
                this.ring.setColor(Color.parseColor("#999999"));
                canvas.drawArc(oval, settings.heart_rateProgStartAngle, this.angleLength, false, ring);
            }

            this.ring.setColor(settings.colorCodes[settings.heart_rateProgColorIndex]);
            canvas.drawArc(oval, settings.heart_rateProgStartAngle, this.heart_rateSweepAngle, false, ring);

            canvas.restoreToCount(count);
        }
    }

    // This doesn't work. There is an error when getting the index
    /*
    public static final Uri CONTENT_HEART_URI = Uri.parse("content://com.huami.watch.health.heartdata");
    private int getSlptHeartRate(){
        Integer system_heart_rate = heartRate.getHeartRate();
        if(system_heart_rate==0){
            try {
                Cursor cursor = mService.getContentResolver().query(CONTENT_HEART_URI, null, null, null, "utc_time DESC LIMIT 1");
                //int index = cursor.getColumnIndex( "heart_rate" );
                system_heart_rate = cursor.getInt( 0 );
                Log.w("DinoDevs-GreatFit", "Heart rate: slpt getHertRate() = "+system_heart_rate);
            }catch(Exception e){
                // sth here
                Log.w("DinoDevs-GreatFit", "Heart rate error: "+e.toString());
            }
        }
        return system_heart_rate;
    }
    */

    // Screen-off (SLPT)
    @Override
    public List<SlptViewComponent> buildSlptViewComponent(Service service) {
        return buildSlptViewComponent(service, false);
    }

    // Screen-off (SLPT) - Better screen quality
    public List<SlptViewComponent> buildSlptViewComponent(Service service, boolean better_resolution) {
        better_resolution = better_resolution && settings.better_resolution_when_raising_hand;
        this.mService = service;
        List<SlptViewComponent> slpt_objects = new ArrayList<>();
        int tmp_left;

        // Do not show in SLPT (but show on raise of hand)
        boolean show_all = (!settings.clock_only_slpt || better_resolution);
        if (!show_all)
            return slpt_objects;

        // Draw heart rate element
        if(settings.heart_rate>0) {
            // Show or Not icon
            if (settings.heart_rateIcon) {
                SlptPictureView heart_rateIcon = new SlptPictureView();
                heart_rateIcon.setImagePicture( SimpleFile.readFileFromAssets(service, ( (better_resolution)?"26wc_":"slpt_" )+"icons/"+settings.is_white_bg+"heart_rate.png") );
                heart_rateIcon.setStart(
                        (int) settings.heart_rateIconLeft,
                        (int) settings.heart_rateIconTop
                );
                slpt_objects.add(heart_rateIcon);
            }

            SlptLinearLayout heart = new SlptLinearLayout();
            heart.add(new SlptLastHeartRateView());
            // Show or Not Units
            if (settings.heart_rateUnits) {
                SlptPictureView bpm = new SlptPictureView();
                bpm.setStringPicture(" bpm");
                heart.add(bpm);
            }
            heart.setTextAttrForAll(
                    settings.heart_rateFontSize,
                    settings.heart_rateColor,
                    ResourceManager.getTypeFace(service.getResources(), settings.font)
            );
            // Position based on screen on
            heart.alignX = 2;
            heart.alignY = 0;
            tmp_left = (int) settings.heart_rateLeft;
            if (!settings.heart_rateAlignLeft) {
                // If text is centered, set rectangle
                heart.setRect(
                        (int) (2 * tmp_left + 640),
                        (int) (((float)settings.font_ratio/100)*settings.heart_rateFontSize)
                );
                tmp_left = -320;
            }
            heart.setStart(
                    tmp_left,
                    (int) (settings.heart_rateTop - ((float) settings.font_ratio / 100) * settings.heart_rateFontSize)
            );
            slpt_objects.add(heart);
        }

        // Draw heart rate bar
        if(settings.heart_rateProg>0 && settings.heart_rateProgType==0){
            // Draw background image
            if(settings.heart_rateProgBgBool) {
                SlptPictureView ring_background = new SlptPictureView();
                ring_background.setImagePicture(SimpleFile.readFileFromAssets(service, ( (better_resolution)?"":"slpt_" )+"circles/ring1_bg.png"));
                ring_background.setStart((int) (settings.heart_rateProgLeft-settings.heart_rateProgRadius), (int) (settings.heart_rateProgTop-settings.heart_rateProgRadius));
                slpt_objects.add(ring_background);
            }

            //if(heartRate==null){heartRate = new HeartRate(0);}

            SlptArcAnglePicView localSlptArcAnglePicView = new SlptArcAnglePicView();
            localSlptArcAnglePicView.setImagePicture(SimpleFile.readFileFromAssets(service, ( (better_resolution)?"":"slpt_" )+settings.heart_rateProgSlptImage));
            localSlptArcAnglePicView.setStart((int) (settings.heart_rateProgLeft-settings.heart_rateProgRadius), (int) (settings.heart_rateProgTop-settings.heart_rateProgRadius));
            localSlptArcAnglePicView.start_angle = (settings.heart_rateProgClockwise==1)? settings.heart_rateProgStartAngle : settings.heart_rateProgEndAngle;
            localSlptArcAnglePicView.len_angle = (int) (this.angleLength * Math.min(settings.temp_heart_rate/this.maxHeartRate,1));
            //Log.w("DinoDevs-GreatFit", "Heart rate: slpt "+settings.temp_heart_rate+", Sweep angle:"+ heart_rateSweepAngle+", %"+(this.angleLength * Math.min(settings.temp_heart_rate/this.maxHeartRate,1)));
            localSlptArcAnglePicView.full_angle = (settings.heart_rateProgClockwise==1)? this.angleLength : -this.angleLength;
            localSlptArcAnglePicView.draw_clockwise = settings.heart_rateProgClockwise;
            slpt_objects.add(localSlptArcAnglePicView);
        }

        return slpt_objects;
    }
}
