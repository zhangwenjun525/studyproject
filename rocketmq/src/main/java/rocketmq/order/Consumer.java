package rocketmq.order;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.*;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/4/29
 * Time: 13:18
 */
public class Consumer {

    private static Integer total = 0;

    public static void main(String[] args) {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("order_producer");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.setConsumeThreadMin(4);
        consumer.setConsumeThreadMax(20);
        consumer.setConsumeMessageBatchMaxSize(32);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        try {
            consumer.subscribe("bus_depart_topic","*");
            consumer.registerMessageListener(new MessageListenerOrderly() {
                @Override
                public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
                    // 设置自动提交
                    System.out.println("consumer list msg size = " + list.size());
                    consumeOrderlyContext.setAutoCommit(true);
                    for (MessageExt msg : list) {
                        System.out.println("key = " + msg.getKeys());
                        System.out.println(msg + ",内容：" + new String(msg.getBody()) + ", total = " + total++);
                    }
                  try {
                        TimeUnit.MILLISECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return ConsumeOrderlyStatus.SUCCESS;
                }
            });
            consumer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        } finally {

        }


    }
}
