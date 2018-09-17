package demo.demo01;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author zhangwj
 * @date 2018/9/13 15:39
 */
public class Producer {

    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER, ActiveMQConnectionFactory.DEFAULT_PASSWORD, "tcp://localhost:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue("itcast");
        MessageProducer producer = session.createProducer(destination);

        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        for (int i = 0; i < 5; ++i) {
            TextMessage message = session.createTextMessage();
            message.setText("我是消息内容,id = " + i);
            producer.send(message);
        }

        if (null != connection) {
            connection.close();
        }
    }

}
