package sample.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.SessionAwareMessageListener;

import javax.jms.*;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/4/21
 * Time: 16:52
 */
public class MessageQueueListener implements SessionAwareMessageListener<Message> {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Destination messageQueue;

    @Override
    public synchronized void onMessage(Message message, Session session) throws JMSException {
            TextMessage msg = (TextMessage) message;
            final String ms = msg.getText();
            System.out.println("收到消息：" + ms);
    }
}
