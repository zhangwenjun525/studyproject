package distributedLock;

import org.I0Itec.zkclient.IZkDataListener;

import java.util.concurrent.CountDownLatch;

/**
 * @author zhangwj
 * @date 2018/9/18 16:02
 */
public class ZookeeperDistrbuteLock extends ZookeeperAbstractLock {

    @Override
    boolean tryLock() {
        try {
            zkClient.createEphemeral(PATH);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    void waitLock() {
        IZkDataListener iZkDataListener = new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {

            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                if (countDownLatch != null) {
                    countDownLatch.countDown();
                }
            }
        };

        zkClient.subscribeDataChanges(PATH, iZkDataListener);

        if (zkClient.exists(PATH)) {
            countDownLatch = new CountDownLatch(1);
            try {
                countDownLatch.await();
            } catch (Exception e) {

            }
        }
        zkClient.unsubscribeDataChanges(PATH, iZkDataListener);
    }
}
