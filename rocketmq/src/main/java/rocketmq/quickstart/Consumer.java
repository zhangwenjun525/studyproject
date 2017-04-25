package rocketmq.quickstart;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/4/25
 * Time: 0:06
 */
public class Consumer {

    public static void main(String[] args) {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("PushConsumer");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.setVipChannelEnabled(false);
        try{
            consumer.subscribe("PushTopic", "push");
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                    Message msg = list.get(0);
                    System.out.println(msg.toString());
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            consumer.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}