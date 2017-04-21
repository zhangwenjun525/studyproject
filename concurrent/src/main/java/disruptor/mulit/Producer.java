package disruptor.mulit;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/4/3
 * Time: 0:29
 */
public class Producer {

    private final RingBuffer<Order> ringBuffer;

    public Producer(RingBuffer<Order> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    private static final EventTranslatorOneArg<Order, String> TRANSLATOR = new EventTranslatorOneArg<Order, String>() {
        @Override
        public void translateTo(Order order, long l, String s) {
            order.setId(s);
        }
    };

    public void onData(String data) {
        ringBuffer.publishEvent(TRANSLATOR, data);
    }

}
