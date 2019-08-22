package com.dinodevs.greatfitwatchface.widget;

import android.app.Service;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.text.TextPaint;

import com.dinodevs.greatfitwatchface.data.DataType;
import com.dinodevs.greatfitwatchface.data.TodayFloor;
import com.dinodevs.greatfitwatchface.resource.ResourceManager;
import com.dinodevs.greatfitwatchface.settings.LoadSettings;
import com.huami.watch.watchface.util.Util;
import com.ingenic.iwds.slpt.view.core.Picture;
import com.ingenic.iwds.slpt.view.core.SlptLinearLayout;
import com.ingenic.iwds.slpt.view.core.SlptPictureView;
import com.ingenic.iwds.slpt.view.core.SlptViewComponent;
import com.ingenic.iwds.slpt.view.sport.SlptSportUtil;
import com.ingenic.iwds.slpt.view.sport.SlptTodayFloorNumView;
import com.ingenic.iwds.slpt.view.utils.SimpleFile;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class FloorWidget extends AbstractWidget {
    private TextPaint textPaint;
    private TodayFloor todayFloor;
    private LoadSettings settings;
    private Bitmap icon;
    private String[] digitalNums = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private Service mService;

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
        this.textPaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), settings.font));
        this.textPaint.setTextSize(settings.floorsFontSize);
        this.textPaint.setTextAlign( (settings.floorsAlignLeft) ? Paint.Align.LEFT : Paint.Align.CENTER );

        if(settings.floorsIcon){
            this.icon = Util.decodeImage(service.getResources(),"icons/"+settings.is_white_bg+"floors.png");
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

    // ************************************
    // Create image based on text (by LFOM)
    // ************************************
    private byte[] floorStringToImagePicture(String string) {
        return stringToImagePicture(
                string,
                settings.floorsFontSize,
                settings.floorsColor,
                settings.font,
                (settings.white_bg?"#ffffff":"#000000") // black or white background
        );
    }

    private byte[] stringToImagePicture(String string, float textSize, int textColor, ResourceManager.Font textFont, String bgColor) {
        Bitmap bitmap = textAsBitmap(string, textSize, textColor, textFont, bgColor);
        return decodeBitmap(bitmap);
    }
    private static byte[] decodeBitmap(Bitmap bmp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        bmp.recycle();
        return byteArray;
    }
    private Bitmap textAsBitmap(String text, float textSize, int textColor, ResourceManager.Font textFont, String bgColor) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTypeface(ResourceManager.getTypeFace(this.mService.getResources(), textFont));
        paint.setTextAlign(Paint.Align.LEFT);
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 0.5f); // round
        int height = (int) ((baseline + paint.descent() + 0.5f) - ((((float) this.settings.font_ratio) / 100.0f) * paint.descent()));
        System.out.println("GreatFit FloorsWidget width: " + width + " | height: " + height + " | baseline: " + baseline + " | descent: " + paint.descent() + " | text size: " + textSize + " | color: " + textColor + " | bg color: "+ Color.parseColor(bgColor) +" | font: " + textFont.toString());
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        Paint rect_paint = new Paint();
        rect_paint.setStyle(Paint.Style.FILL);
        rect_paint.setColor(Color.parseColor(bgColor));
        canvas.drawRect(0, 0, width, height, rect_paint);
        canvas.drawText(text, 0, baseline, paint);
        return image;
    }
    // ************************************

    // SLPT mode, screen off
    @Override
    public List<SlptViewComponent> buildSlptViewComponent(Service service) {
        return buildSlptViewComponent(service, false);
    }

    public List<SlptViewComponent> buildSlptViewComponent(Service service, boolean better_resolution) {
        //better_resolution = better_resolution && settings.better_resolution_when_raising_hand;
        List<SlptViewComponent> slpt_objects = new ArrayList<>();
        this.mService = service;

        // Do not show in SLPT (but show on raise of hand)
        boolean show_all = (!settings.clock_only_slpt || better_resolution);
        if (!show_all)
            return slpt_objects;


        if(settings.floors>0){
            // Show or Not icon
            if (settings.floorsIcon) {
                SlptPictureView floorsIcon = new SlptPictureView();
                floorsIcon.setImagePicture( SimpleFile.readFileFromAssets(service, ( (better_resolution)?"26wc_":"slpt_" )+"icons/"+settings.is_white_bg+"floors.png") );
                floorsIcon.setStart(
                        (int) settings.floorsIconLeft,
                        (int) settings.floorsIconTop
                );
                slpt_objects.add(floorsIcon);
            }

            SlptLinearLayout floorsLayout = new SlptLinearLayout();
            // define digits as pictures
            byte[][] SlptNumArray = {
                    floorStringToImagePicture(this.digitalNums[0]),
                    floorStringToImagePicture(this.digitalNums[1]),
                    floorStringToImagePicture(this.digitalNums[2]),
                    floorStringToImagePicture(this.digitalNums[3]),
                    floorStringToImagePicture(this.digitalNums[4]),
                    floorStringToImagePicture(this.digitalNums[5]),
                    floorStringToImagePicture(this.digitalNums[6]),
                    floorStringToImagePicture(this.digitalNums[7]),
                    floorStringToImagePicture(this.digitalNums[8]),
                    floorStringToImagePicture(this.digitalNums[9])
            };
            // create bg image with 0 written (floors are never 0)
            floorsLayout.background.picture = new Picture.ImagePicture(SlptNumArray[0]);

            /*
            SlptPictureView floorStr0 = new SlptPictureView();
            floorStr0.setStringPicture( "0" );
            floorsLayout.add(floorStr0);

            SlptLinearLayout floorStr1 = new SlptLinearLayout();
            floorStr1.add(new SlptTodayFloorNumView());
            floorStr1.background.color = Color.parseColor((settings.white_bg?"#ffffff":"#000000"));
            floorStr1.setStringPictureArrayForAll(this.digitalNums);
            floorsLayout.add(floorStr1);
            */

            //floorsLayout.background.color = Color.parseColor((settings.white_bg?"#ffffff":"#000000"));
            floorsLayout.add(new SlptTodayFloorNumView());
            floorsLayout.setImagePictureArrayForAll(SlptNumArray);

            //floorsLayout.setStringPictureArrayForAll(this.digitalNums);
            /*
            floorsLayout.setTextAttrForAll(
                    settings.floorsFontSize,
                    settings.floorsColor,
                    ResourceManager.getTypeFace(service.getResources(), settings.font)
            );
            */

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

            // Invalid floor
            /* NOT TESTED
            SlptPictureView invalidFloor = new SlptPictureView();
            invalidFloor.setStringPicture( "-" );
            // Position based on screen on
            invalidFloor.alignX = 2;
            invalidFloor.alignY = 0;
            if(!settings.floorsAlignLeft) {
                // If text is centered, set rectangle
                invalidFloor.setRect(
                        (int) (2 * tmp_left + 640),
                        (int) (((float)settings.font_ratio/100)*settings.floorsFontSize)
                );
                tmp_left = -320;
            }
            invalidFloor.setStart(
                    (int) tmp_left,
                    (int) (settings.floorsTop-((float)settings.font_ratio/100)*settings.floorsFontSize)
            );
            SlptSportUtil.setTodayFloorInvalidView(invalidFloor);
             */
        }

        return slpt_objects;
    }
}
