package distributedLock;

import org.I0Itec.zkclient.ZkClient;

import java.util.concurrent.CountDownLatch;

/**
 * @author zhangwj
 * @date 2018/9/18 15:56
 */
public abstract class ZookeeperAbstractLock implements Lock {

    // zk连接地址
    private static final String CONNECTSTRING = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
    // 创建zk连接
    protected ZkClient zkClient = new ZkClient(CONNECTSTRING);
    protected static final String PATH = "/lock";
    protected CountDownLatch countDownLatch = null;

    @Override
    public void getLock() {
        if (tryLock()) {
            System.out.println("###获取锁成功#####");
        } else {
            waitLock();
            getLock();
        }
    }

    @Override
    public void unLock() {
        if (zkClient != null) {
            zkClient.close();
            System.out.println("释放锁资源");
        }
    }

    abstract boolean tryLock();


    abstract void waitLock();
}
