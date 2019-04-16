package com.dinodevs.greatfitwatchface.widget;

import android.app.Service;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.provider.Settings;
import android.text.TextPaint;

import com.dinodevs.greatfitwatchface.data.DataType;
import com.dinodevs.greatfitwatchface.data.WeatherData;
import com.dinodevs.greatfitwatchface.resource.ResourceManager;
import com.dinodevs.greatfitwatchface.settings.LoadSettings;
import com.huami.watch.watchface.util.Util;
import com.ingenic.iwds.slpt.view.core.SlptLinearLayout;
import com.ingenic.iwds.slpt.view.core.SlptPictureView;
import com.ingenic.iwds.slpt.view.core.SlptViewComponent;
import com.ingenic.iwds.slpt.view.utils.SimpleFile;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;


public class MoonPhaseWidget extends AbstractWidget {
    private Calendar today;
    private String txtx = "fase";
    private Service mService;

    private List<String> moonphaseImageStrList;
    private Bitmap moonphaseImageIcon;
    private TextPaint txtPaint;
    private TextPaint moonphase_imgPaint;

    private Bitmap moonIcon;

    private LoadSettings settings;

    // Constructor
    public MoonPhaseWidget(LoadSettings settings) {
        this.settings = settings;

        // Load weather icons
        String[] weatherIconNames = new String[]{
                "New moon",        	// 0
                "Waxing crescent",	// 1 
                "First quarter",	// 2 
                "Waxing gibbous",	// 3
                "Full moon",		// 4 
                "Waning gibbous",	// 5
                "Last quarter",	    // 6
                "Waning crescent"	// 7
        };
        this.moonphaseImageStrList =  Arrays.asList(weatherIconNames);
    }

    // Screen-on init (runs once)
    @Override
    public void init(Service service) {
        this.mService = service;

        // Temperature
        if (settings.moonphase >0) {
            this.txtPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
            this.txtPaint.setColor(settings.temperatureColor);
            this.txtPaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE));
            this.txtPaint.setTextSize(settings.temperatureFontSize);
            this.txtPaint.setTextAlign((settings.temperatureAlignLeft) ? Paint.Align.LEFT : Paint.Align.CENTER);
            if(settings.moonphaseIcon){
                this.moonphaseImageIcon = Util.decodeImage(service.getResources(),"icons/moonphase.png");
            }
        }
/*
    TODO: icone della luna
        // Load weather icons
        if(settings.weather_img>0) {
            // Get weather data
            this.weather = getSlptWeather();
            this.weatherImageIcon = Util.decodeImage(service.getResources(),"weather/"+this.weatherImageStrList.get(22)+".png");
            this.weather_imgPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
            this.weather_imgPaint.setColor(settings.weather_imgColor);
            this.weather_imgPaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE));
            this.weather_imgPaint.setTextSize(settings.weather_imgFontSize);
            this.weather_imgPaint.setTextAlign((settings.weather_imgAlignLeft) ? Paint.Align.LEFT : Paint.Align.CENTER);
        }*/
    }

    // Register listener
    @Override
    public List<DataType> getDataTypes() {
        return Collections.singletonList(DataType.DATE);
    }

    // Updater
    @Override
    public void onDataUpdate(DataType type, Object value) {
        this.txtx = "Luna Nuova";

        // TODO: implementare
        //this.weatherImageIcon = Util.decodeImage(mService.getResources(),"weather/"+this.weatherImageStrList.get(this.weather.weatherType)+".png");
    }

    // Screen on
    @Override
    public void draw(Canvas canvas, float width, float height, float centerX, float centerY) {
        // Draw Text
        if(settings.moonphase>0) {
            canvas.drawText(txtx, settings.moonphaseLeft, settings.moonphaseTop, txtPaint);
        }


        /* TODO Draw moonphase icon
        if(settings.weather_img>0) {
            canvas.drawBitmap(this.weatherImageIcon, settings.weather_imgIconLeft, settings.weather_imgIconTop, settings.mGPaint);
            if(settings.weather_imgIcon) {//In the weather image widget, if icon is disabled, temperature is not shown!
                String units = (settings.weather_imgUnits) ? weather.getUnits() : ""; //"ºC"
                canvas.drawText(weather.getTemperature() + units, settings.weather_imgLeft, settings.weather_imgTop, weather_imgPaint);
            }
        }*/
    }

    /* Get Weather Data on screen off
    based on HuamiWatchFaces.jar!\com\huami\watch\watchface\widget\slpt\SlptWeatherWidget.class
    and AmazfitWeather.jar!\com\huami\watch\weather\WeatherUtil.class */
    public String getSlptMoonPhase() {
        // Default variables
        String txtx = "no";
        return txtx;
    }

    // Screen-off (SLPT)
    @Override
    public List<SlptViewComponent> buildSlptViewComponent(Service service) {
        return buildSlptViewComponent(service, false);
    }

    // Screen-off (SLPT) - Better screen quality
    public List<SlptViewComponent> buildSlptViewComponent(Service service, boolean better_resolution) {
        better_resolution = better_resolution && settings.better_resolution_when_raising_hand;
        List<SlptViewComponent> slpt_objects = new ArrayList<>();

        // Get weather data
        this.mService = service;
        this.txtx = getSlptMoonPhase();



        // TODO : moonphase Icons
        /*if(settings.weather_img>0){
            SlptPictureView weatherIcon = new SlptPictureView();
            weatherIcon.setImagePicture( SimpleFile.readFileFromAssets(service, String.format(( (better_resolution)?"":"slpt_" )+"weather/%s.png", this.weatherImageStrList.get(this.weather.weatherType))) );
            weatherIcon.setStart(
                    (int) settings.weather_imgIconLeft,
                    (int) settings.weather_imgIconTop
            );
            slpt_objects.add(weatherIcon);

            if(settings.weather_imgIcon) {//In the weather image widget, if icon is disabled, temperature is not shown!
                SlptLinearLayout weatherLayout = new SlptLinearLayout();
                // Show temperature with units or not
                SlptPictureView weather_imgNum = new SlptPictureView();
                weather_imgNum.setStringPicture(this.weather.tempString + (settings.weather_imgUnits ? this.weather.getUnits() : ""));
                weather_imgNum.setTextAttr(
                        settings.weather_imgFontSize,
                        settings.weather_imgColor,
                        ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE)
                );
                weatherLayout.add(weather_imgNum);
                // Position based on screen on
                weatherLayout.alignX = 2;
                weatherLayout.alignY = 0;
                int tmp_left = (int) settings.weather_imgLeft;
                if (!settings.weather_imgAlignLeft) {
                    // If text is centered, set rectangle
                    weatherLayout.setRect(
                            (int) (2 * tmp_left + 640),
                            (int) (settings.weather_imgFontSize)
                    );
                    tmp_left = -320;
                }
                weatherLayout.setStart(
                        (int) tmp_left,
                        (int) (settings.weather_imgTop - ((float) settings.font_ratio / 100) * settings.weather_imgFontSize)
                );
                slpt_objects.add(weatherLayout);
            }
        }*/

        // Draw moonphase
        if(settings.moonphase>0){

            SlptLinearLayout moonphaseLayout = new SlptLinearLayout();
            SlptPictureView moonphaseText = new SlptPictureView();
            moonphaseText.setStringPicture(txtx);
            moonphaseText.setTextAttr(
                    settings.moonphaseFontSize,
                    settings.moonphaseColor,
                    ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE)
            );
            moonphaseLayout.add(moonphaseText);
            moonphaseLayout.setTextAttrForAll(
                    settings.moonphaseFontSize,
                    settings.moonphaseColor,
                    ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE)
            );
            // Position based on screen on
            moonphaseLayout.alignX = 2;
            moonphaseLayout.alignY = 0;
            int tmp_left = (int) settings.moonphaseLeft;
            if(!settings.moonphaseAlignLeft) {
                // If text is centered, set rectangle
                moonphaseLayout.setRect(
                        (int) (2 * tmp_left + 640),
                        (int) (settings.moonphaseFontSize)
                );
                tmp_left = -320;
            }
            moonphaseLayout.setStart(
                    (int) tmp_left,
                    (int) (settings.moonphaseTop-((float)settings.font_ratio/100)*settings.moonphaseFontSize)
            );
            slpt_objects.add(moonphaseLayout);
        }

        return slpt_objects;
    }
}
