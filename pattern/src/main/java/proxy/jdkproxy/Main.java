package proxy.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author zhangwj
 * @date 2018/9/27 9:05
 */
public class Main {

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        Class<? extends UserService> clazz = userService.getClass();
        UserService userServiceProxy = (UserService) Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if ("getName".equalsIgnoreCase(method.getName())) {
                    System.out.println("++++++before " + method.getName() + "++++++");
                    Object result = method.invoke(userService, args);
                    System.out.println("++++++after " + method.getName() + "++++++");
                    return result;
                } else {
                    Object result = method.invoke(userService, args);
                    return result;
                }
            }
        });

        System.out.println(userServiceProxy.getAge(1));
        System.out.println(userServiceProxy.getName(1));

    }
}
