package disruptor.generate1;

import com.lmax.disruptor.*;

import java.util.concurrent.*;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/4/2
 * Time: 22:48
 */
public class Main1 {

    public static void main(String[] args) throws Exception {
        int BUFFER_SIZE = 1024;
        int THREAD_NUMBER = 4;

        RingBuffer<Trade> ringBuffer = RingBuffer.createSingleProducer(
                () -> new Trade(),
                BUFFER_SIZE,
                new YieldingWaitStrategy());

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_NUMBER);

        SequenceBarrier barrier = ringBuffer.newBarrier();

        BatchEventProcessor<Trade> batchEventProcessor = new BatchEventProcessor<>(ringBuffer, barrier, new TradeHandler());

        //把消费者的位置信息告诉生产端
        ringBuffer.addGatingSequences(batchEventProcessor.getSequence());

        executorService.submit(batchEventProcessor);

        Future<Void> future = executorService.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                for (int i = 0; i < 10; ++i) {
                    long sequeue = ringBuffer.next();
                    Trade trade = ringBuffer.get(sequeue);
                    trade.setPrice(Math.random() * 10000);
                    ringBuffer.publish(sequeue);
                }
                return null;
            }
        });

        future.get();
        TimeUnit.SECONDS.sleep(1);
        //通知事件处理器可以结束了（并不是立即结束）
        batchEventProcessor.halt();
        executorService.shutdown();
    }
}
