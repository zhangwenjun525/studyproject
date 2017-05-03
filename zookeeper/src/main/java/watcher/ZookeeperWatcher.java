package watcher;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/5/3
 * Time: 23:07
 */
public class ZookeeperWatcher implements Watcher {

    private AtomicInteger seq = new AtomicInteger();
    /** 定义session失效的时间*/
    private static final int SESSION_TIMEOUT = 10000;
    /** Zookeeper服务器地址*/
    private static final String ZOOKEEPER_CONNECT_URL="127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
    /** zk父路径设置*/
    private static final String PARENT_PATH="/testWatch";
    /** zk子路径设置*/
    private static final String CHILDREN_PATH="/testWatch/children";
    /** 进入标识*/
    private static final String LOG_PREFIX_OF_MAIN = "[main]";
    /** zk变量 */
    private ZooKeeper zk = null;
    /** 信号量设置，用于等待zookeeper连接建立之后通知阻塞程序继续向下执行*/
    private CountDownLatch connectedSemaphore = new CountDownLatch(1);

    /**
     * 创建zk连接
     * @param connectUrl ZK服务器地址列表
     * @param sessionTimeout Session超时时间
     */
    public void createConnection(String connectUrl, int sessionTimeout){
        this.releaseConnection();
        try{
            zk = new ZooKeeper(connectUrl,sessionTimeout, this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 关闭ZK连接
     */
    public void releaseConnection(){
        if(this.zk != null){
            try {
                this.zk.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }




    @Override
    public void process(WatchedEvent watchedEvent) {
        Event.EventType type = watchedEvent.getType();
        Event.KeeperState state = watchedEvent.getState();

    }
}
