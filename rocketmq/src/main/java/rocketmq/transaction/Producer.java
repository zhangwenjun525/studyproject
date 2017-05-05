package rocketmq.transaction;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.LocalTransactionState;
import com.alibaba.rocketmq.client.producer.TransactionCheckListener;
import com.alibaba.rocketmq.client.producer.TransactionMQProducer;
import com.alibaba.rocketmq.client.producer.TransactionSendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageExt;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/4/29
 * Time: 20:07
 */
public class Producer {

    public static void main(String[] args) throws MQClientException {
        TransactionMQProducer producer = new TransactionMQProducer("transalation_producer_group");
        producer.setNamesrvAddr("127.0.0.1:9876");

        /**
         * 事务回查
         * 事务回查最小并发数
         * 事务回查最大并发数
         * 事务
         */
        producer.setCheckThreadPoolMinSize(5);
        producer.setCheckThreadPoolMaxSize(20);
        producer.setCheckRequestHoldMax(2000);

        producer.start();

        //检查本地事务成功还是失败
        producer.setTransactionCheckListener(new TransactionCheckListener() {
            @Override
            public LocalTransactionState checkLocalTransactionState(MessageExt messageExt) {
                System.out.println("state -- " + new String(messageExt.getBody()) );
                return LocalTransactionState.COMMIT_MESSAGE;
            }
        });

        TransactionExecuterImpl executer = new TransactionExecuterImpl();

        for(int i = 1; i <= 2; ++i){
            Message msg = new Message("TopicTransaction", "Transaction" + i, "key", ("Hello Rocket" + i).getBytes());
            TransactionSendResult sendResult = producer.sendMessageInTransaction(msg, executer, "tq");
            System.out.println(sendResult);
        }

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                producer.shutdown();
            }
        }));

        System.exit(0);
    }

}
