package com.dinodevs.greatfitwatchface.widget;

import android.app.Service;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.text.TextPaint;
import android.util.Log;

import com.dinodevs.greatfitwatchface.resource.SlptAnalogAmPmView;
import com.dinodevs.greatfitwatchface.resource.SlptSecondHView;
import com.dinodevs.greatfitwatchface.resource.SlptSecondLView;
import com.ingenic.iwds.datatransactor.elf.HealthInfo;
import com.ingenic.iwds.datatransactor.elf.MusicControlInfo;
import com.ingenic.iwds.datatransactor.elf.PhoneState;
import com.ingenic.iwds.datatransactor.elf.ScheduleInfo;
import com.ingenic.iwds.slpt.view.core.SlptLinearLayout;
import com.ingenic.iwds.slpt.view.core.SlptPictureView;
import com.ingenic.iwds.slpt.view.core.SlptViewComponent;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import com.dinodevs.greatfitwatchface.data.DataType;
import com.dinodevs.greatfitwatchface.data.Time;
import com.dinodevs.greatfitwatchface.resource.ResourceManager;
import com.dinodevs.greatfitwatchface.R;


public class GreatWidget extends AbstractWidget {
    private TextPaint ampmPaint;
    private TextPaint alarmPaint;
    private Time time;
    private String text;
    private String wifi;
    private String alarm;
    private int textint;
    private Boolean alarmBool;
    private Boolean alarmAlignLeftBool;
    private Boolean ampmBool;
    private Boolean ampmAlignLeftBool;
    private Service mService;

    private float ampmTop;
    private float ampmLeft;
    private float alarmTop;
    private float alarmLeft;

    @Override
    public void init(Service service){
        // This service
        this.mService = service;

        //Tests
        //PhoneState var = new PhoneState();
        //Log.w("DinoDevs-GreatFit", var.toString());
        //HealthInfo var2 = new HealthInfo();
        //Log.w("DinoDevs-GreatFit", var2.toString());
        //MusicControlInfo var3 = new MusicControlInfo();
        //Log.w("DinoDevs-GreatFit", var3.toString());
        //ScheduleInfo var4 = new ScheduleInfo(0);
        //Log.w("DinoDevs-GreatFit", var4.toString());
        // Settings.System.getString(service.getContentResolver(), "springboard_widget_order_in");

        // Get AM/PM
        this.time = getSlptTime();

        // Get next alarm
        this.alarm = getAlarm(); // ex: Fri 10:30
        //Log.w("DinoDevs-GreatFit", "Alarm: "+alarm );

        this.ampmLeft = service.getResources().getDimension(R.dimen.ampm_left);
        this.ampmTop = service.getResources().getDimension(R.dimen.ampm_top);
        this.alarmLeft = service.getResources().getDimension(R.dimen.alarm_left);
        this.alarmTop = service.getResources().getDimension(R.dimen.alarm_top);

        this.ampmBool = service.getResources().getBoolean(R.bool.ampm);
        this.ampmAlignLeftBool = service.getResources().getBoolean(R.bool.ampm_left_align);
        this.alarmBool = service.getResources().getBoolean(R.bool.alarm);
        this.alarmAlignLeftBool = service.getResources().getBoolean(R.bool.alarm_left_align);

        this.ampmPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        this.ampmPaint.setColor(service.getResources().getColor(R.color.ampm_colour));
        this.ampmPaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE));
        this.ampmPaint.setTextSize(service.getResources().getDimension(R.dimen.ampm_font_size));
        this.ampmPaint.setTextAlign( (this.ampmAlignLeftBool) ? Paint.Align.LEFT : Paint.Align.CENTER );

        this.alarmPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        this.alarmPaint.setColor(service.getResources().getColor(R.color.alarm_colour));
        this.alarmPaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE));
        this.alarmPaint.setTextSize(service.getResources().getDimension(R.dimen.alarm_font_size));
        this.alarmPaint.setTextAlign( (this.alarmAlignLeftBool) ? Paint.Align.LEFT : Paint.Align.CENTER );
    }

    @Override
    public void draw(Canvas canvas, float width, float height, float centerX, float centerY) {
        // Draw AM or PM, if enabled
        if(this.ampmBool) {
            //Calendar now = Calendar.getInstance();
            //String periode = (now.get(Calendar.AM_PM) == Calendar.AM) ? "AM" : "PM";
            this.text = String.format("%S", this.time.ampmStr);//Capitalize
            canvas.drawText(text, ampmLeft, ampmTop, ampmPaint);
        }
        // Draw Alarm, if enabled
        if(this.alarmBool) {
            canvas.drawText(this.alarm, alarmLeft, alarmTop, alarmPaint);
        }
    }

    @Override
    public List<DataType> getDataTypes() {
        // For many refreshes
        return Arrays.asList(DataType.BATTERY, DataType.STEPS, DataType.DISTANCE, DataType.TOTAL_DISTANCE, DataType.TIME,  DataType.CALORIES,  DataType.DATE,  DataType.HEART_RATE,  DataType.FLOOR, DataType.WEATHER);
    }

    @Override
    public void onDataUpdate(DataType type, Object value) {
        //Log.w("DinoDevs-GreatFit", type.toString()+" => "+value.toString() );
        //this.time = (Time) value;

        switch (type) {
            case TIME:
                // Get AM/PM
                this.time = (Time) value;
                break;
        }

        this.alarm = getAlarm();

        //this.time = getSlptTime();

        //ConnectivityManager connManager = (ConnectivityManager) this.mService.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        //NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        //this.wifi = mWifi.toString();
        //Log.w("DinoDevs-GreatFit", "WiFi: "+mWifi );
        //if (mWifi.isConnected()) {
        // Do whatever
        //}

        //this.time = getSlptTime();
    }


    public Time getSlptTime() {
        Calendar now = Calendar.getInstance();
        int periode = (now.get(Calendar.HOUR_OF_DAY) <= 12)?0:1;
        return new Time(periode);
    }

    public String getSlptBluetooth(Service service) {
        String str = Settings.System.getString(service.getApplicationContext().getContentResolver(), "wifi");
        Log.w("DinoDevs-GreatFit", "Wifi: "+str);
        return (str!=null)?str:"null";
    }

    public String getAlarm() {
        String str = Settings.System.getString(this.mService.getContentResolver(), Settings.System.NEXT_ALARM_FORMATTED);
        return (str!=null)?str:"-";
    }

    @Override
    public List<SlptViewComponent> buildSlptViewComponent(Service service) {
        // Variables
        // This service
        this.mService = service;
        this.ampmBool = service.getResources().getBoolean(R.bool.ampm);

        // Get AM/PM
        this.time = getSlptTime();

        // Get next alarm
        this.alarm = getAlarm();

        // Get wifi
        this.wifi = getSlptBluetooth(service);

        // Draw AM or PM
        SlptLinearLayout ampm = new SlptLinearLayout();
        SlptPictureView ampmStr = new SlptPictureView();
        ampmStr.setStringPicture( this.time.ampmStr );
        ampm.add(ampmStr);
        //ampm.add(new SlptAnalogAmPmView());
        ampm.setTextAttrForAll(
                service.getResources().getDimension(R.dimen.ampm_font_size),
                service.getResources().getColor(R.color.ampm_colour_slpt),
                ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE)
        );
        // Position based on screen on
        ampm.alignX = 2;
        ampm.alignY = 0;
        int tmp_left = (int) service.getResources().getDimension(R.dimen.ampm_left);
        if(!service.getResources().getBoolean(R.bool.ampm_left_align)) {
            // If text is centered, set rectangle
            ampm.setRect(
                    (int) (2 * tmp_left + 640),
                    (int) (service.getResources().getDimension(R.dimen.ampm_font_size))
            );
            tmp_left = -320;
        }
        ampm.setStart(
                (int) tmp_left,
                (int) (service.getResources().getDimension(R.dimen.ampm_top)-((float)service.getResources().getInteger(R.integer.font_ratio)/100)*service.getResources().getDimension(R.dimen.ampm_font_size))
        );
        // Hide if disabled
        if(!this.ampmBool){ampm.show=false;}


        // Draw Alarm
        SlptLinearLayout alarmLayout = new SlptLinearLayout();
        SlptPictureView alarmStr = new SlptPictureView();
        alarmStr.setStringPicture( this.alarm );
        alarmLayout.add(alarmStr);
        alarmLayout.setTextAttrForAll(
                service.getResources().getDimension(R.dimen.alarm_font_size),
                service.getResources().getColor(R.color.alarm_colour_slpt),
                ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE)
        );
        // Position based on screen on
        alarmLayout.alignX = 2;
        alarmLayout.alignY = 0;
        tmp_left = (int) service.getResources().getDimension(R.dimen.alarm_left);
        if(!service.getResources().getBoolean(R.bool.alarm_left_align)) {
            // If text is centered, set rectangle
            alarmLayout.setRect(
                    (int) (2 * tmp_left + 640),
                    (int) (service.getResources().getDimension(R.dimen.alarm_font_size))
            );
            tmp_left = -320;
        }
        alarmLayout.setStart(
                (int) tmp_left,
                (int) (service.getResources().getDimension(R.dimen.alarm_top)-((float)service.getResources().getInteger(R.integer.font_ratio)/100)*service.getResources().getDimension(R.dimen.alarm_font_size))
        );
        // Hide if disabled
        if(!service.getResources().getBoolean(R.bool.alarm)){alarmLayout.show=false;}


        // Draw WiFi
        SlptLinearLayout wifiLayout = new SlptLinearLayout();
        SlptPictureView wifiStr = new SlptPictureView();
        wifiStr.setStringPicture( this.wifi );
        wifiLayout.add(wifiStr);
        wifiLayout.setTextAttrForAll(
                service.getResources().getDimension(R.dimen.ampm_font_size),
                service.getResources().getColor(R.color.ampm_colour_slpt),
                ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE)
        );
        // If enabled
        if(true) {wifiLayout.show = false;}
        // Position based on screen on
        wifiLayout.alignX = 2;
        wifiLayout.alignY = 0;
        wifiLayout.setRect(
                (int) (2*150+640),
                (int) (service.getResources().getDimension(R.dimen.ampm_font_size))
        );
        wifiLayout.setStart(
                -320,
                (int) (150-((float)service.getResources().getInteger(R.integer.font_ratio)/100)*service.getResources().getDimension(R.dimen.ampm_font_size))
        );

        return Arrays.asList(new SlptViewComponent[]{ampm, alarmLayout, wifiLayout});
    }
}
