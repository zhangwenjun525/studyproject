package rocketmq.subwaytrip;

import java.util.Date;
import java.util.UUID;

public class IDHelper {

    private static final int UUID_LENGTH = 8;
    private static final int RIDE_UUID_LENGTH = 4;

    public IDHelper() {
    }

    public static String createRideID() {
        StringBuilder id = new StringBuilder(DateUtil.formatDate(new Date(), new Object[]{"yyMMddHHmmss"}));
        id.append(UUID.randomUUID().toString().substring(0, 4));
        return id.toString();
    }

    public static String createID() {
        StringBuilder id = new StringBuilder(DateUtil.getDateTimes());
        id.append(UUID.randomUUID().toString().substring(0, 8));
        return id.toString();
    }

    public static String createID(Date date) {
        StringBuilder id = new StringBuilder(DateUtil.formatDate(date, new Object[]{"yyMMddHHmmss"}));
        id.append(UUID.randomUUID().toString().substring(0, 8));
        return id.toString();
    }

    public static int createNumericID() {
        return UUID.randomUUID().hashCode();
    }
}
