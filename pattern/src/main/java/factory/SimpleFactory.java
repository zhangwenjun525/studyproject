package factory;

/**
 * @author zhangwj
 * @date 2018/8/31 15:56
 */
public class SimpleFactory {

    public static void main(String[] args) {
        CarFactroy.createCar("奥迪").run();
    }

}

interface Car {

    void run();
}

class AoDi implements Car {

    @Override
    public void run() {
        System.out.println("我是奥迪");
    }
}

class Benz implements Car {

    @Override
    public void run() {
        System.out.println("我是奔驰");
    }
}

class CarFactroy {

    public static Car createCar(String name) {
        switch (name) {
            case "奥迪":
                return new AoDi();
            case "奔驰":
                return new Benz();
            default:
                return null;
        }
    }
}
