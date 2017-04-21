package selector;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/4/20
 * Time: 11:11
 */
public class Consumer {

    public static void main(String[] args) throws JMSException {

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,
                ActiveMQConnectionFactory.DEFAULT_PASSWORD, "tcp://182.92.235.106:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        Destination destination = session.createQueue("order_queue");
        // 创建消费者
        MessageConsumer consumerA = session.createConsumer(destination, "JMSXGroupID='A'");
        consumerA.setMessageListener((message) -> {
            TextMessage textMessage = (TextMessage) message;
            try {
                message.acknowledge();
                System.out.println("A：" + textMessage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });

        // 创建消费者
        MessageConsumer consumerB = session.createConsumer(destination, "JMSXGroupID='B'");
        consumerB.setMessageListener((message) -> {
            TextMessage textMessage = (TextMessage) message;
            try {
                message.acknowledge();
                System.out.println("B：" + textMessage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });
    }
}
