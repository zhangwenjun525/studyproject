package thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/3/24
 * Time: 14:18
 */
public class ListAdd {
    private volatile static List list = new ArrayList<>();

    public void add() {
        list.add("bjsxt");
    }

    public int size() {
        return list.size();
    }

    public static void main(String[] args) {
        ListAdd listAdd = new ListAdd();

        CountDownLatch countDownLatch = new CountDownLatch(1);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 100; ++i) {
                        if (list.size() == 5) {
                            System.out.println("发送通知");
                            countDownLatch.countDown();
                        }
                        listAdd.add();
                        System.out.println(Thread.currentThread().getName() + "添加一个元素");
                        TimeUnit.MILLISECONDS.sleep(500);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (listAdd.size() != 5) {
                        countDownLatch.await();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "收到通知");
                throw new RuntimeException();
            }
        }, "t2");


        t1.start();
        t2.start();


    }


}
