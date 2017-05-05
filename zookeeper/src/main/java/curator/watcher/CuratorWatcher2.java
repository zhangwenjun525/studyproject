package curator.watcher;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.TimeUnit;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/5/5
 * Time: 11:13
 */
public class CuratorWatcher2 {

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

        /** 建立一个PathChildrenCache缓存， 第三个参数表示是否接收节点数据内容 */
        PathChildrenCache cache = new PathChildrenCache(cf, "/super", true);

        /** 初始化的时候进行缓存监听 */
        cache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);;

        cache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                switch (pathChildrenCacheEvent.getType()){
                    case CHILD_ADDED:
                        System.out.println("CHILD_ADDED: " + pathChildrenCacheEvent.getData().getPath());
                        System.out.println("CHILD_ADDED: " + new String(pathChildrenCacheEvent.getData().getData()));
                        break;

                    case CHILD_UPDATED:
                        System.out.println("CHILD_UPDATED: " + pathChildrenCacheEvent.getData().getPath());
                        System.out.println("CHILD_UPDATED: " + new String(pathChildrenCacheEvent.getData().getData()));
                        break;

                    case CHILD_REMOVED:
                        System.out.println("CHILD_REMOVED: " + pathChildrenCacheEvent.getData().getPath());
                        System.out.println("CHILD_REMOVED: " + new String(pathChildrenCacheEvent.getData().getData()));
                        break;

                    default:
                        break;
                }
            }
        });

        cf.create().forPath("/super", "init".getBytes());
        TimeUnit.SECONDS.sleep(1);
        cf.create().forPath("/super/c1", "c1 data".getBytes());
        TimeUnit.SECONDS.sleep(1);
        cf.create().forPath("/super/c2", "c2 data".getBytes());
        TimeUnit.SECONDS.sleep(1);
        cf.setData().forPath("/super/c1", "c1 new data".getBytes());
        cf.delete().guaranteed().forPath("/super/c2");
        TimeUnit.SECONDS.sleep(10);
        cf.delete().guaranteed().deletingChildrenIfNeeded().forPath("/super");
        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }
}
