package com.dinodevs.greatfitwatchface.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.provider.Settings;

import com.dinodevs.greatfitwatchface.R;

import org.json.JSONException;
import org.json.JSONObject;

public class LoadSettings {

    private Context context;
    private SharedPreferences sharedPreferences;

    public LoadSettings(Context context){
        this.context = context;

        sharedPreferences = context.getSharedPreferences(context.getPackageName()+"_settings", Context.MODE_PRIVATE);

        // Default Parameters
        defaultParameters();
        // Parameters from settings (if set)
        settingsParameters();
    }

    // Default Parameters
    private void defaultParameters(){
        // All
            this.font_ratio = context.getResources().getInteger(R.integer.font_ratio);
            this.watchface = context.getResources().getString(R.string.watch_face);
            this.author = context.getResources().getString(R.string.author);
            this.language = sharedPreferences.getInt( "language", 0);
            this.color = sharedPreferences.getInt( "sltp_circle_color", 0);

        // Circles Widget
            this.batteryBool = context.getResources().getBoolean(R.bool.battery);
            this.stepsBool = context.getResources().getBoolean(R.bool.steps);
            this.todayDistanceBool = context.getResources().getBoolean(R.bool.today_distance);
            this.totalDistanceBool = context.getResources().getBoolean(R.bool.total_distance);
            this.batteryCircleBool = context.getResources().getBoolean(R.bool.battery_circle);
            this.stepCircleBool = context.getResources().getBoolean(R.bool.steps_circle);
            this.todayDistanceCircleBool = context.getResources().getBoolean(R.bool.today_distance_circle);
            // Text positions
            this.batteryTextLeft = context.getResources().getDimension(R.dimen.battery_text_left);
            this.batteryTextTop = context.getResources().getDimension(R.dimen.battery_text_top);
            this.stepsTextLeft = context.getResources().getDimension(R.dimen.steps_text_left);
            this.stepsTextTop = context.getResources().getDimension(R.dimen.steps_text_top);
            this.todayDistanceTextLeft = context.getResources().getDimension(R.dimen.today_distance_text_left);
            this.todayDistanceTextTop = context.getResources().getDimension(R.dimen.today_distance_text_top);
            this.totalDistanceTextLeft = context.getResources().getDimension(R.dimen.total_distance_text_left);
            this.totalDistanceTextTop = context.getResources().getDimension(R.dimen.total_distance_text_top);
            // Circles variables
            this.startAngleBattery = context.getResources().getInteger(R.integer.battery_circle_start_angle);
            this.fullAngleBattery = context.getResources().getInteger(R.integer.battery_circle_full_angle);
            this.startAngleSteps = context.getResources().getInteger(R.integer.steps_circle_start_angle);
            this.fullAngleSteps = context.getResources().getInteger(R.integer.steps_circle_full_angle);
            this.startAngleSport = context.getResources().getInteger(R.integer.today_distance_circle_start_angle);
            this.fullAngleSport = context.getResources().getInteger(R.integer.today_distance_circle_full_angle);
            this.thickness = (int) context.getResources().getDimension(R.dimen.circles_thickness);
            this.padding = (int) context.getResources().getDimension(R.dimen.circles_padding);
            this.circlesBackgroundBool = context.getResources().getBoolean(R.bool.circles_background);;
            // Get colors
            this.backgroundColour = context.getResources().getColor(R.color.circles_background);
            this.batteryColour = context.getResources().getColor(R.color.battery_circle_colour);
            this.stepsColour = context.getResources().getColor(R.color.steps_circle_colour);
            this.sportColour = context.getResources().getColor(R.color.today_distance_circle_colour);
            this.sltp_circle_color = this.color;//context.getResources().getInteger(R.integer.sltp_circle_color);
            // Text colors and fonts
            this.batteryFontSize = context.getResources().getDimension(R.dimen.battery_font_size);
            this.batteryTextColor = context.getResources().getColor(R.color.battery_colour);
            this.stepsFontSize = context.getResources().getDimension(R.dimen.steps_font_size);
            this.stepsTextColor = context.getResources().getColor(R.color.steps_colour);
            this.todayDistanceFontSize = context.getResources().getDimension(R.dimen.today_distance_font_size);
            this.todayDistanceTextColor = context.getResources().getColor(R.color.today_distance_colour);
            this.totalDistanceFontSize = context.getResources().getDimension(R.dimen.total_distance_font_size);
            this.totalDistanceTextColor = context.getResources().getColor(R.color.total_distance_colour);
            // Show units boolean
            this.showDistanceUnits = context.getResources().getBoolean(R.bool.distance_units);
            // Align left true or false (false= align center)
            this.batteryAlignLeftBool = context.getResources().getBoolean(R.bool.battery_left_align);
            this.stepsAlignLeftBool = context.getResources().getBoolean(R.bool.steps_left_align);
            this.todayDistanceAlignLeftBool = context.getResources().getBoolean(R.bool.today_distance_left_align);
            this.totalDistanceAlignLeftBool = context.getResources().getBoolean(R.bool.total_distance_left_align);

        // HEART RATE WIDGET
            this.heartRateBool = context.getResources().getBoolean(R.bool.heart_rate);

        // CALORIES WIDGET
            this.caloriesBool = context.getResources().getBoolean(R.bool.calories);

        // FLOOR WIDGET
            this.floorsBool = context.getResources().getBoolean(R.bool.floor);

        // BATTERY IMG WIDGET
            this.batteryImgBool = context.getResources().getBoolean(R.bool.battery_icon);

        // WEATHER WIDGET
            this.temperatureBool = context.getResources().getBoolean(R.bool.temperature);
            //context.getResources().getColor(R.palette.temperature_colour)
            //context.getResources().getDimension(R.dimen.temperature_font_size)
            this.cityBool = context.getResources().getBoolean(R.bool.city);
            //context.getResources().getColor(R.palette.city_colour)
            //context.getResources().getDimension(R.dimen.city_font_size)
            //context.getResources().getBoolean(R.bool.city_left_align)
            this.cityLeft = context.getResources().getDimension(R.dimen.city_left);
            this.cityTop = context.getResources().getDimension(R.dimen.city_top);
            this.humidityBool = context.getResources().getBoolean(R.bool.humidity);
            //context.getResources().getColor(R.palette.humidity_colour)
            //context.getResources().getDimension(R.dimen.humidity_font_size)
            //context.getResources().getBoolean(R.bool.humidity_left_align)
            this.humidityLeft = context.getResources().getDimension(R.dimen.humidity_left);
            this.humidityTop = context.getResources().getDimension(R.dimen.humidity_top);
            this.uvBool = context.getResources().getBoolean(R.bool.uv);
            //context.getResources().getColor(R.palette.uv_colour)
            //context.getResources().getDimension(R.dimen.uv_font_size)
            //context.getResources().getBoolean(R.bool.uv_left_align)
            this.uvLeft = context.getResources().getDimension(R.dimen.uv_left);
            this.uvTop = context.getResources().getDimension(R.dimen.uv_top);
            this.windDirectionBool = context.getResources().getBoolean(R.bool.wind_direction);
            //context.getResources().getColor(R.palette.wind_direction_colour)
            //context.getResources().getDimension(R.dimen.wind_direction_font_size)
            //context.getResources().getBoolean(R.bool.wind_direction_left_align)
            this.windDirectionLeft = context.getResources().getDimension(R.dimen.wind_direction_left);
            this.windDirectionTop = context.getResources().getDimension(R.dimen.wind_direction_top);
            this.windDirectionAsArrowBool = context.getResources().getBoolean(R.bool.wind_direction_as_arrows);
            this.windStrengthBool = context.getResources().getBoolean(R.bool.wind_strength);
            //context.getResources().getColor(R.palette.wind_strength_colour)
            //context.getResources().getDimension(R.dimen.wind_strength_font_size)
            //context.getResources().getBoolean(R.bool.wind_strength_left_align)
            this.windStrengthLeft = context.getResources().getDimension(R.dimen.wind_strength_left);
            this.windStrengthTop = context.getResources().getDimension(R.dimen.wind_strength_top);
            this.weatherBool = context.getResources().getBoolean(R.bool.weather_image);
            this.showTemperatureUnits = context.getResources().getBoolean(R.bool.temperature_units);

        // GREAT WIDGET
            this.airPressureBool = context.getResources().getBoolean(R.bool.air_pressure);
            this.altitudeBool = context.getResources().getBoolean(R.bool.altitude);
            this.phoneBatteryBool = context.getResources().getBoolean(R.bool.phoneBattery);
            this.ampmLeft = context.getResources().getDimension(R.dimen.ampm_left);
            this.ampmTop = context.getResources().getDimension(R.dimen.ampm_top);
            this.alarmLeft = context.getResources().getDimension(R.dimen.alarm_left);
            this.alarmTop = context.getResources().getDimension(R.dimen.alarm_top);
            this.xdripLeft = context.getResources().getDimension(R.dimen.xdrip_left);
            this.xdripTop = context.getResources().getDimension(R.dimen.xdrip_top);
            this.airPressureLeft = context.getResources().getDimension(R.dimen.air_pressure_left);
            this.airPressureTop = context.getResources().getDimension(R.dimen.air_pressure_top);
            this.altitudeLeft = context.getResources().getDimension(R.dimen.altitude_left);
            this.altitudeTop = context.getResources().getDimension(R.dimen.altitude_top);
            this.phoneBatteryLeft = context.getResources().getDimension(R.dimen.phoneBattery_left);
            this.phoneBatteryTop = context.getResources().getDimension(R.dimen.phoneBattery_top);
            this.ampmBool = context.getResources().getBoolean(R.bool.ampm);
            this.ampmAlignLeftBool = context.getResources().getBoolean(R.bool.ampm_left_align);
            this.alarmBool = context.getResources().getBoolean(R.bool.alarm);
            this.alarmAlignLeftBool = context.getResources().getBoolean(R.bool.alarm_left_align);
            this.xdripBool = context.getResources().getBoolean(R.bool.xdrip);
            this.xdripAlignLeftBool = context.getResources().getBoolean(R.bool.xdrip_left_align);
            this.showAirPressureUnits = context.getResources().getBoolean(R.bool.air_pressure_units);
            this.airPressureAlignLeftBool = context.getResources().getBoolean(R.bool.air_pressure_left_align);
            this.showAltitudeUnits = context.getResources().getBoolean(R.bool.altitude_units);
            this.altitudeAlignLeftBool = context.getResources().getBoolean(R.bool.altitude_left_align);
            this.phoneBatteryAlignLeftBool = context.getResources().getBoolean(R.bool.phoneBattery_left_align);
    }

    // Overwrite parameters with settings parameters
    private void settingsParameters(){
        String settings = Settings.System.getString(context.getContentResolver(), this.watchface+"Settings");
        if (settings == null || settings.equals("")) {settings = "{}";}

        // Extract data from JSON
        JSONObject json_settings;
        try {
            json_settings = new JSONObject(settings);

            // Circles Widget
            if (json_settings.has("battery")) {this.batteryBool = json_settings.getBoolean("battery");}
            if (json_settings.has("steps")) {this.stepsBool = json_settings.getBoolean("steps");}
            if (json_settings.has("todayDistance")) {this.todayDistanceBool = json_settings.getBoolean("todayDistance");}
            if (json_settings.has("totalDistance")) {this.totalDistanceBool = json_settings.getBoolean("totalDistance");}
            if (json_settings.has("batteryCircle")) {this.batteryCircleBool = json_settings.getBoolean("batteryCircle");}
            if (json_settings.has("stepsCircle")) {this.stepCircleBool = json_settings.getBoolean("stepsCircle");}
            if (json_settings.has("todayDistanceCircle")) {this.todayDistanceCircleBool = json_settings.getBoolean("todayDistanceCircle");}
            if(isCircles()){
                if(batteryBool){


                }

            }

        } catch (JSONException e) {
            //Settings.System.putString(getContentResolver(), this.watchface+"Settings", "{}");//reset wrong settings data
        }
    }

    // All
    public int font_ratio;
    public String watchface;
    public String author;
    public int language;
    public int color;
    public Integer[] colorCodes = {
        Color.parseColor("#ff0000"),
        Color.parseColor("#00ffff"),
        Color.parseColor("#00ff00"),
        Color.parseColor("#ff00ff"),
        Color.parseColor("#ffffff"),
        Color.parseColor("#ffff00"),
        Color.parseColor("#111111")
    };

    // CIRCLE WIDGET
    // Get widget text show/hide booleans
    public boolean batteryBool;
    public boolean stepsBool;
    public boolean todayDistanceBool;
    public boolean totalDistanceBool;
    // Get circles show/hide booleans
    public boolean batteryCircleBool;
    public boolean stepCircleBool;
    public boolean todayDistanceCircleBool;
    // Angles
    public int startAngleBattery;
    public int fullAngleBattery;
    public int startAngleSteps;
    public int fullAngleSteps;
    public int startAngleSport;
    public int fullAngleSport;
    public boolean circlesBackgroundBool;
    public int thickness;
    public int padding;
    // Get positions
    public float batteryTextLeft;
    public float batteryTextTop;
    public float stepsTextLeft;
    public float stepsTextTop;
    public float todayDistanceTextLeft;
    public float todayDistanceTextTop;
    public float totalDistanceTextLeft;
    public float totalDistanceTextTop;
    // Colors & font size
    public int backgroundColour;
    public int batteryColour;
    public int stepsColour;
    public int sportColour;
    public float batteryFontSize;
    public int batteryTextColor;
    public float stepsFontSize;
    public int stepsTextColor;
    public float todayDistanceFontSize;
    public int todayDistanceTextColor;
    public float totalDistanceFontSize;
    public int totalDistanceTextColor;
    public int sltp_circle_color;
    // Align
    public boolean batteryAlignLeftBool;
    public boolean stepsAlignLeftBool;
    public boolean todayDistanceAlignLeftBool;
    public boolean totalDistanceAlignLeftBool;
    public boolean showDistanceUnits;

    public boolean isCircles(){
        return this.batteryBool || this.stepsBool || this.todayDistanceBool || this.totalDistanceBool || this.batteryCircleBool || this.stepCircleBool || this.todayDistanceCircleBool;
    }

    // HEART RATE WIDGET
    public boolean heartRateBool;

    public boolean isHeartRate(){
        return this.heartRateBool;
    }

    // CALORIES WIDGET
    public boolean caloriesBool;
    public boolean isCalories(){
        return this.caloriesBool;
    }

    // FLOOR WIDGET
    public boolean floorsBool;
    public boolean isFloor(){
        return this.floorsBool;
    }

    // BATTERY IMG WIDGET
    public boolean batteryImgBool;
    public boolean isBattery(){
        return this.batteryImgBool;
    }

    // WEATHER WIDGET
    public boolean temperatureBool;
    public boolean cityBool;
    public boolean humidityBool;
    public boolean uvBool;
    public boolean windDirectionBool;
    public boolean windStrengthBool;
    public boolean weatherBool;
    public float windStrengthLeft;
    public float windStrengthTop;
    public float windDirectionLeft;
    public float windDirectionTop;
    public boolean windDirectionAsArrowBool;
    public float uvLeft;
    public float uvTop;
    public float humidityLeft;
    public float humidityTop;
    public float cityLeft;
    public float cityTop;
    public boolean showTemperatureUnits;

    public boolean isWeather(){
        return this.temperatureBool || this.cityBool || this.humidityBool || this.uvBool || this.windDirectionBool || this.windStrengthBool || this.weatherBool;
    }

    // GREAT WIDGET
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

    public boolean isGreat(){
        return this.ampmBool || alarmBool || xdripBool || airPressureBool || altitudeBool || phoneBatteryBool;
    }
}