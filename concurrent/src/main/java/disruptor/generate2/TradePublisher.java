package disruptor.generate2;

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.dsl.Disruptor;
import disruptor.generate1.Trade;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/4/2
 * Time: 23:48
 */
public class TradePublisher implements Runnable {

    private CountDownLatch latch;

    private Disruptor<Trade> disruptor;

    public TradePublisher(CountDownLatch latch, Disruptor<Trade> disruptor) {
        this.latch = latch;
        this.disruptor = disruptor;
    }

    @Override
    public void run() {
        TradeEventTranslator tradeEventTranslator = new TradeEventTranslator();
        for (int i = 0; i < 10; ++i) {
            disruptor.publishEvent(tradeEventTranslator);
        }
        latch.countDown();
    }
}

class TradeEventTranslator implements EventTranslator<Trade> {

    @Override
    public void translateTo(Trade trade, long l) {

    }

    private Trade generateTrade(Trade trade) {
        trade.setPrice(new Random().nextDouble() * 9999);
        return trade;
    }

}
