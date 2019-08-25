package com.dinodevs.greatfitwatchface.data;

public class Pressure {
    public float airPressure;
    public float altitude;

    public Pressure(float measurement, int temperature) {
        if(measurement<0){
            measurement = 0;
        }

        this.airPressure = measurement;
        double p0 = 1013.25; // pressure at sea level in hPa
        if( this.airPressure < 1200 ) { // less than 1.2m underwater
            //int temperature = WeatherWidget.getSlptWeather().getTemperatureValueCelsius();
            //int temperature = 15;
            // Altitude conversion using hypsometric formula
            this.altitude = (float) Math.round( ( 1 - Math.pow(this.airPressure/p0, 1/5.25579) )*(temperature+273.15)/0.0065 );
        }else{
            // Dive depth mode
            this.altitude = (float) - Math.round( measurement - p0 )/100;
        }
    }

    // getAltitude( WeatherWidget.getSlptWeather().getTemperatureValueCelsius() )
    public float getAltitude(int temperature) {
        double p0 = 1013.25; // pressure at sea level in hPa
        if( this.airPressure < 1200 ) { // less than 1.2m underwater
            //int temperature = WeatherWidget.getSlptWeather().getTemperatureValueCelsius();
            // Altitude conversion using hypsometric formula
            this.altitude = (float) Math.round( ( 1 - Math.pow(this.airPressure/p0, 1/5.25579) )*(temperature+273.15)/0.0065 );
        }else{
            // Dive depth mode
            this.altitude = (float) - Math.round( this.airPressure - p0 )/100;
        }
        return this.altitude;
    }


    public String getAltitudeMetric(boolean units) {
        if(this.altitude<0){
            // Dive
            return Math.round(this.altitude*100)/100 + ((units)?" m":"");
        }else {
            return Math.round(this.altitude) + ((units)?" m":"");
        }
    }

    public String getAltitudeImperial(boolean units) {
        if(this.altitude<0){
            // Dive
            return Math.round(this.altitude*3.28084*100)/100 + ((units)?" ft":"");
        }else {
            return Math.round(this.altitude*3.28084)+((units)?" ft":"");
        }
    }

    public String getPressure(boolean units) {
        return getPressure(units, 0);
    }
    public String getPressure(boolean units, int unitMode) {
        if(unitMode==1){
            return Math.round(this.airPressure*0.75006157584566) + ((units)?" mmHg":"");
        }else {
            return Math.round(this.airPressure) + ((units)?" hPa":"");
        }
    }
}