package mulit;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/4/21
 * Time: 9:55
 */
public class Producer {

    public static void main(String[] args) throws JMSException {
        ConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER, ActiveMQConnectionFactory.DEFAULT_PASSWORD, "tcp://182.92.235.106:61616");
        Connection connection = factory.createConnection();
        connection.start();

        Session session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE);
        Queue queue = session.createQueue("myqueue");
        MessageProducer producer = session.createProducer(queue);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);

        for(int i = 0; i < 5; ++i){
            TextMessage message = session.createTextMessage();
            message.setText("我是topic:mytopic的信息" + i);
            producer.send(message);
        }

        session.commit();
        connection.close();
    }

}
