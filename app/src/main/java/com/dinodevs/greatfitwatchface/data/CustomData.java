package com.dinodevs.greatfitwatchface.data;

import android.provider.Settings;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class CustomData {
    public String JSONstr;

    public String airPressure;
    public String altitude;
    public String phoneBattery;
    public String phoneAlarm;
    public String notifications;

    public CustomData(String parmStr1) {
        this.JSONstr = parmStr1;

        this.airPressure = "--";
        this.phoneBattery = "--";
        this.phoneAlarm = "--";
        this.altitude = "--";
        this.notifications = "--";
        if(parmStr1!=null && !parmStr1.equals("")){
            try {
                // Extract data from JSON
                JSONObject json_data = new JSONObject(parmStr1);
                if(json_data.has("airPressure")) {
                    this.airPressure = json_data.getString("airPressure");
                    try {// Convert from float to int
                        float p = Float.parseFloat(this.airPressure);
                        this.airPressure = Integer.toString((int) p);
                        double p0 = 1013.25; // pressure at sea level in hPa
                        if(p<1200) {
                            // Altitude conversion using hypsometric formula
                            int T;
                            if(json_data.has("temperature"))
                                T = Integer.parseInt(json_data.getString("temperature")); //temperature in oC
                            else
                                T = 15; //temperature in oC
                            int h = (int) ( ( 1 - Math.pow(p/p0, 1/5.25579) )*(T+273.15)/0.0065 );
                            this.altitude = Integer.toString(h);
                        }else{
                            // Dive depth mode
                            float d = (float) -(p-p0)/100;
                            this.altitude = String.format("%.2f", d); //Float.toString(d);
                        }
                    } catch (Exception e) {
                        Log.d("DinoDevs-GreatFit", "Error converting pressure float to int ("+this.airPressure+")");
                    }
                }
                if(json_data.has("phoneBattery")) {
                    this.phoneBattery = json_data.getString("phoneBattery");
                }
                if(json_data.has("phoneAlarm")) {
                    this.phoneAlarm = json_data.getString("phoneAlarm");
                }
                if(json_data.has("notifications")) {
                    this.notifications = json_data.getString("notifications");
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