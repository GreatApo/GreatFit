package com.dinodevs.greatfitwatchface.widget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.provider.Settings;
import android.text.TextPaint;
import android.util.Log;

import com.dinodevs.greatfitwatchface.AbstractWatchFace;
import com.dinodevs.greatfitwatchface.data.Alarm;
import com.dinodevs.greatfitwatchface.data.CustomData;
import com.dinodevs.greatfitwatchface.data.Pressure;
import com.dinodevs.greatfitwatchface.data.Steps;
import com.dinodevs.greatfitwatchface.data.Xdrip;
import com.dinodevs.greatfitwatchface.settings.LoadSettings;
import com.huami.watch.watchface.util.Util;
import com.ingenic.iwds.slpt.view.arc.SlptArcAnglePicView;
import com.ingenic.iwds.slpt.view.core.SlptLinearLayout;
import com.ingenic.iwds.slpt.view.core.SlptNumView;
import com.ingenic.iwds.slpt.view.core.SlptPictureView;
import com.ingenic.iwds.slpt.view.core.SlptViewComponent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import com.dinodevs.greatfitwatchface.data.DataType;
import com.dinodevs.greatfitwatchface.data.Time;
import com.dinodevs.greatfitwatchface.resource.ResourceManager;
import com.ingenic.iwds.slpt.view.digital.SlptMinuteHView;
import com.ingenic.iwds.slpt.view.digital.SlptMinuteLView;
import com.ingenic.iwds.slpt.view.utils.SimpleFile;

import org.json.JSONException;
import org.json.JSONObject;

import static com.dinodevs.greatfitwatchface.data.DataType.TIME;


public class GreatWidget extends AbstractWidget {
    private Time time;
    private CustomData customData;
    private Alarm alarmData;
    private Xdrip xdripData;
    private static Steps stepsData;
    private static Pressure pressureData;

    private TextPaint ampmPaint, alarmPaint, xdripPaint, airPressurePaint, altitudePaint, phoneBatteryPaint, phoneAlarmPaint, world_timePaint, notificationsPaint, walked_distancePaint;
    private Bitmap watch_alarmIcon, xdripIcon, air_pressureIcon, altitudeIcon, phone_batteryIcon, phone_alarmIcon, world_timeIcon, notificationsIcon, walked_distanceIcon;

    private String tempAMPM;
    private String time_format;
    private String alarm;
    private Integer tempHour;
    private static float altitude;
    private static int pressure;
    private static int steps;

    private Float phone_batterySweepAngle=0f;
    private Integer angleLength;
    private Paint ring;

    private Service mService;
    private LoadSettings settings;

    private boolean firstRun = false;

    // Pressure sensor
    private boolean airPressureBool;
    private String tempAirPressure = "--";
    private SensorManager mManager;
    private Sensor mPressureSensor;
    private SensorEventListener mListener;
    private Sensor mStepsSensor;
    private SensorEventListener mStepsListener;

    private final static  String TAG = "DinoDevs-GreatFit";

    // Constructor
    public GreatWidget(LoadSettings settings) {
        this.settings = settings;

        if(!(settings.phone_batteryProg>0 && settings.phone_batteryProgType==0)){return;}
        if(settings.phone_batteryProgClockwise==1) {
            this.angleLength = (settings.phone_batteryProgEndAngle < settings.phone_batteryProgStartAngle) ? 360 - (settings.phone_batteryProgStartAngle - settings.phone_batteryProgEndAngle) : settings.phone_batteryProgEndAngle - settings.phone_batteryProgStartAngle;
        }else{
            this.angleLength = (settings.phone_batteryProgEndAngle > settings.phone_batteryProgStartAngle) ? 360 - (settings.phone_batteryProgStartAngle - settings.phone_batteryProgEndAngle) : settings.phone_batteryProgEndAngle - settings.phone_batteryProgStartAngle;
        }
    }

    // Screen-on init (runs once)
    @Override
    public void init(Service service){
        // This service
        this.mService = service;

        // Get AM/PM
        if(settings.am_pm_always) {
            this.time = getSlptTime();
            this.time_format = Settings.System.getString(this.mService.getContentResolver(), "time_12_24");
            this.tempAMPM = this.time.ampmStr;
            this.ampmPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
            this.ampmPaint.setColor(settings.am_pmColor);
            this.ampmPaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), settings.font));
            this.ampmPaint.setTextSize(settings.am_pmFontSize);
            this.ampmPaint.setTextAlign((settings.am_pmAlignLeft) ? Paint.Align.LEFT : Paint.Align.CENTER);
        }

        // Get next alarm
        if(settings.watch_alarm>0) {
            this.alarmData = getAlarm();
            this.alarm = this.alarmData.alarm; // ex: Fri 10:30
            this.alarmPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
            this.alarmPaint.setColor(settings.watch_alarmColor);
            this.alarmPaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), settings.font));
            this.alarmPaint.setTextSize(settings.watch_alarmFontSize);
            this.alarmPaint.setTextAlign((settings.watch_alarmAlignLeft) ? Paint.Align.LEFT : Paint.Align.CENTER);

            if(settings.watch_alarmIcon){
                this.watch_alarmIcon = Util.decodeImage(service.getResources(),"icons/"+settings.is_white_bg+"alarm.png");
            }
        }

        // Xdrip
        if(settings.xdrip>0) {
            // Get xdrip
            this.xdripData = getXdrip();
            this.xdripPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
            this.xdripPaint.setColor(settings.xdripColor);
            this.xdripPaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), settings.font));
            this.xdripPaint.setTextSize(settings.xdripFontSize);
            this.xdripPaint.setTextAlign((settings.xdripAlignLeft) ? Paint.Align.LEFT : Paint.Align.CENTER);

            if(settings.xdripIcon){
                this.xdripIcon = Util.decodeImage(service.getResources(),"icons/"+settings.is_white_bg+"xdrip.png");
            }
        }

        // CustomData
        this.customData = getCustomData();
        if(settings.air_pressure>0) {
            this.airPressurePaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
            this.airPressurePaint.setColor(settings.air_pressureColor);
            this.airPressurePaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), settings.font));
            this.airPressurePaint.setTextSize(settings.air_pressureFontSize);
            this.airPressurePaint.setTextAlign((settings.air_pressureAlignLeft) ? Paint.Align.LEFT : Paint.Align.CENTER);

            if(settings.air_pressureIcon){
                this.air_pressureIcon = Util.decodeImage(service.getResources(),"icons/"+settings.is_white_bg+"air_pressure.png");
            }
        }
        if(settings.altitude>0) {
            this.altitudePaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
            this.altitudePaint.setColor(settings.altitudeColor);
            this.altitudePaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), settings.font));
            this.altitudePaint.setTextSize(settings.altitudeFontSize);
            this.altitudePaint.setTextAlign((settings.altitudeAlignLeft) ? Paint.Align.LEFT : Paint.Align.CENTER);

            if(settings.altitudeIcon){
                this.altitudeIcon = Util.decodeImage(service.getResources(),"icons/"+settings.is_white_bg+"altitude.png");
            }
        }
        if(settings.phone_battery>0) {
            this.phoneBatteryPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
            this.phoneBatteryPaint.setColor(settings.phone_batteryColor);
            this.phoneBatteryPaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), settings.font));
            this.phoneBatteryPaint.setTextSize(settings.phone_batteryFontSize);
            this.phoneBatteryPaint.setTextAlign((settings.phone_batteryAlignLeft) ? Paint.Align.LEFT : Paint.Align.CENTER);

            if(settings.phone_batteryIcon){
                this.phone_batteryIcon = Util.decodeImage(service.getResources(),"icons/"+settings.is_white_bg+"phone_battery.png");
            }
        }
        if(settings.phone_alarm>0) {
            this.phoneAlarmPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
            this.phoneAlarmPaint.setColor(settings.phone_alarmColor);
            this.phoneAlarmPaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), settings.font));
            this.phoneAlarmPaint.setTextSize(settings.phone_alarmFontSize);
            this.phoneAlarmPaint.setTextAlign((settings.phone_alarmAlignLeft) ? Paint.Align.LEFT : Paint.Align.CENTER);

            if(settings.phone_alarmIcon){
                this.phone_alarmIcon = Util.decodeImage(service.getResources(),"icons/"+settings.is_white_bg+"phone_alarm.png");
            }
        }
        if(settings.notifications>0) {
            this.notificationsPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
            this.notificationsPaint.setColor(settings.notificationsColor);
            this.notificationsPaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), settings.font));
            this.notificationsPaint.setTextSize(settings.notificationsFontSize);
            this.notificationsPaint.setTextAlign((settings.notificationsAlignLeft) ? Paint.Align.LEFT : Paint.Align.CENTER);

            if(settings.notificationsIcon){
                this.notificationsIcon = Util.decodeImage(service.getResources(),"icons/"+settings.is_white_bg+"notifications.png");
            }
        }

        // World time
        if(settings.world_time>0) {
            // Set initial temp value
            Calendar now = Calendar.getInstance();
            if(settings.world_time_zone%1!=0)
                now.add(Calendar.MINUTE, 30);
            this.tempHour = now.get(Calendar.HOUR_OF_DAY);

            // Get world_time
            this.world_timePaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
            this.world_timePaint.setColor(settings.world_timeColor);
            this.world_timePaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), settings.font));
            this.world_timePaint.setTextSize(settings.world_timeFontSize);
            this.world_timePaint.setTextAlign((settings.world_timeAlignLeft) ? Paint.Align.LEFT : Paint.Align.CENTER);

            if(settings.world_timeIcon){
                this.world_timeIcon = Util.decodeImage(service.getResources(),"icons/"+settings.is_white_bg+"world_time.png");
            }
        }

        // Progress Bar Circle
        if(settings.phone_batteryProg>0 && settings.phone_batteryProgType==0){
            this.ring = new Paint(Paint.ANTI_ALIAS_FLAG);
            this.ring.setStrokeCap(Paint.Cap.ROUND);
            this.ring.setStyle(Paint.Style.STROKE);
            this.ring.setStrokeWidth(settings.phone_batteryProgThickness);
        }

        // Get AirPressure in hPa
        airPressureBool = (settings.air_pressure>0 || settings.altitude>0);
        if(airPressureBool) {
            // WearCompass.jar!\com\huami\watch\compass\logic\GeographicManager.class
            this.mManager = (SensorManager) this.mService.getApplicationContext().getSystemService(Context.SENSOR_SERVICE);
            try {
                this.mPressureSensor = this.mManager.getDefaultSensor(6);
                this.mListener = new SensorEventListener() {
                    public void onAccuracyChanged(Sensor parameter1, int parameter2) {
                    }

                    public void onSensorChanged(SensorEvent parameters) {
                        // Unregister sensor
                        GreatWidget.this.mManager.unregisterListener(this);
                        // Get sensor data
                        float[] pressure = parameters.values;
                        if (pressure != null && pressure.length > 0) {
                            float value = pressure[0];
                            int temp = GreatWidget.this.getTemperature();
                            //Log.d(TAG, "Pressure is " + value + " hPa and temperature is "+temp+" C");
                            onDataUpdate(DataType.PRESSURE, new Pressure(value, temp));
                        }
                    }
                };
            } catch (NullPointerException e) {
                Log.e( TAG, "GreatWidget pressure-sensor"+ e.getMessage() );
            }
        }

        // Walked distance
        if(settings.walked_distance>0){
            this.walked_distancePaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
            this.walked_distancePaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), settings.font));
            this.walked_distancePaint.setTextSize(settings.walked_distanceFontSize);
            this.walked_distancePaint.setColor(settings.walked_distanceColor);
            this.walked_distancePaint.setTextAlign( (settings.walked_distanceAlignLeft) ? Paint.Align.LEFT : Paint.Align.CENTER );

            if(settings.walked_distanceIcon){
                this.walked_distanceIcon = Util.decodeImage(service.getResources(),"icons/"+settings.is_white_bg+"today_distance.png");
            }

            // Steps sensor
            if(this.mManager == null)
                this.mManager = (SensorManager) this.mService.getApplicationContext().getSystemService(Context.SENSOR_SERVICE);
            try {
                this.mStepsSensor = this.mManager.getDefaultSensor(19);
                this.mStepsListener = new SensorEventListener() {
                    public void onAccuracyChanged(Sensor parameter1, int parameter2) {
                    }

                    public void onSensorChanged(SensorEvent parameters) {
                        // Unregister sensor
                        GreatWidget.this.mManager.unregisterListener(this);
                        // Get sensor data
                        float[] steps = parameters.values;
                        if (steps != null && steps.length > 0) {
                            int value = (int) steps[0];
                            if (value < 0)
                                Log.w(GreatWidget.TAG, "GreatWidget steps-sensor: value is below zero!");
                            else
                                onDataUpdate(DataType.STEPS, new Steps(value, 0));
                        }
                    }
                };
            } catch (NullPointerException e) {
                Log.e( TAG, "GreatWidget steps-sensor"+ e.getMessage() );
            }
        }

        // Custom data refresher
        scheduleUpdate();
    }

    // Draw screen-on
    @Override
    public void draw(Canvas canvas, float width, float height, float centerX, float centerY) {
        // Draw AM or PM, if enabled
        if(settings.am_pm_always && this.time_format.equals("24")) {
            canvas.drawText(/*Capitalize*/String.format("%S", this.tempAMPM), settings.am_pmLeft, settings.am_pmTop, ampmPaint);
        }

        // Draw Alarm, if enabled
        if(settings.watch_alarm>0) {
            if(settings.watch_alarmIcon){
                canvas.drawBitmap(this.watch_alarmIcon, settings.watch_alarmIconLeft, settings.watch_alarmIconTop, settings.mGPaint);
            }
            canvas.drawText(translate_alarm(this.alarmData.alarm), settings.watch_alarmLeft, settings.watch_alarmTop, alarmPaint);
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
            String pres = "--";
            if(this.pressureData!=null) {
                pres = pressureData.getPressure(settings.air_pressureUnits, settings.pressure_to_mmhg?1:0);
            }

            canvas.drawText(pres, settings.air_pressureLeft, settings.air_pressureTop, airPressurePaint);
        }

        // Draw Altitude, if enabled
        if(settings.altitude>0) {
            if(settings.altitudeIcon){
                canvas.drawBitmap(this.altitudeIcon, settings.altitudeIconLeft, settings.altitudeIconTop, settings.mGPaint);
            }
            String alt = "--";
            if(this.pressureData!=null) {
                if (settings.isMetric) {
                    alt = pressureData.getAltitudeMetric(settings.altitudeUnits);
                } else {
                    alt = pressureData.getAltitudeImperial(settings.altitudeUnits);
                }
            }
            canvas.drawText(alt, settings.altitudeLeft, settings.altitudeTop, altitudePaint);
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
            if(settings.phone_alarmIcon){
                canvas.drawBitmap(this.phone_alarmIcon, settings.phone_alarmIconLeft, settings.phone_alarmIconTop, settings.mGPaint);
            }
            canvas.drawText(translate_alarm(this.customData.phoneAlarm), settings.phone_alarmLeft, settings.phone_alarmTop, phoneAlarmPaint);
        }

        // Draw notifications
        if(settings.notifications>0) {
            if(settings.notificationsIcon){
                canvas.drawBitmap(this.notificationsIcon, settings.notificationsIconLeft, settings.notificationsIconTop, settings.mGPaint);
            }
            canvas.drawText(this.customData.notifications, settings.notificationsLeft, settings.notificationsTop, notificationsPaint);
        }

        // Draw world_time, if enabled
        if(settings.world_time>0) {
            if(settings.world_timeIcon){
                canvas.drawBitmap(this.world_timeIcon, settings.world_timeIconLeft, settings.world_timeIconTop, settings.mGPaint);
            }
            Calendar now = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
            now.add(Calendar.HOUR, (int) settings.world_time_zone);
            if(settings.world_time_zone%1!=0) {
                now.add(Calendar.MINUTE, (settings.world_time_zone>0)?30:-30);
            }
            int hours = now.get(Calendar.HOUR_OF_DAY);
            int minutes = now.get(Calendar.MINUTE);
            canvas.drawText(Util.formatTime(hours)+":"+Util.formatTime(minutes), settings.world_timeLeft, settings.world_timeTop, world_timePaint);
        }

        // Draw walked distance
        if(settings.walked_distance>0) {
            if(settings.walked_distanceIcon){
                canvas.drawBitmap(this.walked_distanceIcon, settings.walked_distanceIconLeft, settings.walked_distanceIconTop, settings.mGPaint);
            }
            String distance = "N/A";
            if (stepsData != null) {
                if (settings.isMetric) {
                    distance = stepsData.getStepsMetric((double) settings.step_length);
                } else {
                    distance = stepsData.getStepsImperial((double) settings.step_length);
                }
            }
            canvas.drawText(distance, settings.walked_distanceLeft, settings.walked_distanceTop, walked_distancePaint);
        }

        // phone_battery bar
        if(settings.phone_batteryProg>0 && settings.phone_batteryProgType==0) {
            int count = canvas.save();
            // Rotate canvas to 0 degrees = 12 o'clock
            canvas.rotate(-90, centerX, centerY);
            // Define circle
            float radius = settings.phone_batteryProgRadius - settings.phone_batteryProgThickness;
            RectF oval = new RectF(settings.phone_batteryProgLeft - radius, settings.phone_batteryProgTop - radius, settings.phone_batteryProgLeft + radius, settings.phone_batteryProgTop + radius);
            // Background
            if(settings.phone_batteryProgBgBool) {
                this.ring.setColor(Color.parseColor("#999999"));
                canvas.drawArc(oval, settings.phone_batteryProgStartAngle, this.angleLength, false, ring);
            }
            this.ring.setColor(settings.colorCodes[settings.phone_batteryProgColorIndex]);
            canvas.drawArc(oval, settings.phone_batteryProgStartAngle, this.phone_batterySweepAngle, false, ring);

            canvas.restoreToCount(count);
        }
    }

    // Register update listeners
    @Override
    public List<DataType> getDataTypes() {
        List<DataType> dataTypes = new ArrayList<>();

        if(settings.am_pm_always || settings.world_time_zone>0)
            dataTypes.add(TIME);

        if( settings.air_pressure>0 || settings.phone_alarm>0 || settings.phone_battery>0 || settings.phone_batteryProg>0 || settings.altitude>0 || settings.notifications>0 )
            dataTypes.add(DataType.CUSTOM);

        if(settings.watch_alarm>0)
            dataTypes.add(DataType.ALARM);

        if(settings.xdrip>0)
            dataTypes.add(DataType.XDRIP);

        if(settings.walked_distance>0)
            dataTypes.add(DataType.STEPS);

        return dataTypes;
    }

    // Value updater
    @Override
    public void onDataUpdate(DataType type, Object value) {
        //Log.w(TAG, type.toString()+" => "+value.toString() );
        boolean refreshSlpt = false;

        // On each Data updated
        switch (type) {
            case TIME:
                // Update AM/PM
                this.time = (Time) value;
                this.time_format = Settings.System.getString(this.mService.getContentResolver(), "time_12_24");
                if((settings.am_pm_always && this.time_format.equals("24")) && !this.tempAMPM.equals(this.time.ampmStr)){
                    this.tempAMPM = this.time.ampmStr;
                    refreshSlpt = true;
                }
                // Update World Time
                if(settings.world_time>0){
                    Integer hours = this.time.hours;
                    if(settings.world_time_zone%1!=0){
                        Calendar now = Calendar.getInstance();//doesn't mates if it is GMT or local, only the hour change maters
                        now.add(Calendar.MINUTE, 30);
                        hours = now.get(Calendar.HOUR_OF_DAY);
                    }
                    if(!this.tempHour.equals(hours)){
                        this.tempHour=hours;
                        refreshSlpt = true;
                    }
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
                if(this.customData!=null) {
                    // Battery bar angle
                    if (settings.phone_batteryProg > 0 && settings.phone_batteryProgType == 0) {
                        int temp_battery = (this.customData.phoneBattery.equals("--")) ? 0 : Integer.parseInt(this.customData.phoneBattery);
                        this.phone_batterySweepAngle = this.angleLength * Math.min(temp_battery / 100f, 1f);
                    }
                }
                break;
            case STEPS:
                // Update walked distance
                this.stepsData = (Steps) value;
                if (this.steps != this.stepsData.getSteps()) {
                    this.steps = this.stepsData.getSteps();
                    refreshSlpt = true;
                }
                break;
            case PRESSURE:
                this.pressureData = (Pressure) value;
                if(this.altitude!=this.pressureData.altitude || this.pressure!=(int) this.pressureData.airPressure){
                    this.altitude = this.pressureData.altitude;
                    this.pressure = (int) this.pressureData.airPressure;
                    refreshSlpt = true;
                }
                break;
        }

        // Refresh Slpt
        if (refreshSlpt) {
            refreshSlpt( type.toString() );
            scheduleUpdate();
        }
    }

    // Data updater scheduler
    public void scheduleUpdate() {
        Calendar now = Calendar.getInstance();
        int hours = now.get(Calendar.HOUR_OF_DAY);
        int minutes = now.get(Calendar.MINUTE);
        int seconds = now.get(Calendar.SECOND);
        int millisecond = now.get(Calendar.MILLISECOND);

        // Send a time update, because we can :P
        Object values = new Time(seconds, minutes, hours, -1);
        onDataUpdate(TIME, values);

        int refreshTime = 48*60*60*1000; //Big value: 2 days
        String type = "default";
        minutes = (59 - minutes)*60*1000; // Minutes to next hour in ms
        seconds = (60 - seconds)*1000; // Seconds to next minute in ms
        long currentTime = System.currentTimeMillis();

        // Refresh AM/PM
        if(settings.am_pm_always && settings.digital_clock){
            refreshTime = (11-(hours % 12))*60*60*1000 + minutes + seconds + millisecond+1;
            type = "AM/PM";
        }

        // Refreshes world_time
        if(settings.world_time>0) {
            // Calculate remaining time to next hour change
            if (settings.world_time_zone % 1 != 0) {
                now.add(Calendar.MINUTE, (settings.world_time_zone > 0) ? 30 : -30);
                minutes = (60 - now.get(Calendar.MINUTE))*60*1000; // 59min because of delay
            }

            int tempRefreshTime = minutes + seconds + millisecond+1;
            if(refreshTime>tempRefreshTime){
                refreshTime = tempRefreshTime;
                type = "World time";
            }
        }

        // Air pressure or Walked distance
        //Log.i(TAG, "scheduleUpdate next alarm: airPressureBool:"+airPressureBool+", custom_refresh_rate:"+settings.custom_refresh_rate+"sec");
        if(airPressureBool || settings.walked_distance>0){
            //Log.d(TAG, "Sensor custom refresh in "+settings.custom_refresh_rate+" sec");

            // Update AirPressure
            if(airPressureBool)
                mManager.registerListener(GreatWidget.this.mListener, GreatWidget.this.mPressureSensor, 60*1000);

            // Update Steps
            if(settings.walked_distance>0)
                mManager.registerListener(GreatWidget.this.mStepsListener, GreatWidget.this.mStepsSensor, 0);

            int tempRefreshTime = (settings.custom_refresh_rate>0)? settings.custom_refresh_rate : /*Big value: 2 days*/ 48*60*60*1000;
            if(refreshTime>tempRefreshTime) {
                refreshTime = tempRefreshTime;
                type = "air pressure / walked distance";
            }
        }

        scheduleUpdate(currentTime + refreshTime);
        // Log the next refresh
        now.setTimeInMillis(currentTime + refreshTime);
        Log.i(TAG, String.format("scheduleUpdate next alarm: %02d:%02d:%02d , type: "+type, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), now.get(Calendar.SECOND)));
    }

    private void scheduleUpdate(long time) {
        Context context = this.mService;
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(AlarmReceiver.REQUEST_CODE, AlarmReceiver.GREATWIDGET_CODE);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, AlarmReceiver.GREATWIDGET_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (alarmIntent == null)
            Log.e(TAG,"scheduleUpdate null error!");
        else if (alarmMgr != null)
            alarmMgr.setExact(AlarmManager.RTC_WAKEUP, time, alarmIntent);
        else
            Log.e(TAG, "scheduleUpdate null alarmMgr!");
    }

    public void refreshSlpt(String reason) {
        refreshSlpt(reason, false);
    }

    public void refreshSlpt(String reason, boolean redraw) {
        if (this.mService instanceof AbstractWatchFace) {
            Log.i(TAG, "onDataUpdate calling slpt refresh: "+reason);
            ((AbstractWatchFace) this.mService).restartSlpt(redraw);
        }
    }


    private void log(String type, boolean isFirstRun) {
        String str = "onDataUpdate refreshSlpt %s ";
        if (isFirstRun)
            str += "firstrun";
        if (this.time != null)
            Log.i(TAG, String.format(str + " - %02d:%02d:%02d", type, this.time.hours, this.time.minutes, this.time.seconds));
        else
            Log.i(TAG, String.format(str, type));
    }


    // Get data functions
    private Time getSlptTime() {
        return new Time(Calendar.getInstance().get(Calendar.HOUR_OF_DAY) < 12 ? 0 : 1);
    }

    private Alarm getAlarm() {
        return new Alarm(Settings.System.getString(this.mService.getContentResolver(), Settings.System.NEXT_ALARM_FORMATTED));
    }

    private Xdrip getXdrip(){
        return new Xdrip(Settings.System.getString(this.mService.getContentResolver(), "xdrip"));
    }

    private CustomData getCustomData(){
        return new CustomData(Settings.System.getString(this.mService.getContentResolver(), "CustomWatchfaceData"));
    }

    public int getTemperature() {
        int temperature = 15; // Default: an average temperature (ºC)
        // Get ALL data from system
        String str = Settings.System.getString(this.mService.getApplicationContext().getContentResolver(), "WeatherInfo");
        JSONObject weather_data;
        try {
            weather_data = new JSONObject(str);
            if (weather_data.has("temp") && weather_data.has("tempUnit")) {
                String temp = weather_data.getString("temp");
                try {// Convert from float to int
                    temperature = (int) Float.parseFloat(temp);
                } catch (Exception e) {
                    // error in the conversion
                    return temperature;
                }

                String tempUnit = weather_data.getString("tempUnit");
                if (!tempUnit.equals("1") && !tempUnit.equals("C")){
                    // Fahrenheit to Celsius
                    temperature = (temperature - 32) * 5/9;
                }
            }
        }catch (JSONException e) {
            // JSON error
        }
        return temperature;
    }

    // Translate the alarm
    private String translate_alarm(String string){
        return string
                .replace("Sun", MainClock.days_3let[settings.language][0])
                .replace("Mon", MainClock.days_3let[settings.language][1])
                .replace("Tue", MainClock.days_3let[settings.language][2])
                .replace("Wed", MainClock.days_3let[settings.language][3])
                .replace("Thu", MainClock.days_3let[settings.language][4])
                .replace("Fri", MainClock.days_3let[settings.language][5])
                .replace("Sat", MainClock.days_3let[settings.language][6]);
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

        // Do not show in SLPT (but show on raise of hand)
        boolean show_all = (!settings.clock_only_slpt || better_resolution);
        // SLPT only clock white bg -> to black
        if(!show_all && settings.isVerge() && settings.white_bg) {
            settings.is_white_bg = "";
            settings.am_pmColor = Color.parseColor("#ffffff");
        }

        // Draw AM or PM
        this.time_format = Settings.System.getString(this.mService.getContentResolver(), "time_12_24");
        if((settings.am_pm_always && time_format.equals("24")) && settings.digital_clock){
            // Draw
            SlptLinearLayout ampm = new SlptLinearLayout();
            SlptPictureView ampmStr = new SlptPictureView();
            ampmStr.setStringPicture( /*Get AM/PM */ getSlptTime().ampmStr );
            ampm.add(ampmStr);
            ampm.setTextAttrForAll(
                    settings.am_pmFontSize,
                    settings.am_pmColor,
                    ResourceManager.getTypeFace(service.getResources(), settings.font)
            );
            // Position based on screen on
            ampm.alignX = 2;
            ampm.alignY = 0;
            tmp_left = (int) settings.am_pmLeft;
            if(!settings.am_pmAlignLeft) {
                // If text is centered, set rectangle
                ampm.setRect(
                        (int) (2 * tmp_left + 640),
                        (int) (((float)settings.font_ratio/100)*settings.am_pmFontSize)
                );
                tmp_left = -320;
            }
            ampm.setStart(
                    (int) tmp_left,
                    (int) (settings.am_pmTop-((float)settings.font_ratio/100)*settings.am_pmFontSize)
            );
            slpt_objects.add(ampm);
        }

        // CustomData
        this.customData = getCustomData();

        // Draw Notifications
        if(settings.notifications>0 && (show_all || (!this.customData.notifications.equals("--") && !this.customData.notifications.equals("0")))){
            // Show or Not icon
            if (settings.notificationsIcon) {
                SlptPictureView notificationIcon = new SlptPictureView();
                notificationIcon.setImagePicture( SimpleFile.readFileFromAssets(service, ( (better_resolution)?"26wc_":"slpt_" )+"icons/"+settings.is_white_bg+"notifications.png") );
                notificationIcon.setStart(
                        (int) settings.notificationsIconLeft,
                        (int) settings.notificationsIconTop
                );
                slpt_objects.add(notificationIcon);
            }

            SlptLinearLayout notificationsLayout = new SlptLinearLayout();
            SlptPictureView notificationsStr = new SlptPictureView();
            // These can be used instead of setTextAttrForAll()
            //notificationsStr.textSize = settings.notificationsFontSize;
            //notificationsStr.typeface = ResourceManager.getTypeFace(service.getResources(), settings.font);
            //notificationsStr.textColor = settings.notificationsColor;
            notificationsStr.setStringPicture( this.customData.notifications+" " );// doesn't work without the space
            notificationsLayout.add(notificationsStr);
            notificationsLayout.setTextAttrForAll(
                    settings.notificationsFontSize,
                    settings.notificationsColor,
                    ResourceManager.getTypeFace(service.getResources(), settings.font)
            );
            // Position based on screen on
            notificationsLayout.alignX = 2;
            notificationsLayout.alignY = 0;
            tmp_left = (int) settings.notificationsLeft;
            if(!settings.notificationsAlignLeft) {
                // If text is centered, set rectangle
                notificationsLayout.setRect(
                        (int) (2 * tmp_left + 640),
                        (int) (((float)settings.font_ratio/100)*settings.notificationsFontSize)
                );
                tmp_left = -320;
            }
            notificationsLayout.setStart(
                    (int) tmp_left,
                    (int) (settings.notificationsTop-((float)settings.font_ratio/100)*settings.notificationsFontSize)
            );
            //Add it to the list
            slpt_objects.add(notificationsLayout);
        }

        // Only CLOCK?
        if (!show_all)
            return slpt_objects;

        // Get next alarm
        this.alarmData = getAlarm();
        this.alarm = alarmData.alarm;

        // Get xdrip
        this.xdripData = getXdrip();

        // Draw Alarm
        if(settings.watch_alarm>0){
            // Show or Not icon
            if (settings.watch_alarmIcon) {
                SlptPictureView watch_alarmIcon = new SlptPictureView();
                watch_alarmIcon.setImagePicture( SimpleFile.readFileFromAssets(service, ( (better_resolution)?"26wc_":"slpt_" )+"icons/"+settings.is_white_bg+"alarm.png") );
                watch_alarmIcon.setStart(
                        (int) settings.watch_alarmIconLeft,
                        (int) settings.watch_alarmIconTop
                );
                slpt_objects.add(watch_alarmIcon);
            }

            SlptLinearLayout alarmLayout = new SlptLinearLayout();
            SlptPictureView alarmStr = new SlptPictureView();
            alarmStr.setStringPicture( translate_alarm(this.alarm) );
            alarmLayout.add(alarmStr);
            alarmLayout.setTextAttrForAll(
                    settings.watch_alarmFontSize,
                    settings.watch_alarmColor,
                    ResourceManager.getTypeFace(service.getResources(), settings.font)
            );
            // Position based on screen on
            alarmLayout.alignX = 2;
            alarmLayout.alignY = 0;
            tmp_left = (int) settings.watch_alarmLeft;
            if(!settings.watch_alarmAlignLeft) {
                // If text is centered, set rectangle
                alarmLayout.setRect(
                        (int) (2 * tmp_left + 640),
                        (int) (((float)settings.font_ratio/100)*settings.watch_alarmFontSize)
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
                xdripIcon.setImagePicture( SimpleFile.readFileFromAssets(service, ( (better_resolution)?"26wc_":"slpt_" )+"icons/"+settings.is_white_bg+"xdrip.png") );
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
                    ResourceManager.getTypeFace(service.getResources(), settings.font)
            );
            // Position based on screen on
            xdripLayout.alignX = 2;
            xdripLayout.alignY = 0;
            tmp_left = (int) settings.xdripLeft;
            if(!settings.xdripAlignLeft) {
                // If text is centered, set rectangle
                xdripLayout.setRect(
                        (int) (2 * tmp_left + 640),
                        (int) (((float)settings.font_ratio/100)*settings.xdripFontSize)
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
                air_pressureIcon.setImagePicture( SimpleFile.readFileFromAssets(service, ( (better_resolution)?"26wc_":"slpt_" )+"icons/"+settings.is_white_bg+"air_pressure.png") );
                air_pressureIcon.setStart(
                        (int) settings.air_pressureIconLeft,
                        (int) settings.air_pressureIconTop
                );
                slpt_objects.add(air_pressureIcon);
            }
            // Get pressure
            String pres = "--";
            if(this.pressureData!=null) {
                pres = pressureData.getPressure(settings.air_pressureUnits, settings.pressure_to_mmhg?1:0);
            }
            SlptLinearLayout airPressureLayout = new SlptLinearLayout();
            SlptPictureView airPressureStr = new SlptPictureView();
            airPressureStr.setStringPicture( pres );
            airPressureLayout.add(airPressureStr);
            airPressureLayout.setTextAttrForAll(
                    settings.air_pressureFontSize,
                    settings.air_pressureColor,
                    ResourceManager.getTypeFace(service.getResources(), settings.font)
            );
            // Position based on screen on
            airPressureLayout.alignX = 2;
            airPressureLayout.alignY = 0;
            tmp_left = (int) settings.air_pressureLeft;
            if(!settings.air_pressureAlignLeft) {
                // If text is centered, set rectangle
                airPressureLayout.setRect(
                        (int) (2 * tmp_left + 640),
                        (int) (((float)settings.font_ratio/100)*settings.air_pressureFontSize)
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
                altitudeIcon.setImagePicture( SimpleFile.readFileFromAssets(service, ( (better_resolution)?"26wc_":"slpt_" )+"icons/"+settings.is_white_bg+"altitude.png") );
                altitudeIcon.setStart(
                        (int) settings.altitudeIconLeft,
                        (int) settings.altitudeIconTop
                );
                slpt_objects.add(altitudeIcon);
            }
            // Get altitude
            String alt = "--";
            if(this.pressureData!=null) {
                if (settings.isMetric) {
                    alt = pressureData.getAltitudeMetric(settings.altitudeUnits);
                } else {
                    alt = pressureData.getAltitudeImperial(settings.altitudeUnits);
                }
            }
            SlptLinearLayout altitudeLayout = new SlptLinearLayout();
            SlptPictureView altitudeStr = new SlptPictureView();
            altitudeStr.setStringPicture( alt );
            altitudeLayout.add(altitudeStr);
            altitudeLayout.setTextAttrForAll(
                    settings.altitudeFontSize,
                    settings.altitudeColor,
                    ResourceManager.getTypeFace(service.getResources(), settings.font)
            );
            // Position based on screen on
            altitudeLayout.alignX = 2;
            altitudeLayout.alignY = 0;
            tmp_left = (int) settings.altitudeLeft;
            if(!settings.altitudeAlignLeft) {
                // If text is centered, set rectangle
                altitudeLayout.setRect(
                        (int) (2 * tmp_left + 640),
                        (int) (((float)settings.font_ratio/100)*settings.altitudeFontSize)
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
                phone_batteryIcon.setImagePicture( SimpleFile.readFileFromAssets(service, ( (better_resolution)?"26wc_":"slpt_" )+"icons/"+settings.is_white_bg+"phone_battery.png") );
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
                    ResourceManager.getTypeFace(service.getResources(), settings.font)
            );
            // Position based on screen on
            phoneBatteryLayout.alignX = 2;
            phoneBatteryLayout.alignY = 0;
            tmp_left = (int) settings.phone_batteryLeft;
            if(!settings.phone_batteryAlignLeft) {
                // If text is centered, set rectangle
                phoneBatteryLayout.setRect(
                        (int) (2 * tmp_left + 640),
                        (int) (((float)settings.font_ratio/100)*settings.phone_batteryFontSize)
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

        // Draw Phone's alarm
        if(settings.phone_alarm>0){
            // Show or Not icon
            if (settings.phone_alarmIcon) {
                SlptPictureView phone_alarmIcon = new SlptPictureView();
                phone_alarmIcon.setImagePicture( SimpleFile.readFileFromAssets(service, ( (better_resolution)?"26wc_":"slpt_" )+"icons/"+settings.is_white_bg+"phone_alarm.png") );
                phone_alarmIcon.setStart(
                        (int) settings.phone_alarmIconLeft,
                        (int) settings.phone_alarmIconTop
                );
                slpt_objects.add(phone_alarmIcon);
            }

            SlptLinearLayout phoneAlarmLayout = new SlptLinearLayout();
            SlptPictureView phoneAlarmStr = new SlptPictureView();
            phoneAlarmStr.setStringPicture( translate_alarm(this.customData.phoneAlarm) );
            phoneAlarmLayout.add(phoneAlarmStr);
            phoneAlarmLayout.setTextAttrForAll(
                    settings.phone_alarmFontSize,
                    settings.phone_alarmColor,
                    ResourceManager.getTypeFace(service.getResources(), settings.font)
            );
            // Position based on screen on
            phoneAlarmLayout.alignX = 2;
            phoneAlarmLayout.alignY = 0;
            tmp_left = (int) settings.phone_alarmLeft;
            if(!settings.phone_alarmAlignLeft) {
                // If text is centered, set rectangle
                phoneAlarmLayout.setRect(
                        (int) (2 * tmp_left + 640),
                        (int) (((float)settings.font_ratio/100)*settings.phone_alarmFontSize)
                );
                tmp_left = -320;
            }
            phoneAlarmLayout.setStart(
                    (int) tmp_left,
                    (int) (settings.phone_alarmTop-((float)settings.font_ratio/100)*settings.phone_alarmFontSize)
            );
            //Add it to the list
            slpt_objects.add(phoneAlarmLayout);
        }

        // Draw world_time
        if(settings.world_time>0){
            // Show or Not icon
            if (settings.world_timeIcon) {
                SlptPictureView world_timeIcon = new SlptPictureView();
                world_timeIcon.setImagePicture( SimpleFile.readFileFromAssets(service, ( (better_resolution)?"26wc_":"slpt_" )+"icons/"+settings.is_white_bg+"world_time.png") );
                world_timeIcon.setStart(
                        (int) settings.world_timeIconLeft,
                        (int) settings.world_timeIconTop
                );
                slpt_objects.add(world_timeIcon);
            }

            // Time calculations
            //Calendar now = Calendar.getInstance(); // local time
            //Log.d(TAG,"World Time: local hour="+now.get(Calendar.HOUR_OF_DAY));
            Calendar now = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
            //Log.d(TAG,"World Time: GMT   hour="+now.get(Calendar.HOUR_OF_DAY));
            now.add(Calendar.HOUR, (int) settings.world_time_zone);
            String[] digitalNums = {"0", "1", "2", "3", "4", "5", "-", "-", "-", "-"}; // first digits of minutes
            if(settings.world_time_zone%1!=0){//+30 minutes
                now.add(Calendar.MINUTE, (settings.world_time_zone > 0)? 30 : -30 );
                digitalNums = new String[]{"3", "4", "5", "0", "1", "2", "-", "-", "-", "-"};
            }

            SlptLinearLayout world_timeLayout = new SlptLinearLayout();
            // Hours
            int hours = now.get(Calendar.HOUR_OF_DAY);
            SlptPictureView world_timeStr = new SlptPictureView();
            world_timeStr.setStringPicture( Util.formatTime(hours)+":" );
            world_timeLayout.add(world_timeStr);
            // Minutes
            SlptViewComponent SlptMinuteHView = new SlptMinuteHView();
            ((SlptNumView) SlptMinuteHView).setStringPictureArray(digitalNums);
            world_timeLayout.add(SlptMinuteHView); // Minutes first digit
            world_timeLayout.add(new SlptMinuteLView()); // Minutes second digit

            world_timeLayout.setTextAttrForAll(
                    settings.world_timeFontSize,
                    settings.world_timeColor,
                    ResourceManager.getTypeFace(service.getResources(), settings.font)
            );
            // Position based on screen on
            world_timeLayout.alignX = 2;
            world_timeLayout.alignY = 0;
            tmp_left = (int) settings.world_timeLeft;
            if(!settings.world_timeAlignLeft) {
                // If text is centered, set rectangle
                world_timeLayout.setRect(
                        (int) (2 * tmp_left + 640),
                        (int) (((float)settings.font_ratio/100)*settings.world_timeFontSize)
                );
                tmp_left = -320;
            }
            world_timeLayout.setStart(
                    (int) tmp_left,
                    (int) (settings.world_timeTop-((float)settings.font_ratio/100)*settings.world_timeFontSize)
            );
            //Add it to the list
            slpt_objects.add(world_timeLayout);
        }

        // Walked distance
        if(settings.walked_distance>0){
            // Show or Not icon
            if (settings.walked_distanceIcon) {
                SlptPictureView walked_distanceIcon = new SlptPictureView();
                walked_distanceIcon.setImagePicture( SimpleFile.readFileFromAssets(service, ( (better_resolution)?"26wc_":"slpt_" )+"icons/"+settings.is_white_bg+"today_distance.png") );
                walked_distanceIcon.setStart(
                        (int) settings.walked_distanceIconLeft,
                        (int) settings.walked_distanceIconTop
                );
                slpt_objects.add(walked_distanceIcon);
            }

            // Get distance
            String distance;
            try {
                if (this.stepsData != null) {
                    if (this.settings.isMetric) {
                        distance = this.stepsData.getStepsMetric((double) this.settings.step_length);
                    } else {
                        distance = this.stepsData.getStepsImperial((double) this.settings.step_length);
                    }
                } else {
                    distance = "N/A";
                }
            } catch (Exception e2) {
                Log.e(TAG, e2.getMessage());
                distance = "Err";
            }

            SlptLinearLayout walked_distanceLayout = new SlptLinearLayout();
            SlptPictureView walked_distanceStr = new SlptPictureView();
            walked_distanceStr.setStringPicture( distance );
            walked_distanceLayout.add(walked_distanceStr);
            walked_distanceLayout.setTextAttrForAll(
                    settings.walked_distanceFontSize,
                    settings.walked_distanceColor,
                    ResourceManager.getTypeFace(service.getResources(), settings.font)
            );
            // Position based on screen on
            walked_distanceLayout.alignX = 2;
            walked_distanceLayout.alignY = 0;
            tmp_left = (int) settings.walked_distanceLeft;
            if(!settings.walked_distanceAlignLeft) {
                // If text is centered, set rectangle
                walked_distanceLayout.setRect(
                        (int) (2 * tmp_left + 640),
                        (int) (((float)settings.font_ratio/100)*settings.walked_distanceFontSize)
                );
                tmp_left = -320;
            }
            walked_distanceLayout.setStart(
                    (int) tmp_left,
                    (int) (settings.walked_distanceTop-((float)settings.font_ratio/100)*settings.walked_distanceFontSize)
            );
            //Add it to the list
            slpt_objects.add(walked_distanceLayout);
        }

        // Draw phone battery bar
        if(settings.phone_batteryProg>0 && settings.phone_batteryProgType==0){
            // Draw background image
            if(settings.phone_batteryProgBgBool) {
                SlptPictureView ring_background = new SlptPictureView();
                ring_background.setImagePicture(SimpleFile.readFileFromAssets(service, ((settings.isVerge())?"verge_":( (better_resolution)?"":"slpt_" ))+"circles/ring1_bg.png"));
                ring_background.setStart((int) (settings.phone_batteryProgLeft-settings.phone_batteryProgRadius), (int) (settings.phone_batteryProgTop-settings.phone_batteryProgRadius));
                slpt_objects.add(ring_background);
            }
            SlptArcAnglePicView localSlptArcAnglePicView = new SlptArcAnglePicView();
            localSlptArcAnglePicView.setImagePicture(SimpleFile.readFileFromAssets(service, ((settings.isVerge())?"verge_":( (better_resolution)?"":"slpt_" ))+settings.phone_batteryProgSlptImage));
            localSlptArcAnglePicView.setStart((int) (settings.phone_batteryProgLeft-settings.phone_batteryProgRadius), (int) (settings.phone_batteryProgTop-settings.phone_batteryProgRadius));
            localSlptArcAnglePicView.start_angle = (settings.phone_batteryProgClockwise==1)? settings.phone_batteryProgStartAngle : settings.phone_batteryProgEndAngle;
            int temp_battery = (this.customData.phoneBattery.equals("--"))?0:Integer.parseInt(this.customData.phoneBattery);
            localSlptArcAnglePicView.len_angle = (int) (this.angleLength * Math.min(temp_battery/100f,1));
            localSlptArcAnglePicView.full_angle = (settings.phone_batteryProgClockwise==1)? this.angleLength : -this.angleLength;
            localSlptArcAnglePicView.draw_clockwise = settings.phone_batteryProgClockwise;
            slpt_objects.add(localSlptArcAnglePicView);
        }

        return slpt_objects;
    }
}
