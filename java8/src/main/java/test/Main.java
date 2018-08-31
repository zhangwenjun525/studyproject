package test;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/6/30
 * Time: 13:10
 */
public class Main {

    public final StaticClass staticClass = null;

    public static void main (String[] args) {
/*        System.out.println(System.currentTimeMillis());
        System.out.println(calculateExpireTime(System.currentTimeMillis(), 366));
        System.out.println(new Date(1501656949063l));*/
        Calendar calendar = Calendar.getInstance();
        long time = calendar.getTime().getTime();
        System.out.println(time);
        calendar.add(Calendar.YEAR, 1);
        long end = calendar.getTime().getTime();
        System.out.println(end - time);
        System.out.println("================================");
        System.out.println(1l * 365 * 1000 * 60 * 60 * 24);
        System.out.println(calculateExpireTime(time, 365));
        System.out.println((end - time) / (24 * 60 * 60 * 1000));
    }

    private static Long calculateExpireTime(Long startTime, Integer days){
        final int MILLISECOND_EACH_SECOND = 1000;
        final int SECOND_EACH_MINUTE = 60;
        final int MINUTE_EACH_HOUR = 60;
        final int HOUR_EACH_DAY = 24;
        System.out.println(days * HOUR_EACH_DAY * MINUTE_EACH_HOUR * SECOND_EACH_MINUTE * MILLISECOND_EACH_SECOND);
        return startTime + days * HOUR_EACH_DAY * MINUTE_EACH_HOUR * SECOND_EACH_MINUTE * MILLISECOND_EACH_SECOND;
    }
}
