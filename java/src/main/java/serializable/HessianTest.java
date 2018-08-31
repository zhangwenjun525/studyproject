package serializable;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;

import java.io.*;
import java.util.Arrays;

public class HessianTest {

    public static void main(String[] args) throws IOException {
        User user = new User();
        user.setName("hollis");
        user.setAge(23);
        System.out.println(user);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        HessianOutput os = new HessianOutput(bos);
        os.writeObject(user);

        byte[] bytes = bos.toByteArray();
        System.out.println(Arrays.toString(bos.toByteArray()));
        System.out.println(bytes.length);

        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        HessianInput ois = new HessianInput(bis);
        User user1 = (User) ois.readObject();
        System.out.println(user1);


    }
}
