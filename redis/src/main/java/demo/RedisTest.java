package demo;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/4/10
 * Time: 10:13
 */
public class RedisTest {

    private Jedis jedis;

    @Before
    public void init() {
        jedis = new Jedis("192.168.4.11", 6379);
    }


    @Test
    public void testJedisConn() {
        System.out.println(jedis);
    }

    @Test
    public void testSet() {
        String result = jedis.set("name", "章文君");
        System.out.println(result);
    }

    @Test
    public void tesetGet() {
        String name = jedis.get("1FD1732");
        System.out.println(name);
    }

    @Test
    public void testHashSet() {
        Map<String, String> map = jedis.hgetAll("user");
        System.out.println(map.toString());
    }

    @Test
    public void inc() throws InterruptedException {
        String key = "1FD16";
        for(int i = 0; i < 10; ++i){
            new Thread(()->{
                inc(key);
            }).start();
        }
        System.out.println("==================");
        TimeUnit.SECONDS.sleep(100);
    }

    private void inc(String key){
        System.out.println(jedis.incr(key));
    }

    @Test
    public void test1(){
        Calendar cl = Calendar.getInstance();
        int week = cl.get(Calendar.WEEK_OF_YEAR);
        System.out.println(week);
    }
}
