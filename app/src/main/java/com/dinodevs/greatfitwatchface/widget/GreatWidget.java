package com.dinodevs.greatfitwatchface.widget;

import android.app.Service;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.provider.Settings;
import android.text.TextPaint;
import android.util.Log;

import com.dinodevs.greatfitwatchface.AbstractWatchFace;
import com.dinodevs.greatfitwatchface.data.Alarm;
import com.dinodevs.greatfitwatchface.data.CustomData;
import com.dinodevs.greatfitwatchface.data.Xdrip;
import com.dinodevs.greatfitwatchface.settings.LoadSettings;
import com.ingenic.iwds.slpt.view.core.SlptLinearLayout;
import com.ingenic.iwds.slpt.view.core.SlptPictureView;
import com.ingenic.iwds.slpt.view.core.SlptViewComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import com.dinodevs.greatfitwatchface.data.DataType;
import com.dinodevs.greatfitwatchface.data.Time;
import com.dinodevs.greatfitwatchface.resource.ResourceManager;
import com.dinodevs.greatfitwatchface.R;


public class GreatWidget extends AbstractWidget {
    private Time time;
    private CustomData customData;
    private Alarm alarmData;
    private Xdrip xdripData;

    private TextPaint ampmPaint;
    private TextPaint alarmPaint;
    private TextPaint xdripPaint;
    private TextPaint airPressurePaint;
    private TextPaint altitudePaint;
    private TextPaint phoneBatteryPaint;

    private String tempAMPM;
    private String text;
    //private String wifi;
    private String alarm;
    private Boolean alarmBool;
    private Boolean alarmAlignLeftBool;
    private Boolean ampmBool;
    private Boolean ampmAlignLeftBool;
    private Boolean xdripBool;
    private Boolean xdripAlignLeftBool;

    private Boolean airPressureBool;
    private Boolean showAirPressureUnits;
    private Boolean airPressureAlignLeftBool;
    private Boolean altitudeBool;
    private Boolean phoneBatteryBool;
    private Boolean showAltitudeUnits;
    private Boolean altitudeAlignLeftBool;
    private Boolean phoneBatteryAlignLeftBool;

    private Service mService;

    private float ampmTop;
    private float ampmLeft;
    private float alarmTop;
    private float alarmLeft;
    private float xdripTop;
    private float xdripLeft;
    private float airPressureTop;
    private float airPressureLeft;
    private float altitudeTop;
    private float altitudeLeft;
    private float phoneBatteryTop;
    private float phoneBatteryLeft;
    private LoadSettings settings;

    public GreatWidget(LoadSettings settings) {
        this.settings = settings;
    }

    @Override
    public void init(Service service){
        // This service
        this.mService = service;

        // Get AM/PM
        this.time = getSlptTime();
        this.tempAMPM = this.time.ampmStr;

        // Get next alarm
        this.alarmData = getAlarm();
        this.alarm = this.alarmData.alarm; // ex: Fri 10:30

        // Get xdrip
        this.xdripData = getXdrip();

        // CustomData
        this.customData = getCustomData();
        // Get AirPressure in hPa
        this.airPressureBool = service.getResources().getBoolean(R.bool.air_pressure);
        // Get Altitude in m
        this.altitudeBool = service.getResources().getBoolean(R.bool.altitude);
        // Get phone's battery %
        this.phoneBatteryBool = service.getResources().getBoolean(R.bool.phoneBattery);

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
        this.altitudeLeft = service.getResources().getDimension(R.dimen.altitude_left);
        this.altitudeTop = service.getResources().getDimension(R.dimen.altitude_top);
        this.phoneBatteryLeft = service.getResources().getDimension(R.dimen.phoneBattery_left);
        this.phoneBatteryTop = service.getResources().getDimension(R.dimen.phoneBattery_top);

        this.ampmBool = service.getResources().getBoolean(R.bool.ampm);
        this.ampmAlignLeftBool = service.getResources().getBoolean(R.bool.ampm_left_align);
        this.alarmBool = service.getResources().getBoolean(R.bool.alarm);
        this.alarmAlignLeftBool = service.getResources().getBoolean(R.bool.alarm_left_align);
        this.xdripBool = service.getResources().getBoolean(R.bool.xdrip);
        this.xdripAlignLeftBool = service.getResources().getBoolean(R.bool.xdrip_left_align);
        this.showAirPressureUnits = service.getResources().getBoolean(R.bool.air_pressure_units);
        this.airPressureAlignLeftBool = service.getResources().getBoolean(R.bool.air_pressure_left_align);
        this.showAltitudeUnits = service.getResources().getBoolean(R.bool.altitude_units);
        this.altitudeAlignLeftBool = service.getResources().getBoolean(R.bool.altitude_left_align);
        this.phoneBatteryAlignLeftBool = service.getResources().getBoolean(R.bool.phoneBattery_left_align);

        this.ampmPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        this.ampmPaint.setColor((settings.sltp_circle_color>0)?settings.colorCodes[settings.sltp_circle_color-1]:service.getResources().getColor(R.color.ampm_colour));
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

        this.altitudePaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        this.altitudePaint.setColor(service.getResources().getColor(R.color.altitude_colour));
        this.altitudePaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE));
        this.altitudePaint.setTextSize(service.getResources().getDimension(R.dimen.altitude_font_size));
        this.altitudePaint.setTextAlign( (this.altitudeAlignLeftBool) ? Paint.Align.LEFT : Paint.Align.CENTER );

        this.phoneBatteryPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        this.phoneBatteryPaint.setColor(service.getResources().getColor(R.color.phoneBattery_colour));
        this.phoneBatteryPaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE));
        this.phoneBatteryPaint.setTextSize(service.getResources().getDimension(R.dimen.phoneBattery_font_size));
        this.phoneBatteryPaint.setTextAlign( (this.phoneBatteryAlignLeftBool) ? Paint.Align.LEFT : Paint.Align.CENTER );
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
            canvas.drawText(this.alarmData.alarm, alarmLeft, alarmTop, alarmPaint);
        }

        // Draw Xdrip, if enabled
        if(this.xdripBool) {
            canvas.drawText(this.xdripData.sgv, xdripLeft, xdripTop, xdripPaint);
        }

        // Draw AirPressure, if enabled
        if(this.airPressureBool) {
            String units = (showAirPressureUnits) ? " hPa" : "";
            canvas.drawText(this.customData.airPressure+units, airPressureLeft, airPressureTop, airPressurePaint);
        }

        // Draw Altitude, if enabled
        if(this.altitudeBool) {
            String units = (showAltitudeUnits) ? " m" : "";
            canvas.drawText(this.customData.altitude+units, altitudeLeft, altitudeTop, altitudePaint);
        }

        // Draw Phone's Battery
        if(this.phoneBatteryBool) {
            canvas.drawText(this.customData.phoneBattery+"%", phoneBatteryLeft, phoneBatteryTop, phoneBatteryPaint);
        }

        // Draw wifi, if enabled
        /*if(true) {
            canvas.drawText(getWifi(), ampmLeft, ampmTop, ampmPaint);
        }*/
    }

    @Override
    public List<DataType> getDataTypes() {
        // For many refreshes
        //return Arrays.asList(DataType.BATTERY, DataType.STEPS, DataType.DISTANCE, DataType.TOTAL_DISTANCE, DataType.TIME,  DataType.CALORIES,  DataType.DATE,  DataType.HEART_RATE,  DataType.FLOOR, DataType.WEATHER);
        //return Arrays.asList(DataType.TIME, DataType.CUSTOM, DataType.ALARM, DataType.XDRIP);
        List<DataType> dataTypes = new ArrayList<>();
        dataTypes.add(DataType.TIME);
        dataTypes.add(DataType.CUSTOM);
        dataTypes.add(DataType.ALARM);
        dataTypes.add(DataType.XDRIP);
        return dataTypes;
    }

    @Override
    public void onDataUpdate(DataType type, Object value) {
        //Log.w("DinoDevs-GreatFit", type.toString()+" => "+value.toString() );
        boolean refreshSlpt = false;

        // On each Data updated
        switch (type) {
            case TIME:
                // Update AM/PM
                this.time = (Time) value;
                if(!this.tempAMPM.equals(this.time.ampmStr)){
                    this.tempAMPM = this.time.ampmStr;
                    refreshSlpt = true;
                }
                break;
            case ALARM:
                // Update Alarm
                this.alarmData = (Alarm) value;
                break;
            case XDRIP:
                // Update Xdrip
                this.xdripData = (Xdrip) value;
                break;
            case CUSTOM:
                this.customData = (CustomData) value;
                //Log.w("DinoDevs-GreatFit", type.toString()+" => "+value.toString() );

                // Update wifi
                /*temp = getWifi();
                if( !this.wifi.equals(temp) ){
                    this.wifi = temp;
                    refreshSlpt = true;
                }*/
                break;
        }

        // Refresh Slpt
        if(refreshSlpt){
            ((AbstractWatchFace) this.mService).restartSlpt();
        }
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

    public Alarm getAlarm() {
        String str = Settings.System.getString(this.mService.getContentResolver(), Settings.System.NEXT_ALARM_FORMATTED);
        return new Alarm(str);
    }

    public Xdrip getXdrip(){
        String str = Settings.System.getString(this.mService.getContentResolver(), "xdrip");
        return new Xdrip(str);
    }

    public CustomData getCustomData(){
        String str = Settings.System.getString(this.mService.getContentResolver(), "CustomWatchfaceData");
        return new CustomData(str);
    }

    @Override
    public List<SlptViewComponent> buildSlptViewComponent(Service service) {
        // Variables
        // This service
        this.mService = service;
        List<SlptViewComponent> slpt_objects = new ArrayList<>();
        this.ampmBool = service.getResources().getBoolean(R.bool.ampm);
        int tmp_left;

        // Get AM/PM
        this.time = getSlptTime();

        // Get next alarm
        this.alarmData = getAlarm();
        this.alarm = alarmData.alarm;

        // Get xdrip
        this.xdripData = getXdrip();

        // CustomData
        this.customData = getCustomData();

        // Get wifi
        //this.wifi = getWifi();

        // Draw AM or PM
        if(this.ampmBool){
            SlptLinearLayout ampm = new SlptLinearLayout();
            SlptPictureView ampmStr = new SlptPictureView();
            ampmStr.setStringPicture( this.time.ampmStr );
            ampm.add(ampmStr);
            ampm.setTextAttrForAll(
                    service.getResources().getDimension(R.dimen.ampm_font_size),
                    (settings.sltp_circle_color>0)?settings.colorCodes[settings.sltp_circle_color-1]:service.getResources().getColor(R.color.ampm_colour_slpt),
                    ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE)
            );
            // Position based on screen on
            ampm.alignX = 2;
            ampm.alignY = 0;
            tmp_left = (int) service.getResources().getDimension(R.dimen.ampm_left);
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
            slpt_objects.add(ampm);
        }


        // Draw Alarm
        if(service.getResources().getBoolean(R.bool.alarm)){
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
            //Add it to the list
            slpt_objects.add(alarmLayout);
        }


        // Draw Xdrip
        if(service.getResources().getBoolean(R.bool.xdrip)){
            SlptLinearLayout xdripLayout = new SlptLinearLayout();
            SlptPictureView xdripStr = new SlptPictureView();
            xdripStr.setStringPicture( this.xdripData.sgv );
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
            //Add it to the list
            slpt_objects.add(xdripLayout);
        }


        // Draw AirPressure
        if(service.getResources().getBoolean(R.bool.air_pressure)){
            SlptLinearLayout airPressureLayout = new SlptLinearLayout();
            SlptPictureView airPressureStr = new SlptPictureView();
            airPressureStr.setStringPicture( this.customData.airPressure );
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
            //Add it to the list
            slpt_objects.add(airPressureLayout);
        }

        // Draw Altitude
        if(service.getResources().getBoolean(R.bool.altitude)){
            SlptLinearLayout altitudeLayout = new SlptLinearLayout();
            SlptPictureView altitudeStr = new SlptPictureView();
            altitudeStr.setStringPicture( this.customData.altitude );
            altitudeLayout.add(altitudeStr);
            // Show or Not Units
            if(service.getResources().getBoolean(R.bool.altitude_units)) {
                SlptPictureView altitudeUnit = new SlptPictureView();
                altitudeUnit.setStringPicture(" m");
                altitudeLayout.add(altitudeUnit);
            }
            altitudeLayout.setTextAttrForAll(
                    service.getResources().getDimension(R.dimen.altitude_font_size),
                    service.getResources().getColor(R.color.altitude_colour_slpt),
                    ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE)
            );
            // Position based on screen on
            altitudeLayout.alignX = 2;
            altitudeLayout.alignY = 0;
            tmp_left = (int) service.getResources().getDimension(R.dimen.altitude_left);
            if(!service.getResources().getBoolean(R.bool.altitude_left_align)) {
                // If text is centered, set rectangle
                altitudeLayout.setRect(
                        (int) (2 * tmp_left + 640),
                        (int) (service.getResources().getDimension(R.dimen.altitude_font_size))
                );
                tmp_left = -320;
            }
            altitudeLayout.setStart(
                    (int) tmp_left,
                    (int) (service.getResources().getDimension(R.dimen.altitude_top)-((float)service.getResources().getInteger(R.integer.font_ratio)/100)*service.getResources().getDimension(R.dimen.altitude_font_size))
            );
            //Add it to the list
            slpt_objects.add(altitudeLayout);
        }

        // Draw Phone's Battery
        if(service.getResources().getBoolean(R.bool.phoneBattery)){
            SlptLinearLayout phoneBatteryLayout = new SlptLinearLayout();
            SlptPictureView phoneBatteryStr = new SlptPictureView();
            phoneBatteryStr.setStringPicture( this.customData.phoneBattery+"%" );
            phoneBatteryLayout.add(phoneBatteryStr);
            phoneBatteryLayout.setTextAttrForAll(
                    service.getResources().getDimension(R.dimen.phoneBattery_font_size),
                    service.getResources().getColor(R.color.phoneBattery_colour_slpt),
                    ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE)
            );
            // Position based on screen on
            phoneBatteryLayout.alignX = 2;
            phoneBatteryLayout.alignY = 0;
            tmp_left = (int) service.getResources().getDimension(R.dimen.phoneBattery_left);
            if(!service.getResources().getBoolean(R.bool.phoneBattery_left_align)) {
                // If text is centered, set rectangle
                phoneBatteryLayout.setRect(
                        (int) (2 * tmp_left + 640),
                        (int) (service.getResources().getDimension(R.dimen.phoneBattery_font_size))
                );
                tmp_left = -320;
            }
            phoneBatteryLayout.setStart(
                    (int) tmp_left,
                    (int) (service.getResources().getDimension(R.dimen.phoneBattery_top)-((float)service.getResources().getInteger(R.integer.font_ratio)/100)*service.getResources().getDimension(R.dimen.phoneBattery_font_size))
            );
            //Add it to the list
            slpt_objects.add(phoneBatteryLayout);
        }

        return slpt_objects;
    }
}
