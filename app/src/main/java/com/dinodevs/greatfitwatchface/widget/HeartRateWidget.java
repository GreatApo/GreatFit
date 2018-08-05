package com.dinodevs.greatfitwatchface.widget;

import android.app.Service;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;

import com.dinodevs.greatfitwatchface.settings.LoadSettings;
import com.ingenic.iwds.slpt.view.core.SlptLinearLayout;
import com.ingenic.iwds.slpt.view.core.SlptPictureView;
import com.ingenic.iwds.slpt.view.core.SlptViewComponent;
import com.ingenic.iwds.slpt.view.sport.SlptLastHeartRateView;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import com.dinodevs.greatfitwatchface.R;
import com.dinodevs.greatfitwatchface.data.DataType;
import com.dinodevs.greatfitwatchface.data.HeartRate;
import com.dinodevs.greatfitwatchface.resource.ResourceManager;


public class HeartRateWidget extends AbstractWidget {

    private TextPaint textPaint;
    private HeartRate heartRate;

    private float textTop;
    private float textLeft;

    private Drawable heartIcon;
    private boolean flashingIconBool;
    private boolean heartAlignLeftBool;
    private boolean showUnits;
    private LoadSettings settings;

    public HeartRateWidget(LoadSettings settings) {
        this.settings = settings;
    }

    @Override
    public void init(Service service) {

        this.textLeft = service.getResources().getDimension(R.dimen.heart_rate_text_left);
        this.textTop = service.getResources().getDimension(R.dimen.heart_rate_text_top);

        // Aling left true or false (false= align center)
        this.heartAlignLeftBool = service.getResources().getBoolean(R.bool.heart_rate_left_align);

        this.textPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        this.textPaint.setColor(service.getResources().getColor(R.color.heart_colour));
        this.textPaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE));
        this.textPaint.setTextSize(service.getResources().getDimension(R.dimen.heart_rate_font_size));
        this.textPaint.setTextAlign( (this.heartAlignLeftBool) ? Paint.Align.LEFT : Paint.Align.CENTER );

        this.flashingIconBool = service.getResources().getBoolean(R.bool.flashing_heart_rate_icon);
        this.heartIcon = service.getResources().getDrawable(R.drawable.flashing_heart, null);
        this.setDrawableBounds(this.heartIcon, service.getResources().getDimension(R.dimen.heart_rate_icon_left), service.getResources().getDimension(R.dimen.heart_rate_icon_top));



        // Show units boolean
        this.showUnits = service.getResources().getBoolean(R.bool.heart_rate_units);
    }

    @Override
    public List<DataType> getDataTypes() {
        return Collections.singletonList(DataType.HEART_RATE);
    }

    @Override
    public void onDataUpdate(DataType type, Object value) {
        this.heartRate = (HeartRate) value;
    }

    @Override
    public void draw(Canvas canvas, float width, float height, float centerX, float centerY) {
        // if units are enabled
        String units = (showUnits)?" bpm":"";
        // Draw Heart rate
        String text = (heartRate == null || heartRate.getHeartRate() < 25) ? "--" : String.format("%d"+units, heartRate.getHeartRate());
        canvas.drawText(text, textLeft, textTop, textPaint);

        // Draw only on NOT even seconds (flashing heart icon)
        if(this.flashingIconBool) {
            Calendar calendar = Calendar.getInstance();
            if (calendar.get(Calendar.SECOND) % 2 == 1) {
                this.heartIcon.draw(canvas);
            }
        }
    }

    @Override
    public List<SlptViewComponent> buildSlptViewComponent(Service service) {
        SlptLinearLayout heart = new SlptLinearLayout();
        heart.add(new SlptLastHeartRateView());
        // Show or Not Units
        if(service.getResources().getBoolean(R.bool.heart_rate_units)) {
            SlptPictureView bpm = new SlptPictureView();
            bpm.setStringPicture(" bpm");
            heart.add(bpm);
        }
        heart.setTextAttrForAll(
                service.getResources().getDimension(R.dimen.heart_rate_font_size),
                service.getResources().getColor(R.color.heart_colour_slpt),
                ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE)
        );
        // Position based on screen on
        heart.alignX = 2;
        heart.alignY = 0;
        int tmp_left = (int) service.getResources().getDimension(R.dimen.heart_rate_text_left);
        if(!service.getResources().getBoolean(R.bool.heart_rate_left_align)) {
            // If text is centered, set rectangle
            heart.setRect(
                    (int) (2 * tmp_left + 640),
                    (int) (service.getResources().getDimension(R.dimen.heart_rate_font_size))
            );
            tmp_left = -320;
        }
        heart.setStart(
                tmp_left,
                (int) (service.getResources().getDimension(R.dimen.heart_rate_text_top)-((float)service.getResources().getInteger(R.integer.font_ratio)/100)*service.getResources().getDimension(R.dimen.heart_rate_font_size))
        );
        // Hide if disabled
        if(!service.getResources().getBoolean(R.bool.heart_rate)){heart.show=false;}

        return Collections.<SlptViewComponent>singletonList(heart);
    }
}
