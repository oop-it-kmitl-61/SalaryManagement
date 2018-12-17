package sample;

import java.util.*;
import java.lang.Object;
import java.time.YearMonth;

public class Day { ;

//    hour = g.get(Calendar.HOUR_OF_DAY);
//    minute = g.get(Calendar.MINUTE);
//    second = g.get(Calendar.SECOND);

    public static void main(String[] args){
        int day, month, year;
        int hour, minute, second;
        Calendar g = Calendar.getInstance();
        year = g.get(Calendar.YEAR);
        month = g.get(Calendar.MONTH)+1;
        day = g.get(Calendar.DAY_OF_MONTH);
        second = g.get(Calendar.SECOND);
        minute = g.get(Calendar.MINUTE);
        hour = g.get(Calendar.HOUR_OF_DAY);
        YearMonth yearMonthObject = YearMonth.of(year, month);
        int daysInMonth = yearMonthObject.lengthOfMonth();
        System.out.println(year +"-" +month +"-"+day+" "+hour+":"+minute+":"+second);
        System.out.println(daysInMonth);
    }

}
