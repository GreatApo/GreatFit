package es.LBA97.PSourceInverted.data;

import java.util.Calendar;

/**
 * TodayFloor data type
 */

public class AmPm {

    private final int periode;
    private String[] defaultAmPmTranslation = {"am", "pm"};

    public AmPm(){
        Calendar now = Calendar.getInstance();
        if(now.get(Calendar.AM_PM) == Calendar.AM){
            this.periode = 0;
        }else{
            this.periode = 1;
        }
    }

    public int getAmPm(){return periode;}


    public String getAmPmStr(String[] ampmTranslation){
        if(ampmTranslation.length > this.periode ) {
            return ampmTranslation[this.periode];
        }else{
            return defaultAmPmTranslation[this.periode];
        }
    }

    public String getAmPmStr(){
        return defaultAmPmTranslation[this.periode];
    }
}
