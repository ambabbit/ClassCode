package app;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.spi.CalendarDataProvider;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello Java");
        App app = new App();
    }

    public App() {

        GregorianCalendar calendar = new GregorianCalendar();
        printOutGregorianDate(calendar);
        calendar.setTimeInMillis(1234567898765L);
        printOutGregorianDate(calendar);
    }

    public void printOutGregorianDate(GregorianCalendar calendar) {
        System.out.println(getGregorianYear(calendar) + ", " + getGregorianMonth(calendar) + ", " + getGregorianDay(calendar) );
    }

    public String getGregorianMonth(Calendar cal) {
        return "" + cal.get(GregorianCalendar.MONTH);
    }
    public String getGregorianYear(Calendar cal) {
        return "" + cal.get(GregorianCalendar.YEAR);
    }
    public String getGregorianDay(Calendar cal) {
        return "" + cal.get(GregorianCalendar.DAY_OF_MONTH);
    }

}