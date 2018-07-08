package com.dinodevs.greatfitwatchface.widget;

import android.app.Service;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.text.TextPaint;
import android.util.Log;

import com.dinodevs.greatfitwatchface.resource.SlptAnalogAmPmView;
import com.dinodevs.greatfitwatchface.resource.SlptSecondHView;
import com.dinodevs.greatfitwatchface.resource.SlptSecondLView;
import com.ingenic.iwds.slpt.view.core.SlptLinearLayout;
import com.ingenic.iwds.slpt.view.core.SlptPictureView;
import com.ingenic.iwds.slpt.view.core.SlptViewComponent;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import com.dinodevs.greatfitwatchface.data.DataType;
import com.dinodevs.greatfitwatchface.data.Time;
import com.dinodevs.greatfitwatchface.resource.ResourceManager;
import com.dinodevs.greatfitwatchface.R;


public class GreatWidget extends AbstractWidget {
    private TextPaint textPaint;
    private Time time;
    private String text;
    private String wifi;
    private int textint;
    private Boolean secondsBool;
    private Boolean ampmBool;
    private Service mService;

    private float textTop;
    private float textLeft;

    @Override
    public void init(Service service){
        this.mService = service;

        this.textLeft = service.getResources().getDimension(R.dimen.ampm_left);
        this.textTop = service.getResources().getDimension(R.dimen.ampm_top);

        this.ampmBool = service.getResources().getBoolean(R.bool.ampm);

        this.textPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        this.textPaint.setColor(service.getResources().getColor(R.color.ampm_colour));
        this.textPaint.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE));
        this.textPaint.setTextSize(service.getResources().getDimension(R.dimen.ampm_font_size));
        this.textPaint.setTextAlign(Paint.Align.CENTER);
    }


    @Override
    public List<DataType> getDataTypes() {
        //return Collections.singletonList(DataType.TIME);
        return Arrays.asList(DataType.BATTERY, DataType.STEPS, DataType.DISTANCE, DataType.TOTAL_DISTANCE, DataType.TIME,  DataType.CALORIES,  DataType.DATE,  DataType.HEART_RATE,  DataType.FLOOR);
    }

    @Override
    public void onDataUpdate(DataType type, Object value) {
        //Log.w("DinoDevs-GreatFit", type.toString()+" => "+value.toString() );
        //this.time = (Time) value;

        Calendar now = Calendar.getInstance();
        int periode = (now.get(Calendar.HOUR_OF_DAY) <= 12)?0:1;
        int seconds = now.get(Calendar.SECOND);

        this.time = new Time(seconds,0,0,periode);

        //ConnectivityManager connManager = (ConnectivityManager) this.mService.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        //NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        //this.wifi = mWifi.toString();
        //Log.w("DinoDevs-GreatFit", "WiFi: "+mWifi );
        //if (mWifi.isConnected()) {
        // Do whatever
        //}

        //this.time = getSlptTime();
    }

    @Override
    public void draw(Canvas canvas, float width, float height, float centerX, float centerY) {
        // Draw AM or PM, if enabled
        if(this.ampmBool) {
            Calendar now = Calendar.getInstance();
            String periode = (now.get(Calendar.AM_PM) == Calendar.AM) ? "AM" : "PM";
            this.text = String.format("%S", periode);//Capitalize
            canvas.drawText(text, textLeft, textTop, textPaint);
        }
    }

    public Time getSlptTime() {
        Calendar now = Calendar.getInstance();
        int periode = (now.get(Calendar.HOUR_OF_DAY) <= 12)?0:1;
        int seconds = now.get(Calendar.SECOND);

        Log.w("DinoDevs-GreatFit", String.format("Seconds= %s", seconds));

        return new Time(seconds,0,0,periode);
    }

    public String getSlptBluetooth(Service service) {
        String str = Settings.System.getString(service.getApplicationContext().getContentResolver(), "wifi");
        Log.w("DinoDevs-GreatFit", "Wifi: "+str);
        return (str!=null)?str:"null";
    }

    @Override
    public List<SlptViewComponent> buildSlptViewComponent(Service service) {
        // Variables
        //this.secondsBool = service.getResources().getBoolean(R.bool.seconds);
        this.ampmBool = service.getResources().getBoolean(R.bool.ampm);

        // Get Seconds & AM/PM
        this.time = getSlptTime();

        // Get wifi
        this.wifi = getSlptBluetooth(service);

        // Draw AM or PM
        SlptLinearLayout ampm = new SlptLinearLayout();
        SlptPictureView ampmStr = new SlptPictureView();
        ampmStr.setStringPicture( this.time.ampmStr );
        ampm.add(ampmStr);
        //ampm.add(new SlptAnalogAmPmView());
        ampm.setTextAttrForAll(
                service.getResources().getDimension(R.dimen.ampm_font_size),
                service.getResources().getColor(R.color.ampm_colour_slpt),
                ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE)
        );
        // If AM/PM is enabled
        if(!this.ampmBool) {ampm.show = false;}
        // Position based on screen on
        ampm.alignX = 2;
        ampm.alignY = 0;
        ampm.setRect(
                (int) (2*service.getResources().getDimension(R.dimen.ampm_left)+640),
                (int) (service.getResources().getDimension(R.dimen.ampm_font_size))
        );
        ampm.setStart(
                -320,
                (int) (service.getResources().getDimension(R.dimen.ampm_top)-((float)service.getResources().getInteger(R.integer.font_ratio)/100)*service.getResources().getDimension(R.dimen.ampm_font_size))
        );

        /*
        // Draw Seconds
        SlptLinearLayout secondsLayout = new SlptLinearLayout();
        //SlptPictureView secondsStr = new SlptPictureView();
        //secondsStr.setStringPicture( this.time.secondsStr );
        //secondsLayout.add(secondsStr);
        secondsLayout.add(new SlptSecondHView());
        secondsLayout.add(new SlptSecondLView());
        secondsLayout.setTextAttrForAll(
                service.getResources().getDimension(R.dimen.seconds_font_size),
                service.getResources().getColor(R.color.seconds_colour_slpt),
                ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE)
        );
        // If Seconds are enabled
        if(!this.secondsBool) {secondsLayout.show = false;}
        // Position based on screen on
        secondsLayout.alignX = 2;
        secondsLayout.alignY = 0;
        secondsLayout.setRect(
                (int) (2*service.getResources().getDimension(R.dimen.seconds_left)+640),
                (int) (service.getResources().getDimension(R.dimen.seconds_font_size))
        );
        secondsLayout.setStart(
                -320,
                (int) (service.getResources().getDimension(R.dimen.seconds_top)-((float)service.getResources().getInteger(R.integer.font_ratio)/100)*service.getResources().getDimension(R.dimen.seconds_font_size))
        );
        */

        // Draw WiFi
        SlptLinearLayout wifiLayout = new SlptLinearLayout();
        SlptPictureView wifiStr = new SlptPictureView();
        wifiStr.setStringPicture( this.wifi );
        wifiLayout.add(wifiStr);
        wifiLayout.setTextAttrForAll(
                service.getResources().getDimension(R.dimen.ampm_font_size),
                service.getResources().getColor(R.color.ampm_colour_slpt),
                ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE)
        );
        // If enabled
        if(true) {wifiLayout.show = false;}
        // Position based on screen on
        wifiLayout.alignX = 2;
        wifiLayout.alignY = 0;
        wifiLayout.setRect(
                (int) (2*150+640),
                (int) (service.getResources().getDimension(R.dimen.ampm_font_size))
        );
        wifiLayout.setStart(
                -320,
                (int) (150-((float)service.getResources().getInteger(R.integer.font_ratio)/100)*service.getResources().getDimension(R.dimen.ampm_font_size))
        );

        return Arrays.asList(new SlptViewComponent[]{ampm, /*secondsLayout,*/ wifiLayout});
    }
}
