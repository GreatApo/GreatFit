package com.dinodevs.greatfitwatchface.data;

import android.provider.Settings;

import org.json.JSONException;
import org.json.JSONObject;

public class CustomData {
    public String JSONstr;

    public String airPressure;
    public String altitude;
    public String phoneBattery;
    public String phoneAlarm;

    public CustomData(String parmStr1) {
        this.JSONstr = parmStr1;

        this.airPressure = "--";
        this.phoneBattery = "--";
        this.phoneAlarm = "--";
        this.altitude = "--";
        if(parmStr1!=null && !parmStr1.equals("")){
            try {
                // Extract data from JSON
                JSONObject json_data = new JSONObject(parmStr1);
                if(json_data.has("airPressure")) {
                    this.airPressure = json_data.getString("airPressure");
                    try {// Convert from float to int
                        float f = Float.parseFloat(this.airPressure);
                        this.airPressure = Integer.toString((int) f);
                        // Convert Pressure to Altitude based on data found here (https://www.mide.com/pages/air-pressure-at-altitude-calculator) (2018 08 03)
                        double d = -7.14622816586906E-11 * Math.pow(f, 5) + 2.64853345946368E-07 * Math.pow(f, 4) - 0.000376963054203727 * Math.pow(f, 3) + 0.262320648297135 * Math.pow(f, 2) - 103.105304780369 * f + 24471.4671194641;
                        this.altitude = Integer.toString((int) d);
                    } catch (Exception e) {
                        // Nothing
                    }
                }
                if(json_data.has("phoneBattery")) {
                    this.phoneBattery = json_data.getString("phoneBattery");
                }
                if(json_data.has("phoneAlarm")) {
                    this.phoneAlarm = json_data.getString("phoneAlarm");
                }
            }catch (JSONException e) {
                // Nothing
            }
        }
    }

    public String toString() {
        // Default onDataUpdate value
        return String.format("[Custom data info] Sting 1:%s", this.JSONstr);
    }
}