package lambda;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/3/11
 * Time: 13:15
 */
public interface Predict<T> {

    boolean predict(T obj);
}
