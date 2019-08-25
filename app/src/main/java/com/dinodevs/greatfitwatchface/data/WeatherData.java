package com.dinodevs.greatfitwatchface.data;

public class WeatherData {
    public String tempFlag;
    public String tempString;
    public int weatherType;
    public String city = "n/a";
    public String humidity = "n/a";
    public String uv = "n/a";
    public String windDirection = "n/a";
    public String windStrength = "n/a";
    public String windArrow = "•";
    public String tempMax="-";
    public String tempMin="-";
    public String tempFormatted="-/-";


    public WeatherData(String tempFlag, String tempString, int weatherType) {
        this.tempFlag = tempFlag;
        this.tempString = tempString;
        this.weatherType = weatherType;
    }

    public WeatherData(String tempFlag, String tempString, int weatherType, String tempCity, String tempHumidity, String tempUV, String tempWindDirection, String tempWindStrength, String tempMax, String tempMin, String tempFormatted) {
        this.tempFlag = tempFlag;
        this.tempString = tempString;
        this.weatherType = weatherType;
        this.city = tempCity;
        this.humidity = tempHumidity;
        this.uv = tempUV;
        this.windDirection = tempWindDirection;
        this.windStrength = tempWindStrength;
        this.windArrow = getWindDirectionArrow();
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.tempFormatted = tempFormatted;
        getTempFormatted();
    }

    public String toString() {
        // Default onDataUpdate value
        return String.format("weather info [tempFlag:%s, tempString:%s, weatherType:%d", new Object[]{this.tempFlag, this.tempString, Integer.valueOf(this.weatherType)});
    }

    public int getWindDirectionType() { // So to be used in images array
        // NW N NE     1 2 3
        // W n/a E  =  4 0 5
        // SW S SE     6 7 8

        int windDirectionType = 0;
        if(this.windDirection.equals("NW")){
            windDirectionType = 1;
        }else if(this.windDirection.equals("N")){
            windDirectionType = 2;
        }else if(this.windDirection.equals("NE")){
            windDirectionType = 3;
        }else if(this.windDirection.equals("W")){
            windDirectionType = 4;
        }else if(this.windDirection.equals("E")){
            windDirectionType = 5;
        }else if(this.windDirection.equals("SW")){
            windDirectionType = 6;
        }else if(this.windDirection.equals("S")){
            windDirectionType = 7;
        }else if(this.windDirection.equals("SE")){
            windDirectionType = 8;
        }

        return windDirectionType;
    }

    public String getWindDirectionArrow() { // Arrows
        // NW N NE     ↘ ↓ ↙
        // W n/a E  =  → • ←
        // SW S SE     ↗ ↑ ↖

        String windDirectionArrow = "•";
        if(this.windDirection.equals("NW") || this.windDirection.equals("西北風")){
            windDirectionArrow = "↘";
        }else if(this.windDirection.equals("N") || this.windDirection.equals("北風")){
            windDirectionArrow = "↓";
        }else if(this.windDirection.equals("NE") || this.windDirection.equals("東北風")){
            windDirectionArrow = "↙";
        }else if(this.windDirection.equals("W") || this.windDirection.equals("西風")){
            windDirectionArrow = "→";
        }else if(this.windDirection.equals("E") || this.windDirection.equals("東風")){
            windDirectionArrow = "←";
        }else if(this.windDirection.equals("SW") || this.windDirection.equals("西南風")){
            windDirectionArrow = "↗";
        }else if(this.windDirection.equals("S") || this.windDirection.equals("南風")){
            windDirectionArrow = "↑";
        }else if(this.windDirection.equals("SE") || this.windDirection.equals("東南風")){
            windDirectionArrow = "↖";
        }

        return windDirectionArrow;
    }

    public String getUnits() {
        return (this.tempFlag.equals("1") || this.tempFlag.equals("C"))?"ºC":"ºF";
    }

    public String getTemperature() {
        if(this.tempString.isEmpty() || this.weatherType==22 || this.tempString.equals("0/0")){
            return "n/a";
        }
        return this.tempString;
    }

    public int getTemperatureValueCelsius() {
        int num = 15; // Default: an average temperature (ºC)
        if(this.tempString.isEmpty() || this.weatherType==22 || this.tempString.equals("0/0")){
            return num;
        }else{
            try {// Convert from float to int
                num = (int) Float.parseFloat(this.tempString);
            } catch (Exception e) {
                // error in the conversion
                return num;
            }
        }

        if (!this.tempFlag.equals("1") && !this.tempFlag.equals("C")){
            // Fahrenheit to Celsius
            num = (num - 32) * 5/9;
        }

        return num;
    }

    private String getTempMax() {
        if (this.tempMax.isEmpty()) {
            return "-";
        }
        return this.tempMax;
    }

    private String getTempMin() {
        if (this.tempMin.isEmpty()) {
            return "-";
        }
        return this.tempMin;
    }

    private void getTempFormatted() {
        this.tempFormatted = getTempWithoutDot(getTempMax())+"/"+getTempWithoutDot(getTempMin())+getUnits();
    }

    private String getTempWithoutDot(String string) {
        int indx = string.lastIndexOf(46);
        if (indx != -1) {
            return string.substring(0, indx);
        }
        return string;
    }
}
