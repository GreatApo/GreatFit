package com.dinodevs.greatfitwatchface.widget;

import android.app.Service;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.text.TextPaint;
import android.util.Log;
import android.widget.Toast;

import com.dinodevs.greatfitwatchface.AbstractWatchFace;
import com.dinodevs.greatfitwatchface.resource.SlptAnalogHourView;
import com.dinodevs.greatfitwatchface.resource.SlptSecondHView;
import com.dinodevs.greatfitwatchface.resource.SlptSecondLView;
import com.dinodevs.greatfitwatchface.settings.LoadSettings;
import com.huami.watch.watchface.util.Util;
import com.ingenic.iwds.slpt.view.analog.SlptAnalogMinuteView;
import com.ingenic.iwds.slpt.view.analog.SlptAnalogSecondView;
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
import com.ingenic.iwds.slpt.view.sport.SlptSportUtil;
import com.ingenic.iwds.slpt.view.utils.SimpleFile;


public class MainClock extends DigitalClockWidget {

    private TextPaint hourFont, minutesFont, secondsFont, indicatorFont, dateFont, dayFont, weekdayFont, monthFont, yearFont, ampmFont;
    private Bitmap dateIcon, hourHand, minuteHand, secondsHand, background;

    private String[] digitalNums = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private String[] digitalNumsNo0 = {"", "1", "2", "3", "4", "5", "6", "7", "8", "9"};//no 0 on first digit

    // Languages
    public static String[] codes = {
            "English", "Български", "中文", "Hrvatski", "Czech", "Dansk", "Nederlands", "Français", "Deutsch", "Ελληνικά", "עברית", "Magyar", "Italiano", "日本語", "한국어", "Polski", "Português", "Română", "Русский", "Slovenčina", "Español", "ไทย", "Türkçe", "Tiếng Việt"
    };

    private static String[][] days = {
            //{"SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"},
            {"SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"},     //English
            {"НЕДЕЛЯ", "ПОНЕДЕЛНИК", "ВТОРНИК", "СРЯДА", "ЧЕТВЪРТЪК", "ПЕТЪК", "СЪБОТА"},       //Bulgarian
            {"星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"},                   //Chinese
            {"NEDJELJA", "PONEDJELJAK", "UTORAK", "SRIJEDA", "ČETVRTAK", "PETAK", "SUBOTA"},    //Croatian
            {"NEDĚLE","PONDĚLÍ", "ÚTERÝ", "STŘEDA", "ČTVRTEK", "PÁTEK", "SOBOTA"},              //Czech
            {"SØNDAG","MANDAG", "TIRSDAG", "ONSDAG", "TORSDAG", "FREDAG", "LØRDAG"},            //Danish
            {"ZONDAG", "MAANDAG", "DINSDAG", "WOENSDAG", "DONDERDAG", "VRIJDAG", "ZATERDAG"},   //Dutch
            {"DIMANCHE", "LUNDI", "MARDI", "MERCREDI", "JEUDI", "VENDREDI", "SAMEDI"},          //French
            {"SONNTAG", "MONTAG", "DIENSTAG", "MITTWOCH", "DONNERSTAG", "FREITAG", "SAMSTAG"},  //German
            {"ΚΥΡΙΑΚΉ", "ΔΕΥΤΈΡΑ", "ΤΡΊΤΗ", "ΤΕΤΆΡΤΗ", "ΠΈΜΠΤΗ", "ΠΑΡΑΣΚΕΥΉ", "ΣΆΒΒΑΤΟ"},       //Greek
            {"ש'","ו'","ה'","ד'","ג'","ב'","א'"},                                               //Hebrew
            {"VASÁRNAP", "HÉTFŐ", "KEDD", "SZERDA", "CSÜTÖRTÖK", "PÉNTEK", "SZOMBAT"},          //Hungarian
            {"DOMENICA", "LUNEDÌ", "MARTEDÌ", "MERCOLEDÌ", "GIOVEDÌ", "VENERDÌ", "SABATO"},     //Italian
            {"日曜日", "月曜日", "火曜日", "水曜日", "木曜日", "金曜日", "土曜日"},                   //Japanese
            {"일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"},                   //Korean
            {"NIEDZIELA", "PONIEDZIAŁEK", "WTOREK", "ŚRODA", "CZWARTEK", "PIĄTEK", "SOBOTA"},   //Polish
            {"DOMINGO", "SEGUNDA", "TERÇA", "QUARTA", "QUINTA", "SEXTA", "SÁBADO"},             //Portuguese
            {"DUMINICĂ", "LUNI", "MARȚI", "MIERCURI", "JOI", "VINERI", "SÂMBĂTĂ"},              //Romanian
            {"ВОСКРЕСЕНЬЕ", "ПОНЕДЕЛЬНИК", "ВТОРНИК", "СРЕДА", "ЧЕТВЕРГ", "ПЯТНИЦА", "СУББОТА"},//Russian
            {"NEDEĽA", "PONDELOK", "UTOROK", "STREDA", "ŠTVRTOK", "PIATOK", "SOBOTA"},          //Slovak
            {"DOMINGO", "LUNES", "MARTES", "MIÉRCOLES", "JUEVES", "VIERNES", "SÁBADO"},         //Spanish
            {"อาทิตย์", "จันทร์", "อังคาร", "พุธ", "พฤหัสบดี", "ุกร์", "สาร์"},                               //Thai
            {"PAZAR", "PAZARTESI", "SALı", "ÇARŞAMBA", "PERŞEMBE", "CUMA", "CUMARTESI"},        //Turkish
            {"CHỦ NHẬT","THỨ 2", "THỨ 3", "THỨ 4", "THỨ 5", "THỨ 6", "THỨ 7"}                   //Vietnamese
    };
    
    public static String[][] days_3let = {
            //{"SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"},
            {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"},                  //English
            {"НЕД", "ПОН", "ВТО", "СРЯ", "ЧЕТ", "ПЕТ", "СЪБ"},                  //Bulgarian
            {"星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"},   //Chinese
            {"NED", "PON", "UTO", "SRI", "ČET", "PET", "SUB"},                  //Croatian
            {"NE", "PO", "ÚT", "ST", "ČT", "PÁ", "SO"},                         //Czech
            {"SØN","MAN", "TIR", "ONS", "TOR", "FRE", "LØR"},                   //Danish
            {"ZON", "MAA", "DIN", "WOE", "DON", "VRI", "ZAT"},                  //Dutch
            {"DIM", "LUN", "MAR", "MER", "JEU", "VEN", "SAM"},                  //French
            {"SO", "MO", "DI", "MI", "DO", "FR", "SA"},                         //German
            {"ΚΥΡ", "ΔΕΥ", "ΤΡΙ", "ΤΕΤ", "ΠΕΜ", "ΠΑΡ", "ΣΑΒ"},                  //Greek
            {"א'", "ב'", "ג'", "ד'", "ה'", "ו'", "ש'"},                         //Hebrew
            {"VAS", "HÉT", "KED", "SZE", "CSÜ", "PÉN", "SZO"},                  //Hungarian
            {"DOM", "LUN", "MAR", "MER", "GIO", "VEN", "SAB"},                  //Italian
            {"日曜日", "月曜日", "火曜日", "水曜日", "木曜日", "金曜日", "土曜日"},   //Japanese
            {"일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"},   //Korean
            {"NIE", "PON", "WTO", "ŚRO", "CZW", "PIĄ", "SOB"},                  //Polish
            {"DOM", "SEG", "TER", "QUA", "QUI", "SEX", "SÁB"},                  //Portuguese
            {"DUM", "LUN", "MAR", "MIE", "JOI", "VIN", "SÂM"},                  //Romanian
            {"ВСК", "ПНД", "ВТР", "СРД", "ЧТВ", "ПТН", "СБТ"},                  //Russian
            {"NED", "PON", "UTO", "STR", "ŠTV", "PIA", "SOB"},                  //Slovak
            {"DOM", "LUN", "MAR", "MIÉ", "JUE", "VIE", "SÁB"},                  //Spanish
            {"อา.", "จ.", "อ.", "พ.", "พฤ.", "ศ.", "ส."},                        //Thai
            {"PAZ", "PZT", "SAL", "ÇAR", "PER", "CUM", "CMT"},                  //Turkish
            {"CN","T2", "T3", "T4", "T5", "T6", "T7"}                           //Vietnamese
    };

    private static String[][] months = {
            //{"DECEMBER", "JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"},
            {"DECEMBER", "JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"},                               //English
            {"ДЕКЕМВРИ", "ЯНУАРИ", "ФЕВРУАРИ", "МАРТ", "АПРИЛ", "МАЙ", "ЮНИ", "ЮЛИ", "АВГУСТ", "СЕПТЕМВРИ", "ОКТОМВРИ", "НОЕМВРИ" , "ДЕКЕМВРИ"},                                  //Bulgarian
            {"十二月", "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"},                                                               //Chinese
            {"PROSINAC", "SIJEČANJ", "VELJAČA", "OŽUJAK", "TRAVANJ", "SVIBANJ", "LIPANJ", "SRPANJ", "KOLOVOZ", "RUJAN", "LISTOPAD", "STUDENI", "PROSINAC"},                       //Croatian
            {"PROSINEC", "LEDEN", "ÚNOR", "BŘEZEN", "DUBEN", "KVĚTEN", "ČERVEN", "ČERVENEC", "SRPEN", "ZÁŘÍ", "ŘÍJEN", "LISTOPAD", "PROSINEC"},                                   //Czech
            {"DECEMBER", "JANUAR", "FEBRUAR", "MARTS", "APRIL", "MAJ", "JUNI", "JULI", "AUGUST", "SEPTEMBER", "OKTOBER", "NOVEMBER", "DECEMBER"},                                 //Danish
            {"DECEMBER", "JANUARI", "FEBRUARI", "MAART", "APRIL", "MEI", "JUNI", "JULI", "AUGUSTUS", "SEPTEMBER", "OKTOBER", "NOVEMBER", "DECEMBER"},                             //Dutch
            {"DÉCEMBRE", "JANVIER", "FÉVRIER", "MARS", "AVRIL", "MAI", "JUIN", "JUILLET", "AOÛT", "SEPTEMBRE", "OCTOBRE", "NOVEMBRE", "DÉCEMBRE"},                                //French
            {"DEZEMBER", "JANUAR", "FEBRUAR", "MÄRZ", "APRIL", "MAI", "JUNI", "JULI", "AUGUST", "SEPTEMBER", "OKTOBER", "NOVEMBER", "DEZEMBER"},                                  //German
            {"ΔΕΚΈΜΒΡΙΟΣ", "ΙΑΝΟΥΆΡΙΟΣ", "ΦΕΒΡΟΥΆΡΙΟΣ", "ΜΆΡΤΙΟΣ", "ΑΠΡΊΛΙΟΣ", "ΜΆΙΟΣ", "ΙΟΎΝΙΟΣ", "ΙΟΎΛΙΟΣ", "ΑΎΓΟΥΣΤΟΣ", "ΣΕΠΤΈΜΒΡΙΟΣ", "ΟΚΤΏΒΡΙΟΣ", "ΝΟΈΜΒΡΙΟΣ", "ΔΕΚΈΜΒΡΙΟΣ"},//Greek
            {"דצמבר", "ינואר", "פברואר", "מרץ", "אפריל", "מאי", "יוני", "יולי", "אוגוסט", "ספטמבר", "אוקטובר", "נובמבר", "דצמבר"},                                                //Hebrew
            {"DECEMBER", "JANUÁR", "FEBRUÁR", "MÁRCIUS", "ÁPRILIS", "MÁJUS", "JÚNIUS", "JÚLIUS", "AUGUSZTUS", "SZEPTEMBER", "OKTÓBER", "NOVEMBER", "DECEMBER"},                  //Hungarian
            {"DICEMBRE", "GENNAIO", "FEBBRAIO", "MARZO", "APRILE", "MAGGIO", "GIUGNO", "LUGLIO", "AGOSTO", "SETTEMBRE", "OTTOBRE", "NOVEMBRE", "DICEMBRE"},                      //Italian
            {"12月", "1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"},                                                                        //Japanese
            {"12월", "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"},                                                                        //Korean
            {"GRUDZIEŃ", "STYCZEŃ", "LUTY", "MARZEC", "KWIECIEŃ", "MAJ", "CZERWIEC", "LIPIEC", "SIERPIEŃ", "WRZESIEŃ", "PAŹDZIERNIK", "LISTOPAD", "GRUDZIEŃ"},                  //Polish
            {"DEZEMBRO", "JANEIRO", "FEVEREIRO", "MARÇO", "ABRIL", "MAIO", "JUNHO", "JULHO", "AGOSTO", "SETEMBRO", "OUTUBRO", "NOVEMBRO", "DEZEMBRO"},                          //Portuguese
            {"DECEMBRIE", "IANUARIE", "FEBRUARIE", "MARTIE", "APRILIE", "MAI", "IUNIE", "IULIE", "AUGUST", "SEPTEMBRIE", "OCTOMBRIE", "NOIEMBRIE", "DECEMBRIE"},                //Romanian
            {"ДЕКАБРЬ", "ЯНВАРЬ", "ФЕВРАЛЬ", "МАРТ", "АПРЕЛЬ", "МАЙ", "ИЮНЬ", "ИЮЛЬ", "АВГУСТ", "СЕНТЯБРЬ", "ОКТЯБРЬ", "НОЯБРЬ", "ДЕКАБРЬ"},                                    //Russian
            {"DECEMBER", "JANUÁR", "FEBRUÁR", "MAREC", "APRÍL", "MÁJ", "JÚN", "JÚL", "AUGUST", "SEPTEMBER", "OKTÓBER", "NOVEMBER", "DECEMBER"},                                 //Slovak
            {"DICIEMBRE", "ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO", "JULIO", "AGOSTO", "SEPTIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE"},                         //Spanish
            {"ันวาคม", "มกราคม", "กุมภาพันธ์", "ีนาคม", "เมษายน", "พฤษภาคม", "มิถุนายน", "กรกฎาคม", "สิงหาคม", "กันยายน", "ตุลาคม", "พฤศจิกายน"},                                                 //Thai
            {"ARALıK", "OCAK", "ŞUBAT", "MART", "NISAN", "MAYıS", "HAZIRAN", "TEMMUZ", "AĞUSTOS", "EYLÜL", "EKIM", "KASıM", "ARALıK"},                                          //Turkish
            {"THÁNG 12", "THÁNG 1", "THÁNG 2", "THÁNG 3", "THÁNG 4", "THÁNG 5", "THÁNG 6", "THÁNG 7", "THÁNG 8", "THÁNG 9", "THÁNG 10", "THÁNG 11", "THÁNG 12"}                 //Vietnamese
    };

    private static String[][] months_3let = {
            //{"DECEMBER", "JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"},
            {"DEC", "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"},            //English
            {"ДЕК", "ЯНУ", "ФЕВ", "МАР", "АПР", "МАЙ", "ЮНИ", "ЮЛИ", "АВГ", "СЕП", "ОКТ", "НОЕ", "ДЕК"},            //Bulgarian
            {"十二月", "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"}, //Chinese
            {"PRO", "SIJ", "VE", "OŽU", "TRA", "SVI", "LIP", "SRP", "KOL", "RUJ", "LIS", "STU", "PRO"},             //Croatian
            {"PRO", "LED", "ÚNO", "BŘE", "DUB", "KVĚ", "ČER", "ČER", "SRP", "ZÁŘ", "ŘÍJ", "LIS", "PRO"},            //Czech
            {"DEC", "JAN", "FEB", "MAR", "APR", "MAJ", "JUN", "JUL", "AUG", "SEP", "OKT", "NOV", "DEC"},            //Danish
            {"DEC", "JAN", "FEB", "MAA", "APR", "MEI", "JUN", "JUL", "AUG", "SEP", "OKT", "NOV", "DEC"},            //Dutch
            {"DÉC", "JAN", "FÉV", "MAR", "AVR", "MAI", "JUI", "JUI", "AOÛ", "SEP", "OCT", "NOV", "DÉC"},            //French
            {"DEZ", "JAN", "FEB", "MÄR", "APR", "MAI", "JUN", "JUL", "AUG", "SEP", "OKT", "NOV", "DEZ"},            //German
            {"ΔΕΚ", "ΙΑΝ", "ΦΕΒ", "ΜΑΡ", "ΑΠΡ", "ΜΑΙ", "ΙΟΥΝ", "ΙΟΥΛ", "ΑΥΓ", "ΣΕΠ", "ΟΚΤ", "ΝΟΕ", "ΔΕΚ"},          //Greek
            {"דצמ", "ינו", "פבר", "מרץ", "אפר", "מאי", "יונ", "יול", "אוג", "ספט", "אוק", "נוב", "דצמ"},            //Hebrew
            {"DEC", "JAN", "FEB", "MÁR", "ÁPR", "MÁJ", "JÚN", "JÚL", "AUG", "SZE", "OKT", "NOV", "DEC"},            //Hungarian
            {"DIC", "GEN", "FEB", "MAR", "APR", "MAG", "GIU", "LUG", "AGO", "SET", "OTT", "NOV", "DIC"},            //Italian
            {"12月", "1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"},            //Japanese
            {"12월", "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"},            //Korean
            {"GRU", "STY", "LUT", "MAR", "KWI", "MAJ", "CZE", "LIP", "SIE", "WRZ", "PAŹ", "LIS", "GRU"},            //Polish
            {"DEZ", "JAN", "FEV", "MAR", "ABR", "MAI", "JUN", "JUL", "AGO", "SET", "OUT", "NOV", "DEZ"},            //Portuguese
            {"DEC", "IAN", "FEB", "MAR", "APR", "MAI", "IUN", "IUL", "AUG", "SEP", "OCT", "NOI", "DEC"},            //Romanian
            {"ДЕК", "ЯНВ", "ФЕВ", "МАР", "АПР", "МАЙ", "ИЮН", "ИЮЛ", "АВГ", "СЕН", "ОКТ", "НОЯ", "ДЕК"},            //Russian
            {"DEC", "JAN", "FEB", "MAR", "APR", "MÁJ", "JÚN", "JÚL", "AUG", "SEP", "OKT", "NOV", "DEC"},            //Slovak
            {"DIC", "ENE", "FEB", "MAR", "ABR", "MAY", "JUN", "JUL", "AGO", "SEP", "OCT", "NOV", "DIC"},            //Spanish
            {"ธ.ค.", "ม.ค.", "ก.พ.", "มี.ค.", "เม.ย.", "พ.ค.", "มิ.ย.", "ก.ค.", "ส.ค.", "ก.ย.", "ต.ค.", "พ.ย.", "ธ.ค."},//Thai
            {"ARA", "OCA", "ŞUB", "MAR", "NIS", "MAY", "HAZ", "TEM", "AĞU", "EYL", "EKI", "KAS", "ARA"},            //Turkish
            {"T12", "T1", "T2", "T3", "T4", "T5", "T6", "T7", "T8", "T9", "T10", "T11", "T12"}                      //Vietnamese
    };

    private Service mService;
    private LoadSettings settings;

    public MainClock(LoadSettings settings) {
        this.settings = settings;
    }

    @Override
    public void init(Service service) {
        //this.background = service.getResources().getDrawable(R.drawable.background); //todo
        //this.background.setBounds(0, 0, 320, 300);
        this.background = Util.decodeImage(service.getResources(),settings.is_white_bg+"background.png");
        if(settings.isVerge())
            this.background = Bitmap.createScaledBitmap(this.background, 360, 360, true);

        if(settings.digital_clock) {
            this.hourFont = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
            this.hourFont.setTypeface(ResourceManager.getTypeFace(service.getResources(), settings.font));
            this.hourFont.setTextSize(settings.hoursFontSize);
            this.hourFont.setColor(settings.hoursColor);
            this.hourFont.setTextAlign((settings.hoursAlignLeft) ? Paint.Align.LEFT : Paint.Align.CENTER);

            this.minutesFont = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
            this.minutesFont.setTypeface(ResourceManager.getTypeFace(service.getResources(), settings.font));
            this.minutesFont.setTextSize(settings.minutesFontSize);
            this.minutesFont.setColor(settings.minutesColor);
            this.minutesFont.setTextAlign((settings.minutesAlignLeft) ? Paint.Align.LEFT : Paint.Align.CENTER);

            this.secondsFont = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
            this.secondsFont.setTypeface(ResourceManager.getTypeFace(service.getResources(), settings.font));
            this.secondsFont.setTextSize(settings.secondsFontSize);
            this.secondsFont.setColor(settings.secondsColor);
            this.secondsFont.setTextAlign((settings.secondsAlignLeft) ? Paint.Align.LEFT : Paint.Align.CENTER);

            this.indicatorFont = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
            this.indicatorFont.setTypeface(ResourceManager.getTypeFace(service.getResources(), settings.font));
            this.indicatorFont.setTextSize(settings.indicatorFontSize);
            this.indicatorFont.setColor(settings.indicatorColor);
            this.indicatorFont.setTextAlign((settings.indicatorAlignLeft) ? Paint.Align.LEFT : Paint.Align.CENTER);

            this.ampmFont = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
            this.ampmFont.setColor(settings.am_pmColor);
            this.ampmFont.setTypeface(ResourceManager.getTypeFace(service.getResources(), settings.font));
            this.ampmFont.setTextSize(settings.am_pmFontSize);
            this.ampmFont.setTextAlign((settings.am_pmAlignLeft) ? Paint.Align.LEFT : Paint.Align.CENTER);
        }

        if(settings.analog_clock) {
            this.hourHand = Util.decodeImage(service.getResources(),"timehand/hour"+ ((settings.isVerge())?"_verge":"") +".png");
            this.minuteHand = Util.decodeImage(service.getResources(),"timehand/minute"+ ((settings.isVerge())?"_verge":"") +".png");
            this.secondsHand = Util.decodeImage(service.getResources(),"timehand/seconds"+ ((settings.isVerge())?"_verge":"") +".png");
        }

        if(settings.date>0) {
            this.dateFont = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
            this.dateFont.setTypeface(ResourceManager.getTypeFace(service.getResources(), settings.font));
            this.dateFont.setTextSize(settings.dateFontSize);
            this.dateFont.setColor(settings.dateColor);
            this.dateFont.setTextAlign((settings.dateAlignLeft) ? Paint.Align.LEFT : Paint.Align.CENTER);
            if (settings.dateIcon) {
                this.dateIcon = Util.decodeImage(service.getResources(), "icons/"+settings.is_white_bg+"date.png");
            }
        }

        this.weekdayFont = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        this.weekdayFont.setTypeface(ResourceManager.getTypeFace(service.getResources(), settings.font));
        this.weekdayFont.setTextSize(settings.weekdayFontSize);
        this.weekdayFont.setColor(settings.weekdayColor);
        this.weekdayFont.setTextAlign( (settings.weekdayAlignLeft) ? Paint.Align.LEFT : Paint.Align.CENTER );

        this.dayFont = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        this.dayFont.setTypeface(ResourceManager.getTypeFace(service.getResources(), settings.font));
        this.dayFont.setTextSize(settings.dayFontSize);
        this.dayFont.setColor(settings.dayColor);
        this.dayFont.setTextAlign( (settings.dayAlignLeft) ? Paint.Align.LEFT : Paint.Align.CENTER );

        this.monthFont = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        this.monthFont.setTypeface(ResourceManager.getTypeFace(service.getResources(), settings.font));
        this.monthFont.setTextSize(settings.monthFontSize);
        this.monthFont.setColor(settings.monthColor);
        this.monthFont.setTextAlign( (settings.monthAlignLeft) ? Paint.Align.LEFT : Paint.Align.CENTER );

        this.yearFont = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        this.yearFont.setTypeface(ResourceManager.getTypeFace(service.getResources(), settings.font));
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

        if(settings.digital_clock) {
            // Draw hours
            canvas.drawText((settings.no_0_on_hour_first_digit) ? hours + "" : Util.formatTime(hours), settings.hoursLeft, settings.hoursTop, this.hourFont);

            // Draw minutes
            canvas.drawText(Util.formatTime(minutes), settings.minutesLeft, settings.minutesTop, this.minutesFont);

            // Draw Seconds
            if (settings.secondsBool) {
                canvas.drawText(Util.formatTime(seconds), settings.secondsLeft, settings.secondsTop, this.secondsFont);
            }

            // : indicator Draw + Flashing
            if (settings.indicatorBool) {
                String indicator = ":";
                if (seconds % 2 == 0 || !settings.flashing_indicator) { // Draw only on even seconds (flashing : symbol)
                    canvas.drawText(indicator, settings.indicatorLeft, settings.indicatorTop, this.indicatorFont);
                }
            }

            // AM-PM (ONLY FOR 12h format)
            switch (ampm) {
                case 0:
                    canvas.drawText("AM", this.settings.am_pmLeft, this.settings.am_pmTop, this.ampmFont);
                    break;
                case 1:
                    canvas.drawText("PM", this.settings.am_pmLeft, this.settings.am_pmTop, this.ampmFont);
                    break;
                default:
                    //Log.d("DinoDevs-GreatFit", "AM-PM: 24h time format is on");
            }
        }

        if(settings.analog_clock) {
            canvas.save();
            canvas.rotate(((float) (hours * 30)) + ((((float) minutes) / 60.0f) * 30.0f), 160.0f + (settings.isVerge()?20f:0f), 159.0f + (settings.isVerge()?20f:0f));
            canvas.drawBitmap(this.hourHand, centerX - this.hourHand.getWidth() / 2f, centerY - this.hourHand.getHeight() / 2f, null);
            canvas.restore();
            canvas.save();
            canvas.rotate((float) (minutes * 6), 160.0f + (settings.isVerge()?20f:0f), 159.0f + (settings.isVerge()?20f:0f));
            canvas.drawBitmap(this.minuteHand, centerX - this.minuteHand.getWidth() / 2f, centerY - this.minuteHand.getHeight() / 2f, null);
            canvas.restore();
            if (settings.secondsBool) {
                canvas.save();
                canvas.rotate((float) (seconds * 6), 160.0f + (settings.isVerge() ? 20f : 0f), 159.0f + (settings.isVerge() ? 20f : 0f));
                canvas.drawBitmap(this.secondsHand, centerX - this.secondsHand.getWidth() / 2f, centerY - this.secondsHand.getHeight() / 2f, null);
                canvas.restore();
            }
        }

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
            String monthText = (settings.month_as_text)? (
                    (settings.three_letters_month_if_text)? months_3let[settings.language][month] : months[settings.language][month]
            ) : (
                    (settings.no_0_on_hour_first_digit)? Integer.toString(month) : String.format("%02d", month)
            ) ;

            canvas.drawText(monthText, settings.monthLeft, settings.monthTop, this.monthFont);
        }

        // Draw Year
        if(settings.yearBool) {
            canvas.drawText(Integer.toString(year), settings.yearLeft, settings.yearTop, this.yearFont);
        }
    }

    // Screen locked/closed watch mode (Slpt mode)
    @Override
    public List<SlptViewComponent> buildSlptViewComponent(Service service) {
        return buildSlptViewComponent(service, false);
    }

    public List<SlptViewComponent> buildSlptViewComponent(Service service, boolean better_resolution) {
        better_resolution = better_resolution && settings.better_resolution_when_raising_hand;
        // SLPT only clock
        boolean show_all = (!settings.clock_only_slpt || better_resolution);
        // SLPT only clock white bg -> to black
        if(!show_all && settings.isVerge() && settings.white_bg) {
            settings.is_white_bg = "";
            settings.hoursColor = Color.parseColor("#ffffff");
            settings.minutesColor = Color.parseColor("#ffffff");
            settings.am_pmColor = Color.parseColor("#ffffff");
        }
        this.mService = service;

        int tmp_left;
        List<SlptViewComponent> slpt_objects = new ArrayList<>();

        // Draw background image
        SlptPictureView background = new SlptPictureView();
        background.setImagePicture(SimpleFile.readFileFromAssets(service, settings.is_white_bg+"background"+ ((better_resolution)?"_better":"") + ((settings.isVerge())?"_verge":"") +"_slpt.png"));
        //Alternative way
        //background.setImagePicture(ResourceManager.getVergeImageFromAssets(settings.isVerge(), service, "background"+ ((better_resolution)?"_better":"") +"_slpt.png"));
        slpt_objects.add(background);

        // Set low power icon
        if(settings.low_power) {
            // Draw low power icon
            SlptPictureView lowpower = new SlptPictureView();
            lowpower.setImagePicture(SimpleFile.readFileFromAssets(service, "slpt_battery/" + settings.is_white_bg + "low_battery.png"));
            //lowpower.picture.setBackgroundColor(backgroundColor);
            lowpower.setStart(
                    (int) settings.low_powerLeft,
                    (int) settings.low_powerTop
            );
            SlptSportUtil.setLowBatteryIconView(lowpower);
            slpt_objects.add(lowpower);
        }

        // Set font
        Typeface timeTypeFace = ResourceManager.getTypeFace(service.getResources(), settings.font);

        if(settings.digital_clock) {
            // Draw hours
            if (settings.hoursBool) {
                SlptLinearLayout hourLayout = new SlptLinearLayout();
                if (settings.no_0_on_hour_first_digit) {// No 0 on first digit
                    SlptViewComponent firstDigit = new SlptHourHView();
                    ((SlptNumView) firstDigit).setStringPictureArray(this.digitalNumsNo0);
                    hourLayout.add(firstDigit);
                    SlptViewComponent secondDigit = new SlptHourLView();
                    ((SlptNumView) secondDigit).setStringPictureArray(this.digitalNums);
                    hourLayout.add(secondDigit);
                } else {
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
                hourLayout.alignY = 0;
                hourLayout.setRect(
                        (int) (2 * settings.hoursLeft + 640),
                        (int) (((float) settings.font_ratio / 100) * settings.hoursFontSize)
                );
                hourLayout.setStart(
                        -320,
                        (int) (settings.hoursTop - ((float) settings.font_ratio / 100) * settings.hoursFontSize)
                );
                //Add it to the list
                slpt_objects.add(hourLayout);
            }

            // Draw minutes
            if (settings.minutesBool) {
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
                minuteLayout.alignY = 0;
                minuteLayout.setRect(
                        (int) (2 * settings.minutesLeft + 640),
                        (int) (((float) settings.font_ratio / 100) * settings.minutesFontSize)
                );
                minuteLayout.setStart(
                        -320,
                        (int) (settings.minutesTop - ((float) settings.font_ratio / 100) * settings.minutesFontSize)
                );
                //Add it to the list
                slpt_objects.add(minuteLayout);
            }

            // Draw indicator
            if (settings.indicatorBool) {
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
                indicatorLayout.alignY = 0;
                indicatorLayout.setRect(
                        (int) (2 * settings.indicatorLeft + 640),
                        (int) (((float) settings.font_ratio / 100) * settings.indicatorFontSize)
                );
                indicatorLayout.setStart(
                        -320,
                        (int) (settings.indicatorTop - ((float) settings.font_ratio / 100) * settings.indicatorFontSize)
                );
                //Add it to the list
                slpt_objects.add(indicatorLayout);
            }

            // Draw Seconds
            if (settings.secondsBool ) { //&& (!settings.isVerge() || better_resolution)
                SlptLinearLayout secondsLayout = new SlptLinearLayout();
                secondsLayout.add(new SlptSecondHView());
                secondsLayout.add(new SlptSecondLView());
                secondsLayout.setTextAttrForAll(
                        settings.secondsFontSize,
                        settings.secondsColor,
                        ResourceManager.getTypeFace(service.getResources(), settings.font)
                );
                // Position based on screen on
                secondsLayout.alignX = 2;
                secondsLayout.alignY = 0;
                secondsLayout.setRect(
                        (int) (2 * settings.secondsLeft + 640),
                        (int) (((float) settings.font_ratio / 100) * settings.secondsFontSize)
                );
                secondsLayout.setStart(
                        -320,
                        (int) (settings.secondsTop - ((float) settings.font_ratio / 100) * settings.secondsFontSize)
                );
                //Add it to the list
                slpt_objects.add(secondsLayout);
            }

            // AM-PM (ONLY FOR 12h format)
            SlptLinearLayout ampm = new SlptLinearLayout();
            SlptPictureView am = new SlptPictureView();
            SlptPictureView pm = new SlptPictureView();
            am.setStringPicture("AM");
            pm.setStringPicture("PM");
            SlptSportUtil.setAmBgView(am);
            SlptSportUtil.setPmBgView(pm);
            ampm.add(am);
            ampm.add(pm);
            ampm.setTextAttrForAll(settings.am_pmFontSize, settings.am_pmColor, ResourceManager.getTypeFace(service.getResources(), settings.font));
            ampm.alignX = 2;
            ampm.alignY = 0;
            tmp_left = (int) this.settings.am_pmLeft;
            if (!this.settings.am_pmAlignLeft) {
                ampm.setRect((tmp_left * 2) + 640, (int) this.settings.am_pmFontSize);
                tmp_left = -320;
            }
            ampm.setStart(tmp_left, (int) (this.settings.am_pmTop - ((settings.font_ratio / 100.0f) * this.settings.am_pmFontSize)));
            slpt_objects.add(ampm);
        }

        if(settings.analog_clock) {
            SlptAnalogHourView slptAnalogHourView = new SlptAnalogHourView();
            slptAnalogHourView.setImagePicture(SimpleFile.readFileFromAssets(service, "timehand/8c/hour"+ ((settings.isVerge())?"_verge":"") +".png"));
            slptAnalogHourView.alignX = (byte) 2;
            slptAnalogHourView.alignY = (byte) 2;
            slptAnalogHourView.setRect(320 + (settings.isVerge()?40:0), 320 + (settings.isVerge()?40:0));
            slpt_objects.add(slptAnalogHourView);

            SlptAnalogMinuteView slptAnalogMinuteView = new SlptAnalogMinuteView();
            slptAnalogMinuteView.setImagePicture(SimpleFile.readFileFromAssets(service, "timehand/8c/minute"+ ((settings.isVerge())?"_verge":"") +".png"));
            slptAnalogMinuteView.alignX = (byte) 2;
            slptAnalogMinuteView.alignY = (byte) 2;
            slptAnalogMinuteView.setRect(320 + (settings.isVerge()?40:0), 320 + (settings.isVerge()?40:0));
            slpt_objects.add(slptAnalogMinuteView);

            if(settings.secondsBool){
                SlptAnalogSecondView slptAnalogSecondView = new SlptAnalogSecondView();
                slptAnalogSecondView.setImagePicture(SimpleFile.readFileFromAssets(service, "timehand/8c/seconds"+ ((settings.isVerge())?"_verge":"") +".png"));
                slptAnalogSecondView.alignX = (byte) 2;
                slptAnalogSecondView.alignY = (byte) 2;
                slptAnalogSecondView.setRect(320 + (settings.isVerge()?40:0), 320 + (settings.isVerge()?40:0));
                slpt_objects.add(slptAnalogSecondView);
            }
        }

        // Only CLOCK?
        if (!show_all)
            return slpt_objects;

        // Draw DATE (30.12.2018)
        if(settings.date>0){
            // Show or Not icon
            if (settings.dateIcon) {
                SlptPictureView dateIcon = new SlptPictureView();
                dateIcon.setImagePicture( SimpleFile.readFileFromAssets(service, ( (better_resolution)?"26wc_":"slpt_" )+"icons/"+settings.is_white_bg+"date.png") );
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
                        (int) (((float)settings.font_ratio/100)*settings.dateFontSize)
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
                        (int) (((float)settings.font_ratio/100)*settings.dayFontSize)
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
            // JAVA calendar get/show time library
            Calendar calendar = Calendar.getInstance();
            int month = calendar.get(Calendar.MONTH);

            SlptLinearLayout monthLayout = new SlptLinearLayout();

            // if as text
            if(settings.month_as_text) {
                monthLayout.add(new SlptMonthLView());

                // Fix 00 type of month
                if(month>=9){ // 9: October, 10: November, 11: December
                    months_3let[settings.language][0] = months_3let[settings.language][10];
                    months_3let[settings.language][1] = months_3let[settings.language][11];
                    months_3let[settings.language][2] = months_3let[settings.language][12];
                    months[settings.language][0] = months[settings.language][10];
                    months[settings.language][1] = months[settings.language][11];
                    months[settings.language][2] = months[settings.language][12];
                }

                if (settings.three_letters_month_if_text) {
                    monthLayout.setStringPictureArrayForAll(months_3let[settings.language]);
                } else {
                    monthLayout.setStringPictureArrayForAll(months[settings.language]);
                }

            // if as number
            }else{
                // show first digit
                if(month>=9 || !settings.no_0_on_hour_first_digit){
                    monthLayout.add(new SlptMonthHView());
                }
                monthLayout.add(new SlptMonthLView());
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
                        (int) (((float)settings.font_ratio/100)*settings.monthFontSize)
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
                        (int) (((float)settings.font_ratio/100)*settings.yearFontSize)
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
        Typeface weekfont = ResourceManager.getTypeFace(service.getResources(), settings.font);

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
                        (int) (((float)settings.font_ratio/100)*settings.weekdayFontSize)
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
