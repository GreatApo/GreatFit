package com.dinodevs.greatfitwatchface.widget;

import android.app.Service;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;

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
    private Float heartRateSweepAngle;
    private Integer angleLength;
    private Integer maxHeartRate = 200;
    private Bitmap heart_rateIcon;
    private LoadSettings settings;

    // Constructor
    public HeartRateWidget(LoadSettings settings) {
        this.settings = settings;
    }

    // Screen-on init (runs once)
    @Override
    public void init(Service service) {
        this.textPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        this.textPaint.setColor(settings.heart_rateColor);
        this.textPaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE));
        this.textPaint.setTextSize(settings.heart_rateFontSize);
        this.textPaint.setTextAlign( (settings.heart_rateAlignLeft) ? Paint.Align.LEFT : Paint.Align.CENTER );

        if(settings.heart_rateIcon){
            this.heart_rateIcon = Util.decodeImage(service.getResources(),"icons/heart_rate.png");
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
    }

    // Draw screen-on
    @Override
    public void draw(Canvas canvas, float width, float height, float centerX, float centerY) {
        // Draw heart rate element
        if(settings.heart_rate>0) {
            if(settings.heart_rateIcon){
                canvas.drawBitmap(this.heart_rateIcon, settings.heart_rateIconLeft, settings.heart_rateIconTop, settings.mGPaint);
            }

            // if units are enabled
            String units = (settings.heart_rateUnits) ? " bpm" : "";
            // Draw Heart rate
            String text = (heartRate == null || heartRate.getHeartRate() < 25) ? "--" : heartRate.getHeartRate() + units;
            canvas.drawText(text, settings.heart_rateLeft, settings.heart_rateTop, textPaint);

            // todo
            // Draw only on NOT even seconds (flashing heart icon)
            /*
            if(settings.flashing_heart_rate) {
                Calendar calendar = Calendar.getInstance();
                if (calendar.get(Calendar.SECOND) % 2 == 1) {
                    this.heartIcon.draw(canvas);
                }
            }
            */
        }

        // Draw progress element
        //if(settings.heart_rateProg>0) {
            // todo
        //}
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
        int tmp_left;

        // Draw heart rate element
        if(settings.heart_rate>0) {
            // Show or Not icon
            if (settings.heart_rateIcon) {
                SlptPictureView heart_rateIcon = new SlptPictureView();
                heart_rateIcon.setImagePicture( SimpleFile.readFileFromAssets(service, ( (better_resolution)?"":"slpt_" )+"icons/heart_rate.png") );
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
                    ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE)
            );
            // Position based on screen on
            heart.alignX = 2;
            heart.alignY = 0;
            tmp_left = (int) settings.heart_rateLeft;
            if (!settings.heart_rateAlignLeft) {
                // If text is centered, set rectangle
                heart.setRect(
                        (int) (2 * tmp_left + 640),
                        (int) (settings.heart_rateFontSize)
                );
                tmp_left = -320;
            }
            heart.setStart(
                    tmp_left,
                    (int) (settings.heart_rateTop - ((float) settings.font_ratio / 100) * settings.heart_rateFontSize)
            );
            slpt_objects.add(heart);
        }

        // Draw heart rate element
        //if(settings.heart_rateProg>0) {
            // todo
            /*
            SlptPictureView ring_background = new SlptPictureView();
            ring_background.setImagePicture(SimpleFile.readFileFromAssets(service, ( (better_resolution)?"":"slpt_" )+"progress/heart_rate_bar.png"));
            slpt_objects.add(ring_background);

            SlptArcAnglePicView localHeartRateArcAnglePicView = new SlptArcAnglePicView();
            localHeartRateArcAnglePicView.setImagePicture(SimpleFile.readFileFromAssets(service, "slpt_circles/ring" + temp_ring + "_splt" + settings.sltp_circle_color + ".png"));
            localHeartRateArcAnglePicView.setStart((int) service.getResources().getDimension(R.dimen.today_distance_circle_left), (int) service.getResources().getDimension(R.dimen.today_distance_circle_top));
            localHeartRateArcAnglePicView.start_angle = settings.startAngleSport;
            localHeartRateArcAnglePicView.len_angle = service.getResources().getInteger(R.integer.today_distance_circle_len_angle);
            localHeartRateArcAnglePicView.full_angle = settings.fullAngleSport;
            localHeartRateArcAnglePicView.draw_clockwise = 1;
            localHeartRateArcAnglePicView.RegisterPictureParam = ;
            slpt_objects.add(localHeartRateArcAnglePicView);
            */
        //}

        return slpt_objects;
    }
}
