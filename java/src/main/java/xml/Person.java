package xml;

import java.util.Date;

public class Person {

    private String name;

    private String address;

    private Integer age;

    private Date birth;

    public Person() {
    }

    public Person(String name, String address, Integer age, Date birth) {
        this.name = name;
        this.address = address;
        this.age = age;
        this.birth = birth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }


    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", age=" + age +
                ", birth=" + birth +
                '}';
    }
}
