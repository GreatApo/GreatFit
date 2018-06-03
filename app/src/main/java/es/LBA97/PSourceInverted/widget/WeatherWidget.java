package es.LBA97.PSourceInverted.widget;

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

import es.LBA97.PSourceInverted.data.DataType;
import es.LBA97.PSourceInverted.data.WeatherData;
import es.LBA97.PSourceInverted.resource.ResourceManager;
import pt.neno.prototype.R;


public class WeatherWidget extends AbstractWidget {
    private TextPaint textPaint;
    private WeatherData weather;

    private float textTop;
    private float textLeft;
    private float imgTop;
    private float imgLeft;
    //private int[] weatherImageList;
    private List<Drawable> weatherImageList;
    private List<String> weatherImageStrList;
    private Drawable weatherImage;
    private boolean showUnits;
    private boolean temperatureBool;
    private boolean weatherBool;

    @Override
    public void init(Service service) {
        this.textLeft = service.getResources().getDimension(R.dimen.temperature_left);
        this.textTop = service.getResources().getDimension(R.dimen.temperature_top);

        this.imgLeft = service.getResources().getDimension(R.dimen.weather_img_left);
        this.imgTop = service.getResources().getDimension(R.dimen.weather_img_top);

        this.textPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        this.textPaint.setColor(service.getResources().getColor(R.color.calories_colour));
        this.textPaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE));
        this.textPaint.setTextSize(service.getResources().getDimension(R.dimen.temperature_font_size));
        this.textPaint.setTextAlign(Paint.Align.CENTER);

        this.temperatureBool = service.getResources().getBoolean(R.bool.temperature);
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
    }

    @Override
    public void onDataUpdate(DataType type, Object value) {
        this.weather = (WeatherData) value;
    }

    @Override
    public void draw(Canvas canvas, float width, float height, float centerX, float centerY) {
        // Draw Temperature
        if(this.temperatureBool) {
            String units = (showUnits) ? "ºC" : "";
            canvas.drawText(weather.getTemperature() + units, textLeft, textTop, textPaint);
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
    public WeatherData getSlptWeather(Service service) {
        String tempUnit = "1";
        String temp = "n/a";
        int weatherType = 22;
        String str = Settings.System.getString(service.getApplicationContext().getContentResolver(), "WeatherCheckedSummary");

        /*
        // {"tempUnit":"1","temp":"0/0","weatherCodeFrom":0}
        String str = "{\"tempUnit\":\"1\",\"temp\":\"0\\/0\",\"weatherCodeFrom\":0}";
        try {
            str = Settings.System.getString(service.getApplicationContext().getContentResolver(), "WeatherCheckedSummary");
        }catch(Exception e){
            return new WeatherData(tempUnit, temp, weatherType);
        }

        if (str == null || str.equals( "{\"tempUnit\":\"1\",\"temp\":\"0\\/0\",\"weatherCodeFrom\":0}" )) {
            return new WeatherData(tempUnit, temp, weatherType);
        }else{
            //Log.w("pt.neno.prototype:JSON", str);
        }*/

        JSONObject weather_data = new JSONObject();
        try {
            weather_data = new JSONObject(str);
            tempUnit = weather_data.getString("tempUnit");
            temp = weather_data.getString("temp");
            weatherType = weather_data.getInt("weatherCodeFrom");
        }
        catch (JSONException e) {
          // Nothing
        }

        if(weatherType<0 || weatherType>22){
            return new WeatherData("1", "n/a", 22);
        }

        return new WeatherData(tempUnit, temp, weatherType);
    }

    @Override
    public List<SlptViewComponent> buildSlptViewComponent(Service service) {
        // Get weather data
        this.weather = getSlptWeather(service);

        // Just to be safe :P
        if(this.weather.weatherType<0 || this.weather.weatherType>22){
            this.weather.weatherType = 22;
        }

        // Load weather icons
        this.weatherImageStrList = new ArrayList<String>();
        this.weatherImageStrList.add("sunny"); //0
        this.weatherImageStrList.add("cloudy"); //1
        this.weatherImageStrList.add("overcast"); //2
        this.weatherImageStrList.add("fog"); //3
        this.weatherImageStrList.add("fog"); //4
        this.weatherImageStrList.add("showers"); //5
        this.weatherImageStrList.add("t_storm"); //6
        this.weatherImageStrList.add("rain"); //7
        this.weatherImageStrList.add("rain"); //8
        this.weatherImageStrList.add("rainstorm"); //9
        this.weatherImageStrList.add("rainstorm"); //10
        this.weatherImageStrList.add("showers"); //11
        this.weatherImageStrList.add("rainsnow"); //12
        this.weatherImageStrList.add("rainsnow"); //13
        this.weatherImageStrList.add("rainsnow"); //14
        this.weatherImageStrList.add("snow"); //15
        this.weatherImageStrList.add("snow"); //16
        this.weatherImageStrList.add("snow"); //17
        this.weatherImageStrList.add("snow"); //18
        this.weatherImageStrList.add("fog"); //19
        this.weatherImageStrList.add("fog"); //20
        this.weatherImageStrList.add("fog"); //21
        this.weatherImageStrList.add("unknow"); //22

        // Draw temperature
        SlptLinearLayout temperatureLayout = new SlptLinearLayout();
        SlptPictureView temperatureNum = new SlptPictureView();
        temperatureNum.setStringPicture( this.weather.tempString );
        temperatureLayout.add(temperatureNum);
        // Show or Not Units
        if(service.getResources().getBoolean(R.bool.temperature_units)) {
            SlptPictureView temperatureUnit = new SlptPictureView();
            temperatureUnit.setStringPicture("ºC");
            temperatureLayout.add(temperatureUnit);
        }
        Typeface caloriesFont = ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE);
        temperatureLayout.setTextAttrForAll(
                service.getResources().getDimension(R.dimen.temperature_font_size),
                service.getResources().getColor(R.color.temperature_colour_slpt),
                caloriesFont
        );
        // Position based on screen on
        temperatureLayout.alignX = 2;
        temperatureLayout.alignY=0;
        temperatureLayout.setRect(
                (int) (2*service.getResources().getDimension(R.dimen.temperature_left)),
                (int) (service.getResources().getDimension(R.dimen.temperature_font_size))
        );
        temperatureLayout.setStart(
                0,
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

        // Show on log cat
        //Log.w("pt.neno.prototype", String.format("slpt_weather/clock_skin_weather_%s.png", weatherImageStrList.get(this.weather.weatherType)));

        return Arrays.asList(new SlptViewComponent[]{temperatureLayout, weatherLayout});
    }
}
