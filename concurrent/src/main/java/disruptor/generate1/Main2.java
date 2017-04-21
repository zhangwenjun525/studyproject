package disruptor.generate1;

import com.lmax.disruptor.*;

import java.util.concurrent.*;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/4/2
 * Time: 23:19
 */
public class Main2 {

    public static void main(String[] args) throws Exception {
        int BUFFER_SIZE = 1024;
        int THREAD_NUMBER = 4;

        RingBuffer<Trade> ringBuffer = RingBuffer.createSingleProducer(
                () -> new Trade(),
                BUFFER_SIZE,
                new YieldingWaitStrategy());

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_NUMBER);

        SequenceBarrier barrier = ringBuffer.newBarrier();

        WorkHandler<Trade> handler = new TradeHandler();
        WorkerPool<Trade> workerPool = new WorkerPool<Trade>(ringBuffer, barrier, new IgnoreExceptionHandler(), handler);
        workerPool.start(executorService);

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
        workerPool.halt();
        executorService.shutdown();


    }

}
