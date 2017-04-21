package jvm;

import java.time.Instant;
import java.util.Date;

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

    }
}
