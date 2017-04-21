package disruptor.base;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/4/2
 * Time: 21:06
 */
public class LongEventMain {

    public static void main(String[] args) {
        //创建缓冲池
        ExecutorService executor = Executors.newCachedThreadPool();

        //创建工厂
        LongEventFactory factory = new LongEventFactory();

        //设置buffer大小，也就是ringBuffer的大小，必须是2的N次方(如果不是2的N次方，会导致效率的降低)
        int ringBufferSize = 1024 * 1024;

        //创建disruptor实例
        /*
         * 1.第一个参数是工厂类对象，用于创建LongEvent,LongEvent就是实际消费的数据
         * 2.第二个参数是缓冲区大小
         * 3.第三个参数是线程池，进行Disruptor内部的数据接收处理调度
         * 4.第四个参数ProducerType.SINGLE和ProducerType.MULTI
         *   ProducerType.SINGLE表示生产者只有一个
         *   ProducerType.MULTI 表示具有多个生产者
         * 5.第五个参数表示策略
         *   BlockingWaitStrategy 是最低效的策略，但其对CPU的消耗最小并且在各种不同部署环境中能提供更加一致的性能表现
         *   SleepingWaitStrategy 的性能表现跟BlockingWaitStrategy差不多，对CPU的消耗也类似，但其对生产者线程的影响最小，适合用于异步日志类似的场景
         *   YieldingWaitStrategy 的性能是最好的，适合用于低延迟的系统。在要求极高性能且事件处理线数小于CPU逻辑核心数的场景中，推荐使用此策略；例如，CPU开启超线程的特性
         */
        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(factory, ringBufferSize, executor, ProducerType.SINGLE, new YieldingWaitStrategy());

        //连接消费事件
        disruptor.handleEventsWith(new LongEventHandler());

        //启动
        disruptor.start();

        //发布事件
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        //LongEventProducer producer = new LongEventProducer(ringBuffer);
        LongEventProducerWithTranslator producer = new LongEventProducerWithTranslator(ringBuffer);

        ByteBuffer buffer = ByteBuffer.allocate(8);
        for (long i = 0; i < 100; ++i) {
            buffer.putLong(0, i);
            producer.onData(buffer);
        }

        disruptor.shutdown();
        executor.shutdown();
    }
}
