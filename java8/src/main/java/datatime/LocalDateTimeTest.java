package datatime;

import org.junit.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/3/13
 * Time: 23:15
 */
public class LocalDateTimeTest {

    @Test
    public void test1(){
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);

        LocalDateTime localDateTime1 = LocalDateTime.of(2017, 3, 13, 23, 18, 23);
        System.out.println(localDateTime1);

        LocalDateTime localDateTime2 = localDateTime1.plusDays(1);
        System.out.println(localDateTime1);
        System.out.println(localDateTime2);
    }

    @Test
    public void test2(){
        Instant instant = Instant.now();
        System.out.println(instant);
    }


    @Test
    public void test3(){
        System.out.println(new Date().getTime());

        Instant instant = Instant.now();
        System.out.println(instant.toEpochMilli());

        System.out.println(System.currentTimeMillis());
    }

}
