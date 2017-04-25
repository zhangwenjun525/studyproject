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
        DefaultMQProducer producer = new DefaultMQProducer("Producer");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.setVipChannelEnabled(false);
        try {
            producer.start();

            Message msg = new Message("PushTopic", "push", "1", "Just for test1.".getBytes());
            SendResult result = producer.send(msg);
            System.out.println("id:" + result.getMsgId() + ", result:" + result.getSendStatus());

            msg = new Message("PushTopic", "push", "2", "Just for test2.".getBytes());
            result = producer.send(msg);
            System.out.println("id:" + result.getMsgId() + ", result:" + result.getSendStatus());

            msg = new Message("PushTopic", "push", "1", "Just for test3.".getBytes());
            result = producer.send(msg);
            System.out.println("id:" + result.getMsgId() + ", result:" + result.getSendStatus());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.shutdown();
        }
    }

}
