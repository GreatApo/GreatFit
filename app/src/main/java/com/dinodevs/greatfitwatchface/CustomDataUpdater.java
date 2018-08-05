package com.dinodevs.greatfitwatchface;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;


public class CustomDataUpdater extends Service {
    // Custom data updater
    private Handler mHandler = new Handler();
    private Integer custom_refresh_rate;
    private SensorManager mManager;
    private Sensor mPressureSensor;
    private SensorEventListener mListener;

    private Boolean airPressureBool;
    private Boolean update = false;

    private String airPressure="--";
    //private String phoneBattery="--";
    //private String phoneAlarm="--";

    public void onCreate() {

    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("DinoDevs-GreatFit", "CustomDataUpdater service started");

        // Get AirPressure in hPa
        airPressureBool = (getResources().getBoolean(R.bool.air_pressure) || getResources().getBoolean(R.bool.altitude));
        custom_refresh_rate = getResources().getInteger(R.integer.custom_refresh_rate)*60*1000;
        // WearCompass.jar!\com\huami\watch\compass\logic\GeographicManager.class
        this.mManager = (SensorManager) getApplicationContext().getSystemService(Context.SENSOR_SERVICE);
        this.mPressureSensor = this.mManager.getDefaultSensor(6);
        this.mListener = new SensorEventListener() {
            public void onAccuracyChanged(Sensor parameter1, int parameter2) {}

            public void onSensorChanged(SensorEvent parameters) {
                CustomDataUpdater.this.mManager.unregisterListener(this);
                float[] pressure = parameters.values;
                if (pressure != null && pressure.length > 0) {
                    float value = pressure[0];
                    if (value > 0 && !(Float.toString(value)).equals(CustomDataUpdater.this.airPressure)) {
                        CustomDataUpdater.this.airPressure = Float.toString(value);
                        save();
                    }
                }
            }
        };

        // Run the handler
        customRefresher.run();

        return super.onStartCommand(intent, flags, startId);
    }

    // Data updater handler
    Runnable customRefresher = new Runnable(){
        @Override
        public void run() {
            if(airPressureBool) {
                updateAirPressure();
            }
            mHandler.postDelayed(customRefresher, custom_refresh_rate);
        }
    };

    public void updateAirPressure(){
        //Log.w("DinoDevs-GreatFit", "Update air pressure started...");
        mManager.registerListener(this.mListener, this.mPressureSensor, 1*60*1000);
    }

    public void save(){
        String data = Settings.System.getString(getContentResolver(), "CustomWatchfaceData");

        if(data==null || data.equals("")){
            //Settings.System.putString(getContentResolver(), "CustomWatchfaceData","{}");//default
            data="{}";
        }

        try {
            // Extract data from JSON
            JSONObject json_data = new JSONObject(data);
            json_data.put("airPressure",this.airPressure);
            //json_data.put("phoneBattery",this.phoneBattery);
            //json_data.put("phoneAlarm",this.phoneAlarm);

            Settings.System.putString(getContentResolver(), "CustomWatchfaceData",json_data.toString());
        }catch (JSONException e) {
            Settings.System.putString(getContentResolver(), "CustomWatchfaceData","{\"airPressure\":\""+this.airPressure+"\"}");//,\"phoneBattery\":\""+this.phoneBattery+"\",\"phoneAlarm\":\""+this.phoneAlarm+"\"}");//default
        }
    }
}
