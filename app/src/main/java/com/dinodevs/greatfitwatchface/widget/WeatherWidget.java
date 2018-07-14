package com.dinodevs.greatfitwatchface.widget;

import android.app.Service;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.text.TextPaint;
import android.util.Log;

import com.ingenic.iwds.slpt.view.core.SlptLinearLayout;
import com.ingenic.iwds.slpt.view.core.SlptPictureView;
import com.ingenic.iwds.slpt.view.core.SlptViewComponent;
import com.ingenic.iwds.slpt.view.utils.SimpleFile;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.dinodevs.greatfitwatchface.data.DataType;
import com.dinodevs.greatfitwatchface.data.WeatherData;
import com.dinodevs.greatfitwatchface.resource.ResourceManager;
import com.dinodevs.greatfitwatchface.R;


public class WeatherWidget extends AbstractWidget {
    private WeatherData weather;
    private Service mService;

    private List<Drawable> weatherImageList;
    private Drawable weatherImage;
    private boolean weatherBool;
    private boolean showUnits;
    private boolean temperatureBool;
    private TextPaint textPaint;
    private boolean temperatureAlignLeftBool;
    private boolean cityBool;
    private TextPaint cityPaint;
    private boolean humidityBool;
    private TextPaint humidityPaint;
    private boolean uvBool;
    private TextPaint uvPaint;
    private boolean windDirectionBool;
    private boolean windDirectionAsArrowBool;
    private TextPaint windDirectionPaint;
    private boolean windStrengthBool;
    private TextPaint windStrengthPaint;

    // Positions
    private float cityTop;
    private float cityLeft;
    private float humidityTop;
    private float humidityLeft;
    private float uvTop;
    private float uvLeft;
    private float windDirectionTop;
    private float windDirectionLeft;
    private float windStrengthTop;
    private float windStrengthLeft;
    private float textTop; // temperature
    private float textLeft; // temperature
    private float imgTop; // weather img
    private float imgLeft; // weather img

    @Override
    public void init(Service service) {
        this.mService = service;

        this.textLeft = service.getResources().getDimension(R.dimen.temperature_left);
        this.textTop = service.getResources().getDimension(R.dimen.temperature_top);

        this.imgLeft = service.getResources().getDimension(R.dimen.weather_img_left);
        this.imgTop = service.getResources().getDimension(R.dimen.weather_img_top);

        // Align left true or false (false= align center)
        this.temperatureAlignLeftBool = service.getResources().getBoolean(R.bool.temperature_left_align);

        // Temperature
        this.temperatureBool = service.getResources().getBoolean(R.bool.temperature);
        if(this.temperatureBool) {
            this.textPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
            this.textPaint.setColor(service.getResources().getColor(R.color.temperature_colour));
            this.textPaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE));
            this.textPaint.setTextSize(service.getResources().getDimension(R.dimen.temperature_font_size));
            this.textPaint.setTextAlign((this.temperatureAlignLeftBool) ? Paint.Align.LEFT : Paint.Align.CENTER);
        }

        // City
        this.cityBool = service.getResources().getBoolean(R.bool.city);
        if(this.cityBool) {
            this.cityPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
            this.cityPaint.setColor(service.getResources().getColor(R.color.city_colour));
            this.cityPaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE));
            this.cityPaint.setTextSize(service.getResources().getDimension(R.dimen.city_font_size));
            this.cityPaint.setTextAlign((service.getResources().getBoolean(R.bool.city_left_align)) ? Paint.Align.LEFT : Paint.Align.CENTER);
            this.cityLeft = service.getResources().getDimension(R.dimen.city_left);
            this.cityTop = service.getResources().getDimension(R.dimen.city_top);
        }

        // Humidity
        this.humidityBool = service.getResources().getBoolean(R.bool.humidity);
        if(this.humidityBool) {
            this.humidityPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
            this.humidityPaint.setColor(service.getResources().getColor(R.color.humidity_colour));
            this.humidityPaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE));
            this.humidityPaint.setTextSize(service.getResources().getDimension(R.dimen.humidity_font_size));
            this.humidityPaint.setTextAlign((service.getResources().getBoolean(R.bool.humidity_left_align)) ? Paint.Align.LEFT : Paint.Align.CENTER);
            this.humidityLeft = service.getResources().getDimension(R.dimen.humidity_left);
            this.humidityTop = service.getResources().getDimension(R.dimen.humidity_top);
        }

        // UV
        this.uvBool = service.getResources().getBoolean(R.bool.uv);
        if(this.uvBool) {
            this.uvPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
            this.uvPaint.setColor(service.getResources().getColor(R.color.uv_colour));
            this.uvPaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE));
            this.uvPaint.setTextSize(service.getResources().getDimension(R.dimen.uv_font_size));
            this.uvPaint.setTextAlign((service.getResources().getBoolean(R.bool.uv_left_align)) ? Paint.Align.LEFT : Paint.Align.CENTER);
            this.uvLeft = service.getResources().getDimension(R.dimen.uv_left);
            this.uvTop = service.getResources().getDimension(R.dimen.uv_top);
        }

        // Wind Direction
        this.windDirectionBool = service.getResources().getBoolean(R.bool.wind_direction);
        if(this.windDirectionBool) {
            this.windDirectionPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
            this.windDirectionPaint.setColor(service.getResources().getColor(R.color.wind_direction_colour));
            this.windDirectionPaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE));
            this.windDirectionPaint.setTextSize(service.getResources().getDimension(R.dimen.wind_direction_font_size));
            this.windDirectionPaint.setTextAlign((service.getResources().getBoolean(R.bool.wind_direction_left_align)) ? Paint.Align.LEFT : Paint.Align.CENTER);
            this.windDirectionLeft = service.getResources().getDimension(R.dimen.wind_direction_left);
            this.windDirectionTop = service.getResources().getDimension(R.dimen.wind_direction_top);
            this.windDirectionAsArrowBool = service.getResources().getBoolean(R.bool.wind_direction_as_arrows);
        }

        // Wind Strength
        this.windStrengthBool = service.getResources().getBoolean(R.bool.wind_strength);
        if(this.windStrengthBool) {
            this.windStrengthPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
            this.windStrengthPaint.setColor(service.getResources().getColor(R.color.wind_strength_colour));
            this.windStrengthPaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE));
            this.windStrengthPaint.setTextSize(service.getResources().getDimension(R.dimen.wind_strength_font_size));
            this.windStrengthPaint.setTextAlign((service.getResources().getBoolean(R.bool.wind_strength_left_align)) ? Paint.Align.LEFT : Paint.Align.CENTER);
            this.windStrengthLeft = service.getResources().getDimension(R.dimen.wind_strength_left);
            this.windStrengthTop = service.getResources().getDimension(R.dimen.wind_strength_top);
        }

        this.weatherBool = service.getResources().getBoolean(R.bool.weather_image);
        // Show units boolean
        this.showUnits = service.getResources().getBoolean(R.bool.temperature_units);

        // Load weather icons
        weatherImageList = new ArrayList<Drawable>();
        weatherImageList.add(service.getResources().getDrawable(R.drawable.clock_skin_weather_sunny)); //0
        weatherImageList.add(service.getResources().getDrawable(R.drawable.clock_skin_weather_cloudy)); //1
        weatherImageList.add(service.getResources().getDrawable(R.drawable.clock_skin_weather_overcast)); //2
        weatherImageList.add(service.getResources().getDrawable(R.drawable.clock_skin_weather_fog)); //3
        weatherImageList.add(service.getResources().getDrawable(R.drawable.clock_skin_weather_fog)); //4
        weatherImageList.add(service.getResources().getDrawable(R.drawable.clock_skin_weather_showers)); //5
        weatherImageList.add(service.getResources().getDrawable(R.drawable.clock_skin_weather_t_storm)); //6
        weatherImageList.add(service.getResources().getDrawable(R.drawable.clock_skin_weather_rain)); //7
        weatherImageList.add(service.getResources().getDrawable(R.drawable.clock_skin_weather_rain)); //8
        weatherImageList.add(service.getResources().getDrawable(R.drawable.clock_skin_weather_rainstorm)); //9
        weatherImageList.add(service.getResources().getDrawable(R.drawable.clock_skin_weather_rainstorm)); //10
        weatherImageList.add(service.getResources().getDrawable(R.drawable.clock_skin_weather_showers)); //11
        weatherImageList.add(service.getResources().getDrawable(R.drawable.clock_skin_weather_rainsnow)); //12
        weatherImageList.add(service.getResources().getDrawable(R.drawable.clock_skin_weather_rainsnow)); //13
        weatherImageList.add(service.getResources().getDrawable(R.drawable.clock_skin_weather_rainsnow)); //14
        weatherImageList.add(service.getResources().getDrawable(R.drawable.clock_skin_weather_snow)); //15
        weatherImageList.add(service.getResources().getDrawable(R.drawable.clock_skin_weather_snow)); //16
        weatherImageList.add(service.getResources().getDrawable(R.drawable.clock_skin_weather_snow)); //17
        weatherImageList.add(service.getResources().getDrawable(R.drawable.clock_skin_weather_snow)); //18
        weatherImageList.add(service.getResources().getDrawable(R.drawable.clock_skin_weather_fog)); //19
        weatherImageList.add(service.getResources().getDrawable(R.drawable.clock_skin_weather_fog)); //20
        weatherImageList.add(service.getResources().getDrawable(R.drawable.clock_skin_weather_fog)); //21
        weatherImageList.add(service.getResources().getDrawable(R.drawable.clock_skin_weather_unknow)); //22
    }

    @Override
    public List<DataType> getDataTypes() {
        return Collections.singletonList(DataType.WEATHER);
        //return Arrays.asList(DataType.BATTERY, DataType.STEPS, DataType.DISTANCE, DataType.TOTAL_DISTANCE, DataType.TIME,  DataType.CALORIES,  DataType.DATE,  DataType.HEART_RATE,  DataType.FLOOR, DataType.WEATHER);
    }

    @Override
    public void onDataUpdate(DataType type, Object value) {
        //this.weather = (WeatherData) value;
        // Value = weather info [tempFlag:1, tempString:29, weatherType:0

        // Get ALL weather data
        this.weather = getSlptWeather();

        //Log.w("DinoDevs-GreatFit", "Data Update: "+type.toString()+" => "+value.toString() );
    }

    @Override
    public void draw(Canvas canvas, float width, float height, float centerX, float centerY) {
        // Draw Temperature
        if(this.temperatureBool) {
            String units = (showUnits) ? weather.getUnits() : ""; //"ºC"
            canvas.drawText(weather.getTemperature() + units, textLeft, textTop, textPaint);
        }

        // Draw City
        if(this.cityBool) {
            canvas.drawText(weather.city, cityLeft, cityTop, cityPaint);
        }

        // Draw Humidity
        if(this.humidityBool) {
            canvas.drawText(weather.humidity, humidityLeft, humidityTop, humidityPaint);
        }

        // Draw UV ray
        if(this.uvBool) {
            canvas.drawText(weather.uv, uvLeft, uvTop, uvPaint);
        }

        // Draw Wind Direction
        if(this.windDirectionBool) {
            canvas.drawText( ((windDirectionAsArrowBool)? this.weather.windArrow : this.weather.windDirection), windDirectionLeft, windDirectionTop, windDirectionPaint);
        }

        // Draw Wind Strength
        if(this.windStrengthBool) {
            canvas.drawText(weather.windStrength, windStrengthLeft, windStrengthTop, windStrengthPaint);
        }

        // Draw Weather icon
        if(this.weatherBool) {
            if (this.weather.weatherType > 22 || this.weather.weatherType < 0) {
                this.weather.weatherType = 22;
            }
            this.weatherImage = weatherImageList.get(this.weather.weatherType);
            this.weatherImage.setBounds((int) this.imgLeft, (int) this.imgTop, ((int) this.imgLeft) + this.weatherImage.getIntrinsicWidth(), ((int) this.imgTop) + this.weatherImage.getIntrinsicHeight());
            this.weatherImage.draw(canvas);
        }
    }

    // Get Weather Data on screen off
    // based on HuamiWatchFaces.jar!\com\huami\watch\watchface\widget\slpt\SlptWeatherWidget.class
    // and AmazfitWeather.jar!\com\huami\watch\weather\WeatherUtil.class
    public WeatherData getSlptWeather() {
        // Default variables
        String tempUnit = "1";
        String temp = "n/a";
        int weatherType = 22;
        String city = "n/a";
        String humidity = "n/a";
        String uv = "n/a";
        String windDirection = "n/a";
        String windStrength = "n/a";

        // Get ALL data from system
        String str = Settings.System.getString(this.mService.getApplicationContext().getContentResolver(), "WeatherInfo");
        //Log.w("DinoDevs-GreatFit", str);

        // WeatherInfo
        // {"isAlert":true, "isNotification":true,
        // "tempFormatted":"28ºC",
        // "tempUnit":"C",
        // "v":1,
        // "weatherCode":0,
        // "aqi":-1,
        // "aqiLevel":0,
        // "city":"Somewhere",
        // "forecasts":[{"tempFormatted":"31ºC/21ºC","tempMax":31,"tempMin":21,"weatherCodeFrom":0,"weatherCodeTo":0,"day":1,"weatherFrom":0,"weatherTo":0},{"tempFormatted":"33ºC/23ºC","tempMax":33,"tempMin":23,"weatherCodeFrom":0,"weatherCodeTo":0,"day":2,"weatherFrom":0,"weatherTo":0},{"tempFormatted":"34ºC/24ºC","tempMax":34,"tempMin":24,"weatherCodeFrom":0,"weatherCodeTo":0,"day":3,"weatherFrom":0,"weatherTo":0},{"tempFormatted":"34ºC/23ºC","tempMax":34,"tempMin":23,"weatherCodeFrom":0,"weatherCodeTo":0,"day":4,"weatherFrom":0,"weatherTo":0},{"tempFormatted":"32ºC/22ºC","tempMax":32,"tempMin":22,"weatherCodeFrom":0,"weatherCodeTo":0,"day":5,"weatherFrom":0,"weatherTo":0}],
        // "pm25":-1,
        // "sd":"50%", //(Humidity)
        // "temp":28,
        // "time":1531292274457,
        // "uv":"Strong",
        // "weather":0,
        // "windDirection":"NW",
        // "windStrength":"7.4km/h"}

        // WeatherCheckedSummary
        // {"tempUnit":"1","temp":"31\/21","weatherCodeFrom":0}

        // Extract data from JSON
        JSONObject weather_data = new JSONObject();
        try {
            weather_data = new JSONObject(str);
            tempUnit = weather_data.getString("tempUnit");
            temp = weather_data.getString("temp");
            //weatherType = weather_data.getInt("weatherCodeFrom");
            weatherType = weather_data.getInt("weatherCode");
            city = weather_data.getString("city");
            humidity = weather_data.getString("sd");
            uv = weather_data.getString("uv");
            windDirection = weather_data.getString("windDirection");
            windStrength = weather_data.getString("windStrength");
        }
        catch (JSONException e) {
          // Nothing
        }

        // Unknown weather
        if(weatherType<0 || weatherType>22){
            return new WeatherData("1", "n/a", 22);
        }

        return new WeatherData(tempUnit, temp, weatherType, city, humidity, uv, windDirection, windStrength);
    }

    @Override
    public List<SlptViewComponent> buildSlptViewComponent(Service service) {
        // Variables
        this.mService = service;
        Typeface font = ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE);

        Log.w("DinoDevs-GreatFit", "Sltp refresh" );

        // Get weather data
        this.weather = getSlptWeather();

        // Just to be safe :P
        if(this.weather.weatherType<0 || this.weather.weatherType>22){
            this.weather.weatherType = 22;
        }

        // Load weather icons
        List<String> weatherImageStrList = new ArrayList<String>();
        weatherImageStrList.add("sunny"); //0
        weatherImageStrList.add("cloudy"); //1
        weatherImageStrList.add("overcast"); //2
        weatherImageStrList.add("fog"); //3
        weatherImageStrList.add("fog"); //4
        weatherImageStrList.add("showers"); //5
        weatherImageStrList.add("t_storm"); //6
        weatherImageStrList.add("rain"); //7
        weatherImageStrList.add("rain"); //8
        weatherImageStrList.add("rainstorm"); //9
        weatherImageStrList.add("rainstorm"); //10
        weatherImageStrList.add("showers"); //11
        weatherImageStrList.add("rainsnow"); //12
        weatherImageStrList.add("rainsnow"); //13
        weatherImageStrList.add("rainsnow"); //14
        weatherImageStrList.add("snow"); //15
        weatherImageStrList.add("snow"); //16
        weatherImageStrList.add("snow"); //17
        weatherImageStrList.add("snow"); //18
        weatherImageStrList.add("fog"); //19
        weatherImageStrList.add("fog"); //20
        weatherImageStrList.add("fog"); //21
        weatherImageStrList.add("unknow"); //22

        // Draw temperature
        SlptLinearLayout temperatureLayout = new SlptLinearLayout();
        // Show or Not icon
        if(service.getResources().getBoolean(R.bool.temperature_icon)) {
            SlptPictureView temperatureIcon = new SlptPictureView();
            temperatureIcon.setStringPicture( (char)Integer.parseInt("F2CB", 16) );
            temperatureIcon.setTextAttr(
                    service.getResources().getDimension(R.dimen.temperature_font_size),
                    service.getResources().getColor(R.color.temperature_colour_slpt),
                    ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.ICONS_FONT)
            );
            temperatureLayout.add(temperatureIcon);
        }
        // Show temperature with units or not
        SlptPictureView temperatureNum = new SlptPictureView();
        temperatureNum.setStringPicture( this.weather.tempString + ((service.getResources().getBoolean(R.bool.temperature_units))?this.weather.getUnits():"") );
        temperatureNum.setTextAttr(
                service.getResources().getDimension(R.dimen.temperature_font_size),
                service.getResources().getColor(R.color.temperature_colour_slpt),
                font
        );
        temperatureLayout.add(temperatureNum);

        // Position based on screen on
        temperatureLayout.alignX = 2;
        temperatureLayout.alignY = 0;
        int tmp_left = (int) service.getResources().getDimension(R.dimen.temperature_left);
        if(!service.getResources().getBoolean(R.bool.temperature_left_align)) {
            // If text is centered, set rectangle
            temperatureLayout.setRect(
                (int) (2 * tmp_left + 640),
                (int) (service.getResources().getDimension(R.dimen.temperature_font_size))
            );
            tmp_left = -320;
        }
        temperatureLayout.setStart(
            tmp_left,
            (int) (service.getResources().getDimension(R.dimen.temperature_top)-((float)service.getResources().getInteger(R.integer.font_ratio)/100)*service.getResources().getDimension(R.dimen.temperature_font_size))
        );
        if(!service.getResources().getBoolean(R.bool.temperature)){temperatureLayout.show=false;}

        // Draw weather icon
        SlptPictureView weatherLayout = new SlptPictureView();
        weatherLayout.setImagePicture( SimpleFile.readFileFromAssets(service, String.format("slpt_weather/clock_skin_weather_%s.png", weatherImageStrList.get(this.weather.weatherType))) );
        weatherLayout.setStart(
            (int) service.getResources().getDimension(R.dimen.weather_img_left),
            (int) service.getResources().getDimension(R.dimen.weather_img_top)
        );
        if(!service.getResources().getBoolean(R.bool.weather_image)){weatherLayout.show=false;}

        // Draw City
        SlptLinearLayout cityLayout = new SlptLinearLayout();
        SlptPictureView cityText = new SlptPictureView();
        cityText.setStringPicture( this.weather.city );
        cityLayout.add(cityText);
        cityLayout.setTextAttrForAll(
            service.getResources().getDimension(R.dimen.city_font_size),
            service.getResources().getColor(R.color.city_colour_slpt),
            font
        );
        // Position based on screen on
        cityLayout.alignX = 2;
        cityLayout.alignY = 0;
        tmp_left = (int) service.getResources().getDimension(R.dimen.city_left);
        if(!service.getResources().getBoolean(R.bool.city_left_align)) {
            // If text is centered, set rectangle
            cityLayout.setRect(
                (int) (2 * tmp_left + 640),
                (int) (service.getResources().getDimension(R.dimen.city_font_size))
            );
            tmp_left = -320;
        }
        cityLayout.setStart(
            tmp_left,
            (int) (service.getResources().getDimension(R.dimen.city_top)-((float)service.getResources().getInteger(R.integer.font_ratio)/100)*service.getResources().getDimension(R.dimen.city_font_size))
        );
        if(!service.getResources().getBoolean(R.bool.city)){cityLayout.show=false;}

        // Draw Humidity
        SlptLinearLayout humidityLayout = new SlptLinearLayout();
        SlptPictureView humidityText = new SlptPictureView();
        humidityText.setStringPicture( this.weather.humidity );
        humidityLayout.add(humidityText);
        humidityLayout.setTextAttrForAll(
            service.getResources().getDimension(R.dimen.humidity_font_size),
            service.getResources().getColor(R.color.humidity_colour_slpt),
            font
        );
        // Position based on screen on
        humidityLayout.alignX = 2;
        humidityLayout.alignY = 0;
        tmp_left = (int) service.getResources().getDimension(R.dimen.humidity_left);
        if(!service.getResources().getBoolean(R.bool.humidity_left_align)) {
            // If text is centered, set rectangle
            humidityLayout.setRect(
                (int) (2 * tmp_left + 640),
                (int) (service.getResources().getDimension(R.dimen.humidity_font_size))
            );
            tmp_left = -320;
        }
        humidityLayout.setStart(
            tmp_left,
            (int) (service.getResources().getDimension(R.dimen.humidity_top)-((float)service.getResources().getInteger(R.integer.font_ratio)/100)*service.getResources().getDimension(R.dimen.humidity_font_size))
        );
        if(!service.getResources().getBoolean(R.bool.humidity)){humidityLayout.show=false;}

        // Draw UV rays (Strong)
        SlptLinearLayout uvLayout = new SlptLinearLayout();
        SlptPictureView uvText = new SlptPictureView();
        uvText.setStringPicture( this.weather.uv );
        uvLayout.add(uvText);
        uvLayout.setTextAttrForAll(
            service.getResources().getDimension(R.dimen.uv_font_size),
            service.getResources().getColor(R.color.uv_colour_slpt),
            font
        );
        // Position based on screen on
        uvLayout.alignX = 2;
        uvLayout.alignY = 0;
        tmp_left = (int) service.getResources().getDimension(R.dimen.uv_left);
        if(!service.getResources().getBoolean(R.bool.uv_left_align)) {
            // If text is centered, set rectangle
            uvLayout.setRect(
                (int) (2 * tmp_left + 640),
                (int) (service.getResources().getDimension(R.dimen.uv_font_size))
            );
            tmp_left = -320;
        }
        uvLayout.setStart(
            tmp_left,
            (int) (service.getResources().getDimension(R.dimen.uv_top)-((float)service.getResources().getInteger(R.integer.font_ratio)/100)*service.getResources().getDimension(R.dimen.uv_font_size))
        );
        if(!service.getResources().getBoolean(R.bool.uv)){uvLayout.show=false;}

        // Draw Wind Direction
        SlptLinearLayout windDirectionLayout = new SlptLinearLayout();
        SlptPictureView windDirectionText = new SlptPictureView();
        windDirectionText.setStringPicture( (service.getResources().getBoolean(R.bool.wind_direction_as_arrows))? this.weather.windArrow : this.weather.windDirection );
        windDirectionLayout.add(windDirectionText);
        windDirectionLayout.setTextAttrForAll(
            service.getResources().getDimension(R.dimen.wind_direction_font_size),
            service.getResources().getColor(R.color.wind_direction_colour_slpt),
            font
        );
        // Position based on screen on
        windDirectionLayout.alignX = 2;
        windDirectionLayout.alignY = 0;
        tmp_left = (int) service.getResources().getDimension(R.dimen.wind_direction_left);
        if(!service.getResources().getBoolean(R.bool.wind_direction_left_align)) {
            // If text is centered, set rectangle
            windDirectionLayout.setRect(
                (int) (2 * tmp_left + 640),
                (int) (service.getResources().getDimension(R.dimen.wind_direction_font_size))
            );
            tmp_left = -320;
        }
        windDirectionLayout.setStart(
            tmp_left,
            (int) (service.getResources().getDimension(R.dimen.wind_direction_top)-((float)service.getResources().getInteger(R.integer.font_ratio)/100)*service.getResources().getDimension(R.dimen.wind_direction_font_size))
        );
        if(!service.getResources().getBoolean(R.bool.wind_direction)){windDirectionLayout.show=false;}

        // Draw Wind Strength (ex. 7.4km/h)
        SlptLinearLayout windStrengthLayout = new SlptLinearLayout();
        SlptPictureView windStrengthText = new SlptPictureView();
        windStrengthText.setStringPicture( this.weather.windStrength );
        windStrengthLayout.add(windStrengthText);
        windStrengthLayout.setTextAttrForAll(
            service.getResources().getDimension(R.dimen.uv_font_size),
            service.getResources().getColor(R.color.uv_colour_slpt),
            font
        );
        // Position based on screen on
        windStrengthLayout.alignX = 2;
        windStrengthLayout.alignY = 0;
        tmp_left = (int) service.getResources().getDimension(R.dimen.wind_strength_left);
        if(!service.getResources().getBoolean(R.bool.wind_strength_left_align)) {
            // If text is centered, set rectangle
            windStrengthLayout.setRect(
                (int) (2 * tmp_left + 640),
                (int) (service.getResources().getDimension(R.dimen.wind_strength_font_size))
            );
            tmp_left = -320;
        }
        windStrengthLayout.setStart(
            tmp_left,
            (int) (service.getResources().getDimension(R.dimen.wind_strength_top)-((float)service.getResources().getInteger(R.integer.font_ratio)/100)*service.getResources().getDimension(R.dimen.wind_strength_font_size))
        );
        if(!service.getResources().getBoolean(R.bool.wind_strength)){windStrengthLayout.show=false;}

        return Arrays.asList(new SlptViewComponent[]{temperatureLayout, weatherLayout, cityLayout, humidityLayout, uvLayout, windDirectionLayout, windStrengthLayout});
    }
}
