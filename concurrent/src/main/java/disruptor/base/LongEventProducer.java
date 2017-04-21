package disruptor.base;

import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/4/2
 * Time: 21:47
 */
public class LongEventProducer {

    private final RingBuffer<LongEvent> ringBuffer;

    public LongEventProducer(RingBuffer<LongEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    /**
     * onData用于发布事件，每调用一次就发布一次事件
     *
     * @param buffer
     */
    public void onData(ByteBuffer buffer) {
        //1.可以把ringBuffer看作一个事件队列，那么next就是得到下一个事件槽
        long sequence = ringBuffer.next();
        try {
            //2.用上面的索引获取一个空的事件用于填充
            LongEvent longEvent = ringBuffer.get(sequence);
            //3.获取要通过事件传递的业务数据
            longEvent.setValue(buffer.getLong(0));
        } finally {
            //4.发布事件
            ringBuffer.publish(sequence);
        }
    }


}
