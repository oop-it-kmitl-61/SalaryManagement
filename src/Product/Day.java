package Product;

import java.time.YearMonth;
import java.util.*;

public class Day {
    int day;
    int month;
    int year;
    public int getDate(){
        GregorianCalendar g = new GregorianCalendar();
        day = g.get(Calendar.DAY_OF_MONTH);
        return day;
    }

    public int getMonth(){
        GregorianCalendar g = new GregorianCalendar();
        month = g.get(Calendar.MONTH)+1;
        year = g.get(Calendar.YEAR);
        YearMonth yearMonthObject = YearMonth.of(year, month);
        int daysInMonth = yearMonthObject.lengthOfMonth();
        return daysInMonth;
    }

    public String getFormat(){
        int hour, minute, second;
        Calendar g = Calendar.getInstance();
        this.year = g.get(Calendar.YEAR);
        this.month = g.get(Calendar.MONTH)+1;
        this.day = g.get(Calendar.DAY_OF_MONTH);
        second = g.get(Calendar.SECOND);
        minute = g.get(Calendar.MINUTE);
        hour = g.get(Calendar.HOUR_OF_DAY);
        return year +"-" +month +"-"+day+" "+hour+":"+minute+":"+second;
    }

    public String dateFormat(){
        Calendar g = Calendar.getInstance();
        this.year = g.get(Calendar.YEAR);
        this.month = g.get(Calendar.MONTH)+1;
        this.day = g.get(Calendar.DAY_OF_MONTH);
        return year +"-" +month +"-"+day;
    }

    protected int monthly(){
        Calendar g = Calendar.getInstance();
        this.month = g.get(Calendar.MONTH)+1;
        return this.month;

    }

    public static void main(String[] args){
        Day day = new Day();
        System.out.println(day.getMonth());
        System.out.println(day.getDate());
        System.out.println(day.getFormat());
    }

}
