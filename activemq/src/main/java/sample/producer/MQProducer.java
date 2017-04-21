package sample.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/4/21
 * Time: 17:06
 */
@Service("mqProducer")
public class MQProducer {

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendMessage(String message){
        jmsTemplate.send(session -> session.createTextMessage(message));
    }


}
