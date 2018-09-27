package proxy.cglibproxy;

/**
 * @author zhangwj
 * @date 2018/9/27 9:24
 */
public class Main {

    public static void main(String[] args) {
        CglibProxy cglibProxy = new CglibProxy();
        UserService userService = (UserService) cglibProxy.getInstance(UserService.class);
        System.out.println(userService.getAge(1));
        System.out.println(userService.getName(1));
    }
}
