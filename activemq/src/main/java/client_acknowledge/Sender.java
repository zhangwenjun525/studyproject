package client_acknowledge;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/4/17
 * Time: 14:58
 */
public class Sender {

    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER, ActiveMQConnectionFactory.DEFAULT_PASSWORD, "tcp://localhost:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(Boolean.TRUE, Session.CLIENT_ACKNOWLEDGE);
        Destination dest = session.createQueue("queue1");
        MessageProducer messageProducer = session.createProducer(dest);
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
        for (int i = 0; i < 5; ++i) {
            TextMessage message = session.createTextMessage();
            message.setText("我是消息内容,id = " + i);
            messageProducer.send(message);
        }
        session.commit();

        if (null != connection) {
            connection.close();
        }
    }

}
