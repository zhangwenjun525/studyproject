package demo.demo01;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author zhangwj
 * @date 2018/9/13 15:46
 */
public class Consumer {

    public static void main(String[] args) throws JMSException {

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER, ActiveMQConnectionFactory.DEFAULT_PASSWORD, "tcp://localhost:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue("itcast");
        MessageConsumer consumer = session.createConsumer(destination);


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
