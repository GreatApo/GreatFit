package com.dinodevs.greatfitwatchface.widget;

import android.app.Service;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.Log;

import com.dinodevs.greatfitwatchface.settings.LoadSettings;
import com.huami.watch.watchface.util.Util;
import com.ingenic.iwds.slpt.view.core.SlptBatteryView;
import com.ingenic.iwds.slpt.view.core.SlptLinearLayout;
import com.ingenic.iwds.slpt.view.core.SlptViewComponent;
import com.ingenic.iwds.slpt.view.sport.SlptPowerNumView;
import com.ingenic.iwds.slpt.view.utils.SimpleFile;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.dinodevs.greatfitwatchface.R;
import com.dinodevs.greatfitwatchface.data.Battery;
import com.dinodevs.greatfitwatchface.data.DataType;
import com.dinodevs.greatfitwatchface.resource.ResourceManager;


public class BatteryWidget extends AbstractWidget {
    private Battery batteryData;
    private TextPaint batteryFont;
    private Drawable batteryIcon0;
    private Drawable batteryIcon10;
    private Drawable batteryIcon20;
    private Drawable batteryIcon30;
    private Drawable batteryIcon40;
    private Drawable batteryIcon50;
    private Drawable batteryIcon60;
    private Drawable batteryIcon70;
    private Drawable batteryIcon80;
    private Drawable batteryIcon90;
    private Drawable batteryIcon100;
    private Boolean batteryImgBool;
    private float leftBattery;
    private float topBattery;
    private String[] numbers = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    private String sBattery;
    private int BatteryNum;
    private LoadSettings settings;

    public BatteryWidget(LoadSettings settings) {
        this.settings = settings;
    }

    public void init(Service service) {
        this.leftBattery = service.getResources().getDimension(R.dimen.battery_icon_left);
        this.topBattery = service.getResources().getDimension(R.dimen.battery_icon_top);

        this.batteryImgBool = service.getResources().getBoolean(R.bool.battery_icon);
        if(this.batteryImgBool) {
            this.batteryIcon0 = service.getResources().getDrawable(R.drawable.battery0, null);
            this.batteryIcon10 = service.getResources().getDrawable(R.drawable.battery1, null);
            this.batteryIcon20 = service.getResources().getDrawable(R.drawable.battery2, null);
            this.batteryIcon30 = service.getResources().getDrawable(R.drawable.battery3, null);
            this.batteryIcon40 = service.getResources().getDrawable(R.drawable.battery4, null);
            this.batteryIcon50 = service.getResources().getDrawable(R.drawable.battery5, null);
            this.batteryIcon60 = service.getResources().getDrawable(R.drawable.battery6, null);
            this.batteryIcon70 = service.getResources().getDrawable(R.drawable.battery7, null);
            this.batteryIcon80 = service.getResources().getDrawable(R.drawable.battery8, null);
            this.batteryIcon90 = service.getResources().getDrawable(R.drawable.battery9, null);
            this.batteryIcon100 = service.getResources().getDrawable(R.drawable.battery10, null);
            this.batteryIcon0.setBounds((int) this.leftBattery, (int) this.topBattery, ((int) this.leftBattery) + batteryIcon0.getIntrinsicWidth(), ((int) this.topBattery) + batteryIcon0.getIntrinsicHeight());
            this.batteryIcon10.setBounds((int) this.leftBattery, (int) this.topBattery, ((int) this.leftBattery) + batteryIcon10.getIntrinsicWidth(), ((int) this.topBattery) + batteryIcon10.getIntrinsicHeight());
            this.batteryIcon20.setBounds((int) this.leftBattery, (int) this.topBattery, ((int) this.leftBattery) + batteryIcon20.getIntrinsicWidth(), ((int) this.topBattery) + batteryIcon20.getIntrinsicHeight());
            this.batteryIcon30.setBounds((int) this.leftBattery, (int) this.topBattery, ((int) this.leftBattery) + batteryIcon30.getIntrinsicWidth(), ((int) this.topBattery) + batteryIcon30.getIntrinsicHeight());
            this.batteryIcon40.setBounds((int) this.leftBattery, (int) this.topBattery, ((int) this.leftBattery) + batteryIcon40.getIntrinsicWidth(), ((int) this.topBattery) + batteryIcon40.getIntrinsicHeight());
            this.batteryIcon50.setBounds((int) this.leftBattery, (int) this.topBattery, ((int) this.leftBattery) + batteryIcon50.getIntrinsicWidth(), ((int) this.topBattery) + batteryIcon50.getIntrinsicHeight());
            this.batteryIcon60.setBounds((int) this.leftBattery, (int) this.topBattery, ((int) this.leftBattery) + batteryIcon60.getIntrinsicWidth(), ((int) this.topBattery) + batteryIcon60.getIntrinsicHeight());
            this.batteryIcon70.setBounds((int) this.leftBattery, (int) this.topBattery, ((int) this.leftBattery) + batteryIcon70.getIntrinsicWidth(), ((int) this.topBattery) + batteryIcon70.getIntrinsicHeight());
            this.batteryIcon80.setBounds((int) this.leftBattery, (int) this.topBattery, ((int) this.leftBattery) + batteryIcon80.getIntrinsicWidth(), ((int) this.topBattery) + batteryIcon80.getIntrinsicHeight());
            this.batteryIcon90.setBounds((int) this.leftBattery, (int) this.topBattery, ((int) this.leftBattery) + batteryIcon90.getIntrinsicWidth(), ((int) this.topBattery) + batteryIcon90.getIntrinsicHeight());
            this.batteryIcon100.setBounds((int) this.leftBattery, (int) this.topBattery, ((int) this.leftBattery) + batteryIcon100.getIntrinsicWidth(), ((int) this.topBattery) + batteryIcon100.getIntrinsicHeight());
        }
    }

    public void onDataUpdate(DataType type, Object value) {
        this.batteryData = (Battery) value;
    }

    public List<DataType> getDataTypes() {
        return Collections.singletonList(DataType.BATTERY);
    }

    public void draw(Canvas canvas, float width, float height, float centerX, float centerY) {
        if (this.batteryData != null && this.batteryImgBool) {
            this.sBattery = String.format("%02d", new Object[]{Integer.valueOf((this.batteryData.getLevel() * 100) / this.batteryData.getScale())});

            if (sBattery.equals("100")) {
                this.batteryIcon100.draw(canvas);
            } else {
                if (sBattery.substring(0, 1).equals("0")) {
                    this.batteryIcon0.draw(canvas);
                }
                if (sBattery.substring(0, 1).equals("1")) {
                    this.batteryIcon10.draw(canvas);
                }
                if (sBattery.substring(0, 1).equals("2")) {
                    this.batteryIcon20.draw(canvas);
                }
                if (sBattery.substring(0, 1).equals("3")) {
                    this.batteryIcon30.draw(canvas);
                }
                if (sBattery.substring(0, 1).equals("4")) {
                    this.batteryIcon40.draw(canvas);
                }
                if (sBattery.substring(0, 1).equals("5")) {
                    this.batteryIcon50.draw(canvas);
                }
                if (sBattery.substring(0, 1).equals("6")) {
                    this.batteryIcon60.draw(canvas);
                }
                if (sBattery.substring(0, 1).equals("7")) {
                    this.batteryIcon70.draw(canvas);
                }
                if (sBattery.substring(0, 1).equals("8")) {
                    this.batteryIcon80.draw(canvas);
                }
                if (sBattery.substring(0, 1).equals("9")) {
                    this.batteryIcon90.draw(canvas);
                }
            }

            if(this.sBattery != null && this.sBattery.matches("[-+]?\\d*\\.?\\d+")) {
                this.BatteryNum = Integer.parseInt(this.sBattery);
            }else{
                Log.w("DinoDevs-GreatFit", "battery value null or not a number ("+this.batteryData.getLevel()+" / "+this.batteryData.getScale()+")");
            }
        }
    }

    public List<SlptViewComponent> buildSlptViewComponent(Service service) {
        int battery_steps = service.getResources().getInteger(R.integer.battery_steps);
        byte[][] arrayOfByte = new byte[battery_steps][];
        for (int i=0; i<arrayOfByte.length; i++){
            arrayOfByte[i] = SimpleFile.readFileFromAssets(service, String.format("slpt_battery/battery%d.png", i));
        }

        SlptBatteryView localSlptBatteryView = new SlptBatteryView(battery_steps);
        localSlptBatteryView.setImagePictureArray(arrayOfByte);
        localSlptBatteryView.setStart((int) service.getResources().getDimension(R.dimen.battery_icon_left), (int) service.getResources().getDimension(R.dimen.battery_icon_top));

        if(!service.getResources().getBoolean(R.bool.battery_icon)){localSlptBatteryView.show=false;}

        return Arrays.asList(new SlptViewComponent[]{localSlptBatteryView});
    }
}
