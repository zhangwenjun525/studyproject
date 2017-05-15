package com.project.shiro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/5/15
 * Time: 15:08
 */
@SpringBootApplication
public class ShiroApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ShiroApplication.class);
        application.run(args);
    }

}
