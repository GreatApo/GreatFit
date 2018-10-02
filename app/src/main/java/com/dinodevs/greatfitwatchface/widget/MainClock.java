package com.dinodevs.greatfitwatchface.widget;

import android.app.Service;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.widget.Toast;

import com.dinodevs.greatfitwatchface.AbstractWatchFace;
import com.dinodevs.greatfitwatchface.resource.SlptSecondHView;
import com.dinodevs.greatfitwatchface.resource.SlptSecondLView;
import com.dinodevs.greatfitwatchface.settings.LoadSettings;
import com.huami.watch.watchface.util.Util;
import com.ingenic.iwds.slpt.view.core.SlptLinearLayout;
import com.ingenic.iwds.slpt.view.core.SlptNumView;
import com.ingenic.iwds.slpt.view.core.SlptPictureView;
import com.ingenic.iwds.slpt.view.core.SlptViewComponent;
import com.ingenic.iwds.slpt.view.digital.SlptDayHView;
import com.ingenic.iwds.slpt.view.digital.SlptDayLView;
import com.ingenic.iwds.slpt.view.digital.SlptHourHView;
import com.ingenic.iwds.slpt.view.digital.SlptHourLView;
import com.ingenic.iwds.slpt.view.digital.SlptMinuteHView;
import com.ingenic.iwds.slpt.view.digital.SlptMinuteLView;
import com.ingenic.iwds.slpt.view.digital.SlptMonthHView;
import com.ingenic.iwds.slpt.view.digital.SlptMonthLView;
import com.ingenic.iwds.slpt.view.digital.SlptWeekView;
import com.ingenic.iwds.slpt.view.digital.SlptYear0View;
import com.ingenic.iwds.slpt.view.digital.SlptYear1View;
import com.ingenic.iwds.slpt.view.digital.SlptYear2View;
import com.ingenic.iwds.slpt.view.digital.SlptYear3View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import com.dinodevs.greatfitwatchface.R;
import com.dinodevs.greatfitwatchface.resource.ResourceManager;
import com.ingenic.iwds.slpt.view.utils.SimpleFile;


public class MainClock extends DigitalClockWidget {

    private TextPaint hourFont;
    private TextPaint minutesFont;
    private TextPaint secondsFont;
    private TextPaint indicatorFont;
    private TextPaint dateFont;
    private TextPaint dayFont;
    private TextPaint weekdayFont;
    private TextPaint monthFont;
    private TextPaint yearFont;

    private Bitmap dateIcon;

    //private Drawable background;
    private Bitmap background;

    private String[] digitalNums = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private String[] digitalNumsNo0 = {"", "1", "2", "3", "4", "5", "6", "7", "8", "9"};//no 0 on first digit

    // Languages
    public static String[] codes = {
            "English", "中文", "Czech", "Nederlands", "Français", "Deutsch", "Ελληνικά", "עברית", "Magyar", "Italiano", "日本語", "한국어", "Polski", "Português", "Русский", "Slovenčina", "Español", "Türkçe"
    };

    private static String[][] days = {
            //{"SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"},
            {"SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"},
            {"星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"},
            {"NEDĚLE","PONDĚLÍ", "ÚTERÝ", "STŘEDA", "ČTVRTEK", "PÁTEK", "SOBOTA"},
            {"ZONDAG", "MAANDAG", "DINSDAG", "WOENSDAG", "DONDERDAG", "VRIJDAG", "ZATERDAG"},
            {"DIMANCHE", "LUNDI", "MARDI", "MERCREDI", "JEUDI", "VENDREDI", "SAMEDI"},
            {"SONNTAG", "MONTAG", "DIENSTAG", "MITTWOCH", "DONNERSTAG", "FREITAG", "SAMSTAG"},
            {"ΚΥΡΙΑΚΉ", "ΔΕΥΤΈΡΑ", "ΤΡΊΤΗ", "ΤΕΤΆΡΤΗ", "ΠΈΜΠΤΗ", "ΠΑΡΑΣΚΕΥΉ", "ΣΆΒΒΑΤΟ"},
            {"ש'","ו'","ה'","ד'","ג'","ב'","א'"},
            {"VASÁRNAP", "HÉTFŐ", "KEDD", "SZERDA", "CSÜTÖRTÖK", "PÉNTEK", "SZOMBATON"},
            {"DOMENICA", "LUNEDÌ", "MARTEDÌ", "MERCOLEDÌ", "GIOVEDÌ", "VENERDÌ", "SABATO"},
            {"日曜日", "月曜日", "火曜日", "水曜日", "木曜日", "金曜日", "土曜日"},
            {"일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"},
            {"NIEDZIELA", "PONIEDZIAŁEK", "WTOREK", "ŚRODA", "CZWARTEK", "PIĄTEK", "SOBOTA"},
            {"DOMINGO", "SEGUNDA", "TERÇA", "QUARTA", "QUINTA", "SEXTA", "SÁBADO"},
            {"ВОСКРЕСЕНЬЕ", "ПОНЕДЕЛЬНИК", "ВТОРНИК", "СРЕДА", "ЧЕТВЕРГ", "ПЯТНИЦА", "СУББОТА"},
            {"NEDEĽA", "PONDELOK", "UTOROK", "STREDA", "ŠTVRTOK", "PIATOK", "SOBOTA"},
            {"DOMINGO", "LUNES", "MARTES", "MIÉRCOLES", "JUEVES", "VIERNES", "SÁBADO"},
            {"PAZAR", "PAZARTESI", "SALı", "ÇARŞAMBA", "PERŞEMBE", "CUMA", "CUMARTESI"},
    };

    private static String[][] days_3let = {
            //{"SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"},
            {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"},
            {"星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"},
            {"NE", "PO", "ÚT", "ST", "ČT", "PÁ", "SO"},
            {"ZON", "MAA", "DIN", "WOE", "DON", "VRI", "ZAT"},
            {"DIM", "LUN", "MAR", "MER", "JEU", "VEN", "SAM"},
            {"SON", "MON", "DIE", "MIT", "DON", "FRE", "SAM"},
            {"ΚΥΡ", "ΔΕΥ", "ΤΡΙ", "ΤΕΤ", "ΠΕΜ", "ΠΑΡ", "ΣΑΒ"},
            {"ש'","ו'","ה'","ד'","ג'","ב'","א'"},
            {"VAS", "HÉT", "KED", "SZE", "CSÜ", "PÉN", "SZO"},
            {"DOM", "LUN", "MAR", "MER", "GIO", "VEN", "SAB"},
            {"日曜日", "月曜日", "火曜日", "水曜日", "木曜日", "金曜日", "土曜日"},
            {"일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"},
            {"NIE", "PON", "WTO", "ŚRO", "CZW", "PIĄ", "SOB"},
            {"DOM", "SEG", "TER", "QUA", "QUI", "SEX", "SÁB"},
            {"ВОС", "ПОН", "ВТО", "СРЕ", "ЧЕТ", "ПЯТ", "СУБ"},
            {"NED", "PON", "UTO", "STR", "ŠTV", "PIA", "SOB"},
            {"DOM", "LUN", "MAR", "MIÉ", "JUE", "VIE", "SÁB"},
            {"PAZ", "PAR", "SAL", "ÇAR", "PER", "CUM", "CUR"},
    };

    private static String[][] months = {
            //{"DECEMBER", "JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"},
            {"DECEMBER", "JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"},
            {"十二月", "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"},
            {"PROSINEC", "LEDEN", "ÚNOR", "BŘEZEN", "DUBEN", "KVĚTEN", "ČERVEN", "ČERVENEC", "SRPEN", "ZÁŘÍ", "ŘÍJEN", "LISTOPAD", "PROSINEC"},
            {"DECEMBER", "JANUARI", "FEBRUARI", "MAART", "APRIL", "MEI", "JUNI", "JULI", "AUGUSTUS", "SEPTEMBER", "OKTOBER", "NOVEMBER", "DECEMBER"},
            {"DÉCEMBRE", "JANVIER", "FÉVRIER", "MARS", "AVRIL", "MAI", "JUIN", "JUILLET", "AOÛT", "SEPTEMBRE", "OCTOBRE", "NOVEMBRE", "DÉCEMBRE"},
            {"DEZEMBER", "JANUAR", "FEBRUAR", "MÄRZ", "APRIL", "MAI", "JUNI", "JULI", "AUGUST", "SEPTEMBER", "OKTOBER", "NOVEMBER", "DEZEMBER"},
            {"ΔΕΚΈΜΒΡΙΟΣ", "ΙΑΝΟΥΆΡΙΟΣ", "ΦΕΒΡΟΥΆΡΙΟΣ", "ΜΆΡΤΙΟΣ", "ΑΠΡΊΛΙΟΣ", "ΜΆΙΟΣ", "ΙΟΎΝΙΟΣ", "ΙΟΎΛΙΟΣ", "ΑΎΓΟΥΣΤΟΣ", "ΣΕΠΤΈΜΒΡΙΟΣ", "ΟΚΤΏΒΡΙΟΣ", "ΝΟΈΜΒΡΙΟΣ", "ΔΕΚΈΜΒΡΙΟΣ"},
            {"דצמבר", "ינואר", "פברואר", "מרץ", "אפריל", "מאי", "יוני", "יולי", "אוגוסט", "ספטמבר", "אוקטובר", "נובמבר", "דצמבר"},
            {"DECEMBER", "JANUÁR", "FEBRUÁR", "MÁRCIUS", "ÁPRILIS", "MÁJUS", "JÚNIUS", "JÚLIUS", "AUGUSZTUS", "SZEPTEMBER", "OKTÓBER", "NOVEMBER", "DECEMBER"},
            {"DICEMBRE", "GENNAIO", "FEBBRAIO", "MARZO", "APRILE", "MAGGIO", "GIUGNO", "LUGLIO", "AGOSTO", "SETTEMBRE", "OTTOBRE", "NOVEMBRE", "DICEMBRE"},
            {"12月", "1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"},
            {"12월", "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"},
            {"GRUDZIEŃ", "STYCZEŃ", "LUTY", "MARZEC", "KWIECIEŃ", "MAJ", "CZERWIEC", "LIPIEC", "SIERPIEŃ", "WRZESIEŃ", "PAŹDZIERNIK", "LISTOPAD", "GRUDZIEŃ"},
            {"DEZEMBRO", "JANEIRO", "FEVEREIRO", "MARÇO", "ABRIL", "MAIO", "JUNHO", "JULHO", "AGOSTO", "SETEMBRO", "OUTUBRO", "NOVEMBRO", "DEZEMBRO"},
            {"ДЕКАБРЬ", "ЯНВАРЬ", "ФЕВРАЛЬ", "МАРТ", "АПРЕЛЬ", "МАЙ", "ИЮНЬ", "ИЮЛЬ", "АВГУСТ", "СЕНТЯБРЬ", "ОКТЯБРЬ", "НОЯБРЬ", "ДЕКАБРЬ"},
            {"DECEMBER", "JANUÁR", "FEBRUÁR", "MAREC", "APRÍL", "MÁJ", "JÚN", "JÚL", "AUGUST", "SEPTEMBER", "OKTÓBER", "NOVEMBER", "DECEMBER"},
            {"DICIEMBRE", "ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO", "JULIO", "AGOSTO", "SEPTIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE"},
            {"ARALıK", "OCAK", "ŞUBAT", "MART", "NISAN", "MAYıS", "HAZIRAN", "TEMMUZ", "AĞUSTOS", "EYLÜL", "EKIM", "KASıM", "ARALıK"},
    };

    private static String[][] months_3let = {
            //{"DECEMBER", "JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"},
            {"DEC", "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV"},
            {"十二月", "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月"},
            {"PRO", "LED", "ÚNO", "BŘE", "DUB", "KVĚ", "ČER", "ČER", "SRP", "ZÁŘ", "ŘÍJ", "LIS"},
            {"DEC", "JAN", "FEB", "MAA", "APR", "MEI", "JUN", "JUL", "AUG", "SEP", "OKT", "NOV"},
            {"DÉC", "JAN", "FÉV", "MAR", "AVR", "MAI", "JUI", "JUI", "AOÛ", "SEP", "OCT", "NOV"},
            {"DEZ", "JAN", "FEB", "MÄR", "APR", "MAI", "JUN", "JUL", "AUG", "SEP", "OKT", "NOV"},
            {"ΔΕΚ", "ΙΑΝ", "ΦΕΒ", "ΜΑΡ", "ΑΠΡ", "ΜΑΙ", "ΙΟΥΝ", "ΙΟΥΛ", "ΑΥΓ", "ΣΕΠ", "ΟΚΤ", "ΝΟΕ"},
            {"דצמ", "ינו", "פבר", "מרץ", "אפר", "מאי", "יונ", "יול", "אוג", "ספט", "אוק", "נוב"},
            {"DEC", "JAN", "FEB", "MÁR", "ÁPR", "MÁJ", "JÚN", "JÚL", "AUG", "SZE", "OKT", "NOV"},
            {"DIC", "GEN", "FEB", "MAR", "APR", "MAG", "GIU", "LUG", "AGO", "SET", "OTT", "NOV"},
            {"12月", "1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月"},
            {"12월", "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월"},
            {"GRU", "STY", "LUT", "MAR", "KWI", "MAJ", "CZE", "LIP", "SIE", "WRZ", "PAŹ", "LIS"},
            {"DEZ", "JAN", "FEV", "MAR", "ABR", "MAI", "JUN", "JUL", "AGO", "SET", "OUT", "NOV"},
            {"ДЕК", "ЯНВ", "ФЕВ", "МАР", "АПР", "МАЙ", "ИЮН", "ИЮЛ", "АВГ", "СЕН", "ОКТ", "НОЯ"},
            {"DEC", "JAN", "FEB", "MAR", "APR", "MÁJ", "JÚN", "JÚL", "AUG", "SEP", "OKT", "NOV"},
            {"DIC", "ENE", "FEB", "MAR", "ABR", "MAY", "JUN", "JUL", "AGO", "SEP", "OCT", "NOV"},
            {"ARA", "OCA", "ŞUB", "MAR", "NIS", "MAY", "HAZ", "TEM", "AĞU", "EYL", "EKI", "KAS"},
    };

    private LoadSettings settings;

    public MainClock(LoadSettings settings) {
        this.settings = settings;
    }

    @Override
    public void init(Service service) {
        // Please do not change the following line
        Toast.makeText(service, "Source code by GreatApo, style by "+service.getResources().getString(R.string.author), Toast.LENGTH_LONG).show();

        //this.background = service.getResources().getDrawable(R.drawable.background); //todo
        //this.background.setBounds(0, 0, 320, 300);
        this.background = Util.decodeImage(service.getResources(),"background.png");

        this.hourFont = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        this.hourFont.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE));
        this.hourFont.setTextSize(settings.hoursFontSize);
        this.hourFont.setColor(settings.hoursColor);
        this.hourFont.setTextAlign((settings.hoursAlignLeft)? Paint.Align.LEFT : Paint.Align.CENTER);

        this.minutesFont = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        this.minutesFont.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE));
        this.minutesFont.setTextSize(settings.minutesFontSize);
        this.minutesFont.setColor(settings.minutesColor);
        this.minutesFont.setTextAlign((settings.minutesAlignLeft)? Paint.Align.LEFT : Paint.Align.CENTER);

        this.secondsFont = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        this.secondsFont.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE));
        this.secondsFont.setTextSize(settings.secondsFontSize);
        this.secondsFont.setColor(settings.secondsColor);
        this.secondsFont.setTextAlign((settings.secondsAlignLeft)? Paint.Align.LEFT : Paint.Align.CENTER);

        this.indicatorFont = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        this.indicatorFont.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE));
        this.indicatorFont.setTextSize(settings.indicatorFontSize);
        this.indicatorFont.setColor(settings.indicatorColor);
        this.indicatorFont.setTextAlign((settings.indicatorAlignLeft)? Paint.Align.LEFT : Paint.Align.CENTER);

        if(settings.date>0) {
            this.dateFont = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
            this.dateFont.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE));
            this.dateFont.setTextSize(settings.dateFontSize);
            this.dateFont.setColor(settings.dateColor);
            this.dateFont.setTextAlign((settings.dateAlignLeft) ? Paint.Align.LEFT : Paint.Align.CENTER);
            if (settings.dateIcon) {
                this.dateIcon = Util.decodeImage(service.getResources(), "icons/date.png");
            }
        }

        this.weekdayFont = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        this.weekdayFont.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE));
        this.weekdayFont.setTextSize(settings.weekdayFontSize);
        this.weekdayFont.setColor(settings.weekdayColor);
        this.weekdayFont.setTextAlign( (settings.weekdayAlignLeft) ? Paint.Align.LEFT : Paint.Align.CENTER );

        this.dayFont = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        this.dayFont.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE));
        this.dayFont.setTextSize(settings.dayFontSize);
        this.dayFont.setColor(settings.dayColor);
        this.dayFont.setTextAlign( (settings.dayAlignLeft) ? Paint.Align.LEFT : Paint.Align.CENTER );

        this.monthFont = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        this.monthFont.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE));
        this.monthFont.setTextSize(settings.monthFontSize);
        this.monthFont.setColor(settings.monthColor);
        this.monthFont.setTextAlign( (settings.monthAlignLeft) ? Paint.Align.LEFT : Paint.Align.CENTER );

        this.yearFont = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        this.yearFont.setTypeface(ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE));
        this.yearFont.setTextSize(settings.yearFontSize);
        this.yearFont.setColor(settings.yearColor);
        this.yearFont.setTextAlign( (settings.yearAlignLeft) ? Paint.Align.LEFT : Paint.Align.CENTER );
    }

    // Screen open watch mode
    @Override
    public void onDrawDigital(Canvas canvas, float width, float height, float centerX, float centerY, int seconds, int minutes, int hours, int year, int month, int day, int week, int ampm) {
        // Draw background image
        //this.background.draw(canvas);
        canvas.drawBitmap(this.background, 0f, 0f, settings.mGPaint);

        // Draw hours
        canvas.drawText( (settings.no_0_on_hour_first_digit)?hours+"":Util.formatTime(hours), settings.hoursLeft, settings.hoursTop, this.hourFont);

        // Draw minutes
        canvas.drawText(Util.formatTime(minutes), settings.minutesLeft, settings.minutesTop, this.minutesFont);

        // JAVA calendar get/show time library
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, week);

        // Draw Date
        if(settings.date>0) {
            if(settings.dateIcon){
                canvas.drawBitmap(this.dateIcon, settings.dateIconLeft, settings.dateIconTop, settings.mGPaint);
            }

            String date = Util.formatTime(day)+"."+Util.formatTime(month)+"."+Integer.toString(year);
            canvas.drawText(date, settings.dateLeft, settings.dateTop, this.dateFont);
        }

        // Draw Day
        if(settings.dayBool) {
            String dayText = Util.formatTime(day);
            canvas.drawText(dayText, settings.dayLeft, settings.dayTop, this.dayFont);
        }

        // Get + Draw WeekDay (using JAVA)
        if(settings.weekdayBool) {
            //String weekday = String.format("%S", new SimpleDateFormat("EE").format(calendar.getTime()));
            int weekdaynum = calendar.get(Calendar.DAY_OF_WEEK)-1;
            String weekday = (settings.three_letters_day_if_text)? days_3let[settings.language][weekdaynum] : days[settings.language][weekdaynum] ;
            canvas.drawText(weekday, settings.weekdayLeft, settings.weekdayTop, this.weekdayFont);
        }

        // Draw Month
        if(settings.monthBool) {
            String monthText = (settings.month_as_text)? ( (settings.three_letters_month_if_text)? months_3let[settings.language][month] : months[settings.language][month] ) : String.format("%02d", month) ;
            canvas.drawText(monthText, settings.monthLeft, settings.monthTop, this.monthFont);
        }

        // Draw Year
        if(settings.yearBool) {
            canvas.drawText(Integer.toString(year), settings.yearLeft, settings.yearTop, this.yearFont);
        }

        // Draw Seconds
        if(settings.secondsBool) {
            canvas.drawText(Util.formatTime(seconds), settings.secondsLeft, settings.secondsTop, this.secondsFont);
        }

        // : indicator Draw + Flashing
        if(settings.indicatorBool) {
            String indicator = ":";
            if (seconds % 2 == 0 || !settings.flashing_indicator) { // Draw only on even seconds (flashing : symbol)
                canvas.drawText(indicator, settings.indicatorLeft, settings.indicatorTop, this.indicatorFont);
            }
        }
    }


    // Screen locked/closed watch mode (Slpt mode)
    @Override
    public List<SlptViewComponent> buildSlptViewComponent(Service service) {
        return buildSlptViewComponent(service, false);
    }

    public List<SlptViewComponent> buildSlptViewComponent(Service service, boolean better_resolution) {
        better_resolution = better_resolution && settings.better_resolution_when_raising_hand;

        int tmp_left;
        List<SlptViewComponent> slpt_objects = new ArrayList<>();

        // Draw background image
        SlptPictureView background = new SlptPictureView();
        background.setImagePicture(SimpleFile.readFileFromAssets(service, "background"+ ((better_resolution)?"_better":"") +"_slpt.png"));
        slpt_objects.add(background);

        // Set font
        Typeface timeTypeFace = ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE);

        // Draw hours
        if(settings.hoursBool){
            SlptLinearLayout hourLayout = new SlptLinearLayout();
            if(settings.no_0_on_hour_first_digit) {// No 0 on first digit
                SlptViewComponent firstDigit = new SlptHourHView();
                ((SlptNumView) firstDigit).setStringPictureArray(this.digitalNumsNo0);
                hourLayout.add(firstDigit);
                SlptViewComponent secondDigit = new SlptHourLView();
                ((SlptNumView) secondDigit).setStringPictureArray(this.digitalNums);
                hourLayout.add(secondDigit);
            }else{
                hourLayout.add(new SlptHourHView());
                hourLayout.add(new SlptHourLView());
                hourLayout.setStringPictureArrayForAll(this.digitalNums);
            }
            hourLayout.setTextAttrForAll(
                    settings.hoursFontSize,
                    settings.hoursColor,
                    timeTypeFace
            );
            // Position based on screen on
            hourLayout.alignX = 2;
            hourLayout.alignY=0;
            hourLayout.setRect(
                    (int) (2*settings.hoursLeft+640),
                    (int) (settings.hoursFontSize)
            );
            hourLayout.setStart(
                    -320,
                    (int) (settings.hoursTop-((float)settings.font_ratio/100)*settings.hoursFontSize)
            );
            //Add it to the list
            slpt_objects.add(hourLayout);
        }

        // Draw minutes
        if(settings.minutesBool){
            SlptLinearLayout minuteLayout = new SlptLinearLayout();
            minuteLayout.add(new SlptMinuteHView());
            minuteLayout.add(new SlptMinuteLView());
            minuteLayout.setStringPictureArrayForAll(this.digitalNums);
            minuteLayout.setTextAttrForAll(
                    settings.minutesFontSize,
                    settings.minutesColor,
                    timeTypeFace
            );
            // Position based on screen on
            minuteLayout.alignX = 2;
            minuteLayout.alignY=0;
            minuteLayout.setRect(
                    (int) (2*settings.minutesLeft+640),
                    (int) (settings.minutesFontSize)
            );
            minuteLayout.setStart(
                    -320,
                    (int) (settings.minutesTop-((float)settings.font_ratio/100)*settings.minutesFontSize)
            );
            //Add it to the list
            slpt_objects.add(minuteLayout);
        }

        // Draw indicator
        if(settings.indicatorBool){
            SlptLinearLayout indicatorLayout = new SlptLinearLayout();
            SlptPictureView colon = new SlptPictureView();
            colon.setStringPicture(":");
            indicatorLayout.add(colon);
            indicatorLayout.setTextAttrForAll(
                    settings.indicatorFontSize,
                    settings.indicatorColor,
                    timeTypeFace
            );
            // Position based on screen on
            indicatorLayout.alignX = 2;
            indicatorLayout.alignY=0;
            indicatorLayout.setRect(
                    (int) (2*settings.indicatorLeft+640),
                    (int) (settings.indicatorFontSize)
            );
            indicatorLayout.setStart(
                    -320,
                    (int) (settings.indicatorTop-((float)settings.font_ratio/100)*settings.indicatorFontSize)
            );
            //Add it to the list
            slpt_objects.add(indicatorLayout);
        }

        // Draw Seconds
        if(settings.secondsBool){
            SlptLinearLayout secondsLayout = new SlptLinearLayout();
            secondsLayout.add(new SlptSecondHView());
            secondsLayout.add(new SlptSecondLView());
            secondsLayout.setTextAttrForAll(
                    settings.secondsFontSize,
                    settings.secondsColor,
                    ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE)
            );
            // Position based on screen on
            secondsLayout.alignX = 2;
            secondsLayout.alignY = 0;
            secondsLayout.setRect(
                    (int) (2*settings.secondsLeft+640),
                    (int) (settings.secondsFontSize)
            );
            secondsLayout.setStart(
                    -320,
                    (int) (settings.secondsTop-((float)settings.font_ratio/100)*settings.secondsFontSize)
            );
            //Add it to the list
            slpt_objects.add(secondsLayout);
        }

        // Draw DATE (30.12.2018)
        if(settings.date>0){
            // Show or Not icon
            if (settings.dateIcon) {
                SlptPictureView dateIcon = new SlptPictureView();
                dateIcon.setImagePicture( SimpleFile.readFileFromAssets(service, ( (better_resolution)?"":"slpt_" )+"icons/date.png") );
                dateIcon.setStart(
                        (int) settings.dateIconLeft,
                        (int) settings.dateIconTop
                );
                slpt_objects.add(dateIcon);
            }

            // Set . string
            SlptPictureView point = new SlptPictureView();
            point.setStringPicture(".");
            SlptPictureView point2 = new SlptPictureView();
            point2.setStringPicture(".");

            SlptLinearLayout dateLayout = new SlptLinearLayout();
            dateLayout.add(new SlptDayHView());
            dateLayout.add(new SlptDayLView());
            dateLayout.add(point);//add .
            dateLayout.add(new SlptMonthHView());
            dateLayout.add(new SlptMonthLView());
            dateLayout.add(point2);//add .
            dateLayout.add(new SlptYear3View());
            dateLayout.add(new SlptYear2View());
            dateLayout.add(new SlptYear1View());
            dateLayout.add(new SlptYear0View());
            dateLayout.setTextAttrForAll(
                    settings.dateFontSize,
                    settings.dateColor,
                    timeTypeFace);
            // Position based on screen on
            dateLayout.alignX = 2;
            dateLayout.alignY = 0;
            tmp_left = (int) settings.dateLeft;
            if(!settings.dateAlignLeft) {
                // If text is centered, set rectangle
                dateLayout.setRect(
                        (int) (2 * tmp_left + 640),
                        (int) (settings.dateFontSize)
                );
                tmp_left = -320;
            }
            dateLayout.setStart(
                    tmp_left,
                    (int) (settings.dateTop-((float)settings.font_ratio/100)*settings.dateFontSize)
            );
            //Add it to the list
            slpt_objects.add(dateLayout);
        }

        // Draw day of month
        if(settings.dayBool){
            SlptLinearLayout dayLayout = new SlptLinearLayout();
            dayLayout.add(new SlptDayHView());
            dayLayout.add(new SlptDayLView());
            dayLayout.setTextAttrForAll(
                    settings.dayFontSize,
                    settings.dayColor,
                    timeTypeFace);
            // Position based on screen on
            dayLayout.alignX = 2;
            dayLayout.alignY = 0;
            tmp_left = (int) settings.dayLeft;
            if(!settings.dayAlignLeft) {
                // If text is centered, set rectangle
                dayLayout.setRect(
                        (int) (2 * tmp_left + 640),
                        (int) (settings.dayFontSize)
                );
                tmp_left = -320;
            }
            dayLayout.setStart(
                    tmp_left,
                    (int) (settings.dayTop-((float)settings.font_ratio/100)*settings.dayFontSize)
            );
            //Add it to the list
            slpt_objects.add(dayLayout);
        }

        // Draw month
        if(settings.monthBool){
            SlptLinearLayout monthLayout = new SlptLinearLayout();
            //monthLayout.add(new SlptMonthHView());
            monthLayout.add(new SlptMonthLView());
            // Fix 00 type of month
                // JAVA calendar get/show time library
                Calendar calendar = Calendar.getInstance();
                int month = calendar.get(Calendar.MONTH);
                if(month>=9){
                    months_3let[2] = months_3let[0];
                    months_3let[0] = months_3let[10];
                    months_3let[1] = months_3let[11];
                    months[2] = months_3let[0];
                    months[0] = months_3let[10];
                    months[1] = months_3let[11];
                }
            if(settings.month_as_text) { // if as text
                if (service.getResources().getBoolean(R.bool.three_letters_month_if_text)) {
                    monthLayout.setStringPictureArrayForAll(months_3let[settings.language]);
                } else {
                    monthLayout.setStringPictureArrayForAll(months[settings.language]);
                }
            }
            monthLayout.setTextAttrForAll(
                    settings.monthFontSize,
                    settings.monthColor,
                    timeTypeFace);
            // Position based on screen on
            monthLayout.alignX = 2;
            monthLayout.alignY = 0;
            tmp_left = (int) settings.monthLeft;
            if(!settings.monthAlignLeft) {
                // If text is centered, set rectangle
                monthLayout.setRect(
                        (int) (2 * tmp_left + 640),
                        (int) (settings.monthFontSize)
                );
                tmp_left = -320;
            }
            monthLayout.setStart(
                    tmp_left,
                    (int) (settings.monthTop-((float)settings.font_ratio/100)*settings.monthFontSize)
            );
            //Add it to the list
            slpt_objects.add(monthLayout);
        }

        // Draw year number
        if(settings.yearBool){
            SlptLinearLayout yearLayout = new SlptLinearLayout();
            yearLayout.add(new SlptYear3View());
            yearLayout.add(new SlptYear2View());
            yearLayout.add(new SlptYear1View());
            yearLayout.add(new SlptYear0View());
            yearLayout.setTextAttrForAll(
                    settings.yearFontSize,
                    settings.yearColor,
                    timeTypeFace
            );
            // Position based on screen on
            yearLayout.alignX = 2;
            yearLayout.alignY = 0;
            tmp_left = (int) settings.yearLeft;
            if(!settings.yearAlignLeft) {
                // If text is centered, set rectangle
                yearLayout.setRect(
                        (int) (2 * tmp_left + 640),
                        (int) (settings.yearFontSize)
                );
                tmp_left = -320;
            }
            yearLayout.setStart(
                    tmp_left,
                    (int) (settings.yearTop-((float)settings.font_ratio/100)*settings.yearFontSize)
            );
            //Add it to the list
            slpt_objects.add(yearLayout);
        }

        // Set day name font
        Typeface weekfont = ResourceManager.getTypeFace(service.getResources(), ResourceManager.Font.FONT_FILE);

        // Draw day name
        if(settings.weekdayBool){
            SlptLinearLayout WeekdayLayout = new SlptLinearLayout();
            WeekdayLayout.add(new SlptWeekView());
            if(settings.three_letters_day_if_text){
                WeekdayLayout.setStringPictureArrayForAll(days_3let[settings.language]);
            }else{
                WeekdayLayout.setStringPictureArrayForAll(days[settings.language]);
            }
            WeekdayLayout.setTextAttrForAll(
                    settings.weekdayFontSize,
                    settings.weekdayColor,
                    weekfont
            );
            // Position based on screen on
            WeekdayLayout.alignX = 2;
            WeekdayLayout.alignY = 0;
            tmp_left = (int) settings.weekdayLeft;
            if(!settings.weekdayAlignLeft) {
                // If text is centered, set rectangle
                WeekdayLayout.setRect(
                        (int) (2 * tmp_left + 640),
                        (int) (settings.weekdayFontSize)
                );
                tmp_left = -320;
            }
            WeekdayLayout.setStart(
                    tmp_left,
                    (int) (settings.weekdayTop-((float)settings.font_ratio/100)*settings.weekdayFontSize)
            );
            //Add it to the list
            slpt_objects.add(WeekdayLayout);
        }

        return slpt_objects;
    }
}
