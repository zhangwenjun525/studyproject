package juc;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/3/14
 * Time: 17:29
 */
public class CopyOnWriteArrayListTest {

    private static List<String> list = Collections.synchronizedList(Arrays.asList("AA", "BB", "CC"));

    @Test
    public void test() {

    }

}
