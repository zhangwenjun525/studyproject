package base;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/5/3
 * Time: 9:40
 */
public class ZookeeperBase {

    private static final String CONNECT_URL = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
    private static final Integer SESSION_TIME_OUT = 30000;
    private static final CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
        ZooKeeper zk = new ZooKeeper(CONNECT_URL, SESSION_TIME_OUT, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                Event.KeeperState state = watchedEvent.getState();
                Event.EventType eventType = watchedEvent.getType();
                if(Event.KeeperState.SyncConnected == state){
                    if(Event.EventType.None == eventType){
                        latch.countDown();
                        System.out.println("zk 建立连接");
                    }
                }
            }
        });

        //进行阻塞
        latch.await();

        //创建父节点
/*        String result = zk.create("/testRoot", "testRoot".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(result);*/

        //创建子节点 EPHEMERAL代表临时节点，生命周期为该次会话
        //result = zk.create("/testRoot/children","children data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        //System.out.println(result);

        //异步删除
/*        zk.delete("/testRoot", -1, new AsyncCallback.VoidCallback() {
            @Override
            public void processResult(int i, String s, Object o) {
                System.out.println(i);
                System.out.println(s);
                System.out.println(o);
            }
        }, null);*/

        //获取数据
/*        byte[] data = zk.getData("/testRoot", false, null);
        System.out.println(new String(data));*/

        //获取直接的子节点
/*        List<String> children = zk.getChildren("/testRoot", false);
        System.out.println(children);*/

/*        zk.setData("/testRoot", "testRoot data".getBytes(), -1);
        System.out.println(new String(zk.getData("/testRoot", false, null)));*/

        Stat exists = zk.exists("/testRoot", false);
        System.out.println(exists);


        TimeUnit.SECONDS.sleep(5);
        zk.close();

    }



}
