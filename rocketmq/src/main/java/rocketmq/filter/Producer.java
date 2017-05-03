package rocketmq.filter;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/4/30
 * Time: 14:37
 */
public class Producer {

    public static void main(String[] args) throws MQClientException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("ProducerGroupName");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.setVipChannelEnabled(false);
        producer.start();
        try {

            for (int i = 0; i < 6000000; i++) {
                Message msg = new Message("TopicFilter7",// topic
                        "TagA",// tag
                        "OrderID001",// key
                        ("Hello MetaQ").getBytes());// body
                msg.putUserProperty("SequenceId", String.valueOf(i));
                SendResult sendResult = producer.send(msg);
                System.out.println(sendResult);
                Thread.sleep(100);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        producer.shutdown();

    }
}
