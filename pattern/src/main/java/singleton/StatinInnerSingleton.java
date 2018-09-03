package singleton;

/**
 * @author zhangwj
 * @date 2018/8/31 15:38
 */
public class StatinInnerSingleton {

    private StatinInnerSingleton() {

    }

    private static class SingletonHolder {
        private static StatinInnerSingleton instance = new StatinInnerSingleton();
    }

    public static StatinInnerSingleton getInstance() {
        return SingletonHolder.instance;
    }

}
