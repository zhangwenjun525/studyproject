package proxy.cglibproxy;

/**
 * @author zhangwj
 * @date 2018/9/27 9:14
 */
public class UserService {

    public String getName(int id) {
        System.out.println("------getName------");
        return "Tom";
    }

    public Integer getAge(int id) {
        System.out.println("------getAge------");
        return 10;
    }
}
