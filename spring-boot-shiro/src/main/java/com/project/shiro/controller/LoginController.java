package com.project.shiro.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/5/15
 * Time: 15:40
 */
@RestController
public class LoginController {

    @PostMapping(value = "/login")
    public String login(){
        return "login success";
    }

}
