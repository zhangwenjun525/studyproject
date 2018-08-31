package test.thread;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/7/7
 * Time: 17:00
 */
public class SyncAccounting implements Runnable {

    static SyncAccounting instance = new SyncAccounting();
    static int i = 0;

    public synchronized void increase(){
        i++;
    }

    @Override
    public void run() {
        for(int j = 0; j < 10000000; ++j){
            increase();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }
}
