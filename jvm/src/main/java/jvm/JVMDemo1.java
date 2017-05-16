package jvm;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/3/19
 * Time: 18:09
 */
public class JVMDemo1 {

    public static JVMDemo1 demo = new JVMDemo1();
    public static int count1 = 0;
    public static int count2;

    public JVMDemo1() {
        count1++;
        count2++;
    }


    public static void main(String[] args) {
        System.out.println(demo.count1);
        System.out.println(demo.count2);


        Long s = Instant.now().getEpochSecond();
        System.out.println(s);


        System.out.println(new Date().getTime());

        List<String> bikeIds = new ArrayList<>(Arrays.asList("123456","1234564","234567"));
        String s1 = JSON.toJSONString(bikeIds);
        System.out.println(s1);

        List<String> strings = JSON.parseArray("[\"123456\",\"1234564\",\"234567\"]", String.class);
        System.out.println(strings);

        String[] bIds = {"123456","1234564","234567"};
        System.out.println(JSON.toJSONString(bIds));
    }
}
