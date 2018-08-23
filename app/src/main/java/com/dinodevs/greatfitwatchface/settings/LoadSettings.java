package com.dinodevs.greatfitwatchface.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.StyleableRes;
import android.util.Log;
import android.widget.ImageView;

import com.dinodevs.greatfitwatchface.BuildConfig;
import com.dinodevs.greatfitwatchface.R;
import com.huami.watch.watchface.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoadSettings {

    private Context context;
    private SharedPreferences sharedPreferences;
    private int versionCode;
    public boolean restartwatchface = false;
    private Resources res;

    public LoadSettings(Context context){
        this.context = context;
        this.res = context.getResources();
        this.sharedPreferences = context.getSharedPreferences(context.getPackageName()+"_settings", Context.MODE_PRIVATE);

        // Default Parameters
        defaultParameters();
    }

    // GENERAL
    public int font_ratio;
    public boolean better_resolution_when_raising_hand;
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
            Color.parseColor("#111111"),
            Color.parseColor("#0000ff")
    };
    public boolean flashing_indicator;
    public boolean month_as_text;
    public boolean three_letters_month_if_text;
    public boolean three_letters_day_if_text;
    public boolean no_0_on_hour_first_digit;
    public Paint mGPaint;
    public List widgets_list;
    public List circle_bars_list;

    // Hours
    public boolean hoursBool;
    public float hoursFontSize;
    public float hoursLeft;
    public float hoursTop;
    public int hoursColor;
    public boolean hoursAlignLeft;
    // Indicator
    public float indicatorFontSize;
    public float indicatorLeft;
    public float indicatorTop;
    public int indicatorColor;
    public boolean indicatorAlignLeft;
    public boolean indicatorBool;
    // Minutes
    public float minutesFontSize;
    public float minutesLeft;
    public float minutesTop;
    public int minutesColor;
    public boolean minutesAlignLeft;
    public boolean minutesBool;
    // Seconds
    public float secondsFontSize;
    public float secondsLeft;
    public float secondsTop;
    public int secondsColor;
    public boolean secondsAlignLeft;
    public boolean secondsBool;
    // am_pm
    public float am_pmFontSize;
    public float am_pmLeft;
    public float am_pmTop;
    public int am_pmColor;
    public boolean am_pmAlignLeft;
    public boolean am_pmBool;
    // weekday
    public float weekdayFontSize;
    public float weekdayLeft;
    public float weekdayTop;
    public int weekdayColor;
    public boolean weekdayAlignLeft;
    public boolean weekdayBool;
    // month
    public float monthFontSize;
    public float monthLeft;
    public float monthTop;
    public int monthColor;
    public boolean monthAlignLeft;
    public boolean monthBool;
    // day
    public float dayFontSize;
    public float dayLeft;
    public float dayTop;
    public int dayColor;
    public boolean dayAlignLeft;
    public boolean dayBool;
    // year
    public float yearFontSize;
    public float yearLeft;
    public float yearTop;
    public int yearColor;
    public boolean yearAlignLeft;
    public boolean yearBool;
    //WIDGETS
    // date
    public int date;
    public float dateFontSize;
    public float dateLeft;
    public float dateTop;
    public int dateColor;
    public boolean dateAlignLeft;
    public boolean dateUnits;
    public boolean dateIcon;
    public float dateIconLeft;
    public float dateIconTop;
    // calories
    public int calories;
    public float caloriesFontSize;
    public float caloriesLeft;
    public float caloriesTop;
    public int caloriesColor;
    public boolean caloriesAlignLeft;
    public boolean caloriesUnits;
    public boolean caloriesIcon;
    public float caloriesIconLeft;
    public float caloriesIconTop;
    // Steps
    public int steps;
    public float stepsFontSize;
    public float stepsLeft;
    public float stepsTop;
    public int stepsColor;
    public boolean stepsAlignLeft;
    public boolean stepsUnits;
    public boolean stepsIcon;
    public float stepsIconLeft;
    public float stepsIconTop;
    // heart_rate
    public int heart_rate;
    public float heart_rateFontSize;
    public float heart_rateLeft;
    public float heart_rateTop;
    public int heart_rateColor;
    public boolean heart_rateAlignLeft;
    public boolean heart_rateUnits;
    public boolean heart_rateIcon;
    public float heart_rateIconLeft;
    public float heart_rateIconTop;
    // total_distance
    public int total_distance;
    public float total_distanceFontSize;
    public float total_distanceLeft;
    public float total_distanceTop;
    public int total_distanceColor;
    public boolean total_distanceAlignLeft;
    public boolean total_distanceUnits;
    public boolean total_distanceIcon;
    public float total_distanceIconLeft;
    public float total_distanceIconTop;
    // today_distance
    public int today_distance;
    public float today_distanceFontSize;
    public float today_distanceLeft;
    public float today_distanceTop;
    public int today_distanceColor;
    public boolean today_distanceAlignLeft;
    public boolean today_distanceUnits;
    public boolean today_distanceIcon;
    public float today_distanceIconLeft;
    public float today_distanceIconTop;
    // floors
    public int floors;
    public float floorsFontSize;
    public float floorsLeft;
    public float floorsTop;
    public int floorsColor;
    public boolean floorsAlignLeft;
    public boolean floorsUnits;
    public boolean floorsIcon;
    public float floorsIconLeft;
    public float floorsIconTop;
    // battery_percent
    public int battery_percent;
    public float battery_percentFontSize;
    public float battery_percentLeft;
    public float battery_percentTop;
    public int battery_percentColor;
    public boolean battery_percentAlignLeft;
    public boolean battery_percentUnits;
    public boolean battery_percentIcon;
    public float battery_percentIconLeft;
    public float battery_percentIconTop;
    // temperature
    public int temperature;
    public float temperatureFontSize;
    public float temperatureLeft;
    public float temperatureTop;
    public int temperatureColor;
    public boolean temperatureAlignLeft;
    public boolean temperatureUnits;
    public boolean temperatureIcon;
    public float temperatureIconLeft;
    public float temperatureIconTop;
    // city
    public int city;
    public float cityFontSize;
    public float cityLeft;
    public float cityTop;
    public int cityColor;
    public boolean cityAlignLeft;
    public boolean cityUnits;
    public boolean cityIcon;
    public float cityIconLeft;
    public float cityIconTop;
    // watch_alarm
    public int watch_alarm;
    public float watch_alarmFontSize;
    public float watch_alarmLeft;
    public float watch_alarmTop;
    public int watch_alarmColor;
    public boolean watch_alarmAlignLeft;
    public boolean watch_alarmUnits;
    public boolean watch_alarmIcon;
    public float watch_alarmIconLeft;
    public float watch_alarmIconTop;
    // humidity
    public int humidity;
    public float humidityFontSize;
    public float humidityLeft;
    public float humidityTop;
    public int humidityColor;
    public boolean humidityAlignLeft;
    public boolean humidityUnits;
    public boolean humidityIcon;
    public float humidityIconLeft;
    public float humidityIconTop;
    // uv
    public int uv;
    public float uvFontSize;
    public float uvLeft;
    public float uvTop;
    public int uvColor;
    public boolean uvAlignLeft;
    public boolean uvUnits;
    public boolean uvIcon;
    public float uvIconLeft;
    public float uvIconTop;
    // wind_direction
    public int wind_direction;
    public float wind_directionFontSize;
    public float wind_directionLeft;
    public float wind_directionTop;
    public int wind_directionColor;
    public boolean wind_directionAlignLeft;
    public boolean wind_directionUnits;
    public boolean wind_directionIcon;
    public float wind_directionIconLeft;
    public float wind_directionIconTop;
    // wind_strength
    public int wind_strength;
    public float wind_strengthFontSize;
    public float wind_strengthLeft;
    public float wind_strengthTop;
    public int wind_strengthColor;
    public boolean wind_strengthAlignLeft;
    public boolean wind_strengthUnits;
    public boolean wind_strengthIcon;
    public float wind_strengthIconLeft;
    public float wind_strengthIconTop;
    // air_pressure
    public int air_pressure;
    public float air_pressureFontSize;
    public float air_pressureLeft;
    public float air_pressureTop;
    public int air_pressureColor;
    public boolean air_pressureAlignLeft;
    public boolean air_pressureUnits;
    public boolean air_pressureIcon;
    public float air_pressureIconLeft;
    public float air_pressureIconTop;
    // altitude
    public int altitude;
    public float altitudeFontSize;
    public float altitudeLeft;
    public float altitudeTop;
    public int altitudeColor;
    public boolean altitudeAlignLeft;
    public boolean altitudeUnits;
    public boolean altitudeIcon;
    public float altitudeIconLeft;
    public float altitudeIconTop;
    // phone_battery
    public int phone_battery;
    public float phone_batteryFontSize;
    public float phone_batteryLeft;
    public float phone_batteryTop;
    public int phone_batteryColor;
    public boolean phone_batteryAlignLeft;
    public boolean phone_batteryUnits;
    public boolean phone_batteryIcon;
    public float phone_batteryIconLeft;
    public float phone_batteryIconTop;
    // phone_alarm
    public int phone_alarm;
    public float phone_alarmFontSize;
    public float phone_alarmLeft;
    public float phone_alarmTop;
    public int phone_alarmColor;
    public boolean phone_alarmAlignLeft;
    public boolean phone_alarmUnits;
    public boolean phone_alarmIcon;
    public float phone_alarmIconLeft;
    public float phone_alarmIconTop;
    // xdrip
    public int xdrip;
    public float xdripFontSize;
    public float xdripLeft;
    public float xdripTop;
    public int xdripColor;
    public boolean xdripAlignLeft;
    public boolean xdripUnits;
    public boolean xdripIcon;
    public float xdripIconLeft;
    public float xdripIconTop;

    // PROGRESS ELEMENTS
    // Steps
    public int stepsProg;
    public float stepsProgLeft;
    public float stepsProgTop;
    public int stepsProgType;
    public float stepsProgRadius;
    public float stepsProgThickness;
    public int stepsProgStartAngle;
    public int stepsProgEndAngle;
    public int stepsProgClockwise;
    public int stepsProgColorIndex;
    public boolean stepsProgBgBool;
    public int stepsProgBgColor;
    public String stepsProgBgImage;
    public String stepsProgSlptImage;
    // today distance
    public int today_distanceProg;
    public float today_distanceProgLeft;
    public float today_distanceProgTop;
    public int today_distanceProgType;
    public float today_distanceProgRadius;
    public float today_distanceProgThickness;
    public int today_distanceProgStartAngle;
    public int today_distanceProgEndAngle;
    public int today_distanceProgClockwise;
    public int today_distanceProgColorIndex;
    public boolean today_distanceProgBgBool;
    public int today_distanceProgBgColor;
    public String today_distanceProgBgImage;
    public String today_distanceProgSlptImage;
    // Battery
    public int batteryProg;
    public float batteryProgLeft;
    public float batteryProgTop;
    public int batteryProgType;
    public float batteryProgRadius;
    public float batteryProgThickness;
    public int batteryProgStartAngle;
    public int batteryProgEndAngle;
    public int batteryProgClockwise;
    public int batteryProgColorIndex;
    public boolean batteryProgBgBool;
    public int batteryProgBgColor;
    public String batteryProgBgImage;
    public String batteryProgSlptImage;
    // Weather
    public int weather_imgProg;
    public float weather_imgProgLeft;
    public float weather_imgProgTop;
    //public int weather_imgProgType;

    // Default Parameters
    private void defaultParameters(){
        // All
            this.font_ratio = sharedPreferences.getInt("font_ratio", res.getInteger(R.integer.font_ratio));
            this.watchface = context.getResources().getString(R.string.watch_face);
            this.author = context.getResources().getString(R.string.author);
            this.language = sharedPreferences.getInt( "language", 0);
            this.color = sharedPreferences.getInt( "color", -1);
            this.better_resolution_when_raising_hand = sharedPreferences.getBoolean( "better_resolution_when_raising_hand", context.getResources().getBoolean(R.bool.better_resolution_when_raising_hand));
            this.flashing_indicator = sharedPreferences.getBoolean( "flashing_indicator", context.getResources().getBoolean(R.bool.flashing_indicator));
            this.month_as_text = sharedPreferences.getBoolean( "month_as_text", context.getResources().getBoolean(R.bool.month_as_text));
            this.three_letters_month_if_text = sharedPreferences.getBoolean( "three_letters_month_if_text", context.getResources().getBoolean(R.bool.three_letters_month_if_text));
            this.three_letters_day_if_text = sharedPreferences.getBoolean( "three_letters_day_if_text", context.getResources().getBoolean(R.bool.three_letters_day_if_text));
            this.no_0_on_hour_first_digit = sharedPreferences.getBoolean( "no_0_on_hour_first_digit", context.getResources().getBoolean(R.bool.no_0_on_hour_first_digit));
            //icon paint
            this.mGPaint = new Paint();
            mGPaint.setAntiAlias(false);
            mGPaint.setFilterBitmap(false);
            List theme_elements = Arrays.asList(context.getResources().getStringArray(R.array.theme_elements));
            String[] color_codes = context.getResources().getStringArray(R.array.color_codes);

            Log.w("DinoDevs-GreatFit", "Language: "+this.language );

        @StyleableRes int i = 0;
        // Hours
            this.hoursBool = sharedPreferences.getBoolean("hoursBool", true);
            if(this.hoursBool) {
                TypedArray hours = res.obtainTypedArray(R.array.hours);
                this.hoursFontSize = sharedPreferences.getFloat("hoursFontSize", hours.getDimension(i++, 0));
                this.hoursLeft = sharedPreferences.getFloat("hoursLeft", hours.getDimension(i++, 0));
                this.hoursTop = sharedPreferences.getFloat("hoursTop", hours.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("hours")>-1){
                    this.hoursColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.hoursColor = sharedPreferences.getInt("hoursColor", hours.getColor(i++, 0));
                }

                this.hoursAlignLeft = sharedPreferences.getBoolean("hoursAlignLeft", hours.getBoolean(i, true));
                hours.recycle();
            }
        // Indicator
            this.indicatorBool = sharedPreferences.getBoolean("indicatorBool", res.getIdentifier("indicator", "array", context.getPackageName())!=0);
            if(this.indicatorBool) {
                TypedArray indicator = res.obtainTypedArray(res.getIdentifier("weekday", "array", context.getPackageName()));
                i = 0;
                this.indicatorFontSize = sharedPreferences.getFloat("indicatorFontSize", indicator.getDimension(i++, 0));
                this.indicatorLeft = sharedPreferences.getFloat("indicatorLeft", indicator.getDimension(i++, 0));
                this.indicatorTop = sharedPreferences.getFloat("indicatorTop", indicator.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("indicator")>-1){
                    this.indicatorColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.indicatorColor = sharedPreferences.getInt("indicatorColor", indicator.getColor(i++, 0));
                }
                this.indicatorAlignLeft = sharedPreferences.getBoolean("indicatorAlignLeft", indicator.getBoolean(i, true));
                indicator.recycle();
            }
        // Minutes
            this.minutesBool = sharedPreferences.getBoolean("minutesBool", true);
            if(this.minutesBool) {
                TypedArray minutes = res.obtainTypedArray(R.array.minutes);
                i = 0;
                this.minutesFontSize = sharedPreferences.getFloat("minutesFontSize", minutes.getDimension(i++, 0));
                this.minutesLeft = sharedPreferences.getFloat("minutesLeft", minutes.getDimension(i++, 0));
                this.minutesTop = sharedPreferences.getFloat("minutesTop", minutes.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("minutes")>-1) {
                    this.minutesColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.minutesColor = sharedPreferences.getInt("minutesColor", minutes.getColor(i++, 0));
                }
                this.minutesAlignLeft = sharedPreferences.getBoolean("minutesAlignLeft", minutes.getBoolean(i, true));
                minutes.recycle();
            }
        // Seconds
            this.secondsBool = sharedPreferences.getBoolean("secondsBool", res.getIdentifier("seconds", "array", context.getPackageName())!=0) && Util.needSlptRefreshSecond(context);
            if(this.secondsBool) {
                TypedArray seconds = res.obtainTypedArray(res.getIdentifier("seconds", "array", context.getPackageName()));
                i = 0;
                this.secondsFontSize = sharedPreferences.getFloat("secondsFontSize", seconds.getDimension(i++, 0));
                this.secondsLeft = sharedPreferences.getFloat("secondsLeft", seconds.getDimension(i++, 0));
                this.secondsTop = sharedPreferences.getFloat("secondsTop", seconds.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("seconds")>-1){
                    this.secondsColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.secondsColor = sharedPreferences.getInt("secondsColor", seconds.getColor(i++, 0));
                }
                this.secondsAlignLeft = sharedPreferences.getBoolean("secondsAlignLeft", seconds.getBoolean(i, false));
                seconds.recycle();
            }
        // am_pm
            this.am_pmBool = sharedPreferences.getBoolean("am_pmBool", res.getIdentifier("am_pm", "array", context.getPackageName())!=0);
            if(this.am_pmBool) {
                TypedArray am_pm = res.obtainTypedArray(res.getIdentifier("am_pm", "array", context.getPackageName()));
                i = 0;
                this.am_pmFontSize = sharedPreferences.getFloat("am_pmFontSize", am_pm.getDimension(i++, 0));
                this.am_pmLeft = sharedPreferences.getFloat("am_pmLeft", am_pm.getDimension(i++, 0));
                this.am_pmTop = sharedPreferences.getFloat("am_pmTop", am_pm.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("am_pm")>-1){
                    this.am_pmColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.am_pmColor = sharedPreferences.getInt("am_pmColor", am_pm.getColor(i++, 0));
                }
                this.am_pmAlignLeft = sharedPreferences.getBoolean("am_pmAlignLeft", am_pm.getBoolean(i, false));
                am_pm.recycle();
            }
        // weekday
            this.weekdayBool = sharedPreferences.getBoolean("weekdayBool", res.getIdentifier("weekday", "array", context.getPackageName())!=0);
            if(this.weekdayBool) {
                TypedArray weekday = res.obtainTypedArray(res.getIdentifier("weekday", "array", context.getPackageName()));
                i = 0;
                this.weekdayFontSize = sharedPreferences.getFloat("weekdayFontSize", weekday.getDimension(i++, 0));
                this.weekdayLeft = sharedPreferences.getFloat("weekdayLeft", weekday.getDimension(i++, 0));
                this.weekdayTop = sharedPreferences.getFloat("weekdayTop", weekday.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("weekday")>-1){
                    this.weekdayColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.weekdayColor = sharedPreferences.getInt("weekdayColor", weekday.getColor(i++, 0));
                }
                this.weekdayAlignLeft = sharedPreferences.getBoolean("weekdayAlignLeft", weekday.getBoolean(i, false));
                weekday.recycle();
            }
        // month
            this.monthBool = sharedPreferences.getBoolean("monthBool", res.getIdentifier("month", "array", context.getPackageName())!=0);
            if(this.monthBool) {
                TypedArray month = res.obtainTypedArray(res.getIdentifier("month", "array", context.getPackageName()));
                i = 0;
                this.monthFontSize = sharedPreferences.getFloat("monthFontSize", month.getDimension(i++, 0));
                this.monthLeft = sharedPreferences.getFloat("monthLeft", month.getDimension(i++, 0));
                this.monthTop = sharedPreferences.getFloat("monthTop", month.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("month")>-1){
                    this.monthColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.monthColor = sharedPreferences.getInt("monthColor", month.getColor(i++, 0));
                }
                this.monthAlignLeft = sharedPreferences.getBoolean("monthAlignLeft", month.getBoolean(i, false));
                month.recycle();
            }
        // day
            this.dayBool = sharedPreferences.getBoolean("dayBool", res.getIdentifier("day", "array", context.getPackageName())!=0);
            if(this.dayBool) {
                TypedArray day = res.obtainTypedArray(res.getIdentifier("day", "array", context.getPackageName()));
                i = 0;
                this.dayFontSize = sharedPreferences.getFloat("dayFontSize", day.getDimension(i++, 0));
                this.dayLeft = sharedPreferences.getFloat("dayLeft", day.getDimension(i++, 0));
                this.dayTop = sharedPreferences.getFloat("dayTop", day.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("day")>-1){
                    this.dayColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.dayColor = sharedPreferences.getInt("dayColor", day.getColor(i++, 0));
                }
                this.dayAlignLeft = sharedPreferences.getBoolean("dayAlignLeft", day.getBoolean(i, false));
                day.recycle();
            }
        // Year
            this.yearBool = sharedPreferences.getBoolean("yearBool", res.getIdentifier("year", "array", context.getPackageName())!=0);
            if(this.yearBool) {
                TypedArray year = res.obtainTypedArray(res.getIdentifier("year", "array", context.getPackageName()));
                i = 0;
                this.yearFontSize = sharedPreferences.getFloat("yearFontSize", year.getDimension(i++, 0));
                this.yearLeft = sharedPreferences.getFloat("yearLeft", year.getDimension(i++, 0));
                this.yearTop = sharedPreferences.getFloat("yearTop", year.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("year")>-1){
                    this.yearColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.yearColor = sharedPreferences.getInt("yearColor", year.getColor(i++, 0));
                }
                this.yearAlignLeft = sharedPreferences.getBoolean("yearAlignLeft", year.getBoolean(i, false));
                year.recycle();
            }

        // Get Widgets
        String widgetsAsText = sharedPreferences.getString("widgets", null);
        if(widgetsAsText==null) {
            int[] widgets_as_num = context.getResources().getIntArray(R.array.widgets);
            String[] supported_widgets = context.getResources().getStringArray(R.array.supported_widgets);
            String[] widgets = new String[widgets_as_num.length];
            for (int j = 0; j < widgets_as_num.length; j++) {
                widgets[j] = supported_widgets[widgets_as_num[j]];
            }
            this.widgets_list = Arrays.asList(widgets);
        }else{
            String text = widgetsAsText.replaceAll("(\\[|\\]| )",""); // Replace "[", "]" and "spaces"
            this.widgets_list = Arrays.asList(text.split(","));
        }
        Log.w("DinoDevs-GreatFit", "Widgets: "+ widgets_list.toString());


        // Date
            this.date = sharedPreferences.getInt("date", widgets_list.indexOf("date")+1);
            if(this.date>0){
                TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("widget"+this.date, "array", context.getPackageName()));
                i = 0;
                this.dateFontSize  = sharedPreferences.getFloat("dateFontSize", widgetN.getDimension(i++, 0));
                this.dateLeft  = sharedPreferences.getFloat("dateLeft", widgetN.getDimension(i++, 0));
                this.dateTop  = sharedPreferences.getFloat("dateTop", widgetN.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("widget"+this.date)>-1){
                    this.dateColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.dateColor = sharedPreferences.getInt("dateColor", widgetN.getColor(i++, 0));
                }
                this.dateAlignLeft = sharedPreferences.getBoolean("dateAlignLeft", widgetN.getBoolean(i++, false));
                this.dateUnits = sharedPreferences.getBoolean("dateUnits", widgetN.getBoolean(i++, true));
                this.dateIcon = sharedPreferences.getBoolean("dateIcon", widgetN.getBoolean(i++, true));
                if(dateIcon) {
                    this.dateIconLeft = sharedPreferences.getFloat("dateIconLeft", widgetN.getDimension(i++, 0));
                    this.dateIconTop = sharedPreferences.getFloat("dateIconTop", widgetN.getDimension(i, 0));
                }
                widgetN.recycle();
            }

        // Calories
            this.calories = sharedPreferences.getInt("calories", widgets_list.indexOf("calories")+1);
            if(this.calories>0){
                TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("widget"+this.calories, "array", context.getPackageName()));
                i = 0;
                this.caloriesFontSize  = sharedPreferences.getFloat("caloriesFontSize", widgetN.getDimension(i++, 0));
                this.caloriesLeft  = sharedPreferences.getFloat("caloriesLeft", widgetN.getDimension(i++, 0));
                this.caloriesTop  = sharedPreferences.getFloat("caloriesTop", widgetN.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("widget"+this.calories)>-1){
                    this.caloriesColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.caloriesColor = sharedPreferences.getInt("caloriesColor", widgetN.getColor(i++, 0));
                }
                this.caloriesAlignLeft = sharedPreferences.getBoolean("caloriesAlignLeft", widgetN.getBoolean(i++, false));
                this.caloriesUnits = sharedPreferences.getBoolean("caloriesUnits", widgetN.getBoolean(i++, true));
                this.caloriesIcon = sharedPreferences.getBoolean("caloriesIcon", widgetN.getBoolean(i++, true));
                if(caloriesIcon){
                    this.caloriesIconLeft = sharedPreferences.getFloat("caloriesIconLeft", widgetN.getDimension(i++, 0));
                    this.caloriesIconTop = sharedPreferences.getFloat("caloriesIconTop", widgetN.getDimension(i, 0));
                }
                widgetN.recycle();
            }

        // steps
            this.steps = sharedPreferences.getInt("steps", widgets_list.indexOf("steps")+1);
            if(this.steps>0){
                TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("widget"+this.steps, "array", context.getPackageName()));
                i = 0;
                this.stepsFontSize  = sharedPreferences.getFloat("stepsFontSize", widgetN.getDimension(i++, 0));
                this.stepsLeft  = sharedPreferences.getFloat("stepsLeft", widgetN.getDimension(i++, 0));
                this.stepsTop  = sharedPreferences.getFloat("stepsTop", widgetN.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("widget"+this.steps)>-1){
                    this.stepsColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.stepsColor = sharedPreferences.getInt("stepsColor", widgetN.getColor(i++, 0));
                }
                this.stepsAlignLeft = sharedPreferences.getBoolean("stepsAlignLeft", widgetN.getBoolean(i++, false));
                this.stepsUnits = sharedPreferences.getBoolean("stepsUnits", widgetN.getBoolean(i++, true));
                this.stepsIcon = sharedPreferences.getBoolean("stepsIcon", widgetN.getBoolean(i++, true));
                if(stepsIcon){
                    this.stepsIconLeft = sharedPreferences.getFloat("stepsIconLeft", widgetN.getDimension(i++, 0));
                    this.stepsIconTop = sharedPreferences.getFloat("stepsIconTop", widgetN.getDimension(i, 0));
                }
                widgetN.recycle();
            }

        // heart_rate
            this.heart_rate = sharedPreferences.getInt("heart_rate", widgets_list.indexOf("heart_rate")+1);
            if(this.heart_rate>0){
                TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("widget"+this.heart_rate, "array", context.getPackageName()));
                i = 0;
                this.heart_rateFontSize  = sharedPreferences.getFloat("heart_rateFontSize", widgetN.getDimension(i++, 0));
                this.heart_rateLeft  = sharedPreferences.getFloat("heart_rateLeft", widgetN.getDimension(i++, 0));
                this.heart_rateTop  = sharedPreferences.getFloat("heart_rateTop", widgetN.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("widget"+this.heart_rate)>-1){
                    this.heart_rateColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.heart_rateColor = sharedPreferences.getInt("heart_rateColor", widgetN.getColor(i++, 0));
                }
                this.heart_rateAlignLeft = sharedPreferences.getBoolean("heart_rateAlignLeft", widgetN.getBoolean(i++, false));
                this.heart_rateUnits = sharedPreferences.getBoolean("heart_rateUnits", widgetN.getBoolean(i++, true));
                this.heart_rateIcon = sharedPreferences.getBoolean("heart_rateIcon", widgetN.getBoolean(i++, true));
                if(heart_rateIcon) {
                    this.heart_rateIconLeft = sharedPreferences.getFloat("heart_rateIconLeft", widgetN.getDimension(i++, 0));
                    this.heart_rateIconTop = sharedPreferences.getFloat("heart_rateIconTop", widgetN.getDimension(i, 0));
                }
                widgetN.recycle();
            }

        // total_distance
            this.total_distance = sharedPreferences.getInt("total_distance", widgets_list.indexOf("total_distance")+1);
            if(this.total_distance>0){
                TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("widget"+this.total_distance, "array", context.getPackageName()));
                i = 0;
                this.total_distanceFontSize  = sharedPreferences.getFloat("total_distanceFontSize", widgetN.getDimension(i++, 0));
                this.total_distanceLeft  = sharedPreferences.getFloat("total_distanceLeft", widgetN.getDimension(i++, 0));
                this.total_distanceTop  = sharedPreferences.getFloat("total_distanceTop", widgetN.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("widget"+this.total_distance)>-1){
                    this.total_distanceColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.total_distanceColor = sharedPreferences.getInt("total_distanceColor", widgetN.getColor(i++, 0));
                }
                this.total_distanceAlignLeft = sharedPreferences.getBoolean("total_distanceAlignLeft", widgetN.getBoolean(i++, false));
                this.total_distanceUnits = sharedPreferences.getBoolean("total_distanceUnits", widgetN.getBoolean(i++, true));
                this.total_distanceIcon = sharedPreferences.getBoolean("total_distanceIcon", widgetN.getBoolean(i++, true));
                if(total_distanceIcon) {
                    this.total_distanceIconLeft = sharedPreferences.getFloat("total_distanceIconLeft", widgetN.getDimension(i++, 0));
                    this.total_distanceIconTop = sharedPreferences.getFloat("total_distanceIconTop", widgetN.getDimension(i, 0));
                }
                widgetN.recycle();
            }

        // today_distance
            this.today_distance = sharedPreferences.getInt("today_distance", widgets_list.indexOf("today_distance")+1);
            if(this.today_distance>0){
                TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("widget"+this.today_distance, "array", context.getPackageName()));
                i = 0;
                this.today_distanceFontSize  = sharedPreferences.getFloat("today_distanceFontSize", widgetN.getDimension(i++, 0));
                this.today_distanceLeft  = sharedPreferences.getFloat("today_distanceLeft", widgetN.getDimension(i++, 0));
                this.today_distanceTop  = sharedPreferences.getFloat("today_distanceTop", widgetN.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("widget"+this.today_distance)>-1){
                    this.today_distanceColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.today_distanceColor = sharedPreferences.getInt("today_distanceColor", widgetN.getColor(i++, 0));
                }
                this.today_distanceAlignLeft = sharedPreferences.getBoolean("today_distanceAlignLeft", widgetN.getBoolean(i++, false));
                this.today_distanceUnits = sharedPreferences.getBoolean("today_distanceUnits", widgetN.getBoolean(i++, true));
                this.today_distanceIcon = sharedPreferences.getBoolean("today_distanceIcon", widgetN.getBoolean(i++, true));
                if(today_distanceIcon) {
                    this.today_distanceIconLeft  = sharedPreferences.getFloat("today_distanceIconLeft", widgetN.getDimension(i++, 0));
                    this.today_distanceIconTop  = sharedPreferences.getFloat("today_distanceIconTop", widgetN.getDimension(i, 0));
                }
                widgetN.recycle();
            }

        // floors
            this.floors = sharedPreferences.getInt("floors", widgets_list.indexOf("floors")+1);
            if(this.floors>0){
                TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("widget"+this.floors, "array", context.getPackageName()));
                i = 0;
                this.floorsFontSize  = sharedPreferences.getFloat("floorsFontSize", widgetN.getDimension(i++, 0));
                this.floorsLeft  = sharedPreferences.getFloat("floorsLeft", widgetN.getDimension(i++, 0));
                this.floorsTop  = sharedPreferences.getFloat("floorsTop", widgetN.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("widget"+this.floors)>-1){
                    this.floorsColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.floorsColor = sharedPreferences.getInt("floorsColor", widgetN.getColor(i++, 0));
                }
                this.floorsAlignLeft = sharedPreferences.getBoolean("floorsAlignLeft", widgetN.getBoolean(i++, false));
                this.floorsUnits = sharedPreferences.getBoolean("floorsUnits", widgetN.getBoolean(i++, true));
                this.floorsIcon = sharedPreferences.getBoolean("floorsIcon", widgetN.getBoolean(i++, true));
                if(floorsIcon) {
                    this.floorsIconLeft = sharedPreferences.getFloat("floorsIconLeft", widgetN.getDimension(i++, 0));
                    this.floorsIconTop = sharedPreferences.getFloat("floorsIconTop", widgetN.getDimension(i, 0));
                }
                widgetN.recycle();
            }

        // battery_percent
            this.battery_percent = sharedPreferences.getInt("battery_percent", widgets_list.indexOf("battery_percent")+1);
            if(this.battery_percent>0){
                TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("widget"+this.battery_percent, "array", context.getPackageName()));
                i = 0;
                this.battery_percentFontSize  = sharedPreferences.getFloat("battery_percentFontSize", widgetN.getDimension(i++, 0));
                this.battery_percentLeft  = sharedPreferences.getFloat("battery_percentLeft", widgetN.getDimension(i++, 0));
                this.battery_percentTop  = sharedPreferences.getFloat("battery_percentTop", widgetN.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("widget"+this.battery_percent)>-1){
                    this.battery_percentColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.battery_percentColor = sharedPreferences.getInt("battery_percentColor", widgetN.getColor(i++, 0));
                }
                this.battery_percentAlignLeft = sharedPreferences.getBoolean("battery_percentAlignLeft", widgetN.getBoolean(i++, false));
                this.battery_percentUnits = sharedPreferences.getBoolean("battery_percentUnits", widgetN.getBoolean(i++, true));
                this.battery_percentIcon = sharedPreferences.getBoolean("battery_percentIcon", widgetN.getBoolean(i++, true));
                if(battery_percentIcon) {
                    this.battery_percentIconLeft = sharedPreferences.getFloat("battery_percentIconLeft", widgetN.getDimension(i++, 0));
                    this.battery_percentIconTop = sharedPreferences.getFloat("battery_percentIconTop", widgetN.getDimension(i, 0));
                }
                widgetN.recycle();
            }

        // temperature
            this.temperature = sharedPreferences.getInt("temperature", widgets_list.indexOf("temperature")+1);
            if(this.temperature>0){
                TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("widget"+this.temperature, "array", context.getPackageName()));
                i = 0;
                this.temperatureFontSize  = sharedPreferences.getFloat("temperatureFontSize", widgetN.getDimension(i++, 0));
                this.temperatureLeft  = sharedPreferences.getFloat("temperatureLeft", widgetN.getDimension(i++, 0));
                this.temperatureTop  = sharedPreferences.getFloat("temperatureTop", widgetN.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("widget"+this.temperature)>-1){
                    this.temperatureColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.temperatureColor = sharedPreferences.getInt("temperatureColor", widgetN.getColor(i++, 0));
                }
                this.temperatureAlignLeft = sharedPreferences.getBoolean("temperatureAlignLeft", widgetN.getBoolean(i++, false));
                this.temperatureUnits = sharedPreferences.getBoolean("temperatureUnits", widgetN.getBoolean(i++, true));
                this.temperatureIcon = sharedPreferences.getBoolean("temperatureIcon", widgetN.getBoolean(i++, true));
                if(temperatureIcon) {
                    this.temperatureIconLeft = sharedPreferences.getFloat("temperatureIconLeft", widgetN.getDimension(i++, 0));
                    this.temperatureIconTop = sharedPreferences.getFloat("temperatureIconTop", widgetN.getDimension(i, 0));
                }
                widgetN.recycle();
            }

        // city
            this.city = sharedPreferences.getInt("city", widgets_list.indexOf("city")+1);
            if(this.city>0){
                TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("widget"+this.city, "array", context.getPackageName()));
                i = 0;
                this.cityFontSize  = sharedPreferences.getFloat("cityFontSize", widgetN.getDimension(i++, 0));
                this.cityLeft  = sharedPreferences.getFloat("cityLeft", widgetN.getDimension(i++, 0));
                this.cityTop  = sharedPreferences.getFloat("cityTop", widgetN.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("widget"+this.city)>-1){
                    this.cityColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.cityColor = sharedPreferences.getInt("cityColor", widgetN.getColor(i++, 0));
                }
                this.cityAlignLeft = sharedPreferences.getBoolean("cityAlignLeft", widgetN.getBoolean(i++, false));
                this.cityUnits = sharedPreferences.getBoolean("cityUnits", widgetN.getBoolean(i++, false));
                this.cityIcon = sharedPreferences.getBoolean("cityIcon", widgetN.getBoolean(i++, true));
                if(cityIcon) {
                    this.cityIconLeft = sharedPreferences.getFloat("cityIconLeft", widgetN.getDimension(i++, 0));
                    this.cityIconTop = sharedPreferences.getFloat("cityIconTop", widgetN.getDimension(i, 0));
                }
                widgetN.recycle();
            }

        // watch_alarm
            this.watch_alarm = sharedPreferences.getInt("watch_alarm", widgets_list.indexOf("watch_alarm")+1);
            if(this.watch_alarm>0){
                TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("widget"+this.watch_alarm, "array", context.getPackageName()));
                i = 0;
                this.watch_alarmFontSize  = sharedPreferences.getFloat("watch_alarmFontSize", widgetN.getDimension(i++, 0));
                this.watch_alarmLeft  = sharedPreferences.getFloat("watch_alarmLeft", widgetN.getDimension(i++, 0));
                this.watch_alarmTop  = sharedPreferences.getFloat("watch_alarmTop", widgetN.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("widget"+this.watch_alarm)>-1){
                    this.watch_alarmColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.watch_alarmColor = sharedPreferences.getInt("watch_alarmColor", widgetN.getColor(i++, 0));
                }
                this.watch_alarmAlignLeft = sharedPreferences.getBoolean("watch_alarmAlignLeft", widgetN.getBoolean(i++, false));
                this.watch_alarmUnits = sharedPreferences.getBoolean("watch_alarmUnits", widgetN.getBoolean(i++, true));
                this.watch_alarmIcon = sharedPreferences.getBoolean("watch_alarmIcon", widgetN.getBoolean(i++, true));
                if(watch_alarmIcon) {
                    this.watch_alarmIconLeft = sharedPreferences.getFloat("watch_alarmIconLeft", widgetN.getDimension(i++, 0));
                    this.watch_alarmIconTop = sharedPreferences.getFloat("watch_alarmIconTop", widgetN.getDimension(i, 0));
                }
                widgetN.recycle();
            }

        // humidity
            this.humidity = sharedPreferences.getInt("humidity", widgets_list.indexOf("humidity")+1);
            if(this.humidity>0){
                TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("widget"+this.humidity, "array", context.getPackageName()));
                i = 0;
                this.humidityFontSize  = sharedPreferences.getFloat("humidityFontSize", widgetN.getDimension(i++, 0));
                this.humidityLeft  = sharedPreferences.getFloat("humidityLeft", widgetN.getDimension(i++, 0));
                this.humidityTop  = sharedPreferences.getFloat("humidityTop", widgetN.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("widget"+this.humidity)>-1){
                    this.humidityColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.humidityColor = sharedPreferences.getInt("humidityColor", widgetN.getColor(i++, 0));
                }
                this.humidityAlignLeft = sharedPreferences.getBoolean("humidityAlignLeft", widgetN.getBoolean(i++, false));
                this.humidityUnits = sharedPreferences.getBoolean("humidityUnits", widgetN.getBoolean(i++, true));
                this.humidityIcon = sharedPreferences.getBoolean("humidityIcon", widgetN.getBoolean(i++, true));
                if(humidityIcon) {
                    this.humidityIconLeft = sharedPreferences.getFloat("humidityIconLeft", widgetN.getDimension(i++, 0));
                    this.humidityIconTop = sharedPreferences.getFloat("humidityIconTop", widgetN.getDimension(i, 0));
                }
                widgetN.recycle();
            }

        // uv
            this.uv = sharedPreferences.getInt("uv", widgets_list.indexOf("uv")+1);
            if(this.uv>0){
                TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("widget"+this.uv, "array", context.getPackageName()));
                i = 0;
                this.uvFontSize  = sharedPreferences.getFloat("uvFontSize", widgetN.getDimension(i++, 0));
                this.uvLeft  = sharedPreferences.getFloat("uvLeft", widgetN.getDimension(i++, 0));
                this.uvTop  = sharedPreferences.getFloat("uvTop", widgetN.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("widget"+this.uv)>-1){
                    this.uvColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.uvColor = sharedPreferences.getInt("uvColor", widgetN.getColor(i++, 0));
                }
                this.uvAlignLeft = sharedPreferences.getBoolean("uvAlignLeft", widgetN.getBoolean(i++, false));
                this.uvUnits = sharedPreferences.getBoolean("uvUnits", widgetN.getBoolean(i++, true));
                this.uvIcon = sharedPreferences.getBoolean("uvIcon", widgetN.getBoolean(i++, true));
                if(uvIcon) {
                    this.uvIconLeft = sharedPreferences.getFloat("uvIconLeft", widgetN.getDimension(i++, 0));
                    this.uvIconTop = sharedPreferences.getFloat("uvIconTop", widgetN.getDimension(i, 0));
                }
                widgetN.recycle();
            }

        // wind_direction
            this.wind_direction = sharedPreferences.getInt("wind_direction", widgets_list.indexOf("wind_direction")+1);
            if(this.wind_direction>0){
                TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("widget"+this.wind_direction, "array", context.getPackageName()));
                i = 0;
                this.wind_directionFontSize  = sharedPreferences.getFloat("wind_directionFontSize", widgetN.getDimension(i++, 0));
                this.wind_directionLeft  = sharedPreferences.getFloat("wind_directionLeft", widgetN.getDimension(i++, 0));
                this.wind_directionTop  = sharedPreferences.getFloat("wind_directionTop", widgetN.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("widget"+this.wind_direction)>-1){
                    this.wind_directionColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.wind_directionColor = sharedPreferences.getInt("wind_directionColor", widgetN.getColor(i++, 0));
                }
                this.wind_directionAlignLeft = sharedPreferences.getBoolean("wind_directionAlignLeft", widgetN.getBoolean(i++, false));
                this.wind_directionUnits = sharedPreferences.getBoolean("wind_directionUnits", widgetN.getBoolean(i++, true));
                this.wind_directionIcon = sharedPreferences.getBoolean("wind_directionIcon", widgetN.getBoolean(i++, true));
                if(wind_directionIcon) {
                    this.wind_directionIconLeft = sharedPreferences.getFloat("wind_directionIconLeft", widgetN.getDimension(i++, 0));
                    this.wind_directionIconTop = sharedPreferences.getFloat("wind_directionIconTop", widgetN.getDimension(i, 0));
                }
                widgetN.recycle();
            }

        // wind_strength
            this.wind_strength = sharedPreferences.getInt("wind_strength", widgets_list.indexOf("wind_strength")+1);
            if(this.wind_strength>0){
                TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("widget"+this.wind_strength, "array", context.getPackageName()));
                i = 0;
                this.wind_strengthFontSize  = sharedPreferences.getFloat("wind_strengthFontSize", widgetN.getDimension(i++, 0));
                this.wind_strengthLeft  = sharedPreferences.getFloat("wind_strengthLeft", widgetN.getDimension(i++, 0));
                this.wind_strengthTop  = sharedPreferences.getFloat("wind_strengthTop", widgetN.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("widget"+this.wind_strength)>-1){
                    this.wind_strengthColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.wind_strengthColor = sharedPreferences.getInt("wind_strengthColor", widgetN.getColor(i++, 0));
                }
                this.wind_strengthAlignLeft = sharedPreferences.getBoolean("wind_strengthAlignLeft", widgetN.getBoolean(i++, false));
                this.wind_strengthUnits = sharedPreferences.getBoolean("wind_strengthUnits", widgetN.getBoolean(i++, true));
                this.wind_strengthIcon = sharedPreferences.getBoolean("wind_strengthIcon", widgetN.getBoolean(i++, true));
                if(wind_strengthIcon) {
                    this.wind_strengthIconLeft = sharedPreferences.getFloat("wind_strengthIconLeft", widgetN.getDimension(i++, 0));
                    this.wind_strengthIconTop = sharedPreferences.getFloat("wind_strengthIconTop", widgetN.getDimension(i, 0));
                }
                widgetN.recycle();
            }

        // air_pressure
            this.air_pressure = sharedPreferences.getInt("air_pressure", widgets_list.indexOf("air_pressure")+1);
            if(this.air_pressure>0){
                TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("widget"+this.air_pressure, "array", context.getPackageName()));
                i = 0;
                this.air_pressureFontSize  = sharedPreferences.getFloat("air_pressureFontSize", widgetN.getDimension(i++, 0));
                this.air_pressureLeft  = sharedPreferences.getFloat("air_pressureLeft", widgetN.getDimension(i++, 0));
                this.air_pressureTop  = sharedPreferences.getFloat("air_pressureTop", widgetN.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("widget"+this.air_pressure)>-1){
                    this.air_pressureColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.air_pressureColor = sharedPreferences.getInt("air_pressureColor", widgetN.getColor(i++, 0));
                }
                this.air_pressureAlignLeft = sharedPreferences.getBoolean("air_pressureAlignLeft", widgetN.getBoolean(i++, false));
                this.air_pressureUnits = sharedPreferences.getBoolean("air_pressureUnits", widgetN.getBoolean(i++, true));
                this.air_pressureIcon = sharedPreferences.getBoolean("air_pressureIcon", widgetN.getBoolean(i++, true));
                if(air_pressureIcon) {
                    this.air_pressureIconLeft = sharedPreferences.getFloat("air_pressureIconLeft", widgetN.getDimension(i++, 0));
                    this.air_pressureIconTop = sharedPreferences.getFloat("air_pressureIconTop", widgetN.getDimension(i, 0));
                }
                widgetN.recycle();
            }

        // altitude
            this.altitude = sharedPreferences.getInt("altitude", widgets_list.indexOf("altitude")+1);
            if(this.altitude>0){
                TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("widget"+this.altitude, "array", context.getPackageName()));
                i = 0;
                this.altitudeFontSize  = sharedPreferences.getFloat("altitudeFontSize", widgetN.getDimension(i++, 0));
                this.altitudeLeft  = sharedPreferences.getFloat("altitudeLeft", widgetN.getDimension(i++, 0));
                this.altitudeTop  = sharedPreferences.getFloat("altitudeTop", widgetN.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("widget"+this.altitude)>-1){
                    this.altitudeColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.altitudeColor = sharedPreferences.getInt("altitudeColor", widgetN.getColor(i++, 0));
                }
                this.altitudeAlignLeft = sharedPreferences.getBoolean("altitudeAlignLeft", widgetN.getBoolean(i++, false));
                this.altitudeUnits = sharedPreferences.getBoolean("altitudeUnits", widgetN.getBoolean(i++, true));
                this.altitudeIcon = sharedPreferences.getBoolean("altitudeIcon", widgetN.getBoolean(i++, true));
                if(altitudeIcon) {
                    this.altitudeIconLeft = sharedPreferences.getFloat("altitude", widgetN.getDimension(i++, 0));
                    this.altitudeIconTop = sharedPreferences.getFloat("altitudeIconTop", widgetN.getDimension(i, 0));
                }
                widgetN.recycle();
            }

        // phone_battery
            this.phone_battery = sharedPreferences.getInt("phone_battery", widgets_list.indexOf("phone_battery")+1);
            if(this.phone_battery>0){
                TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("widget"+this.phone_battery, "array", context.getPackageName()));
                i = 0;
                this.phone_batteryFontSize  = sharedPreferences.getFloat("phone_batteryFontSize", widgetN.getDimension(i++, 0));
                this.phone_batteryLeft  = sharedPreferences.getFloat("phone_batteryLeft", widgetN.getDimension(i++, 0));
                this.phone_batteryTop  = sharedPreferences.getFloat("phone_batteryTop", widgetN.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("widget"+this.phone_battery)>-1){
                    this.phone_batteryColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.phone_batteryColor = sharedPreferences.getInt("phone_batteryColor", widgetN.getColor(i++, 0));
                }
                this.phone_batteryAlignLeft = sharedPreferences.getBoolean("phone_batteryAlignLeft", widgetN.getBoolean(i++, false));
                this.phone_batteryUnits = sharedPreferences.getBoolean("phone_batteryUnits", widgetN.getBoolean(i++, true));
                this.phone_batteryIcon = sharedPreferences.getBoolean("phone_batteryIcon", widgetN.getBoolean(i++, true));
                if(phone_batteryIcon) {
                    this.phone_batteryIconLeft = sharedPreferences.getFloat("phone_batteryIconLeft", widgetN.getDimension(i++, 0));
                    this.phone_batteryIconTop = sharedPreferences.getFloat("phone_batteryIconTop", widgetN.getDimension(i, 0));
                }
                widgetN.recycle();
            }

        // phone_alarm
            this.phone_alarm = sharedPreferences.getInt("phone_alarm", widgets_list.indexOf("phone_alarm")+1);
            if(this.phone_alarm>0){
                TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("widget"+this.phone_alarm, "array", context.getPackageName()));
                i = 0;
                this.phone_alarmFontSize  = sharedPreferences.getFloat("phone_alarmFontSize", widgetN.getDimension(i++, 0));
                this.phone_alarmLeft  = sharedPreferences.getFloat("phone_alarmLeft", widgetN.getDimension(i++, 0));
                this.phone_alarmTop  = sharedPreferences.getFloat("phone_alarmTop", widgetN.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("widget"+this.phone_alarm)>-1){
                    this.phone_alarmColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.phone_alarmColor = sharedPreferences.getInt("phone_alarmColor", widgetN.getColor(i++, 0));
                }
                this.phone_alarmAlignLeft = sharedPreferences.getBoolean("phone_alarmAlignLeft", widgetN.getBoolean(i++, false));
                this.phone_alarmUnits = sharedPreferences.getBoolean("phone_alarmUnits", widgetN.getBoolean(i++, true));
                this.phone_alarmIcon = sharedPreferences.getBoolean("phone_alarmIcon", widgetN.getBoolean(i++, true));
                if(phone_alarmIcon) {
                    this.phone_alarmIconLeft = sharedPreferences.getFloat("phone_alarmIconLeft", widgetN.getDimension(i++, 0));
                    this.phone_alarmIconTop = sharedPreferences.getFloat("phone_alarmIconTop", widgetN.getDimension(i, 0));
                }
                widgetN.recycle();
            }

        // xdrip
            this.xdrip = sharedPreferences.getInt("xdrip", widgets_list.indexOf("xdrip")+1);
            if(this.xdrip>0){
                TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("widget"+this.xdrip, "array", context.getPackageName()));
                i = 0;
                this.xdripFontSize  = sharedPreferences.getFloat("xdripFontSize", widgetN.getDimension(i++, 0));
                this.xdripLeft  = sharedPreferences.getFloat("xdripLeft", widgetN.getDimension(i++, 0));
                this.xdripTop  = sharedPreferences.getFloat("xdripTop", widgetN.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("widget"+this.xdrip)>-1){
                    this.xdripColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.xdripColor = sharedPreferences.getInt("xdripColor", widgetN.getColor(i++, 0));
                }
                this.xdripAlignLeft = sharedPreferences.getBoolean("xdripAlignLeft", widgetN.getBoolean(i++, false));
                this.xdripUnits = sharedPreferences.getBoolean("xdripUnits", widgetN.getBoolean(i++, true));
                this.xdripIcon = sharedPreferences.getBoolean("xdripIcon", widgetN.getBoolean(i++, true));
                if(xdripIcon) {
                    this.xdripIconLeft = sharedPreferences.getFloat("xdripIconLeft", widgetN.getDimension(i++, 0));
                    this.xdripIconTop = sharedPreferences.getFloat("xdripIconTop", widgetN.getDimension(i, 0));
                }
                widgetN.recycle();
            }

        // weather_img
        this.weather_imgProg = sharedPreferences.getInt("weather_imgProg", widgets_list.indexOf("weather_img")+1);
        if(this.weather_imgProg>0){
            TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("widget"+this.weather_imgProg, "array", context.getPackageName()));
            i = 7;
            this.weather_imgProgLeft  = sharedPreferences.getFloat("weather_imgProgLeft", widgetN.getDimension(i++, 0));
            this.weather_imgProgTop  = sharedPreferences.getFloat("weather_imgProgTop", widgetN.getDimension(i, 0));
            /*
            this.weather_imgProgType = sharedPreferences.getInt("weather_imgProgType", widgetN.getColor(i++, 0));
            if(this.weather_imgProgType==1){
                // Progression with images
                //this.weather_imgProgSlptImages = res.obtainTypedArray(res.getIdentifier("param_progress_element_slpt"+this.weather_imgProg, "array", context.getPackageName()));
            }
            */
            widgetN.recycle();
        }

        // Get Progress Bars
        String barsAsText = sharedPreferences.getString("progress_bars", null);
        if(barsAsText==null) {
            int[] circle_bars_as_num = context.getResources().getIntArray(R.array.circle_bars);
            String[] supported_progress_elements = context.getResources().getStringArray(R.array.supported_progress_elements);
            String[] circle_bars = new String[ circle_bars_as_num.length ];
            for (int j = 0; j < circle_bars_as_num.length; j++) {
                circle_bars[j] = supported_progress_elements[circle_bars_as_num[j]];
            }
            this.circle_bars_list = Arrays.asList(circle_bars);
        }else{
            String text = barsAsText.replaceAll("(\\[|\\]| )",""); // Replace "[", "]" and "spaces"
            this.circle_bars_list = Arrays.asList(text.split(","));
        }

        Log.w("DinoDevs-GreatFit", "Bars: "+ circle_bars_list.toString());

        // StepsProg
        this.stepsProg = sharedPreferences.getInt("stepsProg", circle_bars_list.indexOf("steps")+1);
        if(this.stepsProg>0){
            TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("progress_element"+this.stepsProg, "array", context.getPackageName()));
            i = 0;
            this.stepsProgLeft  = sharedPreferences.getFloat("stepsProgLeft", widgetN.getDimension(i++, 0));
            this.stepsProgTop  = sharedPreferences.getFloat("stepsProgTop", widgetN.getDimension(i++, 0));
            this.stepsProgType = sharedPreferences.getInt("stepsProgType", widgetN.getColor(i++, 0));

            if(this.stepsProgType==0){ // Circle bar element
                this.stepsProgRadius  = sharedPreferences.getFloat("stepsProgRadius", widgetN.getDimension(i++, 0));
                this.stepsProgThickness  = sharedPreferences.getFloat("stepsProgRadius", widgetN.getDimension(i++, 0));
                this.stepsProgStartAngle = sharedPreferences.getInt("stepsProgStartAngle", widgetN.getInteger(i++, 0));
                this.stepsProgEndAngle = sharedPreferences.getInt("stepsProgEndAngle", widgetN.getInteger(i++, 0));
                this.stepsProgClockwise = sharedPreferences.getInt("stepsProgClockwise", widgetN.getInteger(i++, 1));
                if(this.color>-1 && theme_elements.indexOf("bar_element"+this.stepsProg)>-1){
                    this.stepsProgColorIndex = this.color;
                    i++;
                }else{
                    this.stepsProgColorIndex = sharedPreferences.getInt("stepsProgColorIndex", widgetN.getInteger(i++, 0));
                }
                this.stepsProgBgBool = sharedPreferences.getBoolean("stepsProgBgBool", widgetN.getBoolean(i++, false));
                this.stepsProgBgColor = sharedPreferences.getInt("stepsProgBgColor", widgetN.getInteger(i++, 0));
                this.stepsProgBgImage = sharedPreferences.getString("stepsProgBgImage", widgetN.getString(i++));
                this.stepsProgSlptImage = sharedPreferences.getString("stepsProgSlptImage", widgetN.getString(i+this.stepsProgColorIndex));
            }else{ // Progression with images
                //this.stepsProgSlptImages = res.obtainTypedArray(res.getIdentifier("param_progress_element_slpt"+this.stepsProg, "array", context.getPackageName()));
            }
            widgetN.recycle();
        }

        // today_distanceProg
        this.today_distanceProg = sharedPreferences.getInt("today_distanceProg", circle_bars_list.indexOf("today_distance")+1);
        if(this.today_distanceProg>0){
            TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("progress_element"+this.today_distanceProg, "array", context.getPackageName()));
            i = 0;
            this.today_distanceProgLeft  = sharedPreferences.getFloat("today_distanceProgLeft", widgetN.getDimension(i++, 0));
            this.today_distanceProgTop  = sharedPreferences.getFloat("today_distanceProgTop", widgetN.getDimension(i++, 0));
            this.today_distanceProgType = sharedPreferences.getInt("today_distanceProgType", widgetN.getColor(i++, 0));
            if(this.today_distanceProgType==0){ // Circle bar element
                this.today_distanceProgRadius  = sharedPreferences.getFloat("today_distanceProgRadius", widgetN.getDimension(i++, 0));
                this.today_distanceProgThickness  = sharedPreferences.getFloat("today_distanceProgThickness", widgetN.getDimension(i++, 0));
                this.today_distanceProgStartAngle = sharedPreferences.getInt("today_distanceProgStartAngle", widgetN.getInteger(i++, 0));
                this.today_distanceProgEndAngle = sharedPreferences.getInt("today_distanceProgEndAngle", widgetN.getInteger(i++, 0));
                this.today_distanceProgClockwise = sharedPreferences.getInt("today_distanceProgClockwise", widgetN.getInteger(i++, 1));
                if(this.color>-1 && theme_elements.indexOf("bar_element"+this.today_distanceProg)>-1){
                    this.today_distanceProgColorIndex = this.color;
                    i++;
                }else{
                    this.today_distanceProgColorIndex = sharedPreferences.getInt("today_distanceProgColorIndex", widgetN.getInteger(i++, 0));
                }
                this.today_distanceProgBgBool = sharedPreferences.getBoolean("today_distanceProgBgBool", widgetN.getBoolean(i++, false));
                this.today_distanceProgBgColor = sharedPreferences.getInt("today_distanceProgBgColor", widgetN.getInteger(i++, 0));
                this.today_distanceProgBgImage = sharedPreferences.getString("today_distanceProgBgImage", widgetN.getString(i++));
                this.today_distanceProgSlptImage = sharedPreferences.getString("today_distanceProgSlptImage", widgetN.getString(i+this.today_distanceProgColorIndex));
            }else{ // Progression with images
                //this.today_distanceProgSlptImages = res.obtainTypedArray(res.getIdentifier("param_progress_element_slpt"+this.today_distanceProg, "array", context.getPackageName()));
            }
            widgetN.recycle();
        }

        // batteryProg
        this.batteryProg = sharedPreferences.getInt("batteryProg", circle_bars_list.indexOf("battery")+1);
        if(this.batteryProg>0){
            TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("progress_element"+this.batteryProg, "array", context.getPackageName()));
            i = 0;
            this.batteryProgLeft  = sharedPreferences.getFloat("batteryProgLeft", widgetN.getDimension(i++, 0));
            this.batteryProgTop  = sharedPreferences.getFloat("batteryProgTop", widgetN.getDimension(i++, 0));
            this.batteryProgType = sharedPreferences.getInt("batteryProgType", widgetN.getColor(i++, 0));

            if(this.batteryProgType==0){ // Circle bar element
                this.batteryProgRadius  = sharedPreferences.getFloat("batteryProgRadius", widgetN.getDimension(i++, 0));
                this.batteryProgThickness  = sharedPreferences.getFloat("batteryProgThickness", widgetN.getDimension(i++, 0));
                this.batteryProgStartAngle = sharedPreferences.getInt("batteryProgStartAngle", widgetN.getInteger(i++, 0));
                this.batteryProgEndAngle = sharedPreferences.getInt("batteryProgEndAngle", widgetN.getInteger(i++, 0));
                this.batteryProgClockwise = sharedPreferences.getInt("batteryProgClockwise", widgetN.getInteger(i++, 1));
                if(this.color>-1 && theme_elements.indexOf("bar_element"+this.batteryProg)>-1){
                    this.batteryProgColorIndex = this.color;
                    i++;
                }else{
                    this.batteryProgColorIndex = sharedPreferences.getInt("batteryProgColorIndex", widgetN.getInteger(i++, 0));
                }
                this.batteryProgBgBool = sharedPreferences.getBoolean("batteryProgBgBool", widgetN.getBoolean(i++, false));
                this.batteryProgBgColor = sharedPreferences.getInt("batteryProgBgColor", widgetN.getInteger(i++, 0));
                this.batteryProgBgImage = sharedPreferences.getString("batteryProgBgImage", widgetN.getString(i++));
                this.batteryProgSlptImage = sharedPreferences.getString("batteryProgSlptImage", widgetN.getString(i+this.batteryProgColorIndex));
            }else{ // Progression with images
                //this.batteryProgSlptImages = res.obtainTypedArray(res.getIdentifier("param_progress_element_slpt"+this.batteryProg, "array", context.getPackageName()));
            }
            widgetN.recycle();
        }

    }

    // STEPS WIDGET
    public boolean isStepsRate(){
        return this.steps>0 || this.stepsProg>0;
    }

    // SPORT'S TODAY DISTANCE WIDGET
    public boolean isTodayDistanceRate(){
        return this.today_distance>0 || this.today_distanceProg>0;
    }

    // HEART RATE WIDGET
    public boolean isHeartRate(){
        return this.heart_rate>0;
    }

    // CALORIES WIDGET
    public boolean isCalories(){
        return this.calories>0;
    }

    // FLOOR WIDGET
    public boolean isFloor(){
        return this.floors>0;
    }

    // BATTERY WIDGET
    public boolean isBattery(){
        return this.battery_percent>0 || this.batteryProg>0;
    }

    // WEATHER WIDGET
    public boolean isWeather(){
        return this.temperature>0 || this.city>0 || this.humidity>0 || this.uv>0 || this.wind_direction>0 || this.wind_strength>0 || this.weather_imgProg>0;
    }

    // GREAT WIDGET
    public boolean isGreat(){
        return this.am_pmBool || watch_alarm>0 || xdrip>0 || air_pressure>0 || altitude>0 || phone_battery>0;
    }
    public boolean isCustom(){
        return air_pressure>0 || altitude>0 || phone_battery>0;
    }
}