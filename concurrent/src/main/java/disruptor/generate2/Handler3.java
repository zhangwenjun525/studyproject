package disruptor.generate2;

import com.lmax.disruptor.EventHandler;
import com.zhangwj.project.java8.thread.disruptor.generate1.Trade;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/4/2
 * Time: 23:43
 */
public class Handler3 implements EventHandler<Trade> {
    @Override
    public void onEvent(Trade trade, long l, boolean b) throws Exception {
        System.out.println("handler3: name: " + trade.getName() + " , price: " + trade.getPrice() + ";  instance: " + trade.toString());
    }
}
