package rocketmq.transaction;

import com.alibaba.rocketmq.client.producer.LocalTransactionExecuter;
import com.alibaba.rocketmq.client.producer.LocalTransactionState;
import com.alibaba.rocketmq.common.message.Message;

/**
 * 执行本地事务
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/4/29
 * Time: 20:42
 */
public class TransactionExecuterImpl implements LocalTransactionExecuter {

    @Override
    public LocalTransactionState executeLocalTransactionBranch(Message message, Object o) {
        System.out.println("msg = " + new String(message.getBody()));
        System.out.println("o = " + o);
        String tag = message.getTags();
        if(tag.equals("Transaction1")){
            System.out.println("这里处理业务逻辑，比如数据库操作，失败情况下RollBack");
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
        return LocalTransactionState.COMMIT_MESSAGE;
    }
}
