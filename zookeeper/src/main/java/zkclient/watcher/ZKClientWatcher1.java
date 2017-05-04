package zkclient.watcher;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/5/4
 * Time: 17:16
 */
public class ZKClientWatcher1 {
    /** zookeeper地址 */
    private static final String ZOOKEEPER_CONNECT_URL = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
    /** session超时时间 单位ms*/
    private static final int SESSION_TIMEOUT = 5000;

    public static void main(String[] args) throws InterruptedException {
        ZkClient zkClient = new ZkClient(new ZkConnection(ZOOKEEPER_CONNECT_URL), 5000);
        zkClient.subscribeChildChanges("/super", new IZkChildListener() {
            @Override
            public void handleChildChange(String s, List<String> list) throws Exception {
                System.out.println("ParentPath: " + s);
                System.out.println("currentChilds: " + list);
            }
        });

        zkClient.subscribeDataChanges("/super", new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                System.out.println("变更的节点：" + s + ", 变更的内容：" + o);
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                System.out.println("删除的节点：" + s);
            }
        });


        TimeUnit.SECONDS.sleep(3);
        zkClient.createPersistent("/super");
        TimeUnit.SECONDS.sleep(1);
        zkClient.createPersistent("/super/c1","c1内容");
        TimeUnit.SECONDS.sleep(1);
        zkClient.createPersistent("/super/c2","c2内容");
        TimeUnit.SECONDS.sleep(1);
        zkClient.writeData("/super/c1","c1新内容");
        TimeUnit.SECONDS.sleep(1);
        zkClient.delete("/super/c2");
        zkClient.writeData("/super","super内容");

        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }
}
