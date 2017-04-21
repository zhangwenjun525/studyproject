package juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/3/14
 * Time: 22:16
 */

class Print{
    private Integer flag = 1;
    private Lock lock = new ReentrantLock();
    private Condition conditionA = lock.newCondition();
    private Condition conditionB = lock.newCondition();
    private Condition conditionC = lock.newCondition();

    public void loopA(int totalLoop){
        try{
            lock.lock();
            if(flag != 1){
                conditionA.await();
            }

            for(int i = 1; i <= 5; ++i){
                System.out.println(Thread.currentThread().getName() + "\t" + i + "\t" + totalLoop);
            }

            flag = 2;
            conditionB.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void loopB(int totalLoop){
        try{
            lock.lock();
            if(flag != 2){
                conditionB.await();
            }

            for(int i = 1; i <= 10; ++i){
                System.out.println(Thread.currentThread().getName() + "\t" + i + "\t" + totalLoop);
            }

            flag = 3;
            conditionC.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
    public void loopC(int totalLoop){
        try{
            lock.lock();
            if(flag != 3){
                conditionC.await();
            }

            for(int i = 1; i <= 15; ++i){
                System.out.println(Thread.currentThread().getName() + "\t" + i + "\t" + totalLoop);
            }

            flag = 1;
            conditionA.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}


public class ABCAlternate {

    public static void main(String[] args) {
        Print print = new Print();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 1; i <= 20; ++i){
                    print.loopA(i);
                }
            }
        },"A").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 1; i <= 20; ++i){
                    print.loopB(i);
                }
            }
        },"B").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 1; i <= 20; ++i){
                    print.loopC(i);
                }
            }
        },"C").start();
    }
}
