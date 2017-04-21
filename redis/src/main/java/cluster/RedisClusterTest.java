package cluster;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/4/17
 * Time: 10:35
 */
public class RedisClusterTest {

    public static void main(String[] args) {

        Set<HostAndPort> hostAndPorts = new HashSet<>();
        hostAndPorts.add(new HostAndPort("182.92.235.106", 7001));
        hostAndPorts.add(new HostAndPort("182.92.235.106", 7002));
        hostAndPorts.add(new HostAndPort("182.92.235.106", 7003));
        hostAndPorts.add(new HostAndPort("182.92.235.106", 7004));
        hostAndPorts.add(new HostAndPort("182.92.235.106", 7005));
        hostAndPorts.add(new HostAndPort("182.92.235.106", 7006));


        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxWaitMillis(-1);
        jedisPoolConfig.setMinIdle(8);
        jedisPoolConfig.setMaxIdle(100);
        jedisPoolConfig.setMaxTotal(1000);
        JedisCluster jedisCluster = new JedisCluster(hostAndPorts, 5000, 6, jedisPoolConfig);

        String age = jedisCluster.get("age");
        System.out.println(age);
    }


}
