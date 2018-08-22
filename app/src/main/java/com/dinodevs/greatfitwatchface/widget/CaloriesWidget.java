package com.dinodevs.greatfitwatchface.widget;

import android.app.Service;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;

import com.dinodevs.greatfitwatchface.settings.LoadSettings;
import com.huami.watch.watchface.util.Util;
import com.ingenic.iwds.slpt.view.core.SlptLinearLayout;
import com.ingenic.iwds.slpt.view.core.SlptPictureView;
import com.ingenic.iwds.slpt.view.core.SlptViewComponent;
import com.ingenic.iwds.slpt.view.sport.SlptTodayCaloriesView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.dinodevs.greatfitwatchface.R;
import com.dinodevs.greatfitwatchface.data.Calories;
import com.dinodevs.greatfitwatchface.data.DataType;
import com.dinodevs.greatfitwatchface.resource.ResourceManager;
import com.ingenic.iwds.slpt.view.utils.SimpleFile;


public class CaloriesWidget extends AbstractWidget {
    private TextPaint textPaint;
    private Calories  calories;
    private Bitmap icon;
    private LoadSettings settings;

    // Constructor
    public CaloriesWidget(LoadSettings settings) {
        this.settings = settings;
    }

    // Screen-on init (runs once)
    @Override
    public void init(Service service) {
        // Font
        this.textPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        this.textPaint.setColor(settings.caloriesColor);
        this.textPaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE));
        this.textPaint.setTextSize(settings.caloriesFontSize);
        this.textPaint.setTextAlign( (settings.caloriesAlignLeft) ? Paint.Align.LEFT : Paint.Align.CENTER );

        if(settings.caloriesIcon){
            this.icon = Util.decodeImage(service.getResources(),"icons/calories.png");
        }
    }

    // Register calories counter
    @Override
    public List<DataType> getDataTypes() {
        return Collections.singletonList(DataType.CALORIES);
    }

    // Calories updater
    @Override
    public void onDataUpdate(DataType type, Object value) {
        this.calories = (Calories) value;
    }

    // Screen on
    @Override
    public void draw(Canvas canvas, float width, float height, float centerX, float centerY) {
        if(settings.calories>0) {
            if(settings.caloriesIcon){
                canvas.drawBitmap(this.icon, settings.caloriesIconLeft, settings.caloriesIconTop, settings.mGPaint);
            }

            String units = (settings.caloriesUnits) ? " kcal" : "";
            canvas.drawText(calories.getCalories() + units, settings.caloriesLeft, settings.caloriesTop, textPaint);
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

        // Show or Not icon
        if (settings.battery_percentIcon) {
            SlptPictureView caloriesIcon = new SlptPictureView();
            caloriesIcon.setImagePicture( SimpleFile.readFileFromAssets(service, ( (better_resolution)?"":"slpt_" )+"icons/calories.png") );
            caloriesIcon.setStart(
                    (int) settings.caloriesIconLeft,
                    (int) settings.caloriesIconTop
            );
            slpt_objects.add(caloriesIcon);
        }

        SlptLinearLayout caloriesLayout = new SlptLinearLayout();
        caloriesLayout.add(new SlptTodayCaloriesView());
        // Show or Not Units
        if(settings.caloriesUnits) {
            SlptPictureView caloriesUnit = new SlptPictureView();
            caloriesUnit.setStringPicture(" kcal");
            caloriesLayout.add(caloriesUnit);
        }
        caloriesLayout.setTextAttrForAll(
                settings.caloriesFontSize,
                settings.caloriesColor,
                ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE)
        );
        // Position based on screen on
        caloriesLayout.alignX = 2;
        caloriesLayout.alignY = 0;
        int tmp_left = (int) settings.caloriesLeft;
        if(!settings.caloriesAlignLeft) {
            // If text is centered, set rectangle
            caloriesLayout.setRect(
                    (int) (2 * tmp_left + 640),
                    (int) (settings.caloriesFontSize)
            );
            tmp_left = -320;
        }
        caloriesLayout.setStart(
                (int) tmp_left,
                (int) (settings.caloriesTop-((float)settings.font_ratio/100)*settings.caloriesFontSize)
        );
        slpt_objects.add(caloriesLayout);

        return slpt_objects;
    }
}
