package helloworld;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/4/17
 * Time: 15:26
 */
public class Receiver {

    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER, ActiveMQConnectionFactory.DEFAULT_PASSWORD, "tcp://localhost:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
        Destination dest = session.createQueue("queue1");
        MessageConsumer consumer = session.createConsumer(dest);

        while (true) {
            TextMessage message = (TextMessage) consumer.receive();
            if (null == message) break;
            System.out.println(message.getText());
        }

        if (null != connection) {
            connection.close();
        }
    }
}
