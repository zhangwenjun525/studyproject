package curator.base;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/5/4
 * Time: 22:01
 */
public class CuratorBase {

    /** zookeeper地址 */
    private static final String ZOOKEEPER_CONNECT_URL = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
    /** session超时时间 单位ms*/
    private static final int SESSION_TIMEOUT = 10000;

    public static void main(String[] args) throws Exception {

        /** 重试策略：初始时间1s, 重试10次 */
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 10);

        /** 通过工厂创建连接 */
        CuratorFramework cf = CuratorFrameworkFactory.builder()
                .connectString(ZOOKEEPER_CONNECT_URL)
                .sessionTimeoutMs(SESSION_TIMEOUT)
                .retryPolicy(retryPolicy)
                .build();

        /** 开启连接 */
        cf.start();

        /** 创建节点，指定节点类型(不加withMode默认为持久化类型的节点), 路径, 内容 */
        cf.create().creatingParentsIfNeeded()
                   .withMode(CreateMode.PERSISTENT)
                   .forPath("/super/c1", "c1内容".getBytes());

        /** 读取 */
        byte[] data = cf.getData().forPath("/super/c1");
        System.out.println("data: " + new String(data));

        /** 修改 */
        cf.setData().forPath("/super/c1", "c1新内容".getBytes());
        data = cf.getData().forPath("/super/c1");
        System.out.println("data: " + new String(data));

        /** 绑定回调事件 */
        ExecutorService pool = Executors.newCachedThreadPool();
        cf.create().creatingParentsIfNeeded()
                .withMode(CreateMode.PERSISTENT)
                .inBackground(new BackgroundCallback() {
                    @Override
                    public void processResult(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                        System.out.println(curatorEvent.getResultCode());
                        System.out.println(curatorEvent.getType());
                        System.out.println(Thread.currentThread().getName());
                    }
                }, pool)
                .forPath("/super/c2");

        /** 读取子节点方法 */
        List<String> list = cf.getChildren().forPath("/super");
        list.stream().forEach(System.out::println);

        /** 检测节点是否存在 */
        Stat stat = cf.checkExists().forPath("/super/c2");
        System.out.println(stat);

        /** 删除节点 */
        cf.delete().guaranteed().deletingChildrenIfNeeded().forPath("/super");

        /** 释放资源 */
        cf.close();
    }


}
