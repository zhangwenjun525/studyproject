package rocketmq.quickstart;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/4/24
 * Time: 23:56
 */
public class Producer {

    public static void main(String[] args) {
        DefaultMQProducer producer = new DefaultMQProducer("TestProducer");
        producer.setNamesrvAddr("127.0.0.1:9876");
        try {
            producer.start();
            for(int i = 0; i < 10; ++i){
                Message msg = new Message("QuickStartTest", ("quick start message " + i).getBytes());
                SendResult sendResult = producer.send(msg);
                System.out.println(sendResult);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.shutdown();
        }
    }

}
