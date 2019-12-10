package com.dinodevs.greatfitwatchface.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.StyleableRes;
import android.util.Log;

import com.dinodevs.greatfitwatchface.R;
import com.dinodevs.greatfitwatchface.resource.ResourceManager;
import com.huami.watch.watchface.util.Util;

import java.util.Arrays;
import java.util.List;

public class LoadSettings {

    private Context context;
    public SharedPreferences sharedPreferences;
    private int versionCode;
    private Resources res;
    private final static  String TAG = "DinoDevs-GreatFit";

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
    public boolean white_bg;
    public String is_white_bg;
    public int font_no;
    public Integer[] colorCodes;
    public boolean flashing_indicator;
    public boolean month_as_text;
    public boolean three_letters_month_if_text;
    public boolean three_letters_day_if_text;
    public boolean no_0_on_hour_first_digit;
    public boolean wind_direction_as_arrows;
    public boolean status_bar;
    public int status_barLeft;
    public int status_barTop;
    public boolean low_power;
    public float low_powerLeft;
    public float low_powerTop;
    public boolean flashing_heart_rate_icon;
    public float target_calories;
    public int custom_refresh_rate;
    public int temp_heart_rate;
    public int temp_calories;
    public boolean am_pm_always;
    public float world_time_zone;
    public boolean pressure_to_mmhg;
    public Paint mGPaint;
    public List widgets_list;
    public List circle_bars_list;
    public boolean isMetric;

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
    // walked_distance
    public int step_length;
    public int walked_distance;
    public float walked_distanceFontSize;
    public float walked_distanceLeft;
    public float walked_distanceTop;
    public int walked_distanceColor;
    public boolean walked_distanceAlignLeft;
    public boolean walked_distanceUnits;
    public boolean walked_distanceIcon;
    public float walked_distanceIconLeft;
    public float walked_distanceIconTop;
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
    // min_max_temperatures
    public int min_max_temperatures;
    public float min_max_temperaturesFontSize;
    public float min_max_temperaturesLeft;
    public float min_max_temperaturesTop;
    public int min_max_temperaturesColor;
    public boolean min_max_temperaturesAlignLeft;
    public boolean min_max_temperaturesUnits;
    public boolean min_max_temperaturesIcon;
    public float min_max_temperaturesIconLeft;
    public float min_max_temperaturesIconTop;
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
    // Weather
    public int weather_img;
    public float weather_imgFontSize;
    public float weather_imgLeft;
    public float weather_imgTop;
    public int weather_imgColor;
    public boolean weather_imgAlignLeft;
    public boolean weather_imgUnits;
    public boolean weather_imgIcon;
    public float weather_imgIconLeft;
    public float weather_imgIconTop;
    // world_time
    public int world_time;
    public float world_timeFontSize;
    public float world_timeLeft;
    public float world_timeTop;
    public int world_timeColor;
    public boolean world_timeAlignLeft;
    public boolean world_timeUnits;
    public boolean world_timeIcon;
    public float world_timeIconLeft;
    public float world_timeIconTop;
    // phone_alarm
    public int notifications;
    public float notificationsFontSize;
    public float notificationsLeft;
    public float notificationsTop;
    public int notificationsColor;
    public boolean notificationsAlignLeft;
    public boolean notificationsUnits;
    public boolean notificationsIcon;
    public float notificationsIconLeft;
    public float notificationsIconTop;
    //moonphase
    public int moonphase;
    public float moonphaseFontSize;
    public float moonphaseLeft;
    public float moonphaseTop;
    public int moonphaseColor;
    public boolean moonphaseAlignLeft;
    public boolean moonphaseIcon;
    public float moonphaseIconLeft;
    public float moonphaseIconTop;

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
    // Calories
    public int caloriesProg;
    public float caloriesProgLeft;
    public float caloriesProgTop;
    public int caloriesProgType;
    public float caloriesProgRadius;
    public float caloriesProgThickness;
    public int caloriesProgStartAngle;
    public int caloriesProgEndAngle;
    public int caloriesProgClockwise;
    public int caloriesProgColorIndex;
    public boolean caloriesProgBgBool;
    public int caloriesProgBgColor;
    public String caloriesProgBgImage;
    public String caloriesProgSlptImage;
    // Heart rate
    public int heart_rateProg;
    public float heart_rateProgLeft;
    public float heart_rateProgTop;
    public int heart_rateProgType;
    public float heart_rateProgRadius;
    public float heart_rateProgThickness;
    public int heart_rateProgStartAngle;
    public int heart_rateProgEndAngle;
    public int heart_rateProgClockwise;
    public int heart_rateProgColorIndex;
    public boolean heart_rateProgBgBool;
    public int heart_rateProgBgColor;
    public String heart_rateProgBgImage;
    public String heart_rateProgSlptImage;
    // Phone Battery
    public int phone_batteryProg;
    public float phone_batteryProgLeft;
    public float phone_batteryProgTop;
    public int phone_batteryProgType;
    public float phone_batteryProgRadius;
    public float phone_batteryProgThickness;
    public int phone_batteryProgStartAngle;
    public int phone_batteryProgEndAngle;
    public int phone_batteryProgClockwise;
    public int phone_batteryProgColorIndex;
    public boolean phone_batteryProgBgBool;
    public int phone_batteryProgBgColor;
    public String phone_batteryProgBgImage;
    public String phone_batteryProgSlptImage;
    // Clock
    public boolean analog_clock;
    public boolean digital_clock;
    public boolean clock_only_slpt;
    public float scale;

    public ResourceManager.Font font;

    // Build.PRODUCT = Amazfit Verge or Amazfit Smartwatch
    public final String[] BUILD_VERGE_MODELS = {"qogir", "qogirUS"};
    public boolean isVerge(){
        return Arrays.asList(BUILD_VERGE_MODELS).contains(Build.PRODUCT);
    }

    // Default Parameters
    private void defaultParameters(){
        // Verge scale
        this.scale = (isVerge())? 1.125f : 1f; // 360/320 = 1.125
        int inverted_text_color = Color.parseColor("#000000");
        //Log.d(TAG,"Scale: "+scale);

        // All
            this.watchface = context.getResources().getString(R.string.watch_face);
            this.author = context.getResources().getString(R.string.author);
            this.language = sharedPreferences.getInt( "language", 0);
            this.color = sharedPreferences.getInt( "color", -1);
            this.white_bg = sharedPreferences.getBoolean( "white_bg", context.getResources().getBoolean(R.bool.white_bg));
            this.is_white_bg = (this.white_bg?"inv-":"");
            this.font_no = sharedPreferences.getInt( "font", 0);
            this.better_resolution_when_raising_hand = sharedPreferences.getBoolean( "better_resolution_when_raising_hand", context.getResources().getBoolean(R.bool.better_resolution_when_raising_hand));
            this.flashing_indicator = sharedPreferences.getBoolean( "flashing_indicator", context.getResources().getBoolean(R.bool.flashing_indicator));
            this.month_as_text = sharedPreferences.getBoolean( "month_as_text", context.getResources().getBoolean(R.bool.month_as_text));
            this.three_letters_month_if_text = sharedPreferences.getBoolean( "three_letters_month_if_text", context.getResources().getBoolean(R.bool.three_letters_month_if_text));
            this.three_letters_day_if_text = sharedPreferences.getBoolean( "three_letters_day_if_text", context.getResources().getBoolean(R.bool.three_letters_day_if_text));
            this.no_0_on_hour_first_digit = sharedPreferences.getBoolean( "no_0_on_hour_first_digit", context.getResources().getBoolean(R.bool.no_0_on_hour_first_digit));
            this.wind_direction_as_arrows = sharedPreferences.getBoolean( "wind_direction_as_arrows", context.getResources().getBoolean(R.bool.wind_direction_as_arrows));
            this.status_bar = sharedPreferences.getBoolean( "status_bar", context.getResources().getBoolean(R.bool.status_bar));
            this.status_barLeft = (int) scale*sharedPreferences.getInt( "status_barLeft", context.getResources().getInteger(R.integer.status_left));
            this.status_barTop = (int) scale*sharedPreferences.getInt( "status_barTop", context.getResources().getInteger(R.integer.status_top));
            this.low_power = sharedPreferences.getBoolean( "low_power", context.getResources().getBoolean(R.bool.low_power));
            this.low_powerLeft = scale*sharedPreferences.getInt( "low_powerLeft", context.getResources().getInteger(R.integer.low_power_left));
            this.low_powerTop = scale*sharedPreferences.getInt( "low_powerTop", context.getResources().getInteger(R.integer.low_power_top));
            this.flashing_heart_rate_icon = sharedPreferences.getBoolean( "flashing_heart_rate_icon", context.getResources().getBoolean(R.bool.flashing_heart_rate_icon));
            this.target_calories = sharedPreferences.getInt( "target_calories", 1000);
            this.custom_refresh_rate = sharedPreferences.getInt( "custom_refresh_rate", context.getResources().getInteger(R.integer.custom_refresh_rate)*1000);
            this.temp_heart_rate = sharedPreferences.getInt( "temp_heart_rate", 0);
            this.temp_calories = sharedPreferences.getInt( "temp_calories", 0);
            this.am_pm_always = sharedPreferences.getBoolean( "am_pm_always", context.getResources().getBoolean(R.bool.am_pm_always));
            this.world_time_zone = sharedPreferences.getFloat( "world_time_zone", -1f);
            this.pressure_to_mmhg = sharedPreferences.getBoolean( "pressure_to_mmhg", context.getResources().getBoolean(R.bool.pressure_to_mmhg));

            this.analog_clock = sharedPreferences.getBoolean( "analog_clock", context.getResources().getBoolean(R.bool.analog_clock));
            this.digital_clock = sharedPreferences.getBoolean( "digital_clock", context.getResources().getBoolean(R.bool.digital_clock));
            this.clock_only_slpt = sharedPreferences.getBoolean( "clock_only_slpt", isVerge() || context.getResources().getBoolean(R.bool.clock_only_slpt));

            this.isMetric = (Settings.Secure.getInt(context.getContentResolver(), "measurement", 0) == 0);

            // Populate color codes
            String[] colorCodes = context.getResources().getStringArray(R.array.color_codes);
            int x = 0;
            this.colorCodes = new Integer[colorCodes.length];
            for(String color : colorCodes){
                this.colorCodes[x] = Color.parseColor(color);
                x++;
            }

            // Select Font
            this.font = ResourceManager.Font.values()[font_no];
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setTextSize(100);// 100 to get the 100% value
            paint.setTypeface(ResourceManager.getTypeFace(context.getResources(), this.font));
            this.font_ratio = (int) -paint.ascent(); // ascent() is negative

        // Icon paint
            this.mGPaint = new Paint();
            mGPaint.setAntiAlias(false);
            mGPaint.setFilterBitmap(false);
            List theme_elements = Arrays.asList(context.getResources().getStringArray(R.array.theme_elements));
            String[] color_codes = context.getResources().getStringArray(R.array.color_codes);

            //Log.d(TAG, "Language: "+this.language );

        @StyleableRes int i = 0;
        // Hours
            this.hoursBool = sharedPreferences.getBoolean("hoursBool", true);
            if(this.hoursBool) {
                TypedArray hours = res.obtainTypedArray(R.array.hours);
                this.hoursFontSize = scale*sharedPreferences.getFloat("hoursFontSize", hours.getDimension(i++, 0));
                this.hoursLeft = scale*sharedPreferences.getFloat("hoursLeft", hours.getDimension(i++, 0));
                this.hoursTop = scale*sharedPreferences.getFloat("hoursTop", hours.getDimension(i++, 0));
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
                TypedArray indicator = res.obtainTypedArray(res.getIdentifier("indicator", "array", context.getPackageName()));
                i = 0;
                this.indicatorFontSize = scale*sharedPreferences.getFloat("indicatorFontSize", indicator.getDimension(i++, 0));
                this.indicatorLeft = scale*sharedPreferences.getFloat("indicatorLeft", indicator.getDimension(i++, 0));
                this.indicatorTop = scale*sharedPreferences.getFloat("indicatorTop", indicator.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("indicator")>-1){
                    this.indicatorColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.indicatorColor = sharedPreferences.getInt("indicatorColor", indicator.getColor(i++, 0));
                }
                if(this.white_bg) this.indicatorColor = inverted_text_color;
                this.indicatorAlignLeft = sharedPreferences.getBoolean("indicatorAlignLeft", indicator.getBoolean(i, true));
                indicator.recycle();
            }
        // Minutes
            this.minutesBool = sharedPreferences.getBoolean("minutesBool", true);
            if(this.minutesBool) {
                TypedArray minutes = res.obtainTypedArray(R.array.minutes);
                i = 0;
                this.minutesFontSize = scale*sharedPreferences.getFloat("minutesFontSize", minutes.getDimension(i++, 0));
                this.minutesLeft = scale*sharedPreferences.getFloat("minutesLeft", minutes.getDimension(i++, 0));
                this.minutesTop = scale*sharedPreferences.getFloat("minutesTop", minutes.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("minutes")>-1) {
                    this.minutesColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.minutesColor = sharedPreferences.getInt("minutesColor", minutes.getColor(i++, 0));
                }
                if(this.white_bg) this.minutesColor = inverted_text_color;
                this.minutesAlignLeft = sharedPreferences.getBoolean("minutesAlignLeft", minutes.getBoolean(i, true));
                minutes.recycle();
            }
        // Seconds
            this.secondsBool = sharedPreferences.getBoolean("secondsBool", res.getIdentifier("seconds", "array", context.getPackageName())!=0) && Util.needSlptRefreshSecond(context);
            if(this.secondsBool) {
                TypedArray seconds = res.obtainTypedArray(res.getIdentifier("seconds", "array", context.getPackageName()));
                i = 0;
                this.secondsFontSize = scale*sharedPreferences.getFloat("secondsFontSize", seconds.getDimension(i++, 0));
                this.secondsLeft = scale*sharedPreferences.getFloat("secondsLeft", seconds.getDimension(i++, 0));
                this.secondsTop = scale*sharedPreferences.getFloat("secondsTop", seconds.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("seconds")>-1){
                    this.secondsColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.secondsColor = sharedPreferences.getInt("secondsColor", seconds.getColor(i++, 0));
                }
                if(this.white_bg) this.secondsColor = inverted_text_color;
                this.secondsAlignLeft = sharedPreferences.getBoolean("secondsAlignLeft", seconds.getBoolean(i, false));
                seconds.recycle();
            }
        // am_pm
            /*
            String time_format = Settings.System.getString(context.getContentResolver(), "time_12_24");
            if(time_format.equals("24") && !this.am_pm_always){
                this.am_pmBool = false;//Hide on 24 if am/pm not always shown
            }else {
                this.am_pmBool = sharedPreferences.getBoolean("am_pmBool", res.getIdentifier("am_pm", "array", context.getPackageName()) != 0);
            }
            //Log.d("DinoDevs-GreatFit", "AM-PM: "+am_pmBool+", share-pref:"+this.am_pm_always+", xml:"+context.getResources().getBoolean(R.bool.am_pm_always)+", time-format:"+time_format);
            */
            //if(this.am_pmBool) {
                TypedArray am_pm = res.obtainTypedArray(res.getIdentifier("am_pm", "array", context.getPackageName()));
                i = 0;
                this.am_pmFontSize = scale*sharedPreferences.getFloat("am_pmFontSize", am_pm.getDimension(i++, 0));
                this.am_pmLeft = scale*sharedPreferences.getFloat("am_pmLeft", am_pm.getDimension(i++, 0));
                this.am_pmTop = scale*sharedPreferences.getFloat("am_pmTop", am_pm.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("am_pm")>-1){
                    this.am_pmColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.am_pmColor = sharedPreferences.getInt("am_pmColor", am_pm.getColor(i++, 0));
                }
                if(this.white_bg) this.am_pmColor = inverted_text_color;
                this.am_pmAlignLeft = sharedPreferences.getBoolean("am_pmAlignLeft", am_pm.getBoolean(i, false));
                am_pm.recycle();
            //}
        // weekday
            this.weekdayBool = sharedPreferences.getBoolean("weekdayBool", res.getIdentifier("weekday", "array", context.getPackageName())!=0);
            if(this.weekdayBool) {
                TypedArray weekday = res.obtainTypedArray(res.getIdentifier("weekday", "array", context.getPackageName()));
                i = 0;
                this.weekdayFontSize = scale*sharedPreferences.getFloat("weekdayFontSize", weekday.getDimension(i++, 0));
                this.weekdayLeft = scale*sharedPreferences.getFloat("weekdayLeft", weekday.getDimension(i++, 0));
                this.weekdayTop = scale*sharedPreferences.getFloat("weekdayTop", weekday.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("weekday")>-1){
                    this.weekdayColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.weekdayColor = sharedPreferences.getInt("weekdayColor", weekday.getColor(i++, 0));
                }
                if(this.white_bg) this.weekdayColor = inverted_text_color;
                this.weekdayAlignLeft = sharedPreferences.getBoolean("weekdayAlignLeft", weekday.getBoolean(i, false));
                weekday.recycle();
            }
        // month
            this.monthBool = sharedPreferences.getBoolean("monthBool", res.getIdentifier("month", "array", context.getPackageName())!=0);
            if(this.monthBool) {
                TypedArray month = res.obtainTypedArray(res.getIdentifier("month", "array", context.getPackageName()));
                i = 0;
                this.monthFontSize = scale*sharedPreferences.getFloat("monthFontSize", month.getDimension(i++, 0));
                this.monthLeft = scale*sharedPreferences.getFloat("monthLeft", month.getDimension(i++, 0));
                this.monthTop = scale*sharedPreferences.getFloat("monthTop", month.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("month")>-1){
                    this.monthColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.monthColor = sharedPreferences.getInt("monthColor", month.getColor(i++, 0));
                }
                if(this.white_bg) this.monthColor = inverted_text_color;
                this.monthAlignLeft = sharedPreferences.getBoolean("monthAlignLeft", month.getBoolean(i, false));
                month.recycle();
            }
        // day
            this.dayBool = sharedPreferences.getBoolean("dayBool", res.getIdentifier("day", "array", context.getPackageName())!=0);
            if(this.dayBool) {
                TypedArray day = res.obtainTypedArray(res.getIdentifier("day", "array", context.getPackageName()));
                i = 0;
                this.dayFontSize = scale*sharedPreferences.getFloat("dayFontSize", day.getDimension(i++, 0));
                this.dayLeft = scale*sharedPreferences.getFloat("dayLeft", day.getDimension(i++, 0));
                this.dayTop = scale*sharedPreferences.getFloat("dayTop", day.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("day")>-1){
                    this.dayColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.dayColor = sharedPreferences.getInt("dayColor", day.getColor(i++, 0));
                }
                if(this.white_bg) this.dayColor = inverted_text_color;
                this.dayAlignLeft = sharedPreferences.getBoolean("dayAlignLeft", day.getBoolean(i, false));
                day.recycle();
            }
        // Year
            this.yearBool = sharedPreferences.getBoolean("yearBool", res.getIdentifier("year", "array", context.getPackageName())!=0);
            if(this.yearBool) {
                TypedArray year = res.obtainTypedArray(res.getIdentifier("year", "array", context.getPackageName()));
                i = 0;
                this.yearFontSize = scale*sharedPreferences.getFloat("yearFontSize", year.getDimension(i++, 0));
                this.yearLeft = scale*sharedPreferences.getFloat("yearLeft", year.getDimension(i++, 0));
                this.yearTop = scale*sharedPreferences.getFloat("yearTop", year.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("year")>-1){
                    this.yearColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.yearColor = sharedPreferences.getInt("yearColor", year.getColor(i++, 0));
                }
                if(this.white_bg) this.yearColor = inverted_text_color;
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
        //Log.d(TAG, "Widgets: "+ widgets_list.toString());


        // Date
            this.date = sharedPreferences.getInt("date", widgets_list.indexOf("date")+1);
            if(this.date>0){
                TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("widget"+this.date, "array", context.getPackageName()));
                i = 0;
                this.dateFontSize  = scale*sharedPreferences.getFloat("dateFontSize", widgetN.getDimension(i++, 0));
                this.dateLeft  = scale*sharedPreferences.getFloat("dateLeft", widgetN.getDimension(i++, 0));
                this.dateTop  = scale*sharedPreferences.getFloat("dateTop", widgetN.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("widget"+this.date)>-1){
                    this.dateColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.dateColor = sharedPreferences.getInt("dateColor", widgetN.getColor(i++, 0));
                }
                if(this.white_bg) this.dateColor = inverted_text_color;
                this.dateAlignLeft = sharedPreferences.getBoolean("dateAlignLeft", widgetN.getBoolean(i++, false));
                this.dateUnits = sharedPreferences.getBoolean("dateUnits", widgetN.getBoolean(i++, true));
                this.dateIcon = sharedPreferences.getBoolean("dateIcon", widgetN.getBoolean(i++, true));
                if(dateIcon) {
                    this.dateIconLeft = scale*sharedPreferences.getFloat("dateIconLeft", widgetN.getDimension(i++, 0));
                    this.dateIconTop = scale*sharedPreferences.getFloat("dateIconTop", widgetN.getDimension(i, 0));
                }
                widgetN.recycle();
            }

        // Calories
            this.calories = sharedPreferences.getInt("calories", widgets_list.indexOf("calories")+1);
            if(this.calories>0){
                TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("widget"+this.calories, "array", context.getPackageName()));
                i = 0;
                this.caloriesFontSize  = scale*sharedPreferences.getFloat("caloriesFontSize", widgetN.getDimension(i++, 0));
                this.caloriesLeft  = scale*sharedPreferences.getFloat("caloriesLeft", widgetN.getDimension(i++, 0));
                this.caloriesTop  = scale*sharedPreferences.getFloat("caloriesTop", widgetN.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("widget"+this.calories)>-1){
                    this.caloriesColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.caloriesColor = sharedPreferences.getInt("caloriesColor", widgetN.getColor(i++, 0));
                }
                if(this.white_bg) this.caloriesColor = inverted_text_color;
                this.caloriesAlignLeft = sharedPreferences.getBoolean("caloriesAlignLeft", widgetN.getBoolean(i++, false));
                this.caloriesUnits = sharedPreferences.getBoolean("caloriesUnits", widgetN.getBoolean(i++, true));
                this.caloriesIcon = sharedPreferences.getBoolean("caloriesIcon", widgetN.getBoolean(i++, true));
                if(caloriesIcon){
                    this.caloriesIconLeft = scale*sharedPreferences.getFloat("caloriesIconLeft", widgetN.getDimension(i++, 0));
                    this.caloriesIconTop = scale*sharedPreferences.getFloat("caloriesIconTop", widgetN.getDimension(i, 0));
                }
                widgetN.recycle();
            }

        // steps
            this.steps = sharedPreferences.getInt("steps", widgets_list.indexOf("steps")+1);
            if(this.steps>0){
                TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("widget"+this.steps, "array", context.getPackageName()));
                i = 0;
                this.stepsFontSize  = scale*sharedPreferences.getFloat("stepsFontSize", widgetN.getDimension(i++, 0));
                this.stepsLeft  = scale*sharedPreferences.getFloat("stepsLeft", widgetN.getDimension(i++, 0));
                this.stepsTop  = scale*sharedPreferences.getFloat("stepsTop", widgetN.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("widget"+this.steps)>-1){
                    this.stepsColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.stepsColor = sharedPreferences.getInt("stepsColor", widgetN.getColor(i++, 0));
                }
                if(this.white_bg) this.stepsColor = inverted_text_color;
                this.stepsAlignLeft = sharedPreferences.getBoolean("stepsAlignLeft", widgetN.getBoolean(i++, false));
                this.stepsUnits = sharedPreferences.getBoolean("stepsUnits", widgetN.getBoolean(i++, true));
                this.stepsIcon = sharedPreferences.getBoolean("stepsIcon", widgetN.getBoolean(i++, true));
                if(stepsIcon){
                    this.stepsIconLeft = scale*sharedPreferences.getFloat("stepsIconLeft", widgetN.getDimension(i++, 0));
                    this.stepsIconTop = scale*sharedPreferences.getFloat("stepsIconTop", widgetN.getDimension(i, 0));
                }
                widgetN.recycle();
            }

        // heart_rate
            this.heart_rate = sharedPreferences.getInt("heart_rate", widgets_list.indexOf("heart_rate")+1);
            if(this.heart_rate>0){
                TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("widget"+this.heart_rate, "array", context.getPackageName()));
                i = 0;
                this.heart_rateFontSize  = scale*sharedPreferences.getFloat("heart_rateFontSize", widgetN.getDimension(i++, 0));
                this.heart_rateLeft  = scale*sharedPreferences.getFloat("heart_rateLeft", widgetN.getDimension(i++, 0));
                this.heart_rateTop  = scale*sharedPreferences.getFloat("heart_rateTop", widgetN.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("widget"+this.heart_rate)>-1){
                    this.heart_rateColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.heart_rateColor = sharedPreferences.getInt("heart_rateColor", widgetN.getColor(i++, 0));
                }
                if(this.white_bg) this.heart_rateColor = inverted_text_color;
                this.heart_rateAlignLeft = sharedPreferences.getBoolean("heart_rateAlignLeft", widgetN.getBoolean(i++, false));
                this.heart_rateUnits = sharedPreferences.getBoolean("heart_rateUnits", widgetN.getBoolean(i++, true));
                this.heart_rateIcon = sharedPreferences.getBoolean("heart_rateIcon", widgetN.getBoolean(i++, true));
                if(heart_rateIcon) {
                    this.heart_rateIconLeft = scale*sharedPreferences.getFloat("heart_rateIconLeft", widgetN.getDimension(i++, 0));
                    this.heart_rateIconTop = scale*sharedPreferences.getFloat("heart_rateIconTop", widgetN.getDimension(i, 0));
                }
                widgetN.recycle();
            }

        // total_distance
            this.total_distance = sharedPreferences.getInt("total_distance", widgets_list.indexOf("total_distance")+1);
            if(this.total_distance>0){
                TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("widget"+this.total_distance, "array", context.getPackageName()));
                i = 0;
                this.total_distanceFontSize  = scale*sharedPreferences.getFloat("total_distanceFontSize", widgetN.getDimension(i++, 0));
                this.total_distanceLeft  = scale*sharedPreferences.getFloat("total_distanceLeft", widgetN.getDimension(i++, 0));
                this.total_distanceTop  = scale*sharedPreferences.getFloat("total_distanceTop", widgetN.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("widget"+this.total_distance)>-1){
                    this.total_distanceColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.total_distanceColor = sharedPreferences.getInt("total_distanceColor", widgetN.getColor(i++, 0));
                }
                if(this.white_bg) this.total_distanceColor = inverted_text_color;
                this.total_distanceAlignLeft = sharedPreferences.getBoolean("total_distanceAlignLeft", widgetN.getBoolean(i++, false));
                this.total_distanceUnits = sharedPreferences.getBoolean("total_distanceUnits", widgetN.getBoolean(i++, true));
                this.total_distanceIcon = sharedPreferences.getBoolean("total_distanceIcon", widgetN.getBoolean(i++, true));
                if(total_distanceIcon) {
                    this.total_distanceIconLeft = scale*sharedPreferences.getFloat("total_distanceIconLeft", widgetN.getDimension(i++, 0));
                    this.total_distanceIconTop = scale*sharedPreferences.getFloat("total_distanceIconTop", widgetN.getDimension(i, 0));
                }
                widgetN.recycle();
            }

        // today_distance
            this.today_distance = sharedPreferences.getInt("today_distance", widgets_list.indexOf("today_distance")+1);
            if(this.today_distance>0){
                TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("widget"+this.today_distance, "array", context.getPackageName()));
                i = 0;
                this.today_distanceFontSize  = scale*sharedPreferences.getFloat("today_distanceFontSize", widgetN.getDimension(i++, 0));
                this.today_distanceLeft  = scale*sharedPreferences.getFloat("today_distanceLeft", widgetN.getDimension(i++, 0));
                this.today_distanceTop  = scale*sharedPreferences.getFloat("today_distanceTop", widgetN.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("widget"+this.today_distance)>-1){
                    this.today_distanceColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.today_distanceColor = sharedPreferences.getInt("today_distanceColor", widgetN.getColor(i++, 0));
                }
                if(this.white_bg) this.today_distanceColor = inverted_text_color;
                this.today_distanceAlignLeft = sharedPreferences.getBoolean("today_distanceAlignLeft", widgetN.getBoolean(i++, false));
                this.today_distanceUnits = sharedPreferences.getBoolean("today_distanceUnits", widgetN.getBoolean(i++, true));
                this.today_distanceIcon = sharedPreferences.getBoolean("today_distanceIcon", widgetN.getBoolean(i++, true));
                if(today_distanceIcon) {
                    this.today_distanceIconLeft  = scale*sharedPreferences.getFloat("today_distanceIconLeft", widgetN.getDimension(i++, 0));
                    this.today_distanceIconTop  = scale*sharedPreferences.getFloat("today_distanceIconTop", widgetN.getDimension(i, 0));
                }
                widgetN.recycle();
            }

        // walked_distance
            this.step_length = Math.round((float) sharedPreferences.getInt("height", 175)*0.414f);
            this.walked_distance = sharedPreferences.getInt("walked_distance", widgets_list.indexOf("walked_distance")+1);
            if(this.walked_distance>0){
                TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("widget"+this.walked_distance, "array", context.getPackageName()));
                i = 0;
                this.walked_distanceFontSize  = scale*sharedPreferences.getFloat("walked_distanceFontSize", widgetN.getDimension(i++, 0));
                this.walked_distanceLeft  = scale*sharedPreferences.getFloat("walked_distanceLeft", widgetN.getDimension(i++, 0));
                this.walked_distanceTop  = scale*sharedPreferences.getFloat("walked_distanceTop", widgetN.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("widget"+this.walked_distance)>-1){
                    this.walked_distanceColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.walked_distanceColor = sharedPreferences.getInt("walked_distanceColor", widgetN.getColor(i++, 0));
                }
                if(this.white_bg) this.walked_distanceColor = inverted_text_color;
                this.walked_distanceAlignLeft = sharedPreferences.getBoolean("walked_distanceAlignLeft", widgetN.getBoolean(i++, false));
                this.walked_distanceUnits = sharedPreferences.getBoolean("walked_distanceUnits", widgetN.getBoolean(i++, true));
                this.walked_distanceIcon = sharedPreferences.getBoolean("walked_distanceIcon", widgetN.getBoolean(i++, true));
                if(walked_distanceIcon) {
                    this.walked_distanceIconLeft  = scale*sharedPreferences.getFloat("walked_distanceIconLeft", widgetN.getDimension(i++, 0));
                    this.walked_distanceIconTop  = scale*sharedPreferences.getFloat("walked_distanceIconTop", widgetN.getDimension(i, 0));
                }
                widgetN.recycle();
            }

        // floors
            this.floors = sharedPreferences.getInt("floors", widgets_list.indexOf("floors")+1);
            if(this.floors>0){
                TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("widget"+this.floors, "array", context.getPackageName()));
                i = 0;
                this.floorsFontSize  = scale*sharedPreferences.getFloat("floorsFontSize", widgetN.getDimension(i++, 0));
                this.floorsLeft  = scale*sharedPreferences.getFloat("floorsLeft", widgetN.getDimension(i++, 0));
                this.floorsTop  = scale*sharedPreferences.getFloat("floorsTop", widgetN.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("widget"+this.floors)>-1){
                    this.floorsColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.floorsColor = sharedPreferences.getInt("floorsColor", widgetN.getColor(i++, 0));
                }
                if(this.white_bg) this.floorsColor = inverted_text_color;
                this.floorsAlignLeft = sharedPreferences.getBoolean("floorsAlignLeft", widgetN.getBoolean(i++, false));
                this.floorsUnits = sharedPreferences.getBoolean("floorsUnits", widgetN.getBoolean(i++, true));
                this.floorsIcon = sharedPreferences.getBoolean("floorsIcon", widgetN.getBoolean(i++, true));
                if(floorsIcon) {
                    this.floorsIconLeft = scale*sharedPreferences.getFloat("floorsIconLeft", widgetN.getDimension(i++, 0));
                    this.floorsIconTop = scale*sharedPreferences.getFloat("floorsIconTop", widgetN.getDimension(i, 0));
                }
                widgetN.recycle();
            }

        // battery_percent
            this.battery_percent = sharedPreferences.getInt("battery_percent", widgets_list.indexOf("battery_percent")+1);
            if(this.battery_percent>0){
                TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("widget"+this.battery_percent, "array", context.getPackageName()));
                i = 0;
                this.battery_percentFontSize  = scale*sharedPreferences.getFloat("battery_percentFontSize", widgetN.getDimension(i++, 0));
                this.battery_percentLeft  = scale*sharedPreferences.getFloat("battery_percentLeft", widgetN.getDimension(i++, 0));
                this.battery_percentTop  = scale*sharedPreferences.getFloat("battery_percentTop", widgetN.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("widget"+this.battery_percent)>-1){
                    this.battery_percentColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.battery_percentColor = sharedPreferences.getInt("battery_percentColor", widgetN.getColor(i++, 0));
                }
                if(this.white_bg) this.battery_percentColor = inverted_text_color;
                this.battery_percentAlignLeft = sharedPreferences.getBoolean("battery_percentAlignLeft", widgetN.getBoolean(i++, false));
                this.battery_percentUnits = sharedPreferences.getBoolean("battery_percentUnits", widgetN.getBoolean(i++, true));
                this.battery_percentIcon = sharedPreferences.getBoolean("battery_percentIcon", widgetN.getBoolean(i++, true));
                if(battery_percentIcon) {
                    this.battery_percentIconLeft = scale*sharedPreferences.getFloat("battery_percentIconLeft", widgetN.getDimension(i++, 0));
                    this.battery_percentIconTop = scale*sharedPreferences.getFloat("battery_percentIconTop", widgetN.getDimension(i, 0));
                }
                widgetN.recycle();
            }

        // temperature
            this.temperature = sharedPreferences.getInt("temperature", widgets_list.indexOf("temperature")+1);
            if(this.temperature>0){
                TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("widget"+this.temperature, "array", context.getPackageName()));
                i = 0;
                this.temperatureFontSize  = scale*sharedPreferences.getFloat("temperatureFontSize", widgetN.getDimension(i++, 0));
                this.temperatureLeft  = scale*sharedPreferences.getFloat("temperatureLeft", widgetN.getDimension(i++, 0));
                this.temperatureTop  = scale*sharedPreferences.getFloat("temperatureTop", widgetN.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("widget"+this.temperature)>-1){
                    this.temperatureColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.temperatureColor = sharedPreferences.getInt("temperatureColor", widgetN.getColor(i++, 0));
                }
                if(this.white_bg) this.temperatureColor = inverted_text_color;
                this.temperatureAlignLeft = sharedPreferences.getBoolean("temperatureAlignLeft", widgetN.getBoolean(i++, false));
                this.temperatureUnits = sharedPreferences.getBoolean("temperatureUnits", widgetN.getBoolean(i++, true));
                this.temperatureIcon = sharedPreferences.getBoolean("temperatureIcon", widgetN.getBoolean(i++, true));
                if(temperatureIcon) {
                    this.temperatureIconLeft = scale*sharedPreferences.getFloat("temperatureIconLeft", widgetN.getDimension(i++, 0));
                    this.temperatureIconTop = scale*sharedPreferences.getFloat("temperatureIconTop", widgetN.getDimension(i, 0));
                }
                widgetN.recycle();
            }

        // city
            this.city = sharedPreferences.getInt("city", widgets_list.indexOf("city")+1);
            if(this.city>0){
                TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("widget"+this.city, "array", context.getPackageName()));
                i = 0;
                this.cityFontSize  = scale*sharedPreferences.getFloat("cityFontSize", widgetN.getDimension(i++, 0));
                this.cityLeft  = scale*sharedPreferences.getFloat("cityLeft", widgetN.getDimension(i++, 0));
                this.cityTop  = scale*sharedPreferences.getFloat("cityTop", widgetN.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("widget"+this.city)>-1){
                    this.cityColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.cityColor = sharedPreferences.getInt("cityColor", widgetN.getColor(i++, 0));
                }
                if(this.white_bg) this.cityColor = inverted_text_color;
                this.cityAlignLeft = sharedPreferences.getBoolean("cityAlignLeft", widgetN.getBoolean(i++, false));
                this.cityUnits = sharedPreferences.getBoolean("cityUnits", widgetN.getBoolean(i++, false));
                this.cityIcon = sharedPreferences.getBoolean("cityIcon", widgetN.getBoolean(i++, true));
                if(cityIcon) {
                    this.cityIconLeft = scale*sharedPreferences.getFloat("cityIconLeft", widgetN.getDimension(i++, 0));
                    this.cityIconTop = scale*sharedPreferences.getFloat("cityIconTop", widgetN.getDimension(i, 0));
                }
                widgetN.recycle();
            }

        // watch_alarm
            this.watch_alarm = sharedPreferences.getInt("watch_alarm", widgets_list.indexOf("watch_alarm")+1);
            if(this.watch_alarm>0){
                TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("widget"+this.watch_alarm, "array", context.getPackageName()));
                i = 0;
                this.watch_alarmFontSize  = scale*sharedPreferences.getFloat("watch_alarmFontSize", widgetN.getDimension(i++, 0));
                this.watch_alarmLeft  = scale*sharedPreferences.getFloat("watch_alarmLeft", widgetN.getDimension(i++, 0));
                this.watch_alarmTop  = scale*sharedPreferences.getFloat("watch_alarmTop", widgetN.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("widget"+this.watch_alarm)>-1){
                    this.watch_alarmColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.watch_alarmColor = sharedPreferences.getInt("watch_alarmColor", widgetN.getColor(i++, 0));
                }
                if(this.white_bg) this.watch_alarmColor = inverted_text_color;
                this.watch_alarmAlignLeft = sharedPreferences.getBoolean("watch_alarmAlignLeft", widgetN.getBoolean(i++, false));
                this.watch_alarmUnits = sharedPreferences.getBoolean("watch_alarmUnits", widgetN.getBoolean(i++, true));
                this.watch_alarmIcon = sharedPreferences.getBoolean("watch_alarmIcon", widgetN.getBoolean(i++, true));
                if(watch_alarmIcon) {
                    this.watch_alarmIconLeft = scale*sharedPreferences.getFloat("watch_alarmIconLeft", widgetN.getDimension(i++, 0));
                    this.watch_alarmIconTop = scale*sharedPreferences.getFloat("watch_alarmIconTop", widgetN.getDimension(i, 0));
                }
                widgetN.recycle();
            }

        // humidity
            this.humidity = sharedPreferences.getInt("humidity", widgets_list.indexOf("humidity")+1);
            if(this.humidity>0){
                TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("widget"+this.humidity, "array", context.getPackageName()));
                i = 0;
                this.humidityFontSize  = scale*sharedPreferences.getFloat("humidityFontSize", widgetN.getDimension(i++, 0));
                this.humidityLeft  = scale*sharedPreferences.getFloat("humidityLeft", widgetN.getDimension(i++, 0));
                this.humidityTop  = scale*sharedPreferences.getFloat("humidityTop", widgetN.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("widget"+this.humidity)>-1){
                    this.humidityColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.humidityColor = sharedPreferences.getInt("humidityColor", widgetN.getColor(i++, 0));
                }
                if(this.white_bg) this.humidityColor = inverted_text_color;
                this.humidityAlignLeft = sharedPreferences.getBoolean("humidityAlignLeft", widgetN.getBoolean(i++, false));
                this.humidityUnits = sharedPreferences.getBoolean("humidityUnits", widgetN.getBoolean(i++, true));
                this.humidityIcon = sharedPreferences.getBoolean("humidityIcon", widgetN.getBoolean(i++, true));
                if(humidityIcon) {
                    this.humidityIconLeft = scale*sharedPreferences.getFloat("humidityIconLeft", widgetN.getDimension(i++, 0));
                    this.humidityIconTop = scale*sharedPreferences.getFloat("humidityIconTop", widgetN.getDimension(i, 0));
                }
                widgetN.recycle();
            }

        // uv
            this.uv = sharedPreferences.getInt("uv", widgets_list.indexOf("uv")+1);
            if(this.uv>0){
                TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("widget"+this.uv, "array", context.getPackageName()));
                i = 0;
                this.uvFontSize  = scale*sharedPreferences.getFloat("uvFontSize", widgetN.getDimension(i++, 0));
                this.uvLeft  = scale*sharedPreferences.getFloat("uvLeft", widgetN.getDimension(i++, 0));
                this.uvTop  = scale*sharedPreferences.getFloat("uvTop", widgetN.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("widget"+this.uv)>-1){
                    this.uvColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.uvColor = sharedPreferences.getInt("uvColor", widgetN.getColor(i++, 0));
                }
                if(this.white_bg) this.uvColor = inverted_text_color;
                this.uvAlignLeft = sharedPreferences.getBoolean("uvAlignLeft", widgetN.getBoolean(i++, false));
                this.uvUnits = sharedPreferences.getBoolean("uvUnits", widgetN.getBoolean(i++, true));
                this.uvIcon = sharedPreferences.getBoolean("uvIcon", widgetN.getBoolean(i++, true));
                if(uvIcon) {
                    this.uvIconLeft = scale*sharedPreferences.getFloat("uvIconLeft", widgetN.getDimension(i++, 0));
                    this.uvIconTop = scale*sharedPreferences.getFloat("uvIconTop", widgetN.getDimension(i, 0));
                }
                widgetN.recycle();
            }

        // wind_direction
            this.wind_direction = sharedPreferences.getInt("wind_direction", widgets_list.indexOf("wind_direction")+1);
            if(this.wind_direction>0){
                TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("widget"+this.wind_direction, "array", context.getPackageName()));
                i = 0;
                this.wind_directionFontSize  = scale*sharedPreferences.getFloat("wind_directionFontSize", widgetN.getDimension(i++, 0));
                this.wind_directionLeft  = scale*sharedPreferences.getFloat("wind_directionLeft", widgetN.getDimension(i++, 0));
                this.wind_directionTop  = scale*sharedPreferences.getFloat("wind_directionTop", widgetN.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("widget"+this.wind_direction)>-1){
                    this.wind_directionColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.wind_directionColor = sharedPreferences.getInt("wind_directionColor", widgetN.getColor(i++, 0));
                }
                if(this.white_bg) this.wind_directionColor = inverted_text_color;
                this.wind_directionAlignLeft = sharedPreferences.getBoolean("wind_directionAlignLeft", widgetN.getBoolean(i++, false));
                this.wind_directionUnits = sharedPreferences.getBoolean("wind_directionUnits", widgetN.getBoolean(i++, true));
                this.wind_directionIcon = sharedPreferences.getBoolean("wind_directionIcon", widgetN.getBoolean(i++, true));
                if(wind_directionIcon) {
                    this.wind_directionIconLeft = scale*sharedPreferences.getFloat("wind_directionIconLeft", widgetN.getDimension(i++, 0));
                    this.wind_directionIconTop = scale*sharedPreferences.getFloat("wind_directionIconTop", widgetN.getDimension(i, 0));
                }
                widgetN.recycle();
            }

        // wind_strength
            this.wind_strength = sharedPreferences.getInt("wind_strength", widgets_list.indexOf("wind_strength")+1);
            if(this.wind_strength>0){
                TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("widget"+this.wind_strength, "array", context.getPackageName()));
                i = 0;
                this.wind_strengthFontSize  = scale*sharedPreferences.getFloat("wind_strengthFontSize", widgetN.getDimension(i++, 0));
                this.wind_strengthLeft  = scale*sharedPreferences.getFloat("wind_strengthLeft", widgetN.getDimension(i++, 0));
                this.wind_strengthTop  = scale*sharedPreferences.getFloat("wind_strengthTop", widgetN.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("widget"+this.wind_strength)>-1){
                    this.wind_strengthColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.wind_strengthColor = sharedPreferences.getInt("wind_strengthColor", widgetN.getColor(i++, 0));
                }
                if(this.white_bg) this.wind_strengthColor = inverted_text_color;
                this.wind_strengthAlignLeft = sharedPreferences.getBoolean("wind_strengthAlignLeft", widgetN.getBoolean(i++, false));
                this.wind_strengthUnits = sharedPreferences.getBoolean("wind_strengthUnits", widgetN.getBoolean(i++, true));
                this.wind_strengthIcon = sharedPreferences.getBoolean("wind_strengthIcon", widgetN.getBoolean(i++, true));
                if(wind_strengthIcon) {
                    this.wind_strengthIconLeft = scale*sharedPreferences.getFloat("wind_strengthIconLeft", widgetN.getDimension(i++, 0));
                    this.wind_strengthIconTop = scale*sharedPreferences.getFloat("wind_strengthIconTop", widgetN.getDimension(i, 0));
                }
                widgetN.recycle();
            }

        // min_max_temperatures
        this.min_max_temperatures = sharedPreferences.getInt("min_max_temperatures", widgets_list.indexOf("min_max_temperatures")+1);
        if(this.min_max_temperatures>0){
            TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("widget"+this.min_max_temperatures, "array", context.getPackageName()));
            i = 0;
            this.min_max_temperaturesFontSize  = scale*sharedPreferences.getFloat("min_max_temperaturesFontSize", widgetN.getDimension(i++, 0));
            this.min_max_temperaturesLeft  = scale*sharedPreferences.getFloat("min_max_temperaturesLeft", widgetN.getDimension(i++, 0));
            this.min_max_temperaturesTop  = scale*sharedPreferences.getFloat("min_max_temperaturesTop", widgetN.getDimension(i++, 0));
            if(this.color>-1 && theme_elements.indexOf("widget"+this.min_max_temperatures)>-1){
                this.min_max_temperaturesColor = Color.parseColor(color_codes[this.color]);
                i++;
            }else{
                this.min_max_temperaturesColor = sharedPreferences.getInt("min_max_temperaturesColor", widgetN.getColor(i++, 0));
            }
            if(this.white_bg) this.min_max_temperaturesColor = inverted_text_color;
            this.min_max_temperaturesAlignLeft = sharedPreferences.getBoolean("min_max_temperaturesAlignLeft", widgetN.getBoolean(i++, false));
            this.min_max_temperaturesUnits = sharedPreferences.getBoolean("min_max_temperaturesUnits", widgetN.getBoolean(i++, true));
            this.min_max_temperaturesIcon = sharedPreferences.getBoolean("min_max_temperaturesIcon", widgetN.getBoolean(i++, true));
            if(min_max_temperaturesIcon) {
                this.min_max_temperaturesIconLeft = scale*sharedPreferences.getFloat("min_max_temperaturesIconLeft", widgetN.getDimension(i++, 0));
                this.min_max_temperaturesIconTop = scale*sharedPreferences.getFloat("min_max_temperaturesIconTop", widgetN.getDimension(i, 0));
            }
            widgetN.recycle();
        }

        // air_pressure
            this.air_pressure = sharedPreferences.getInt("air_pressure", widgets_list.indexOf("air_pressure")+1);
            if(this.air_pressure>0){
                TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("widget"+this.air_pressure, "array", context.getPackageName()));
                i = 0;
                this.air_pressureFontSize  = scale*sharedPreferences.getFloat("air_pressureFontSize", widgetN.getDimension(i++, 0));
                this.air_pressureLeft  = scale*sharedPreferences.getFloat("air_pressureLeft", widgetN.getDimension(i++, 0));
                this.air_pressureTop  = scale*sharedPreferences.getFloat("air_pressureTop", widgetN.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("widget"+this.air_pressure)>-1){
                    this.air_pressureColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.air_pressureColor = sharedPreferences.getInt("air_pressureColor", widgetN.getColor(i++, 0));
                }
                if(this.white_bg) this.air_pressureColor = inverted_text_color;
                this.air_pressureAlignLeft = sharedPreferences.getBoolean("air_pressureAlignLeft", widgetN.getBoolean(i++, false));
                this.air_pressureUnits = sharedPreferences.getBoolean("air_pressureUnits", widgetN.getBoolean(i++, true));
                this.air_pressureIcon = sharedPreferences.getBoolean("air_pressureIcon", widgetN.getBoolean(i++, true));
                if(air_pressureIcon) {
                    this.air_pressureIconLeft = scale*sharedPreferences.getFloat("air_pressureIconLeft", widgetN.getDimension(i++, 0));
                    this.air_pressureIconTop = scale*sharedPreferences.getFloat("air_pressureIconTop", widgetN.getDimension(i, 0));
                }
                widgetN.recycle();
            }

        // altitude
            this.altitude = sharedPreferences.getInt("altitude", widgets_list.indexOf("altitude")+1);
            if(this.altitude>0){
                TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("widget"+this.altitude, "array", context.getPackageName()));
                i = 0;
                this.altitudeFontSize  = scale*sharedPreferences.getFloat("altitudeFontSize", widgetN.getDimension(i++, 0));
                this.altitudeLeft  = scale*sharedPreferences.getFloat("altitudeLeft", widgetN.getDimension(i++, 0));
                this.altitudeTop  = scale*sharedPreferences.getFloat("altitudeTop", widgetN.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("widget"+this.altitude)>-1){
                    this.altitudeColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.altitudeColor = sharedPreferences.getInt("altitudeColor", widgetN.getColor(i++, 0));
                }
                if(this.white_bg) this.altitudeColor = inverted_text_color;
                this.altitudeAlignLeft = sharedPreferences.getBoolean("altitudeAlignLeft", widgetN.getBoolean(i++, false));
                this.altitudeUnits = sharedPreferences.getBoolean("altitudeUnits", widgetN.getBoolean(i++, true));
                this.altitudeIcon = sharedPreferences.getBoolean("altitudeIcon", widgetN.getBoolean(i++, true));
                if(altitudeIcon) {
                    this.altitudeIconLeft = scale*sharedPreferences.getFloat("altitude", widgetN.getDimension(i++, 0));
                    this.altitudeIconTop = scale*sharedPreferences.getFloat("altitudeIconTop", widgetN.getDimension(i, 0));
                }
                widgetN.recycle();
            }

        // phone_battery
            this.phone_battery = sharedPreferences.getInt("phone_battery", widgets_list.indexOf("phone_battery")+1);
            if(this.phone_battery>0){
                TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("widget"+this.phone_battery, "array", context.getPackageName()));
                i = 0;
                this.phone_batteryFontSize  = scale*sharedPreferences.getFloat("phone_batteryFontSize", widgetN.getDimension(i++, 0));
                this.phone_batteryLeft  = scale*sharedPreferences.getFloat("phone_batteryLeft", widgetN.getDimension(i++, 0));
                this.phone_batteryTop  = scale*sharedPreferences.getFloat("phone_batteryTop", widgetN.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("widget"+this.phone_battery)>-1){
                    this.phone_batteryColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.phone_batteryColor = sharedPreferences.getInt("phone_batteryColor", widgetN.getColor(i++, 0));
                }
                if(this.white_bg) this.phone_batteryColor = inverted_text_color;
                this.phone_batteryAlignLeft = sharedPreferences.getBoolean("phone_batteryAlignLeft", widgetN.getBoolean(i++, false));
                this.phone_batteryUnits = sharedPreferences.getBoolean("phone_batteryUnits", widgetN.getBoolean(i++, true));
                this.phone_batteryIcon = sharedPreferences.getBoolean("phone_batteryIcon", widgetN.getBoolean(i++, true));
                if(phone_batteryIcon) {
                    this.phone_batteryIconLeft = scale*sharedPreferences.getFloat("phone_batteryIconLeft", widgetN.getDimension(i++, 0));
                    this.phone_batteryIconTop = scale*sharedPreferences.getFloat("phone_batteryIconTop", widgetN.getDimension(i, 0));
                }
                widgetN.recycle();
            }

        // phone_alarm
            this.phone_alarm = sharedPreferences.getInt("phone_alarm", widgets_list.indexOf("phone_alarm")+1);
            if(this.phone_alarm>0){
                TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("widget"+this.phone_alarm, "array", context.getPackageName()));
                i = 0;
                this.phone_alarmFontSize  = scale*sharedPreferences.getFloat("phone_alarmFontSize", widgetN.getDimension(i++, 0));
                this.phone_alarmLeft  = scale*sharedPreferences.getFloat("phone_alarmLeft", widgetN.getDimension(i++, 0));
                this.phone_alarmTop  = scale*sharedPreferences.getFloat("phone_alarmTop", widgetN.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("widget"+this.phone_alarm)>-1){
                    this.phone_alarmColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.phone_alarmColor = sharedPreferences.getInt("phone_alarmColor", widgetN.getColor(i++, 0));
                }
                if(this.white_bg) this.phone_alarmColor = inverted_text_color;
                this.phone_alarmAlignLeft = sharedPreferences.getBoolean("phone_alarmAlignLeft", widgetN.getBoolean(i++, false));
                this.phone_alarmUnits = sharedPreferences.getBoolean("phone_alarmUnits", widgetN.getBoolean(i++, true));
                this.phone_alarmIcon = sharedPreferences.getBoolean("phone_alarmIcon", widgetN.getBoolean(i++, true));
                if(phone_alarmIcon) {
                    this.phone_alarmIconLeft = scale*sharedPreferences.getFloat("phone_alarmIconLeft", widgetN.getDimension(i++, 0));
                    this.phone_alarmIconTop = scale*sharedPreferences.getFloat("phone_alarmIconTop", widgetN.getDimension(i, 0));
                }
                widgetN.recycle();
            }

        // xdrip
            this.xdrip = sharedPreferences.getInt("xdrip", widgets_list.indexOf("xdrip")+1);
            if(this.xdrip>0){
                TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("widget"+this.xdrip, "array", context.getPackageName()));
                i = 0;
                this.xdripFontSize  = scale*sharedPreferences.getFloat("xdripFontSize", widgetN.getDimension(i++, 0));
                this.xdripLeft  = scale*sharedPreferences.getFloat("xdripLeft", widgetN.getDimension(i++, 0));
                this.xdripTop  = scale*sharedPreferences.getFloat("xdripTop", widgetN.getDimension(i++, 0));
                if(this.color>-1 && theme_elements.indexOf("widget"+this.xdrip)>-1){
                    this.xdripColor = Color.parseColor(color_codes[this.color]);
                    i++;
                }else{
                    this.xdripColor = sharedPreferences.getInt("xdripColor", widgetN.getColor(i++, 0));
                }
                if(this.white_bg) this.xdripColor = inverted_text_color;
                this.xdripAlignLeft = sharedPreferences.getBoolean("xdripAlignLeft", widgetN.getBoolean(i++, false));
                this.xdripUnits = sharedPreferences.getBoolean("xdripUnits", widgetN.getBoolean(i++, true));
                this.xdripIcon = sharedPreferences.getBoolean("xdripIcon", widgetN.getBoolean(i++, true));
                if(xdripIcon) {
                    this.xdripIconLeft = scale*sharedPreferences.getFloat("xdripIconLeft", widgetN.getDimension(i++, 0));
                    this.xdripIconTop = scale*sharedPreferences.getFloat("xdripIconTop", widgetN.getDimension(i, 0));
                }
                widgetN.recycle();
            }

        // weather_img
        this.weather_img = sharedPreferences.getInt("weather_img", widgets_list.indexOf("weather_img")+1);
        if(this.weather_img>0){
            TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("widget"+this.weather_img, "array", context.getPackageName()));
            i = 0;
            this.weather_imgFontSize  = scale*sharedPreferences.getFloat("weather_imgFontSize", widgetN.getDimension(i++, 0));
            this.weather_imgLeft  = scale*sharedPreferences.getFloat("weather_imgLeft", widgetN.getDimension(i++, 0));
            this.weather_imgTop  = scale*sharedPreferences.getFloat("weather_imgTop", widgetN.getDimension(i++, 0));
            if(this.color>-1 && theme_elements.indexOf("widget"+this.weather_img)>-1){
                this.weather_imgColor = Color.parseColor(color_codes[this.color]);
                i++;
            }else{
                this.weather_imgColor = sharedPreferences.getInt("weather_imgColor", widgetN.getColor(i++, 0));
            }
            if(this.white_bg) this.weather_imgColor = inverted_text_color;
            this.weather_imgAlignLeft = sharedPreferences.getBoolean("weather_imgAlignLeft", widgetN.getBoolean(i++, false));
            this.weather_imgUnits = sharedPreferences.getBoolean("weather_imgUnits", widgetN.getBoolean(i++, true));
            this.weather_imgIcon = sharedPreferences.getBoolean("weather_imgIcon", widgetN.getBoolean(i++, true));
            if(weather_imgIcon) {
                this.weather_imgIconLeft = scale*sharedPreferences.getFloat("weather_imgIconLeft", widgetN.getDimension(i++, 0));
                this.weather_imgIconTop = scale*sharedPreferences.getFloat("weather_imgIconTop", widgetN.getDimension(i, 0));
            }
            widgetN.recycle();
        }

        // world_time
        this.world_time = sharedPreferences.getInt("world_time", widgets_list.indexOf("world_time")+1);
        if(this.world_time>0){
            TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("widget"+this.world_time, "array", context.getPackageName()));
            i = 0;
            this.world_timeFontSize  = scale*sharedPreferences.getFloat("world_timeFontSize", widgetN.getDimension(i++, 0));
            this.world_timeLeft  = scale*sharedPreferences.getFloat("world_timeLeft", widgetN.getDimension(i++, 0));
            this.world_timeTop  = scale*sharedPreferences.getFloat("world_timeTop", widgetN.getDimension(i++, 0));
            if(this.color>-1 && theme_elements.indexOf("widget"+this.world_time)>-1){
                this.world_timeColor = Color.parseColor(color_codes[this.color]);
                i++;
            }else{
                this.world_timeColor = sharedPreferences.getInt("world_timeColor", widgetN.getColor(i++, 0));
            }
            if(this.white_bg) this.world_timeColor = inverted_text_color;
            this.world_timeAlignLeft = sharedPreferences.getBoolean("world_timeAlignLeft", widgetN.getBoolean(i++, false));
            this.world_timeUnits = sharedPreferences.getBoolean("world_timeUnits", widgetN.getBoolean(i++, true));
            this.world_timeIcon = sharedPreferences.getBoolean("world_timeIcon", widgetN.getBoolean(i++, true));
            if(world_timeIcon) {
                this.world_timeIconLeft = scale*sharedPreferences.getFloat("world_timeIconLeft", widgetN.getDimension(i++, 0));
                this.world_timeIconTop = scale*sharedPreferences.getFloat("world_timeIconTop", widgetN.getDimension(i, 0));
            }
            widgetN.recycle();
        }

        // moonphase
        this.moonphase = sharedPreferences.getInt("moonphase", widgets_list.indexOf("moonphase")+1);
        if(this.moonphase>0){
            TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("widget"+this.moonphase, "array", context.getPackageName()));
            i = 0;
            this.moonphaseFontSize  = scale*sharedPreferences.getFloat("moonphaseFontSize", widgetN.getDimension(i++, 0));
            this.moonphaseLeft  = scale*sharedPreferences.getFloat("moonphaseLeft", widgetN.getDimension(i++, 0));
            this.moonphaseTop  = scale*sharedPreferences.getFloat("moonphaseTop", widgetN.getDimension(i++, 0));
            if(this.color>-1 && theme_elements.indexOf("widget"+this.moonphase)>-1){
                this.moonphaseColor = Color.parseColor(color_codes[this.color]);
                i++;
            }else{
                this.moonphaseColor = sharedPreferences.getInt("moonphaseColor", widgetN.getColor(i++, 0));
            }
            if(this.white_bg) this.moonphaseColor = inverted_text_color;
            this.moonphaseAlignLeft = sharedPreferences.getBoolean("moonphaseAlignLeft", widgetN.getBoolean(i++, false));
            sharedPreferences.getBoolean("moonphaseUnits", widgetN.getBoolean(i++, true));/*dummy*/
            this.moonphaseIcon = sharedPreferences.getBoolean("moonphaseIcon", widgetN.getBoolean(i++, true));

            if(moonphaseIcon) {
                this.moonphaseIconLeft = scale*sharedPreferences.getFloat("moonphaseIconLeft", widgetN.getDimension(i++, 0));
                this.moonphaseIconTop = scale*sharedPreferences.getFloat("moonphaseIconTop", widgetN.getDimension(i, 0));
            }

            widgetN.recycle();
        }

        // notifications
        this.notifications = sharedPreferences.getInt("notifications", widgets_list.indexOf("notifications")+1);
        if(this.notifications>0){
            TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("widget"+this.notifications, "array", context.getPackageName()));
            i = 0;
            this.notificationsFontSize  = scale*sharedPreferences.getFloat("notificationsFontSize", widgetN.getDimension(i++, 0));
            this.notificationsLeft  = scale*sharedPreferences.getFloat("notificationsLeft", widgetN.getDimension(i++, 0));
            this.notificationsTop  = scale*sharedPreferences.getFloat("notificationsTop", widgetN.getDimension(i++, 0));
            if(this.color>-1 && theme_elements.indexOf("widget"+this.notifications)>-1){
                this.notificationsColor = Color.parseColor(color_codes[this.color]);
                i++;
            }else{
                this.notificationsColor = sharedPreferences.getInt("notificationsColor", widgetN.getColor(i++, 0));
            }
            if(this.white_bg) this.notificationsColor = inverted_text_color;
            this.notificationsAlignLeft = sharedPreferences.getBoolean("notificationsAlignLeft", widgetN.getBoolean(i++, false));
            this.notificationsUnits = sharedPreferences.getBoolean("notificationsUnits", widgetN.getBoolean(i++, true));
            this.notificationsIcon = sharedPreferences.getBoolean("notificationsIcon", widgetN.getBoolean(i++, true));
            if(notificationsIcon) {
                this.notificationsIconLeft = scale*sharedPreferences.getFloat("notificationsIconLeft", widgetN.getDimension(i++, 0));
                this.notificationsIconTop = scale*sharedPreferences.getFloat("notificationsIconTop", widgetN.getDimension(i, 0));
            }
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

        //Log.d(TAG, "Bars: "+ circle_bars_list.toString());

        // StepsProg
        this.stepsProg = sharedPreferences.getInt("stepsProg", circle_bars_list.indexOf("steps")+1);
        if(this.stepsProg>0){
            TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("progress_element"+this.stepsProg, "array", context.getPackageName()));
            i = 0;
            this.stepsProgLeft  = scale*sharedPreferences.getFloat("stepsProgLeft", widgetN.getDimension(i++, 0));
            this.stepsProgTop  = scale*sharedPreferences.getFloat("stepsProgTop", widgetN.getDimension(i++, 0));
            this.stepsProgType = sharedPreferences.getInt("stepsProgType", widgetN.getColor(i++, 0));

            if(this.stepsProgType==0){ // Circle bar element
                this.stepsProgRadius  = scale*sharedPreferences.getFloat("stepsProgRadius", widgetN.getDimension(i++, 0));
                this.stepsProgThickness  = scale*sharedPreferences.getFloat("stepsProgRadius", widgetN.getDimension(i++, 0));
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
            this.today_distanceProgLeft  = scale*sharedPreferences.getFloat("today_distanceProgLeft", widgetN.getDimension(i++, 0));
            this.today_distanceProgTop  = scale*sharedPreferences.getFloat("today_distanceProgTop", widgetN.getDimension(i++, 0));
            this.today_distanceProgType = sharedPreferences.getInt("today_distanceProgType", widgetN.getColor(i++, 0));
            if(this.today_distanceProgType==0){ // Circle bar element
                this.today_distanceProgRadius  = scale*sharedPreferences.getFloat("today_distanceProgRadius", widgetN.getDimension(i++, 0));
                this.today_distanceProgThickness  = scale*sharedPreferences.getFloat("today_distanceProgThickness", widgetN.getDimension(i++, 0));
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
            this.batteryProgLeft  = scale*sharedPreferences.getFloat("batteryProgLeft", widgetN.getDimension(i++, 0));
            this.batteryProgTop  = scale*sharedPreferences.getFloat("batteryProgTop", widgetN.getDimension(i++, 0));
            this.batteryProgType = sharedPreferences.getInt("batteryProgType", widgetN.getColor(i++, 0));

            if(this.batteryProgType==0){ // Circle bar element
                this.batteryProgRadius  = scale*sharedPreferences.getFloat("batteryProgRadius", widgetN.getDimension(i++, 0));
                this.batteryProgThickness  = scale*sharedPreferences.getFloat("batteryProgThickness", widgetN.getDimension(i++, 0));
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

        // caloriesProg
        this.caloriesProg = sharedPreferences.getInt("caloriesProg", circle_bars_list.indexOf("calories")+1);
        if(this.caloriesProg>0){
            TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("progress_element"+this.caloriesProg, "array", context.getPackageName()));
            i = 0;
            this.caloriesProgLeft  = scale*sharedPreferences.getFloat("caloriesProgLeft", widgetN.getDimension(i++, 0));
            this.caloriesProgTop  = scale*sharedPreferences.getFloat("caloriesProgTop", widgetN.getDimension(i++, 0));
            this.caloriesProgType = sharedPreferences.getInt("caloriesProgType", widgetN.getColor(i++, 0));

            if(this.caloriesProgType==0){ // Circle bar element
                this.caloriesProgRadius  = scale*sharedPreferences.getFloat("caloriesProgRadius", widgetN.getDimension(i++, 0));
                this.caloriesProgThickness  = scale*sharedPreferences.getFloat("caloriesProgThickness", widgetN.getDimension(i++, 0));
                this.caloriesProgStartAngle = sharedPreferences.getInt("caloriesProgStartAngle", widgetN.getInteger(i++, 0));
                this.caloriesProgEndAngle = sharedPreferences.getInt("caloriesProgEndAngle", widgetN.getInteger(i++, 0));
                this.caloriesProgClockwise = sharedPreferences.getInt("caloriesProgClockwise", widgetN.getInteger(i++, 1));
                if(this.color>-1 && theme_elements.indexOf("bar_element"+this.caloriesProg)>-1){
                    this.caloriesProgColorIndex = this.color;
                    i++;
                }else{
                    this.caloriesProgColorIndex = sharedPreferences.getInt("caloriesProgColorIndex", widgetN.getInteger(i++, 0));
                }
                this.caloriesProgBgBool = sharedPreferences.getBoolean("caloriesProgBgBool", widgetN.getBoolean(i++, false));
                this.caloriesProgBgColor = sharedPreferences.getInt("caloriesProgBgColor", widgetN.getInteger(i++, 0));
                this.caloriesProgBgImage = sharedPreferences.getString("caloriesProgBgImage", widgetN.getString(i++));
                this.caloriesProgSlptImage = sharedPreferences.getString("caloriesProgSlptImage", widgetN.getString(i+this.caloriesProgColorIndex));
            }else{ // Progression with images
                //this.caloriesProgSlptImages = res.obtainTypedArray(res.getIdentifier("param_progress_element_slpt"+this.caloriesProg, "array", context.getPackageName()));
            }
            widgetN.recycle();
        }

        // heart_rateProg
        this.heart_rateProg = sharedPreferences.getInt("heart_rateProg", circle_bars_list.indexOf("heart_rate")+1);
        if(this.heart_rateProg>0){
            TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("progress_element"+this.heart_rateProg, "array", context.getPackageName()));
            i = 0;
            this.heart_rateProgLeft  = scale*sharedPreferences.getFloat("heart_rateProgLeft", widgetN.getDimension(i++, 0));
            this.heart_rateProgTop  = scale*sharedPreferences.getFloat("heart_rateProgTop", widgetN.getDimension(i++, 0));
            this.heart_rateProgType = sharedPreferences.getInt("heart_rateProgType", widgetN.getColor(i++, 0));

            if(this.heart_rateProgType==0){ // Circle bar element
                this.heart_rateProgRadius  = scale*sharedPreferences.getFloat("heart_rateProgRadius", widgetN.getDimension(i++, 0));
                this.heart_rateProgThickness  = scale*sharedPreferences.getFloat("heart_rateProgThickness", widgetN.getDimension(i++, 0));
                this.heart_rateProgStartAngle = sharedPreferences.getInt("heart_rateProgStartAngle", widgetN.getInteger(i++, 0));
                this.heart_rateProgEndAngle = sharedPreferences.getInt("heart_rateProgEndAngle", widgetN.getInteger(i++, 0));
                this.heart_rateProgClockwise = sharedPreferences.getInt("heart_rateProgClockwise", widgetN.getInteger(i++, 1));
                if(this.color>-1 && theme_elements.indexOf("bar_element"+this.heart_rateProg)>-1){
                    this.heart_rateProgColorIndex = this.color;
                    i++;
                }else{
                    this.heart_rateProgColorIndex = sharedPreferences.getInt("heart_rateProgColorIndex", widgetN.getInteger(i++, 0));
                }
                this.heart_rateProgBgBool = sharedPreferences.getBoolean("heart_rateProgBgBool", widgetN.getBoolean(i++, false));
                this.heart_rateProgBgColor = sharedPreferences.getInt("heart_rateProgBgColor", widgetN.getInteger(i++, 0));
                this.heart_rateProgBgImage = sharedPreferences.getString("heart_rateProgBgImage", widgetN.getString(i++));
                this.heart_rateProgSlptImage = sharedPreferences.getString("heart_rateProgSlptImage", widgetN.getString(i+this.heart_rateProgColorIndex));
            }else{ // Progression with images
                //this.heart_rateProgSlptImages = res.obtainTypedArray(res.getIdentifier("param_progress_element_slpt"+this.heart_rateProg, "array", context.getPackageName()));
            }
            widgetN.recycle();
        }

        // phone_batteryProg
        this.phone_batteryProg = sharedPreferences.getInt("phone_batteryProg", circle_bars_list.indexOf("phone_battery")+1);
        if(this.phone_batteryProg>0){
            TypedArray widgetN = res.obtainTypedArray(res.getIdentifier("progress_element"+this.phone_batteryProg, "array", context.getPackageName()));
            i = 0;
            this.phone_batteryProgLeft  = scale*sharedPreferences.getFloat("phone_batteryProgLeft", widgetN.getDimension(i++, 0));
            this.phone_batteryProgTop  = scale*sharedPreferences.getFloat("phone_batteryProgTop", widgetN.getDimension(i++, 0));
            this.phone_batteryProgType = sharedPreferences.getInt("phone_batteryProgType", widgetN.getColor(i++, 0));

            if(this.phone_batteryProgType==0){ // Circle bar element
                this.phone_batteryProgRadius  = scale*sharedPreferences.getFloat("phone_batteryProgRadius", widgetN.getDimension(i++, 0));
                this.phone_batteryProgThickness  = scale*sharedPreferences.getFloat("phone_batteryProgThickness", widgetN.getDimension(i++, 0));
                this.phone_batteryProgStartAngle = sharedPreferences.getInt("phone_batteryProgStartAngle", widgetN.getInteger(i++, 0));
                this.phone_batteryProgEndAngle = sharedPreferences.getInt("phone_batteryProgEndAngle", widgetN.getInteger(i++, 0));
                this.phone_batteryProgClockwise = sharedPreferences.getInt("phone_batteryProgClockwise", widgetN.getInteger(i++, 1));
                if(this.color>-1 && theme_elements.indexOf("bar_element"+this.phone_batteryProg)>-1){
                    this.phone_batteryProgColorIndex = this.color;
                    i++;
                }else{
                    this.phone_batteryProgColorIndex = sharedPreferences.getInt("phone_batteryProgColorIndex", widgetN.getInteger(i++, 0));
                }
                this.phone_batteryProgBgBool = sharedPreferences.getBoolean("phone_batteryProgBgBool", widgetN.getBoolean(i++, false));
                this.phone_batteryProgBgColor = sharedPreferences.getInt("phone_batteryProgBgColor", widgetN.getInteger(i++, 0));
                this.phone_batteryProgBgImage = sharedPreferences.getString("phone_batteryProgBgImage", widgetN.getString(i++));
                this.phone_batteryProgSlptImage = sharedPreferences.getString("phone_batteryProgSlptImage", widgetN.getString(i+this.phone_batteryProgColorIndex));
            }else{ // Progression with images
                //this.phone_batteryProgSlptImages = res.obtainTypedArray(res.getIdentifier("param_progress_element_slpt"+this.phone_batteryProg, "array", context.getPackageName()));
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

    // SPORT'S TOTAL DISTANCE WIDGET
    public boolean isTotalDistanceRate(){
        return this.total_distance>0;
    }

    // HEART RATE WIDGET
    public boolean isHeartRate(){
        return this.heart_rate>0 || this.heart_rateProg>0;
    }

    // CALORIES WIDGET
    public boolean isCalories(){
        return this.calories>0 || this.caloriesProg>0;
    }

    // FLOOR WIDGET
    public boolean isFloor(){
        return this.floors>0;
    }

    // MOONPHASE WIDGET
    public boolean isMoonPhase(){
        return this.moonphase>0;
    }

    // BATTERY WIDGET
    public boolean isBattery(){
        return this.battery_percent>0 || this.batteryProg>0;
    }

    // WEATHER WIDGET
    public boolean isWeather(){
        return this.temperature>0 || this.city>0 || this.humidity>0 || this.uv>0 || this.wind_direction>0 || this.wind_strength>0 || this.min_max_temperatures>0 || this.weather_img>0;
    }

    // GREAT WIDGET
    public boolean isGreat(){
        return this.am_pm_always || watch_alarm>0 || xdrip>0 || air_pressure>0 || altitude>0 || phone_battery>0 || phone_alarm>0 || notifications>0 || phone_batteryProg>0 || world_time>0 || walked_distance>0;
    }
}