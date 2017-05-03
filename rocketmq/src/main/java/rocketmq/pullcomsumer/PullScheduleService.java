package rocketmq.pullcomsumer;

import com.alibaba.rocketmq.client.consumer.*;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.message.MessageQueue;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.Date;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/4/30
 * Time: 1:29
 */
public class PullScheduleService {
    public static void main(String[] args) throws MQClientException {
        final MQPullConsumerScheduleService scheduleService = new MQPullConsumerScheduleService("GroupName1");
        scheduleService.getDefaultMQPullConsumer().setNamesrvAddr("127.0.0.1:9876");
        scheduleService.setMessageModel(MessageModel.CLUSTERING);
        scheduleService.registerPullTaskCallback("TopicTest1", new PullTaskCallback() {

            @Override
            public void doPullTask(MessageQueue messageQueue, PullTaskContext pullTaskContext) {
                MQPullConsumer consumer = pullTaskContext.getPullConsumer();
                try {

                    long offset = consumer.fetchConsumeOffset(messageQueue, false);
                    if (offset < 0)
                        offset = 0;

                    PullResult pullResult = consumer.pull(messageQueue, "*", offset, 32);
                    System.out.println(new Date()+"--"+offset + "\t" + messageQueue + "\t" + pullResult);
                    switch (pullResult.getPullStatus()) {
                        case FOUND:
                            break;
                        case NO_MATCHED_MSG:
                            break;
                        case NO_NEW_MSG:
                        case OFFSET_ILLEGAL:
                            break;
                        default:
                            break;
                    }
                    consumer.updateConsumeOffset(messageQueue, pullResult.getNextBeginOffset());

                    //设置隔多长时间进行下次拉去
                    pullTaskContext.setPullNextDelayTimeMillis(10000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        scheduleService.start();
    }
}
