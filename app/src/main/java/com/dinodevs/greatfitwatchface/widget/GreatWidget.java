package com.dinodevs.greatfitwatchface.widget;

import android.app.Service;
import android.graphics.Bitmap;
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
import com.huami.watch.watchface.util.Util;
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
import com.ingenic.iwds.slpt.view.utils.SimpleFile;


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

    private Bitmap watch_alarmIcon;
    private Bitmap xdripIcon;
    private Bitmap air_pressureIcon;
    private Bitmap altitudeIcon;
    private Bitmap phone_batteryIcon;

    private String tempAMPM;
    private String alarm;

    private Service mService;
    private LoadSettings settings;

    // Constructor
    public GreatWidget(LoadSettings settings) {
        this.settings = settings;
    }

    // Screen-on init (runs once)
    @Override
    public void init(Service service){
        // This service
        this.mService = service;

        // Get AM/PM
        if(settings.am_pmBool) {
            this.time = getSlptTime();
            this.tempAMPM = this.time.ampmStr;
            this.ampmPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
            this.ampmPaint.setColor(settings.am_pmColor);
            this.ampmPaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE));
            this.ampmPaint.setTextSize(settings.am_pmFontSize);
            this.ampmPaint.setTextAlign((settings.am_pmAlignLeft) ? Paint.Align.LEFT : Paint.Align.CENTER);
        }

        // Get next alarm
        if(settings.watch_alarm>0) {
            this.alarmData = getAlarm();
            this.alarm = this.alarmData.alarm; // ex: Fri 10:30
            this.alarmPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
            this.alarmPaint.setColor(settings.watch_alarmColor);
            this.alarmPaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE));
            this.alarmPaint.setTextSize(settings.watch_alarmFontSize);
            this.alarmPaint.setTextAlign((settings.watch_alarmAlignLeft) ? Paint.Align.LEFT : Paint.Align.CENTER);

            if(settings.watch_alarmIcon){
                this.watch_alarmIcon = Util.decodeImage(service.getResources(),"icons/alarm.png");
            }
        }

        // Xdrip
        if(settings.xdrip>0) {
            // Get xdrip
            this.xdripData = getXdrip();
            this.xdripPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
            this.xdripPaint.setColor(settings.xdripColor);
            this.xdripPaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE));
            this.xdripPaint.setTextSize(settings.xdripColor);
            this.xdripPaint.setTextAlign((settings.xdripAlignLeft) ? Paint.Align.LEFT : Paint.Align.CENTER);

            if(settings.xdripIcon){
                this.xdripIcon = Util.decodeImage(service.getResources(),"icons/xdrip.png");
            }
        }

        // Custom
        if(settings.isCustom()) {
            // CustomData
            this.customData = getCustomData();
            if(settings.air_pressure>0) {
                this.airPressurePaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
                this.airPressurePaint.setColor(settings.air_pressureColor);
                this.airPressurePaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE));
                this.airPressurePaint.setTextSize(settings.air_pressureFontSize);
                this.airPressurePaint.setTextAlign((settings.air_pressureAlignLeft) ? Paint.Align.LEFT : Paint.Align.CENTER);

                if(settings.air_pressureIcon){
                    this.air_pressureIcon = Util.decodeImage(service.getResources(),"icons/air_pressure.png");
                }
            }
            if(settings.altitude>0) {
                this.altitudePaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
                this.altitudePaint.setColor(settings.altitudeColor);
                this.altitudePaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE));
                this.altitudePaint.setTextSize(settings.altitudeFontSize);
                this.altitudePaint.setTextAlign((settings.altitudeAlignLeft) ? Paint.Align.LEFT : Paint.Align.CENTER);

                if(settings.altitudeIcon){
                    this.altitudeIcon = Util.decodeImage(service.getResources(),"icons/altitude.png");
                }
            }
            if(settings.phone_battery>0) {
                this.phoneBatteryPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
                this.phoneBatteryPaint.setColor(settings.phone_batteryColor);
                this.phoneBatteryPaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE));
                this.phoneBatteryPaint.setTextSize(settings.phone_batteryFontSize);
                this.phoneBatteryPaint.setTextAlign((settings.phone_batteryAlignLeft) ? Paint.Align.LEFT : Paint.Align.CENTER);

                if(settings.phone_batteryIcon){
                    this.phone_batteryIcon = Util.decodeImage(service.getResources(),"icons/phone_battery.png");
                }
            }
        }
    }

    // Draw screen-on
    @Override
    public void draw(Canvas canvas, float width, float height, float centerX, float centerY) {
        // Draw AM or PM, if enabled
        if(settings.am_pmBool) {
            //Calendar now = Calendar.getInstance();
            //String periode = (now.get(Calendar.AM_PM) == Calendar.AM) ? "AM" : "PM";
            String text = String.format("%S", this.tempAMPM);//Capitalize
            canvas.drawText(text, settings.am_pmLeft, settings.am_pmTop, ampmPaint);
        }

        // Draw Alarm, if enabled
        if(settings.watch_alarm>0) {
            if(settings.watch_alarmIcon){
                canvas.drawBitmap(this.watch_alarmIcon, settings.watch_alarmIconLeft, settings.watch_alarmIconTop, settings.mGPaint);
            }
            canvas.drawText(this.alarmData.alarm, settings.watch_alarmLeft, settings.watch_alarmTop, alarmPaint);
        }

        // Draw Xdrip, if enabled
        if(settings.xdrip>0) {
            if(settings.xdripIcon){
                canvas.drawBitmap(this.xdripIcon, settings.xdripIconLeft, settings.xdripIconTop, settings.mGPaint);
            }
            canvas.drawText(this.xdripData.sgv, settings.xdripLeft, settings.xdripTop, xdripPaint);
        }

        // Draw AirPressure, if enabled
        if(settings.air_pressure>0) {
            if(settings.air_pressureIcon){
                canvas.drawBitmap(this.air_pressureIcon, settings.air_pressureIconLeft, settings.air_pressureIconTop, settings.mGPaint);
            }
            String units = (settings.air_pressureUnits) ? " hPa" : "";
            canvas.drawText(this.customData.airPressure+units, settings.air_pressureLeft, settings.air_pressureTop, airPressurePaint);
        }

        // Draw Altitude, if enabled
        if(settings.altitude>0) {
            if(settings.altitudeIcon){
                canvas.drawBitmap(this.altitudeIcon, settings.altitudeIconLeft, settings.altitudeIconTop, settings.mGPaint);
            }
            String units = (settings.altitudeUnits) ? " m" : "";
            canvas.drawText(this.customData.altitude+units, settings.altitudeLeft, settings.altitudeTop, altitudePaint);
        }

        // Draw Phone's Battery
        if(settings.phone_battery>0) {
            if(settings.phone_batteryIcon){
                canvas.drawBitmap(this.phone_batteryIcon, settings.phone_batteryIconLeft, settings.phone_batteryIconTop, settings.mGPaint);
            }
            canvas.drawText(this.customData.phoneBattery+"%", settings.phone_batteryLeft, settings.phone_batteryTop, phoneBatteryPaint);
        }

        // Draw Phone's alarm
        if(settings.phone_alarm>0) {
            /*
            if(settings.phone_alarmIcon){
                canvas.drawBitmap(this.phone_alarmIcon, settings.phone_alarmIconLeft, settings.phone_alarmIconTop, settings.mGPaint);
            }
            */
            // todo
            //canvas.drawText(this.customData.phoneAlarm+"%", settings.phone_alarmLeft, settings.phone_alarmTop, phoneAlarmPaint);
        }
    }

    // Register update listeners
    @Override
    public List<DataType> getDataTypes() {
        List<DataType> dataTypes = new ArrayList<>();

        if(settings.am_pmBool) {
            dataTypes.add(DataType.TIME);
        }

        if( settings.air_pressure>0 || settings.phone_alarm>0 || settings.phone_battery>0 || settings.altitude>0 ) {
            dataTypes.add(DataType.CUSTOM);
        }

        if(settings.watch_alarm>0) {
            dataTypes.add(DataType.ALARM);
        }

        if(settings.xdrip>0) {
            dataTypes.add(DataType.XDRIP);
        }

        return dataTypes;
    }

    // Value updater
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
                break;
        }

        // Refresh Slpt
        if(refreshSlpt){
            ((AbstractWatchFace) this.mService).restartSlpt();
        }
    }

    // Get data functions
    public Time getSlptTime() {
        Calendar now = Calendar.getInstance();
        int periode = (now.get(Calendar.HOUR_OF_DAY) < 12)?0:1;
        return new Time(periode);
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

    // Screen-off (SLPT)
    @Override
    public List<SlptViewComponent> buildSlptViewComponent(Service service) {
        return buildSlptViewComponent(service, false);
    }

    // Screen-off (SLPT) - Better screen quality
    public List<SlptViewComponent> buildSlptViewComponent(Service service, boolean better_resolution) {
        // Variables
        better_resolution = better_resolution && settings.better_resolution_when_raising_hand;
        this.mService = service;
        List<SlptViewComponent> slpt_objects = new ArrayList<>();
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

        // Draw AM or PM
        if(settings.am_pmBool){
            SlptLinearLayout ampm = new SlptLinearLayout();
            SlptPictureView ampmStr = new SlptPictureView();
            ampmStr.setStringPicture( this.time.ampmStr );
            ampm.add(ampmStr);
            ampm.setTextAttrForAll(
                    settings.am_pmFontSize,
                    settings.am_pmColor,
                    ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE)
            );
            // Position based on screen on
            ampm.alignX = 2;
            ampm.alignY = 0;
            tmp_left = (int) settings.am_pmLeft;
            if(!settings.am_pmAlignLeft) {
                // If text is centered, set rectangle
                ampm.setRect(
                        (int) (2 * tmp_left + 640),
                        (int) (settings.am_pmFontSize)
                );
                tmp_left = -320;
            }
            ampm.setStart(
                    (int) tmp_left,
                    (int) (settings.am_pmTop-((float)settings.font_ratio/100)*settings.am_pmFontSize)
            );
            slpt_objects.add(ampm);
        }


        // Draw Alarm
        if(settings.watch_alarm>0){
            // Show or Not icon
            if (settings.watch_alarmIcon) {
                SlptPictureView watch_alarmIcon = new SlptPictureView();
                watch_alarmIcon.setImagePicture( SimpleFile.readFileFromAssets(service, ( (better_resolution)?"":"slpt_" )+"icons/alarm.png") );
                watch_alarmIcon.setStart(
                        (int) settings.watch_alarmIconLeft,
                        (int) settings.watch_alarmIconTop
                );
                slpt_objects.add(watch_alarmIcon);
            }

            SlptLinearLayout alarmLayout = new SlptLinearLayout();
            SlptPictureView alarmStr = new SlptPictureView();
            alarmStr.setStringPicture( this.alarm );
            alarmLayout.add(alarmStr);
            alarmLayout.setTextAttrForAll(
                    settings.watch_alarmFontSize,
                    settings.watch_alarmColor,
                    ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE)
            );
            // Position based on screen on
            alarmLayout.alignX = 2;
            alarmLayout.alignY = 0;
            tmp_left = (int) settings.watch_alarmLeft;
            if(!settings.watch_alarmAlignLeft) {
                // If text is centered, set rectangle
                alarmLayout.setRect(
                        (int) (2 * tmp_left + 640),
                        (int) (settings.watch_alarmFontSize)
                );
                tmp_left = -320;
            }
            alarmLayout.setStart(
                    (int) tmp_left,
                    (int) (settings.watch_alarmTop-((float)settings.font_ratio/100)*settings.watch_alarmFontSize)
            );
            //Add it to the list
            slpt_objects.add(alarmLayout);
        }


        // Draw Xdrip
        if(settings.xdrip>0){
            // Show or Not icon
            if (settings.xdripIcon) {
                SlptPictureView xdripIcon = new SlptPictureView();
                xdripIcon.setImagePicture( SimpleFile.readFileFromAssets(service, ( (better_resolution)?"":"slpt_" )+"icons/xdrip.png") );
                xdripIcon.setStart(
                        (int) settings.xdripIconLeft,
                        (int) settings.xdripIconTop
                );
                slpt_objects.add(xdripIcon);
            }

            SlptLinearLayout xdripLayout = new SlptLinearLayout();
            SlptPictureView xdripStr = new SlptPictureView();
            xdripStr.setStringPicture( this.xdripData.sgv );
            xdripLayout.add(xdripStr);
            xdripLayout.setTextAttrForAll(
                    settings.xdripFontSize,
                    settings.xdripColor,
                    ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE)
            );
            // Position based on screen on
            xdripLayout.alignX = 2;
            xdripLayout.alignY = 0;
            tmp_left = (int) settings.xdripLeft;
            if(!settings.xdripAlignLeft) {
                // If text is centered, set rectangle
                xdripLayout.setRect(
                        (int) (2 * tmp_left + 640),
                        (int) (settings.xdripFontSize)
                );
                tmp_left = -320;
            }
            xdripLayout.setStart(
                    (int) tmp_left,
                    (int) (settings.xdripTop-((float)settings.font_ratio/100)*settings.xdripFontSize)
            );
            //Add it to the list
            slpt_objects.add(xdripLayout);
        }


        // Draw AirPressure
        if(settings.air_pressure>0){
            // Show or Not icon
            if (settings.air_pressureIcon) {
                SlptPictureView air_pressureIcon = new SlptPictureView();
                air_pressureIcon.setImagePicture( SimpleFile.readFileFromAssets(service, ( (better_resolution)?"":"slpt_" )+"icons/air_pressure.png") );
                air_pressureIcon.setStart(
                        (int) settings.air_pressureIconLeft,
                        (int) settings.air_pressureIconTop
                );
                slpt_objects.add(air_pressureIcon);
            }

            SlptLinearLayout airPressureLayout = new SlptLinearLayout();
            SlptPictureView airPressureStr = new SlptPictureView();
            airPressureStr.setStringPicture( this.customData.airPressure );
            airPressureLayout.add(airPressureStr);
            // Show or Not Units
            if(settings.air_pressureUnits) {
                SlptPictureView airPressureUnit = new SlptPictureView();
                airPressureUnit.setStringPicture(" hPa");
                airPressureLayout.add(airPressureUnit);
            }
            airPressureLayout.setTextAttrForAll(
                    settings.air_pressureFontSize,
                    settings.air_pressureColor,
                    ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE)
            );
            // Position based on screen on
            airPressureLayout.alignX = 2;
            airPressureLayout.alignY = 0;
            tmp_left = (int) settings.air_pressureLeft;
            if(!settings.air_pressureAlignLeft) {
                // If text is centered, set rectangle
                airPressureLayout.setRect(
                        (int) (2 * tmp_left + 640),
                        (int) (settings.air_pressureFontSize)
                );
                tmp_left = -320;
            }
            airPressureLayout.setStart(
                    (int) tmp_left,
                    (int) (settings.air_pressureTop-((float)settings.font_ratio/100)*settings.air_pressureFontSize)
            );
            //Add it to the list
            slpt_objects.add(airPressureLayout);
        }

        // Draw Altitude
        if(settings.altitude>0){
            // Show or Not icon
            if (settings.altitudeIcon) {
                SlptPictureView altitudeIcon = new SlptPictureView();
                altitudeIcon.setImagePicture( SimpleFile.readFileFromAssets(service, ( (better_resolution)?"":"slpt_" )+"icons/altitude.png") );
                altitudeIcon.setStart(
                        (int) settings.altitudeIconLeft,
                        (int) settings.altitudeIconTop
                );
                slpt_objects.add(altitudeIcon);
            }

            SlptLinearLayout altitudeLayout = new SlptLinearLayout();
            SlptPictureView altitudeStr = new SlptPictureView();
            altitudeStr.setStringPicture( this.customData.altitude );
            altitudeLayout.add(altitudeStr);
            // Show or Not Units
            if(settings.altitudeUnits) {
                SlptPictureView altitudeUnit = new SlptPictureView();
                altitudeUnit.setStringPicture(" m");
                altitudeLayout.add(altitudeUnit);
            }
            altitudeLayout.setTextAttrForAll(
                    settings.altitudeFontSize,
                    settings.altitudeColor,
                    ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE)
            );
            // Position based on screen on
            altitudeLayout.alignX = 2;
            altitudeLayout.alignY = 0;
            tmp_left = (int) settings.altitudeLeft;
            if(!settings.altitudeAlignLeft) {
                // If text is centered, set rectangle
                altitudeLayout.setRect(
                        (int) (2 * tmp_left + 640),
                        (int) (settings.altitudeFontSize)
                );
                tmp_left = -320;
            }
            altitudeLayout.setStart(
                    (int) tmp_left,
                    (int) (settings.altitudeTop-((float)settings.font_ratio/100)*settings.altitudeFontSize)
            );
            //Add it to the list
            slpt_objects.add(altitudeLayout);
        }

        // Draw Phone's Battery
        if(settings.phone_battery>0){
            // Show or Not icon
            if (settings.phone_batteryIcon) {
                SlptPictureView phone_batteryIcon = new SlptPictureView();
                phone_batteryIcon.setImagePicture( SimpleFile.readFileFromAssets(service, ( (better_resolution)?"":"slpt_" )+"icons/phone_battery.png") );
                phone_batteryIcon.setStart(
                        (int) settings.phone_batteryIconLeft,
                        (int) settings.phone_batteryIconTop
                );
                slpt_objects.add(phone_batteryIcon);
            }

            SlptLinearLayout phoneBatteryLayout = new SlptLinearLayout();
            SlptPictureView phoneBatteryStr = new SlptPictureView();
            phoneBatteryStr.setStringPicture( this.customData.phoneBattery+"%" );
            phoneBatteryLayout.add(phoneBatteryStr);
            phoneBatteryLayout.setTextAttrForAll(
                    settings.phone_batteryFontSize,
                    settings.phone_batteryColor,
                    ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE)
            );
            // Position based on screen on
            phoneBatteryLayout.alignX = 2;
            phoneBatteryLayout.alignY = 0;
            tmp_left = (int) settings.phone_batteryLeft;
            if(!settings.phone_batteryAlignLeft) {
                // If text is centered, set rectangle
                phoneBatteryLayout.setRect(
                        (int) (2 * tmp_left + 640),
                        (int) (settings.phone_batteryFontSize)
                );
                tmp_left = -320;
            }
            phoneBatteryLayout.setStart(
                    (int) tmp_left,
                    (int) (settings.phone_batteryTop-((float)settings.font_ratio/100)*settings.phone_batteryFontSize)
            );
            //Add it to the list
            slpt_objects.add(phoneBatteryLayout);
        }

        return slpt_objects;
    }
}
