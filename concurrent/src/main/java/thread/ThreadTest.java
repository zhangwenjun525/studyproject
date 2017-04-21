package thread;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/3/23
 * Time: 10:00
 */

class PrintNum implements Runnable{

    private Integer count = 5;

    @Override
    public synchronized void run() {
        count--;
        System.out.println(Thread.currentThread().getName() + " -------> " + count);
    }
}

public class ThreadTest {

    public static void main(String[] args) {
        PrintNum printNum = new PrintNum();
        new Thread(printNum, "t1").start();
        new Thread(printNum, "t2").start();
        new Thread(printNum, "t3").start();
        new Thread(printNum, "t4").start();
        new Thread(printNum, "t5").start();
    }
}
