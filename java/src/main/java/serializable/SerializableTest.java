package serializable;

import java.io.*;
import java.util.Arrays;

public class SerializableTest {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        User user = new User();
        user.setName("hollis");
        user.setAge(23);
        System.out.println(user);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream  os = new ObjectOutputStream(bos);
        os.writeObject(user);

        byte[] bytes = bos.toByteArray();
        System.out.println(Arrays.toString(bos.toByteArray()));
        System.out.println(bytes.length);

        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bis);
        User user1 = (User) ois.readObject();
        System.out.println(user1);

    }
}
