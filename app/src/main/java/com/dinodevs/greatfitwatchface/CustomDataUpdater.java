package com.dinodevs.greatfitwatchface;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import com.dinodevs.greatfitwatchface.settings.LoadSettings;

import org.json.JSONException;
import org.json.JSONObject;


public class CustomDataUpdater extends Service {
    // Custom data updater
    private Handler mHandler = new Handler();
    private SensorManager mManager;
    private Sensor mPressureSensor;
    private SensorEventListener mListener;

    private Boolean airPressureBool;

    private LoadSettings settings;

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

        // Load settings
        settings = new LoadSettings(getApplicationContext());

        // Get AirPressure in hPa
        airPressureBool = (settings.air_pressure>0 || settings.altitude>0);

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
                    Log.d("DinoDevs-GreatFit", "Pressure is "+value+" hPa");
                    if (value > 0 && !(Float.toString(value)).equals(CustomDataUpdater.this.airPressure)) {
                    //int value = (int) pressure[0];
                    //if (value > 0 && !(Integer.toString(value)).equals(CustomDataUpdater.this.airPressure)) {
                        CustomDataUpdater.this.airPressure = Float.toString(value);
                        save();
                    }
                }
            }
        };

        // Run the handler
        if(airPressureBool) {
            customRefresher.run();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    // Data updater handler
    Runnable customRefresher = new Runnable(){
        @Override
        public void run() {
            if(android.provider.Settings.System.getString(getContentResolver(), "slptName").equals("GreatFitSlpt")) {
                Log.d("DinoDevs-GreatFit", "Sensor custom refresh in "+settings.custom_refresh_rate+" sec");
                if (airPressureBool) {
                    updateAirPressure();
                }
            }
            mHandler.postDelayed(customRefresher, (settings.custom_refresh_rate>0)? settings.custom_refresh_rate*1000 : 60*60*1000 );
        }
    };

    public void updateAirPressure(){
        //Log.w("DinoDevs-GreatFit", "Update air pressure started...");
        mManager.registerListener(this.mListener, this.mPressureSensor, 60*1000);
    }

    public void save(){
        Log.d("DinoDevs-GreatFit", "Saving custom data");
        String data = Settings.System.getString(getContentResolver(), "CustomWatchfaceData");

        if(data==null || data.equals("")){
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
