package selector;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/4/20
 * Time: 10:58
 */
public class Producer {

    public static void main(String[] args) throws JMSException {

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,
                ActiveMQConnectionFactory.DEFAULT_PASSWORD, "tcp://182.92.235.106:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE);
        Destination dest = session.createQueue("order_queue");
        MessageProducer producer = session.createProducer(dest);
        for (int i = 0; i < 100; ++i) {
            // 创建一个文本消息
            TextMessage message = session.createTextMessage("A-张三-" + i);
            // 这里我们分别设置对应的消息信息，当成是一组消息
            message.setStringProperty("JMSXGroupID", "A");
            producer.send(message);

            TextMessage message1 = session.createTextMessage("B-李四-" + i);
            message1.setStringProperty("JMSXGroupID", "B");
            producer.send(message1);
        }
        session.commit();
        connection.close();
    }
}
