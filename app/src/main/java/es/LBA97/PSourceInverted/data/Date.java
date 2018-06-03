package es.LBA97.PSourceInverted.data;

/**
 * Created by chupe on 24/04/2017.
 */

public class Date {

    private final int year;
    private final int month;
    private final int day;
    private final int week;

    public Date(int year, int month, int day, int week) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.week = week;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getWeek() {
        return week;
    }
}
