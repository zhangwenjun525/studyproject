package thread;

import java.util.concurrent.TimeUnit;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/3/24
 * Time: 9:42
 */
public class VolatileTest extends Thread {

    /*volatile*/
    private Boolean running = Boolean.TRUE;

    public Boolean getRunning() {
        return running;
    }

    public void setRunning(Boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        while (running) {

        }
        System.out.println("run end");
    }


    public static void main(String[] args) throws InterruptedException {
        VolatileTest test = new VolatileTest();
        test.start();
        TimeUnit.SECONDS.sleep(1);
        test.setRunning(false);
        TimeUnit.SECONDS.sleep(1);
        System.out.println(test.running);
    }
}
