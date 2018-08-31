package test.thread;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/7/7
 * Time: 15:36
 */
public class JoinMain {
    public volatile static int i = 0;

    public static class AddThread extends Thread{

        @Override
        public void run() {
            for(i = 0; i < 10000000; ++i);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        AddThread at = new AddThread();
        at.start();
        at.join();
        System.out.println(i);
    }

}
