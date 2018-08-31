package xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.util.Date;

public class XStreamTest {

    public static void main(String[] args) {
        Person person = new Person("章文君", "xxxx", 28, new Date());

        XStream xStream = new XStream(new DomDriver());
        xStream.alias("person", Person.class);
        String personXML = xStream.toXML(person);
        System.out.println(personXML);

    }
}
