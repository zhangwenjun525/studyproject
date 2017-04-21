package helloworld;

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

        /**
         *  参数1 是否开启事务  如果开启了事务， 发送完成后需要手动调用session.commit方法提交事务
         *  参数2 是否开启自动告知  AUTO_ACKNOWLEDGE 自动告知
         *                       CLIENT_ACKNOWLEDGE 消费端需要手动调用message.acknowledge方法告知已接收到消息
         */
        Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
        Destination dest = session.createQueue("queue1");
        MessageProducer messageProducer = session.createProducer(dest);
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
        for (int i = 0; i < 5; ++i) {
            TextMessage message = session.createTextMessage();
            message.setText("我是消息内容,id = " + i);
            messageProducer.send(message);
        }

        if (null != connection) {
            connection.close();
        }
    }

}
