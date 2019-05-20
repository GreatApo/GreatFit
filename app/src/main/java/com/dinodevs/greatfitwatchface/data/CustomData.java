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
                        float f = Float.parseFloat(this.airPressure);
                        this.airPressure = Integer.toString((int) f);
                        // Convert Pressure to Altitude based on (https://www.weather.gov/media/epz/wxcalc/pressureAltitude.pdf) (2019 05 20)
                        if(f<1200) {
                            // Altitude mode
                            int d = (int) ( ( 1 - Math.pow(f/1013.25, 0.190284) )*145366.45*0.3048 );
                            this.altitude = Integer.toString(d);
                        }else{
                            // Dive depth mode
                            float d = -(f-1011)/100;
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