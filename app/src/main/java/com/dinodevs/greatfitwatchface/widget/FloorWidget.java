package com.dinodevs.greatfitwatchface.widget;

import android.app.Service;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;

import com.dinodevs.greatfitwatchface.data.DataType;
import com.dinodevs.greatfitwatchface.data.TodayFloor;
import com.dinodevs.greatfitwatchface.resource.ResourceManager;
import com.dinodevs.greatfitwatchface.settings.LoadSettings;
import com.huami.watch.watchface.util.Util;
import com.ingenic.iwds.slpt.view.core.SlptLinearLayout;
import com.ingenic.iwds.slpt.view.core.SlptPictureView;
import com.ingenic.iwds.slpt.view.core.SlptViewComponent;
import com.ingenic.iwds.slpt.view.sport.SlptTodayFloorNumView;
import com.ingenic.iwds.slpt.view.utils.SimpleFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class FloorWidget extends AbstractWidget {
    private TextPaint textPaint;
    private TodayFloor todayFloor;
    private LoadSettings settings;
    private Bitmap icon;
    private String[] digitalNums = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    // Constructor
    public FloorWidget(LoadSettings settings) {
        this.settings = settings;
    }

    // Screen-on init (runs once)
    @Override
    public void init(Service service) {
        // Font
        this.textPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        this.textPaint.setColor(settings.floorsColor);
        this.textPaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE));
        this.textPaint.setTextSize(settings.floorsFontSize);
        this.textPaint.setTextAlign( (settings.floorsAlignLeft) ? Paint.Align.LEFT : Paint.Align.CENTER );

        if(settings.floorsIcon){
            this.icon = Util.decodeImage(service.getResources(),"icons/floors.png");
        }
    }

    // Register floors counter
    @Override
    public List<DataType> getDataTypes() {
        return Collections.singletonList(DataType.FLOOR);
    }

    // floors updater
    @Override
    public void onDataUpdate(DataType type, Object value) { this.todayFloor = (TodayFloor) value; }

    // Screen on
    @Override
    public void draw(Canvas canvas, float width, float height, float centerX, float centerY) {
        if(settings.floors>0) {
            if(settings.floorsIcon){
                canvas.drawBitmap(this.icon, settings.floorsIconLeft, settings.floorsIconTop, settings.mGPaint);
            }

            canvas.drawText(Integer.toString(todayFloor.getFloor()) , settings.floorsLeft, settings.floorsTop, textPaint);
        }

    }

    // SLPT mode, screen off
    @Override
    public List<SlptViewComponent> buildSlptViewComponent(Service service) {
        return buildSlptViewComponent(service, false);
    }

    public List<SlptViewComponent> buildSlptViewComponent(Service service, boolean better_resolution) {
        //better_resolution = better_resolution && settings.better_resolution_when_raising_hand;
        List<SlptViewComponent> slpt_objects = new ArrayList<>();

        // Do not show in SLPT (but show on raise of hand)
        boolean show_all = (!settings.clock_only_slpt || better_resolution);
        if (!show_all)
            return slpt_objects;


        if(settings.floors>0){
            // Show or Not icon
            if (settings.floorsIcon) {
                SlptPictureView floorsIcon = new SlptPictureView();
                floorsIcon.setImagePicture( SimpleFile.readFileFromAssets(service, ( (better_resolution)?"26wc_":"slpt_" )+"icons/floors.png") );
                floorsIcon.setStart(
                        (int) settings.floorsIconLeft,
                        (int) settings.floorsIconTop
                );
                slpt_objects.add(floorsIcon);
            }

            SlptLinearLayout floorsLayout = new SlptLinearLayout();
            floorsLayout.add(new SlptTodayFloorNumView());
            floorsLayout.setStringPictureArrayForAll(this.digitalNums);
            floorsLayout.setTextAttrForAll(
                    settings.floorsFontSize,
                    settings.floorsColor,
                    ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE)
            );
            // Position based on screen on
            floorsLayout.alignX = 2;
            floorsLayout.alignY = 0;
            int tmp_left = (int) settings.floorsLeft;
            if(!settings.floorsAlignLeft) {
                // If text is centered, set rectangle
                floorsLayout.setRect(
                        (int) (2 * tmp_left + 640),
                        (int) (((float)settings.font_ratio/100)*settings.floorsFontSize)
                );
                tmp_left = -320;
            }
            floorsLayout.setStart(
                    (int) tmp_left,
                    (int) (settings.floorsTop-((float)settings.font_ratio/100)*settings.floorsFontSize)
            );
            slpt_objects.add(floorsLayout);
        }

        return slpt_objects;
    }
}
