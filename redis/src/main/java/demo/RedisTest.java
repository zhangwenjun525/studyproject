package demo;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Map;

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
        jedis = new Jedis("182.92.235.106", 6379);
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
        String name = jedis.get("name");
        System.out.println(name);
    }

    @Test
    public void testHashSet() {
        Map<String, String> map = jedis.hgetAll("user");
        System.out.println(map.toString());
    }


}
