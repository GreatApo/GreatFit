package com.dinodevs.greatfitwatchface.resource;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**<p>Moon phase calculation routines for computing:</p> 
 * <ol>
 * <li> current moon phase as an index from 0 to 7,</li> 
 * <li> current moon age as days/hours/minutes, and</li>
 * <li> percentage of the moon's illuminated portion</li>
 * </ol>
 * <p>Converted from rc-utils.c in the GCal package 
 * (http://ftp.gnu.org/gnu/gcal/gcal-2.40.tar.gz)</p>
 */
public class MoonPhase {

    //Prefs prefs;

    private static final double MY_PI = 3.14159265358979323846;
    private static final double EPOCH = 2444238.5;    /* 1980 January 0.0. */
    private static final double SUN_ELONG_EPOCH = 278.833540;   /* Ecliptic longitude of the Sun at epoch 1980.0. */
    private static final double SUN_ELONG_PERIGEE = 282.596403;   /* Ecliptic longitude of the Sun at perigee. */
    private static final double ECCENT_EARTH_ORBIT = 0.016718;     /* Eccentricity of Earth's orbit. */
    private static final double MOON_MEAN_LONGITUDE_EPOCH = 64.975464;    /* Moon's mean lonigitude at the epoch. */
    private static final double MOON_MEAN_LONGITUDE_PERIGREE = 349.383063;   /* Mean longitude of the perigee at the epoch. */
    private static final double KEPLER_EPSILON = 1E-6;         /* Accurancy of the Kepler equation. */
    private static final double SYNMONTH    = 29.53058868;    /* Synodic month (new Moon to new Moon) */

    private static final boolean orthodox_calendar = false;

    public static final int mvec[] =
            {
                    0, 31, 59, 90, 120, 151,
                    181, 212, 243, 273, 304, 334
            };
    private static final int greg[] = {1582, 10, 5, 14};
    private static final int YEAR = 0;
    private static final int MONTH = 1;
    public static final int FIRST_DAY = 2;
    private static final int LAST_DAY = 3;
    
    

    //private instance fields
    private Calendar _curCal;
    private double _JD;
    private double _phase;
    private double _moonAgeAsDays;
    
    private String moon_phase_name[] = {
    		"New moon",        	// 0
			"Waxing crescent",	// 1 
			"First quarter",	// 2 
			"Waxing gibbous",	// 3
			"Full moon",		// 4 
			"Waning gibbous",	// 5
			"Last quarter",	// 6
			"Waning crescent"	// 7
			};


    public MoonPhase(Calendar c){
        _curCal = c;
    }

    public MoonPhase() {
        _curCal = Calendar.getInstance();
    }

    /*
     * Some useful mathematical functions used by John Walkers `phase()' function.
     */
    private double FIXANGLE(double a) {
        return (a) - 360.0 * (Math.floor((a) / 360.0));
    }

    private double TORAD(double d) {
        return (d) * (MY_PI / 180.0);
    }

    private double TODEG(double r) {
        return (r) * (180.0 / MY_PI);
    }

    /*
       Solves the equation of Kepler.
    */
    private double kepler(double m) {
        double e;
        double delta;
        e = m = TORAD(m);
        do {
            delta = e - ECCENT_EARTH_ORBIT * Math.sin(e) - m;
            e -= delta / (1.0 - ECCENT_EARTH_ORBIT * Math.cos(e));
        } while (Math.abs(delta) - KEPLER_EPSILON > 0.0);

        return (e);
    }

//    /**
//     * Computes the absolute number of days of the given date since
//     * 00010101(==YYYYMMDD) respecting the missing period of the
//     * Gregorian Reformation.
//     * @param day int day as integer value
//     * @param month int month as integer value
//     * @param year int year as integer value
//     */
//    public static int date2num(int day,
//                        int month,
//                        int year) {
//        int julian_days = (year - 1) * (DAY_LAST) + ((year - 1) >> 2);
//
//
//        if (year > greg[YEAR]
//                || ((year == greg[YEAR])
//                && (month > greg[MONTH]
//                || ((month == greg[MONTH])
//                && (day > greg[LAST_DAY])))))
//            julian_days -= greg[LAST_DAY] - greg[FIRST_DAY] + 1;
//        if (year > greg[YEAR]) {
//            julian_days += (((year - 1) / 400) - (greg[YEAR] / 400));
//            julian_days -= (((year - 1) / 100) - (greg[YEAR] / 100));
//            if (!((greg[YEAR] % 100) == 0)
//                    && ((greg[YEAR] % 400) == 0)) {
//                julian_days--;
//            }
//        }
//        julian_days += mvec[month - 1];
//        julian_days += day;
//        if ((days_of_february(year) == 29)
//                && (month > 2))
//            julian_days++;
//
//        return (julian_days);
//    }
//
    /*
       Computes the number of days in February --- respecting the Gregorian
         Reformation period likewise the leap year rule as used by the
         Eastern orthodox churches --- and returns them.
    */
    private int days_of_february(int year) {
        int day;
        if ((year > greg[YEAR])
                || ((year == greg[YEAR])
                && (greg[MONTH] == 1
                || ((greg[MONTH] == 2)
                && (greg[LAST_DAY] >= 28))))) {
            if (orthodox_calendar)
                day = ((year & 3) != 0) ? 28 : ((!((year % 100) != 0)) ? (((year % 9) == 2 || (year % 9) == 6) ? 29 : 28) : 29);
            else
                day = ((year & 3) != 0) ? 28 : ((!((year % 100) != 0) && ((year % 400) != 0)) ? 28 : 29);
        } else
            day = ((year & 3) != 0) ? 28 : 29;
        /*
           Exception, the year 4 AD was historically NO leap year!
        */
        if (year == 4)
            day--;

        return (day);
    }


    private int SGN(double gc_x) {
        return gc_x < 0 ? -1 : gc_x > 0 ? 1 : 0;
    }


    /**
       <p>Calculates the phase of the Moon and returns the illuminated fraction of
         the Moon's disc as a value within the range of -99.9~...0.0...+99.9~,
         which has a negative sign in case the Moon wanes, otherwise the sign
         is positive.  The New Moon phase is around the 0.0 value and the Full
         Moon phase is around the +/-99.9~ value.  The argument is the time for
         which the phase is requested, expressed as a Julian date and fraction.</p>
       <p>This function is taken from the program "moontool" by John Walker,
         February 1988, which is in the public domain.  So see it for more
         information!  It is adapted (crippled) and `pretty-printed' to the
         requirements of Gcal, which means it is lacking all the other useful
         computations of astronomical values of the original code.</p>

       <p>Here is the blurb from "moontool":</p>
       <p>...The algorithms used in this program to calculate the positions Sun
       and Moon as seen from the Earth are given in the book "Practical Astronomy
       With Your Calculator" by Peter Duffett-Smith, Second Edition,
       Cambridge University Press, 1981. Ignore the word "Calculator" in the
       title; this is an essential reference if you're interested in
       developing software which calculates planetary positions, orbits,
       eclipses, and the like. If you're interested in pursuing such
       programming, you should also obtain:</p>

       <p>"Astronomical Formulae for Calculators" by Jean Meeus, Third Edition,
       Willmann-Bell, 1985. A must-have.</p>

       </p>"Planetary Programs and Tables from -4000 to +2800" by Pierre
       Bretagnon and Jean-Louis Simon, Willmann-Bell, 1986. If you want the
       utmost (outside of JPL) accuracy for the planets, it's here.</p>

       <p>"Celestial BASIC" by Eric Burgess, Revised Edition, Sybex, 1985. Very
       cookbook oriented, and many of the algorithms are hard to dig out of
       the turgid BASIC code, but you'll probably want it anyway.</p>

       <p>Many of these references can be obtained from Willmann-Bell, P.O. Box
       35025, Richmond, VA 23235, USA. Phone: (804) 320-7016. In addition
       to their own publications, they stock most of the standard references
       for mathematical and positional astronomy.</p>

       <p>This program was written by:</p>

          <p>John Walker<br>
          Autodesk, Inc.<br>
          2320 Marinship Way<br>
          Sausalito, CA 94965<br>
          (415) 332-2344 Ext. 829</p>

          <p>Usenet: {sun!well}!acad!kelvin</p>

       <p>This program is in the public domain: "Do what thou wilt shall be the
       whole of the law". I'd appreciate receiving any bug fixes and/or
       enhancements, which I'll incorporate in future versions of the
       program. Please leave the original attribution information intact so
       that credit and blame may be properly apportioned.</p>
    */
    private double phase(double julian_date) {
        double date_within_epoch;
        double sun_eccent;
        double sun_mean_anomaly;
        double sun_perigree_co_ordinates_to_epoch;
        double sun_geocentric_elong;
        double moon_evection;
        double moon_variation;
        double moon_mean_anomaly;
        double moon_mean_longitude;
        double moon_annual_equation;
        double moon_correction_term1;
        double moon_correction_term2;
        double moon_correction_equation_of_center;
        double moon_corrected_anomaly;
        double moon_corrected_longitude;
        double moon_present_age;
        double moon_present_phase;
        double moon_present_longitude;

        /*
           Calculation of the Sun's position.
        */
        date_within_epoch = julian_date - EPOCH;
        sun_mean_anomaly = FIXANGLE((360.0 / 365.2422) * date_within_epoch);
        sun_perigree_co_ordinates_to_epoch = FIXANGLE(sun_mean_anomaly + SUN_ELONG_EPOCH - SUN_ELONG_PERIGEE);
        sun_eccent = kepler(sun_perigree_co_ordinates_to_epoch);
        sun_eccent = Math.sqrt((1.0 + ECCENT_EARTH_ORBIT) / (1.0 - ECCENT_EARTH_ORBIT)) * Math.tan(sun_eccent / 2.0);
        sun_eccent = 2.0 * TODEG(Math.atan(sun_eccent));
        sun_geocentric_elong = FIXANGLE(sun_eccent + SUN_ELONG_PERIGEE);
        /*
           Calculation of the Moon's position.
        */
        moon_mean_longitude = FIXANGLE(13.1763966 * date_within_epoch + MOON_MEAN_LONGITUDE_EPOCH);
        moon_mean_anomaly = FIXANGLE(moon_mean_longitude - 0.1114041 * date_within_epoch - MOON_MEAN_LONGITUDE_PERIGREE);
        moon_evection = 1.2739 * Math.sin(TORAD(2.0 * (moon_mean_longitude - sun_geocentric_elong) - moon_mean_anomaly));
        moon_annual_equation = 0.1858 * Math.sin(TORAD(sun_perigree_co_ordinates_to_epoch));
        moon_correction_term1 = 0.37 * Math.sin(TORAD(sun_perigree_co_ordinates_to_epoch));
        moon_corrected_anomaly = moon_mean_anomaly + moon_evection - moon_annual_equation - moon_correction_term1;
        moon_correction_equation_of_center = 6.2886 * Math.sin(TORAD(moon_corrected_anomaly));
        moon_correction_term2 = 0.214 * Math.sin(TORAD(2.0 * moon_corrected_anomaly));
        moon_corrected_longitude = moon_mean_longitude + moon_evection + moon_correction_equation_of_center
                - moon_annual_equation + moon_correction_term2;
        moon_variation = 0.6583 * Math.sin(TORAD(2.0 * (moon_corrected_longitude - sun_geocentric_elong)));

        // true longitude
        moon_present_longitude = moon_corrected_longitude + moon_variation;
        moon_present_age = moon_present_longitude - sun_geocentric_elong;
        moon_present_phase = 100.0 * ((1.0 - Math.cos(TORAD(moon_present_age))) / 2.0);

        if (0.0 < FIXANGLE(moon_present_age) - 180.0) {
            moon_present_phase = -moon_present_phase;
        }

        _moonAgeAsDays = SYNMONTH * (FIXANGLE(moon_present_age) / 360.0);

        return moon_present_phase;
    }

/**  UCTTOJ  --  Convert GMT date and time to astronomical
                Julian time (i.e. Julian date plus day fraction,
                expressed as a double).
 @param cal Calendar object
 @return JD float Julian date
 <p>Converted to Java by vriolk@gmail.com from original file mooncalc.c,
 part of moontool http://www.fourmilab.ch/moontoolw/moont16s.zip</p>
 */
    private double  calendarToJD(Calendar cal) {

        /* Algorithm as given in Meeus, Astronomical Algorithms, Chapter 7, page 61*/
        long year = cal.get(Calendar.YEAR);
        int mon   = cal.get(Calendar.MONTH);
        int mday  = cal.get(Calendar.DATE);
        int hour  = cal.get(Calendar.HOUR_OF_DAY);
        int min   = cal.get(Calendar.MINUTE);
        int sec   = cal.get(Calendar.SECOND);

        int a, b, m;
        long y;

        m = mon + 1;
        y = year;

        if (m <= 2) {
            y--;
            m += 12;
        }

        /* Determine whether date is in Julian or Gregorian
         * calendar based on canonical date of calendar reform.
         */
        if ((year < 1582) || ((year == 1582) && ((mon < 9) || (mon == 9 && mday < 5)))) {
            b = 0;
        } else {
            a = ((int) (y / 100));
            b = 2 - a + (a / 4);
        }

        return (((long) (365.25 * (y + 4716))) + ((int) (30.6001 * (m + 1))) +
                mday + b - 1524.5) +
                ((sec + 60L * (min + 60L * hour)) / 86400.0);
    }

    /**
     * Returns current phase as double value
     * Uses class Calendar field _curCal
     * */
    public double getPhase(){
        _JD = calendarToJD(_curCal);
        _phase = phase(_JD);
        return _phase;
    }

    public int getPhaseIndex(){
    	
    	return computePhaseIndex(_curCal);
        
    }
    
    public String getPhaseName() {
    	return moon_phase_name[getPhaseIndex()];
    }
    /**
     * Computes the moon phase index as a value from 0 to 7
     * Used to display the phase name and the moon image
     * for the current phase
     * @param cal Calendar calendar object for today's date
     * @return moon index 0..7
     * 
     */
    private int computePhaseIndex(Calendar cal){

     int    day_year[] = { -1, -1, 30, 58, 89, 119,
                                               150, 180, 211, 241, 272,
                                               303, 333 };
    
    int phase;  // Moon phase
    int year, month, day, hour, min, sec;
        
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH) + 1; // 0 = Jan, 1 = Feb, etc.
        day = cal.get(Calendar.DATE);
        hour = cal.get(Calendar.HOUR);
        min = cal.get(Calendar.MINUTE);
        sec = cal.get(Calendar.SECOND);
        
        double day_exact = day + hour/24 + min/1440 + sec/86400;
        

        //int      phase;    // Moon phase
        int        cent;     // Century number (1979 = 20)
        int        epact;    // Age of the moon on Jan. 1
        double     diy;      // Day in the year
        int        golden;   // Moon's golden number

        if (month < 0 || month > 12) {
            month = 0; // Just in case
        }     // Just in case
        
        diy = day_exact + day_year[month];                // Day in the year
        
        if ((month > 2) && isLeapYearP(year)) {
            diy++; 
        }                                  // Leapyear fixup
        cent = (year / 100) + 1;                    // Century number
        golden = (year % 19) + 1;                   // Golden number
        epact = ((11 * golden) + 20                 // Golden number
                + (((8 * cent) + 5) / 25) - 5       // 400 year cycle
                - (((3 * cent) / 4) - 12)) % 30;    //Leap year correction
        if (epact <= 0) {
            epact += 30; 
        }                        // Age range is 1 .. 30
        if ((epact == 25 && golden > 11) ||
            epact == 24) {
            epact++;

            // Calculate the phase, using the magic numbers defined above.
            // Note that (phase and 7) is equivalent to (phase mod 8) and
            // is needed on two days per year (when the algorithm yields 8).
        }

        // Calculate the phase, using the magic numbers defined above.
        // Note that (phase and 7) is equivalent to (phase mod 8) and
        // is needed on two days per year (when the algorithm yields 8).
        // this.factor = ((((diy + (double)epact) * 6) + 11) % 100 );
        phase = ((((((int)diy + epact) * 6) + 11) % 177) / 22) & 7;

        return(phase);
    }
    
    /** isLeapYearP
     Return true if the year is a leapyear
     */
    private boolean isLeapYearP(int year) {
        return ((year % 4 == 0) &&
                ((year % 400 == 0) || (year % 100 != 0)));
    }

    public String getMoonAgeAsDays() {

        int aom_d = (int) _moonAgeAsDays;
        int aom_h = (int) (24 * (_moonAgeAsDays - Math.floor(_moonAgeAsDays)));
        int aom_m = (int) (1440 * (_moonAgeAsDays - Math.floor(_moonAgeAsDays))) % 60;

        return "" + aom_d + (aom_d == 1 ? " day, ": " days, " ) +
                aom_h + (aom_h == 1 ? " hour, ":" hours, ") +
                aom_m + (aom_m == 1 ? " minute":" minutes");
    }

/*    
public static void main(String args[]) {
        System.out.println(new Date());

        MoonPhase mp = new MoonPhase(Calendar.getInstance());
        System.out.printf("Current phase: %f%n", mp.getPhase());
        System.out.println("Moon Age: " + mp.getMoonAgeAsDays());

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_WEEK, -22);

        for (int i=0; i< 33; i++){
            c.add(Calendar.DAY_OF_WEEK, 1);
            mp = new MoonPhase(c);
            System.out.format("%1$td %1$tB,%1$tY  %1$tH:%1$tM:%1$tS  ",c);
			System.out.printf("Phase index: %d ", mp.getPhaseIndex());
			System.out.printf("Phase string: %s ", mp.getPhaseName());
            System.out.printf("%f%n", mp.getPhase());
        }
}
*/
     private Calendar adjustTimeZone(Calendar c, int offsetInHours){
        long currTime = c.getTime().getTime();
        c.setTime(new Date(currTime + offsetInHours*1000*60*60 ));
        return c;
    }

    public int getCurrentTimeZone(){
            return TimeZone.getDefault().getRawOffset()/1000*60*60;
    }

}
