package com.dinodevs.greatfitwatchface.data;

import android.util.Log;

import java.util.Calendar;

/**
 * Time data
 */

public class Time {

    public int seconds;
    public String secondsStr;
    public int minutes;
    public int hours;
    public int ampm;
    public String ampmStr;
    private String[] defaultAmPmTranslation = {"am", "pm"};

    public Time(int seconds, int minutes, int hours, int ampm) {
        this.seconds = seconds;
        this.minutes = minutes;
        this.hours = hours;
        this.ampm = ampm;
        // Fix am/pm if not given
        if(this.ampm < 0 || this.ampm > 1){
            Calendar now = Calendar.getInstance();
            this.ampm = (now.get(Calendar.HOUR_OF_DAY) < 12)?0:1;
        }
        this.secondsStr = getStrSeconds(seconds);

        this.ampmStr = defaultAmPmTranslation[this.ampm];
    }

    public Time(int ampm) {
        this.ampm = ampm;
        this.ampmStr = (defaultAmPmTranslation.length>ampm && ampm>=0)?defaultAmPmTranslation[ampm]:"n/a";
    }

    public int getSeconds() {
        return seconds;
    }

    public String getStrSeconds(int second) {
        return String.format("%02d", second);
    }

    public int getMinutes() {
        return minutes;
    }

    public int getHours() {
        return hours;
    }

    public int getAmpm() {
        return ampm;
    }

    public String getAmpmStr(int ampm){
        return defaultAmPmTranslation[ampm];
    }
}
