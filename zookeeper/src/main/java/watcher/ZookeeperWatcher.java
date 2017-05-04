package watcher;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
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

    /**
     * 创建节点
     * @param path 节点路径
     * @param data 数据内容
     * @return
     */
    public boolean createPath(String path, String data, boolean needWatch){
        try{
            //设置监控(由于zookeeper的监控都是一次性的，所以每次必须设置监控)
            this.zk.exists(path, needWatch);
            System.out.println(LOG_PREFIX_OF_MAIN + "节点创建成功, path: "
                    + this.zk.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT)
                    + ", content: " + data);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 读取指定节点数据内容
     * @param path  节点路径
     * @param needWatch 是否需要监控
     * @return
     */
    public String readData(String path, boolean needWatch){
        try{
            return new String(this.zk.getData(path, needWatch, null));
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 更新指定节点数据内容
     * @param path 节点路径
     * @param data 数据内容
     * @return
     */
    public boolean writeData(String path, String data){
        try{
            System.out.println(LOG_PREFIX_OF_MAIN + "更新数据成功, path:" + path + ", stat: "
                               + this.zk.setData(path, data.getBytes(), -1));
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除指定的节点
     * @param path
     */
    public void deleteNode(String path){
        try {
            this.zk.delete(path, -1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断指定节点是否存在
     * @param path 节点路径
     */
    public Stat exists(String path, boolean needWatch) {
        try {
            return this.zk.exists(path, needWatch);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取子节点
     * @param path 节点路径
     */
    private List<String> getChildren(String path, boolean needWatch) {
        try {
            return this.zk.getChildren(path, needWatch);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 删除所有节点
     */
    public void deleteAllTestPath() {
        if(this.exists(CHILDREN_PATH, false) != null){
            this.deleteNode(CHILDREN_PATH);
        }
        if(this.exists(PARENT_PATH, false) != null){
            this.deleteNode(PARENT_PATH);
        }
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("进入 process......event = " + watchedEvent);

        /** 连接状态 */
        Event.KeeperState state = watchedEvent.getState();
        /** 事件类型 */
        Event.EventType type = watchedEvent.getType();
        /** 受影响的path */
        String path = watchedEvent.getPath();

        String logPrefix = "[Watcher-" + this.seq.incrementAndGet() + "]";
        System.out.println(logPrefix + "收到watcher通知");
        System.out.println(logPrefix + "连接状态:\t" + state.toString());
        System.out.println(logPrefix + "事件类型:\t" + type.toString());

        if(Event.KeeperState.SyncConnected == state){
            /** 成功连接上ZK服务器*/
            if(Event.EventType.None == type){
                System.out.println(logPrefix + "成功连上ZK服务器");
                connectedSemaphore.countDown();
            }
            /** 创建节点 */
            else if(Event.EventType.NodeCreated == type){
                System.out.println(logPrefix + "节点创建");
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.exists(path, true);
            }
            /** 更新节点 */
            else if(Event.EventType.NodeDataChanged == type){
                System.out.println(logPrefix + "节点数据更新");
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(logPrefix + "数据内容: " + this.readData(path, true));
            }
            /** 更新子节点 */
            else if(Event.EventType.NodeChildrenChanged == type){
                System.out.println(logPrefix + "子节点变更");
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(logPrefix + "子节点列表: " + this.getChildren(PARENT_PATH, true));
            }
            /** 删除节点 */
            else if(Event.EventType.NodeDeleted == type){
                System.out.println(logPrefix + "节点" + path + " 被删除");
            }
            else ;
        }else if(Event.KeeperState.Disconnected == state){
            System.out.println(logPrefix + "与ZK服务器断开连接");
        }else if(Event.KeeperState.AuthFailed == state){
            System.out.println(logPrefix + "权限检查失败");
        }else if (Event.KeeperState.Expired == state){
            System.out.println(logPrefix + "会话失效");
        }else ;

        System.out.println("------------------------------------------------------------------------------");
    }

    public static void main(String[] args) throws Exception {
        /** 建立watcher */
        ZookeeperWatcher zkWatch = new ZookeeperWatcher();
        /** 创建连接 */
        zkWatch.createConnection(ZOOKEEPER_CONNECT_URL, SESSION_TIMEOUT);

        TimeUnit.SECONDS.sleep(1);

        /** 清空节点 */
        zkWatch.deleteAllTestPath();

        if(zkWatch.createPath(PARENT_PATH, System.currentTimeMillis() + "", true)){

            TimeUnit.SECONDS.sleep(1);

            /** 读取数据 */
            System.out.println("--------------read parent------------------");
            zkWatch.readData(PARENT_PATH, true);

            /** 读取子节点 */
            System.out.println("--------------read children path------------------");
            zkWatch.getChildren(PARENT_PATH, true);

            /** 更新数据 */
            zkWatch.writeData(PARENT_PATH, System.currentTimeMillis() + "");

            TimeUnit.SECONDS.sleep(1);

            /** 创建子节点 */
            zkWatch.createPath(CHILDREN_PATH, System.currentTimeMillis() + "",  true);

            TimeUnit.SECONDS.sleep(1);

            zkWatch.writeData(CHILDREN_PATH, System.currentTimeMillis() + "");
        }

        TimeUnit.SECONDS.sleep(5);
        zkWatch.deleteAllTestPath();
        TimeUnit.SECONDS.sleep(1);
        zkWatch.releaseConnection();
    }
}
