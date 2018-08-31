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
public class Producer2 {

    public static void main(String[] args) {
        DefaultMQProducer producer = new DefaultMQProducer("rocketmq_subwaytrip_2");
        producer.setNamesrvAddr("127.0.0.1:9876");

        try {
            producer.start();
            for(int i = 0; i < 1; ++i){
                SubwayTripArriveInfo info = new SubwayTripArriveInfo();
                info.setUserId("userId" + String.valueOf(i + 1));
                info.setArriveSiteId(String.valueOf(i + 1));
                info.setArriveSite("site" + (i + 1));
                info.setArriveTime(new Date());
                Message msg = new Message("subway_arrive_topic", "*", UUID.randomUUID().toString().replaceAll("-", ""), JSON.toJSONString(info).getBytes());
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
