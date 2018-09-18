package distributedLock;

/**
 * @author zhangwj
 * @date 2018/9/18 15:55
 */
public interface Lock {

    void getLock();

    void unLock();

}
