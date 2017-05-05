package curator.lock;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/5/5
 * Time: 15:20
 */
public class Lock1 {

    private static ReentrantLock lock = new ReentrantLock();
    private static int count = 10;

    public static void genarNo(){
        try{
            lock.lock();
            count--;
            System.out.println(count);
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        final CountDownLatch countdown = new CountDownLatch(1);
        for(int i = 0; i < 10; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        countdown.await();
                        genarNo();
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss|SSS");
                        System.out.println(sdf.format(new Date()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                    }
                }
            },"t" + i).start();
        }
        try {
            TimeUnit.MILLISECONDS.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        countdown.countDown();
    }
}
