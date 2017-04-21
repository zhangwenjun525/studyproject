package collection.delayqueue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/3/27
 * Time: 14:52
 */
public class Netizen implements Delayed {

    private String name;

    private String idCard;

    private long endTime;

    public Netizen(String name, String idCard, long endTime) {
        this.name = name;
        this.idCard = idCard;
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return endTime - System.currentTimeMillis();
    }

    @Override
    public int compareTo(Delayed o) {
        Netizen netizen = (Netizen) o;
        return this.endTime - netizen.endTime > 0 ? 1 : (this.endTime - netizen.endTime < 0 ? -1 : 0);
    }
}
