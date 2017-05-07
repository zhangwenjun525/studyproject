package sample;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sample.service.SampleService;

import java.io.IOException;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/5/7
 * Time: 11:08
 */
public class Main {

    public static void main(String[] args) throws IOException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        System.out.println(applicationContext);

        SampleService sampleService = (SampleService) applicationContext.getBean("sampleService");
        String s = sampleService.sayHello("zhangwenjun");
        System.out.println(s);


        System.in.read();
    }


}
