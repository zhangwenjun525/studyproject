package lambda;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.*;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/3/11
 * Time: 13:10
 */
public class LambdaTest {

    List<Employee> employeeList = Arrays.asList(
            new Employee("张三", 18, 10000D),
            new Employee("李四", 20, 20000D),
            new Employee("王五", 30, 15000D),
            new Employee("赵六", 25, 18500D)
    );


    public List<Employee> findByCondition(List<Employee> employeeList, Predict<Employee> predict) {
        List<Employee> employees = new ArrayList<Employee>();
        for (Employee employee : employeeList) {
            if (predict.predict(employee)) {
                employees.add(employee);
            }
        }
        return employees;
    }

    @Test
    public void testFindByAge() {
        findByCondition(employeeList, (e) -> e.getAge() >= 20).forEach(System.out::println);
    }

    @Test
    public void testFindBySalary() {
        findByCondition(employeeList, (e) -> e.getSalary() <= 18000).forEach(System.out::println);
    }


    @Test
    public void testStream() {
        employeeList.stream().filter(e -> e.getSalary() <= 18000).forEach(System.out::println);
    }


    @Test
    public void testConsumer() {
        Consumer<String> consumer = System.out::println;
        consumer.accept("xxxx");
    }

    @Test
    public void testComparator() {
        Comparator<Integer> comparator = Integer::compare;
        System.out.println(comparator.compare(100, 200));
    }

    @Test
    public void test1() {
        BiPredicate<String, String> predicate = String::equals;
        System.out.println(predicate.test("xxx", "xxxx"));
    }

    @Test
    public void test2() {
        Supplier<Employee> supplier = Employee::new;
        System.out.println(supplier.get().toString());

        Function<String, Employee> function = Employee::new;
        System.out.println(function.apply("张文军"));

        BiFunction<String, Integer, Employee> biFunction = Employee::new;
        System.out.println(biFunction.apply("张文军", 27));
    }

    @Test
    public void test3() {
        Function<Integer, String[]> function = String[]::new;
        System.out.println(function.apply(10).length);
    }


}
