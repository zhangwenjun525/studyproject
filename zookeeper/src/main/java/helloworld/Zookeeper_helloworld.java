package helloworld;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/5/2
 * Time: 17:30
 */
public class Zookeeper_helloworld {

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ZooKeeper zk = new ZooKeeper("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183", 30000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("====================================");
                System.out.println("path:" + watchedEvent.getPath());
                System.out.println("type:" + watchedEvent.getType());
                System.out.println("state:" + watchedEvent.getState());
                System.out.println("====================================");
            }
        });
        String node = "/node2";
        Stat stat = zk.exists(node, false);
        if(null == stat){
            String createResult = zk.create(node, "test".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println(createResult);
        }
        byte[] b = zk.getData(node, false, stat);
        System.out.println(new String(b));
        zk.close();
    }

}
