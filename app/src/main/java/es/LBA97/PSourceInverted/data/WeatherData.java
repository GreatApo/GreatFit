package es.LBA97.PSourceInverted.data;

import android.app.Service;
import android.provider.Settings;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherData {
    public String tempFlag;
    public String tempString;
    public int weatherType;

    public WeatherData(String tempFlag, String tempString, int weatherType) {
        this.tempFlag = tempFlag;
        this.tempString = tempString;
        this.weatherType = weatherType;
    }

    public String toString() {
        return String.format("weather info [tempFlag:%s, tempString:%s, weatherType:%d", new Object[]{this.tempFlag, this.tempString, Integer.valueOf(this.weatherType)});
    }

    public int getWeather() {
        return this.weatherType;
    }

    public String getTemperature() {
        if(this.tempString.isEmpty() || this.weatherType==22 || this.tempString.equals("0/0")){
            return "n/a";
        }
        return this.tempString;
    }
}
