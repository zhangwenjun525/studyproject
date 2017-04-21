package thread;

import java.util.concurrent.TimeUnit;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/3/23
 * Time: 10:11
 */
public class DirtyRead {

    private String username = "abc";
    private String password = "123";

    public synchronized void setValue(String username, String password){
        this.username = username;
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.password = password;
        System.out.println("setValue最终结果 username: " + username + " ,password = " + password);
    }

    /*synchronized*/
    public  void getValue(){
        System.out.println("getValue最终结果 username: " + username + " ,password = " + password);
    }

    public static void main(String[] args) {
        DirtyRead dirtyRead = new DirtyRead();
        new Thread(()->dirtyRead.setValue("def","456")).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dirtyRead.getValue();
    }

}
