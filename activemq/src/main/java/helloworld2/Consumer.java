package helloworld2;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/4/18
 * Time: 9:44
 */
public class Consumer {
    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,
                ActiveMQConnectionFactory.DEFAULT_PASSWORD,
                "tcp://182.92.235.106:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(Boolean.FALSE, Session.CLIENT_ACKNOWLEDGE);
        Queue dest = session.createQueue("queue1");
        MessageConsumer consumer = session.createConsumer(dest);

        while (true) {
            TextMessage message = (TextMessage) consumer.receive();
            message.acknowledge();
            if (message == null) break;
            System.out.println(message.getText());
        }

        connection.close();
    }

}
