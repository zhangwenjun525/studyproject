package test.thread;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/7/7
 * Time: 9:55
 */
public class InterruptDemo {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(){
            @Override
            public void run() {
                 while (true){
                     if(Thread.currentThread().isInterrupted()){
                         System.out.println("Interrupted!");
                         break;
                     }
                     try {
                         Thread.sleep(200);
                     } catch (InterruptedException e) {
                         System.out.println("Interrupt when sleep");
                         Thread.currentThread().interrupt();
                     }
                     Thread.yield();
                 }
            }
        };

        t1.start();
        Thread.sleep(2000);
        t1.interrupt();
    }

}
