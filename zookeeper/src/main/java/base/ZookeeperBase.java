package base;

import org.apache.zookeeper.*;

import java.util.concurrent.CountDownLatch;

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
        //String result = zk.create("/testRoot", "testRoot".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        //System.out.println(result);

        //创建子节点 EPHEMERAL代表临时节点，生命周期为该次会话
        //result = zk.create("/testRoot/children","children data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        //System.out.println(result);



        zk.close();


    }



}
