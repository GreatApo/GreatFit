package com.dinodevs.greatfitwatchface.data;

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
        this.secondsStr = getStrSeconds(seconds);
        this.ampmStr = (defaultAmPmTranslation.length>ampm && ampm>=0)?defaultAmPmTranslation[ampm]:"n/a";
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
