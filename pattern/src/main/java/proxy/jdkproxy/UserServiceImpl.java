package proxy.jdkproxy;

/**
 * @author zhangwj
 * @date 2018/9/27 9:04
 */
public class UserServiceImpl implements UserService {

    @Override
    public String getName(int id) {
        System.out.println("------getName------");
        return "Tom";
    }

    @Override
    public Integer getAge(int id) {
        System.out.println("------getAge------");
        return 10;
    }
}
