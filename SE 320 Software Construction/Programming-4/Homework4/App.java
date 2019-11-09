package app;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class App {
    public static void main(String[] args) throws Exception {
        App app = new App();
    }

    public App() {

        GregorianCalendar calendar = new GregorianCalendar();
        printOutGregorianDate(calendar);
        calendar.setTimeInMillis(1234567898765L);
        printOutGregorianDate(calendar);
    }

    /**
     * prints out the Gregorian year, month, and day to console
     * 
     * @param calendar Gegorian calendar who's date will be printed
     */
    public void printOutGregorianDate(GregorianCalendar calendar) {
        System.out.println(getGregorianYear(calendar) + ", " + getGregorianMonth(calendar) + ", "
             + getGregorianDay(calendar) );
    }

    /**
     * gets the Gregorian month of a given calendar
     * @param cal the calendar who's month is returned
     * @return  String containing the Gregorian month's number, ie January is returned as 1
     */
    public String getGregorianMonth(Calendar cal) {
        return "" + cal.get(GregorianCalendar.MONTH);
    }

    /**
     * gets the Gregorian year of a given calendar
     * @param cal the calendar who's year is returned
     * @return  String containing the Gregorian year
     */
    public String getGregorianYear(Calendar cal) {
        return "" + cal.get(GregorianCalendar.YEAR);
    }
    
    /**
     * gets the Gregorian day of the month of a given calendar
     * @param cal the calendar who's day of the month is returned
     * @return  String containing the Gregorian day of the month
     */
    public String getGregorianDay(Calendar cal) {
        return "" + cal.get(GregorianCalendar.DAY_OF_MONTH);
    }

}