package demo;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/4/18
 * Time: 17:34
 */
public class MainTestDemo {

    public static String myEmailAccount = "tiantai@qibeitech.com";
    public static String myEmailPassword = "123qweasD";
    public static String myEmailSMTPHost = "smtp.mxhichina.com";

    // 收件人邮箱（替换为自己知道的有效邮箱）
    public static String receiveMailAccount = "zhangwj@qibeitech.com";

    public static void main(String[] args) throws Exception {

        Properties props = new Properties();                    // 参数配置
        props.setProperty("mail.smtp.timeout", "50000");
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(myEmailSMTPHost);
        mailSender.setPort(465);
        mailSender.setUsername(myEmailAccount);
        mailSender.setPassword(myEmailPassword);
        mailSender.setJavaMailProperties(props);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");
        helper.setFrom(myEmailAccount);
        helper.setTo(receiveMailAccount);
        helper.setSubject("邮箱注册验证码");// 设置主题
        helper.setText("<html><p>这是一封来自骑呗App邮件,验证码:[123456]</html>", true);// 邮件体
        mailSender.send(message);// 发送邮件
    }
}
