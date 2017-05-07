package sample.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import sample.service.SampleService;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/5/6
 * Time: 23:36
 */
@Service()
public class SampleServiceImpl implements SampleService {
    @Override
    public String sayHello(String name) {
        return "hello" + name;
    }
}
