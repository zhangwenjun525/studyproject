package collection.delayqueue;

import java.util.concurrent.DelayQueue;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/3/27
 * Time: 15:01
 */
public class InternetBar implements Runnable {

    private DelayQueue<Netizen> queue = new DelayQueue<>();

    public void enter(String name, String idCard, int money) {
        Netizen netizen = new Netizen(name, idCard, System.currentTimeMillis() + 3000 * money);
        System.out.println("网名" + netizen.getName() + " 身份证" + netizen.getIdCard() + "交钱" + money + "块,开始上机...");
        queue.add(netizen);
    }

    public void quit(Netizen netizen) {
        System.out.println("网名" + netizen.getName() + " 身份证" + netizen.getIdCard() + "时间到下机...");
    }

    @Override
    public void run() {
        while (true) {
            try {
                Netizen netizen = queue.take();
                quit(netizen);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]) {
        try {
            System.out.println("网吧开始营业");
            InternetBar siyu = new InternetBar();
            Thread shangwang = new Thread(siyu);
            shangwang.start();

            siyu.enter("路人甲", "123", 1);
            siyu.enter("路人乙", "234", 10);
            siyu.enter("路人丙", "345", 5);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
