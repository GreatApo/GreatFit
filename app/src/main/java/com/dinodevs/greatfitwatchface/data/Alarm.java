package com.dinodevs.greatfitwatchface.data;

public class Alarm {
    public final String alarm;

    public Alarm(String parmStr1) {
        if(parmStr1!=null && !parmStr1.equals("")){
            this.alarm = parmStr1;
        }else{
            this.alarm = "--";
        }
    }

    public String toString() {
        // Default onDataUpdate value
        return String.format("[alarm data info] Sting 1:%s", this.alarm);
    }
}