package rocketmq.subwaytrip;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    private static final Integer DAY_COUNT_EACH_WEEK = Integer.valueOf(7);
    private static final String UTC_8_DATE_TIME_ZONE = "Asia/Shanghai";

    public DateUtil() {
    }

    public static Date from(int year, int month, int day, int hour, int min, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day, hour, min, second);
        return calendar.getTime();
    }

    public static String formatDate(Date date, Object... pattern) {
        String formatDate = null;
        if (date == null) {
            return "";
        } else {
            if (pattern != null && pattern.length > 0) {
                formatDate = DateFormatUtils.format(date, pattern[0].toString());
            } else {
                formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
            }

            return formatDate;
        }
    }

    public static String getDateTimes() {
        return formatDate(new Date(), "yyyyMMddHHmmss");
    }

    public static Integer getWeekOfYear(Calendar calendar) {
        if (calendar == null) {
            calendar = Calendar.getInstance();
        }

        int year = calendar.get(1);
        Calendar firstDay = Calendar.getInstance();
        firstDay.set(year, 0, 1);
        int dayOfWeek = firstDay.get(7);
        dayOfWeek = dayOfWeek == 1 ? DAY_COUNT_EACH_WEEK.intValue() : dayOfWeek - 1;
        int offSet = DAY_COUNT_EACH_WEEK.intValue() - dayOfWeek + 1;
        int week = (calendar.get(6) - offSet + DAY_COUNT_EACH_WEEK.intValue() - 1) / DAY_COUNT_EACH_WEEK.intValue() + 1;
        return week;
    }
}
