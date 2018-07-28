package com.dinodevs.greatfitwatchface.widget;

import android.app.Service;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.wearable.watchface.CanvasWatchFaceService;
import android.text.TextPaint;
import android.util.Log;

import com.dinodevs.greatfitwatchface.AbstractWatchFace;
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
    private TextPaint xdripPaint;
    private TextPaint airPressurePaint;
    private Time time;
    private String tempAMPM;
    private String text;
    //private String wifi;
    private String alarm;
    private String xdrip;
    private String airPressure;
    private int textint;
    private Boolean alarmBool;
    private Boolean alarmAlignLeftBool;
    private Boolean ampmBool;
    private Boolean ampmAlignLeftBool;
    private Boolean xdripBool;
    private Boolean xdripAlignLeftBool;
    private Boolean airPressureBool;
    private Boolean showAirPressureUnits;
    private Boolean airPressureAlignLeftBool;
    private Service mService;

    private float ampmTop;
    private float ampmLeft;
    private float alarmTop;
    private float alarmLeft;
    private float xdripTop;
    private float xdripLeft;
    private float airPressureTop;
    private float airPressureLeft;

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
        this.tempAMPM = this.time.ampmStr;

        // Get next alarm
        this.alarm = getAlarm(); // ex: Fri 10:30
        //Log.w("DinoDevs-GreatFit", "Alarm: "+alarm );

        // Get xdrip
        this.xdrip = getXdrip();

        // Get AirPressure in hPa
        this.airPressureBool = service.getResources().getBoolean(R.bool.air_pressure);
        if(this.airPressureBool){
            updateAirPressure();
        }
        this.airPressure = getAirPressure();

        // Get wifi status
        //this.wifi = getWifi();

        this.ampmLeft = service.getResources().getDimension(R.dimen.ampm_left);
        this.ampmTop = service.getResources().getDimension(R.dimen.ampm_top);
        this.alarmLeft = service.getResources().getDimension(R.dimen.alarm_left);
        this.alarmTop = service.getResources().getDimension(R.dimen.alarm_top);
        this.xdripLeft = service.getResources().getDimension(R.dimen.xdrip_left);
        this.xdripTop = service.getResources().getDimension(R.dimen.xdrip_top);
        this.airPressureLeft = service.getResources().getDimension(R.dimen.air_pressure_left);
        this.airPressureTop = service.getResources().getDimension(R.dimen.air_pressure_top);

        this.ampmBool = service.getResources().getBoolean(R.bool.ampm);
        this.ampmAlignLeftBool = service.getResources().getBoolean(R.bool.ampm_left_align);
        this.alarmBool = service.getResources().getBoolean(R.bool.alarm);
        this.alarmAlignLeftBool = service.getResources().getBoolean(R.bool.alarm_left_align);
        this.xdripBool = service.getResources().getBoolean(R.bool.xdrip);
        this.xdripAlignLeftBool = service.getResources().getBoolean(R.bool.xdrip_left_align);
        this.showAirPressureUnits = service.getResources().getBoolean(R.bool.air_pressure_units);
        this.airPressureAlignLeftBool = service.getResources().getBoolean(R.bool.air_pressure_left_align);

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

        this.xdripPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        this.xdripPaint.setColor(service.getResources().getColor(R.color.xdrip_colour));
        this.xdripPaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE));
        this.xdripPaint.setTextSize(service.getResources().getDimension(R.dimen.xdrip_font_size));
        this.xdripPaint.setTextAlign( (this.xdripAlignLeftBool) ? Paint.Align.LEFT : Paint.Align.CENTER );

        this.airPressurePaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        this.airPressurePaint.setColor(service.getResources().getColor(R.color.air_pressure_colour));
        this.airPressurePaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE));
        this.airPressurePaint.setTextSize(service.getResources().getDimension(R.dimen.air_pressure_font_size));
        this.airPressurePaint.setTextAlign( (this.airPressureAlignLeftBool) ? Paint.Align.LEFT : Paint.Align.CENTER );
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

        // Draw Xdrip, if enabled
        if(this.xdripBool) {
            canvas.drawText(this.xdrip, xdripLeft, xdripTop, xdripPaint);
        }

        // Draw AirPressure, if enabled
        if(this.airPressureBool) {
            String units = (showAirPressureUnits) ? " hPa" : "";
            canvas.drawText(this.airPressure+units, airPressureLeft, airPressureTop, airPressurePaint);
        }

        // Draw wifi, if enabled
        /*if(true) {
            canvas.drawText(getWifi(), ampmLeft, ampmTop, ampmPaint);
        }*/
    }

    @Override
    public List<DataType> getDataTypes() {
        // For many refreshes
        return Arrays.asList(DataType.BATTERY, DataType.STEPS, DataType.DISTANCE, DataType.TOTAL_DISTANCE, DataType.TIME,  DataType.CALORIES,  DataType.DATE,  DataType.HEART_RATE,  DataType.FLOOR, DataType.WEATHER);
    }

    @Override
    public void onDataUpdate(DataType type, Object value) {
        boolean refreshSlpt = false;
        String temp;

        // On each Data updated
        //Log.w("DinoDevs-GreatFit", type.toString()+" => "+value.toString() );
        switch (type) {
            case TIME:
                // Update AM/PM
                this.time = (Time) value;
                if(!this.tempAMPM.equals(this.time.ampmStr)){
                    refreshSlpt = true;
                }
                break;
        }
        // Update AirPressure
        temp = getAirPressure();
        if( !this.airPressure.equals(temp) ){
            this.airPressure = temp;
            refreshSlpt = true;
        }

        // Update Alarm
        temp = getAlarm();
        if( !this.alarm.equals(temp) ){
            this.alarm = temp;
            refreshSlpt = true;
        }

        // Update Xdrip
        temp = getXdrip();
        if( !this.xdrip.equals(temp) ){
            this.xdrip = temp;
            refreshSlpt = true;
        }

        // Update wifi
        /*temp = getWifi();
        if( !this.wifi.equals(temp) ){
            this.wifi = temp;
            refreshSlpt = true;
        }*/

        // Refresh Slpt
        if(refreshSlpt){
            ((AbstractWatchFace) this.mService).restartSlpt();
        }

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

    public String getWifi() {
        String str = Settings.System.getString(this.mService.getApplicationContext().getContentResolver(), "wifi");
        Log.w("DinoDevs-GreatFit", "Wifi: "+str);
        return (str!=null)?str:"null";
    }

    public String getAlarm() {
        String str = Settings.System.getString(this.mService.getContentResolver(), Settings.System.NEXT_ALARM_FORMATTED);
        return (str!=null && !str.equals(""))?str:"--";
    }

    public String getXdrip(){
        String str = Settings.System.getString(this.mService.getContentResolver(), "sgv");
        return (str!=null && !str.equals(""))?str:"--";
    }

    public void updateAirPressure(){
        // WearCompass.jar!\com\huami\watch\compass\logic\GeographicManager.class
        final SensorManager mManager = (SensorManager) this.mService.getApplicationContext().getSystemService(Context.SENSOR_SERVICE);
        Sensor mPressureSensor = mManager.getDefaultSensor(6);
        SensorEventListener mListener = new SensorEventListener() {
            public void onAccuracyChanged(Sensor parameter1, int parameter2) {}

            public void onSensorChanged(SensorEvent parameters) {
                //mManager.unregisterListener(this);
                float[] pressure = parameters.values;
                if (pressure != null && pressure.length > 0) {
                    int value = ((int) pressure[0]);
                    if (value > 0 && !(value+"").equals(GreatWidget.this.airPressure)) {
                        GreatWidget.this.airPressure = value+"";
                        Log.w("DinoDevs-GreatFit", "AirPressure: "+value);
                        Settings.System.putString(GreatWidget.this.mService.getContentResolver(), "GreatFit_AirPressure",value+"");
                    }
                }
            }
        };
        mManager.registerListener(mListener, mPressureSensor, 0);
    }

    public String getAirPressure(){
        String str = Settings.System.getString(this.mService.getContentResolver(), "GreatFit_AirPressure");
        return (str!=null && !str.equals(""))?str:"--";
    }

    @Override
    public List<SlptViewComponent> buildSlptViewComponent(Service service) {
        // Variables
        // This service
        this.mService = service;
        this.ampmBool = service.getResources().getBoolean(R.bool.ampm);

        // Get AM/PM
        this.time = getSlptTime();
        this.tempAMPM = this.time.ampmStr;

        // Get next alarm
        this.alarm = getAlarm();

        // Get xDrip
        this.xdrip = getXdrip();

        // Get AirPressure in hPa
        this.airPressure = getAirPressure();

        // Get wifi
        //this.wifi = getWifi();

        // Draw AM or PM
        SlptLinearLayout ampm = new SlptLinearLayout();
        SlptPictureView ampmStr = new SlptPictureView();
        ampmStr.setStringPicture( this.tempAMPM );
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


        // Draw Xdrip
        SlptLinearLayout xdripLayout = new SlptLinearLayout();
        SlptPictureView xdripStr = new SlptPictureView();
        xdripStr.setStringPicture( this.xdrip );
        xdripLayout.add(xdripStr);
        xdripLayout.setTextAttrForAll(
                service.getResources().getDimension(R.dimen.xdrip_font_size),
                service.getResources().getColor(R.color.xdrip_colour_slpt),
                ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE)
        );
        // Position based on screen on
        xdripLayout.alignX = 2;
        xdripLayout.alignY = 0;
        tmp_left = (int) service.getResources().getDimension(R.dimen.xdrip_left);
        if(!service.getResources().getBoolean(R.bool.xdrip_left_align)) {
            // If text is centered, set rectangle
            xdripLayout.setRect(
                    (int) (2 * tmp_left + 640),
                    (int) (service.getResources().getDimension(R.dimen.xdrip_font_size))
            );
            tmp_left = -320;
        }
        xdripLayout.setStart(
                (int) tmp_left,
                (int) (service.getResources().getDimension(R.dimen.xdrip_top)-((float)service.getResources().getInteger(R.integer.font_ratio)/100)*service.getResources().getDimension(R.dimen.xdrip_font_size))
        );
        // Hide if disabled
        if(!service.getResources().getBoolean(R.bool.xdrip)){xdripLayout.show=false;}


        // Draw AirPressure
        SlptLinearLayout airPressureLayout = new SlptLinearLayout();
        SlptPictureView airPressureStr = new SlptPictureView();
        airPressureStr.setStringPicture( this.airPressure );
        airPressureLayout.add(airPressureStr);
        // Show or Not Units
        if(service.getResources().getBoolean(R.bool.air_pressure_units)) {
            SlptPictureView airPressureUnit = new SlptPictureView();
            airPressureUnit.setStringPicture(" hPa");
            airPressureLayout.add(airPressureUnit);
        }
        airPressureLayout.setTextAttrForAll(
                service.getResources().getDimension(R.dimen.air_pressure_font_size),
                service.getResources().getColor(R.color.air_pressure_colour_slpt),
                ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE)
        );
        // Position based on screen on
        airPressureLayout.alignX = 2;
        airPressureLayout.alignY = 0;
        tmp_left = (int) service.getResources().getDimension(R.dimen.air_pressure_left);
        if(!service.getResources().getBoolean(R.bool.air_pressure_left_align)) {
            // If text is centered, set rectangle
            airPressureLayout.setRect(
                    (int) (2 * tmp_left + 640),
                    (int) (service.getResources().getDimension(R.dimen.air_pressure_font_size))
            );
            tmp_left = -320;
        }
        airPressureLayout.setStart(
                (int) tmp_left,
                (int) (service.getResources().getDimension(R.dimen.air_pressure_top)-((float)service.getResources().getInteger(R.integer.font_ratio)/100)*service.getResources().getDimension(R.dimen.air_pressure_font_size))
        );
        // Hide if disabled
        if(!service.getResources().getBoolean(R.bool.air_pressure)){airPressureLayout.show=false;}


        return Arrays.asList(new SlptViewComponent[]{ampm, alarmLayout, xdripLayout, airPressureLayout});
    }
}
