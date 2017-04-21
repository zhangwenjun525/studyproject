package helloworld2;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.UUID;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/4/18
 * Time: 9:20
 */
public class Producer {

    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,
                ActiveMQConnectionFactory.DEFAULT_PASSWORD,
                "tcp://182.92.235.106:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(Boolean.TRUE, Session.CLIENT_ACKNOWLEDGE);
        Queue dest = session.createQueue("queue1");
        MessageProducer producer = session.createProducer(dest);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        for (int i = 0; i < 5; ++i) {
            TextMessage message = session.createTextMessage();
            message.setText("发布消息Id:" + UUID.randomUUID().toString().replaceAll("-", ""));
            producer.send(message);
        }
        session.commit();
        connection.close();
    }

}
