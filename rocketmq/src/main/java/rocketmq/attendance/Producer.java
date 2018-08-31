package rocketmq.attendance;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.MessageQueueSelector;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageQueue;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import rocketmq.subwaytrip.IDHelper;

import java.util.Date;
import java.util.List;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/4/29
 * Time: 13:11
 */
public class Producer {

    public static void main(String[] args) {
        DefaultMQProducer producer = new DefaultMQProducer("order_producer");
        producer.setNamesrvAddr("127.0.0.1:9876");

        try {
            producer.start();
            for(int i = 0; i < 1; ++i){
                EntranceGuardPassMessage message = new EntranceGuardPassMessage();
                message.setId(IDHelper.createID());
                message.setUserId("1");
                message.setMobile("13588755652");
                message.setName("章文君");
                message.setImsi("13588755652-imsi");
                message.setPassTime(new Date());
                message.setCellTowerCode("2");
                message.setCellTowerName("骑呗科技门禁-out");
                message.setCustomerId("1");
                message.setStatus(1);
                message.setCustomerName("杭州骑呗科技有限公司");
                Message msg = new Message("attendance_topic", "order_1", "KEY" + i, JSON.toJSONString(message).getBytes());
                SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
                    @Override
                    public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                        Integer id = (Integer) o;
                        System.out.println(id);
                        int index = id % list.size();
                        return list.get(index);
                    }
                }, 0);
                System.out.println(sendResult);
            }
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } finally {
            producer.shutdown();
        }

    }

}
