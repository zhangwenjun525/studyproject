package optional;

import lambda.Employee;
import org.junit.Test;

import java.util.Optional;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/3/13
 * Time: 17:58
 */
public class OptionalTest {

    @Test
    public void test1() {
        Optional<Employee> op = Optional.of(new Employee());
        Employee employee = op.get();
        System.out.println(employee);

        System.out.println(op.isPresent());

        op = Optional.ofNullable(null);
        if (op.isPresent()) {
            System.out.println(op.get());
        }

        Employee employee1 = op.orElse(new Employee());
        System.out.println(employee1);

        Employee employee2 = op.orElseGet(Employee::new);
        System.out.println(employee2);


        op = Optional.ofNullable(new Employee("章文君", 27, 12000D));
        Optional<Double> aDouble = op.map(Employee::getSalary);
        System.out.println(aDouble.get());
        System.out.println("------------------------------------------");
        Optional<Double> aDouble1 = op.flatMap(e -> Optional.of(e.getSalary()));
        System.out.println(aDouble1.get());
    }
}
