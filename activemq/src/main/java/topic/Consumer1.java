package topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/4/21
 * Time: 10:03
 */
public class Consumer1 {

    public static void main(String[] args) throws JMSException {
        ConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER, ActiveMQConnectionFactory.DEFAULT_PASSWORD, "tcp://182.92.235.106:61616");
        Connection connection = factory.createConnection();
        connection.setClientID("client1");
        connection.start();

        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        Topic mytopic = session.createTopic("mytopic");
        MessageConsumer consumer = session.createDurableSubscriber(mytopic, "client1");

        consumer.setMessageListener(message -> {
            TextMessage textMessage = (TextMessage) message;
            try {
                message.acknowledge();
                System.out.println(textMessage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });


    }

}
