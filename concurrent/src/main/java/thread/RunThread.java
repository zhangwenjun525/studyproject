package thread;

import java.util.concurrent.TimeUnit;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/3/23
 * Time: 23:03
 */
public class RunThread extends Thread {

    private volatile boolean running = Boolean.TRUE;

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        while(running){

        }
        System.out.println("game over");
    }

    public static void main(String[] args) throws InterruptedException {
        RunThread thread = new RunThread();
        thread.start();
        TimeUnit.SECONDS.sleep(1);
        thread.setRunning(false);
        TimeUnit.SECONDS.sleep(1);
        System.out.println(thread.isRunning());
    }

}
