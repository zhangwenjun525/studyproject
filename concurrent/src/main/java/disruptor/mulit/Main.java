package disruptor.mulit;

import com.lmax.disruptor.*;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/4/3
 * Time: 0:16
 */
public class Main {

    public static void main(String[] args) throws Exception {
        RingBuffer<Order> ringBuffer = RingBuffer.createMultiProducer(() -> new Order(), 1024 * 1024, new YieldingWaitStrategy());
        SequenceBarrier barrier = ringBuffer.newBarrier();

        Consumer[] consumers = new Consumer[3];
        for (int i = 0; i < 3; ++i) {
            consumers[i] = new Consumer("c" + i);
        }

        WorkerPool<Order> workerPool = new WorkerPool<Order>(ringBuffer, barrier, new IgnoreExceptionHandler(), consumers);
        ringBuffer.addGatingSequences(workerPool.getWorkerSequences());

        workerPool.start(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));
        CountDownLatch latch = new CountDownLatch(1);

        for (int i = 0; i < 100; ++i) {
            Producer producer = new Producer(ringBuffer);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        latch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for (int j = 0; j < 100; ++j) {
                        producer.onData(UUID.randomUUID().toString());
                    }
                }
            }).start();
        }

        TimeUnit.SECONDS.sleep(2);
        System.out.println("开始生产数据");
        latch.countDown();
        TimeUnit.SECONDS.sleep(5);
        System.out.println("总量" + consumers[0].getCount());
    }
}
