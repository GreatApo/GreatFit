package com.dinodevs.greatfitwatchface.widget;

import android.app.Service;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;


import com.ingenic.iwds.slpt.view.core.SlptLinearLayout;
import com.ingenic.iwds.slpt.view.core.SlptViewComponent;
import com.ingenic.iwds.slpt.view.sport.SlptTodayFloorNumView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.dinodevs.greatfitwatchface.R;
import com.dinodevs.greatfitwatchface.data.DataType;
import com.dinodevs.greatfitwatchface.data.TodayFloor;
import com.dinodevs.greatfitwatchface.resource.ResourceManager;


public class FloorWidget extends AbstractWidget {
    private TextPaint textPaint;
    private TodayFloor todayFloor;
    private String text;
    private float textTop;
    private float textLeft;
    private Boolean floorsBool;
    private String[] digitalNums = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    @Override
    public void init(Service service){
        this.textLeft = service.getResources().getDimension(R.dimen.floors_text_left);
        this.textTop = service.getResources().getDimension(R.dimen.floors_text_top);

        this.textPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        this.textPaint.setColor(service.getResources().getColor(R.color.floors_colour));
        this.textPaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE));
        this.textPaint.setTextSize(service.getResources().getDimension(R.dimen.floors_font_size));
        this.textPaint.setTextAlign(Paint.Align.CENTER);

        this.floorsBool = service.getResources().getBoolean(R.bool.floor);
    }

    @Override
    public List<DataType> getDataTypes() {
        return Collections.singletonList(DataType.FLOOR);
    }

    @Override
    public void onDataUpdate (DataType type, Object value) {this.todayFloor = (TodayFloor) value;}

    @Override
    public void draw(Canvas canvas, float width, float height, float centerX, float centerY) {
        // Draw floors
        if(this.floorsBool) {
            this.text = String.format("%s", todayFloor.getFloor());
            canvas.drawText(text, textLeft, textTop, textPaint);
        }
    }

    @Override
    public List<SlptViewComponent> buildSlptViewComponent(Service service) {
        // Draw floors
        SlptLinearLayout floors = new SlptLinearLayout();
        floors.add(new SlptTodayFloorNumView());
        floors.setStringPictureArrayForAll(this.digitalNums);
        floors.setTextAttrForAll(
                service.getResources().getDimension(R.dimen.floors_font_size),
                service.getResources().getColor(R.color.floors_colour_slpt),
                ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE)
        );
        // Position based on screen on
        floors.alignX = 2;
        floors.alignY=0;
        floors.setRect(
                (int) (2*service.getResources().getDimension(R.dimen.floors_text_left)+640),
                (int) (service.getResources().getDimension(R.dimen.floors_font_size))
        );
        floors.setStart(
                -320,
                (int) (service.getResources().getDimension(R.dimen.floors_text_top)-((float)service.getResources().getInteger(R.integer.font_ratio)/100)*service.getResources().getDimension(R.dimen.floors_font_size))
        );
        // Hide if disabled
        if(!service.getResources().getBoolean(R.bool.floor)){floors.show=false;}

        return Arrays.asList(new SlptViewComponent[]{floors});
    }
}
