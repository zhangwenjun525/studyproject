package curator.watcher;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.TimeUnit;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/5/5
 * Time: 11:13
 */
public class CuratorWatcher1 {

    /** zookeeper地址 */
    private static final String ZOOKEEPER_CONNECT_URL = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
    /** session超时时间 单位ms*/
    private static final int SESSION_TIMEOUT = 10000;

    public static void main(String[] args) throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 10);

        CuratorFramework cf = CuratorFrameworkFactory.builder()
                .connectString(ZOOKEEPER_CONNECT_URL)
                .sessionTimeoutMs(SESSION_TIMEOUT)
                .retryPolicy(retryPolicy)
                .build();

        cf.start();

        if(cf.checkExists().forPath("/super") != null){
            cf.delete().guaranteed().deletingChildrenIfNeeded().forPath("/super");
        }

        /** 建立一个cache缓存 */
        final NodeCache cache = new NodeCache(cf, "/super", false);
        cache.start(true);

        cache.getListenable().addListener(new NodeCacheListener() {
            /**
             * 触发事件为创建节点和更新节点，在删除节点的时候不会触发该事件
             * @throws Exception
             */
            @Override
            public void nodeChanged() throws Exception {
                System.out.println("路径: " + cache.getCurrentData().getPath());
                System.out.println("数据: " + new String(cache.getCurrentData().getData()));
                System.out.println("状态: " + cache.getCurrentData().getStat());
            }
        });

        TimeUnit.SECONDS.sleep(1);
        cf.create().forPath("/super", "123".getBytes());

        TimeUnit.SECONDS.sleep(1);
        cf.setData().forPath("/super", "456".getBytes());

        TimeUnit.SECONDS.sleep(1);
        cf.delete().guaranteed().forPath("/super");

        TimeUnit.SECONDS.sleep(10);
    }
}
