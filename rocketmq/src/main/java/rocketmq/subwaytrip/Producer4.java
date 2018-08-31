package rocketmq.subwaytrip;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;

import java.util.Date;
import java.util.UUID;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/4/29
 * Time: 13:11
 */
public class Producer4 {

    public static void main(String[] args) {
        DefaultMQProducer producer = new DefaultMQProducer("rocketmq_subwaytrip_4");
        producer.setNamesrvAddr("127.0.0.1:9876");

        try {
            producer.start();
            for(int i = 0; i < 1; ++i){
                DoorGuardTripInfo info = new DoorGuardTripInfo();
                info.setTripId(IDHelper.createID());
                info.setUserId("userId" + String.valueOf(i + 1));
                info.setMobile("mobile-" + (i + 1));
                info.setEnterTime(new Date());
                info.setEnterType("访客");
                info.setGuardName("近江六园西南门");
                info.setCommunity("近江六园");
                info.setCity("杭州");
                info.setManager("近江六园管理");
                info.setLongitude(120.00);
                info.setLatitude(30.0);
                Message msg = new Message("door_guard_enter_topic", "*", UUID.randomUUID().toString().replaceAll("-", ""), JSON.toJSONString(info).getBytes());
                SendResult sendResult = producer.send(msg);
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
