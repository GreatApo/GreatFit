package com.dinodevs.greatfitwatchface.data;

import android.app.Service;
import android.provider.Settings;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherData {
    public String tempFlag;
    public String tempString;
    public int weatherType;
    public String city = "n/a";
    public String humidity = "n/a";
    public String uv = "n/a";
    public String windDirection = "n/a";
    public String windStrength = "n/a";
    public String windArrow = "•";

    public WeatherData(String tempFlag, String tempString, int weatherType) {
        this.tempFlag = tempFlag;
        this.tempString = tempString;
        this.weatherType = weatherType;
    }

    public WeatherData(String tempFlag, String tempString, int weatherType, String tempCity, String tempHumidity, String tempUV, String tempWindDirection, String tempWindStrength) {
        this.tempFlag = tempFlag;
        this.tempString = tempString;
        this.weatherType = weatherType;
        this.city = tempCity;
        this.humidity = tempHumidity;
        this.uv = tempUV;
        this.windDirection = tempWindDirection;
        this.windStrength = tempWindStrength;
        this.windArrow = getWindDirectionArrow();
    }

    public String toString() {
        // Default onDataUpdate value
        return String.format("weather info [tempFlag:%s, tempString:%s, weatherType:%d", new Object[]{this.tempFlag, this.tempString, Integer.valueOf(this.weatherType)});
    }

    public int getWindDirectionType() { // So to be used in images array
        // NW N NE     1 2 3
        // W n/a E  =  4 0 5
        // SW S SE     6 7 8

        int windDirectionType = 0;
        if(this.windDirection.equals("NW")){
            windDirectionType = 1;
        }else if(this.windDirection.equals("N")){
            windDirectionType = 2;
        }else if(this.windDirection.equals("NE")){
            windDirectionType = 3;
        }else if(this.windDirection.equals("W")){
            windDirectionType = 4;
        }else if(this.windDirection.equals("E")){
            windDirectionType = 5;
        }else if(this.windDirection.equals("SW")){
            windDirectionType = 6;
        }else if(this.windDirection.equals("S")){
            windDirectionType = 7;
        }else if(this.windDirection.equals("SE")){
            windDirectionType = 8;
        }

        return windDirectionType;
    }

    public String getWindDirectionArrow() { // Arrows
        // NW N NE     ↖ ↑ ↗
        // W n/a E  =  ← • →
        // SW S SE     ↙ ↓ ↘

        String windDirectionArrow = "•";
        if(this.windDirection.equals("NW")){
            windDirectionArrow = "↖";
        }else if(this.windDirection.equals("N")){
            windDirectionArrow = "↑";
        }else if(this.windDirection.equals("NE")){
            windDirectionArrow = "↗";
        }else if(this.windDirection.equals("W")){
            windDirectionArrow = "←";
        }else if(this.windDirection.equals("E")){
            windDirectionArrow = "→";
        }else if(this.windDirection.equals("SW")){
            windDirectionArrow = "↙";
        }else if(this.windDirection.equals("S")){
            windDirectionArrow = "↓";
        }else if(this.windDirection.equals("SE")){
            windDirectionArrow = "↘";
        }

        return windDirectionArrow;
    }

    public String getUnits() {
        return (this.tempFlag.equals("1") || this.tempFlag.equals("C"))?"ºC":"ºF";
    }

    public String getTemperature() {
        if(this.tempString.isEmpty() || this.weatherType==22 || this.tempString.equals("0/0")){
            return "n/a";
        }
        return this.tempString;
    }
}
