package stream;

import lambda.Employee;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Create by IntelliJ IDEA
 * <p>
 * User: zhangwj
 * Date: 2017/3/12
 * Time: 14:40
 */
public class StreamTest {

    public void test1() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4);
        Stream<Integer> stream = list.stream();
    }

    public void test2() {
        Employee[] employees = new Employee[10];
        Stream<Employee> stream = Arrays.stream(employees);
    }

    public void test3() {
        Stream<String> aa = Stream.of("aa", "bb", "cc");
    }

    @Test
    public void test4() {
        Stream.iterate(0, t -> t + 2).limit(10).forEach(System.out::println);
    }

    @Test
    public void test5() {
        Stream.generate(Math::random).limit(10).forEach(System.out::println);
    }

    List<Employee> employeeList = Arrays.asList(
            new Employee("张三", 18, 10000D),
            new Employee("李四", 20, 20000D),
            new Employee("王五", 30, 15000D),
            new Employee("王五", 30, 15000D),
            new Employee("赵六", 25, 18500D)
    );

    @Test
    public void testFilter() {
        employeeList.stream().filter(e -> e.getSalary() < 20000D).forEach(System.out::println);
    }


    @Test
    public void testLimit() {
        employeeList.stream().filter(e -> e.getSalary() > 5000).limit(2).forEach(System.out::println);
    }


    @Test
    public void testSkip() {
        employeeList.stream().filter(e -> e.getSalary() > 5000).skip(2).forEach(System.out::println);
    }

    @Test
    public void testDistinct() {
        employeeList.stream().distinct().forEach(System.out::println);
    }

    @Test
    public void testMap() {
        List<String> list = Arrays.asList("aaa", "bbb", "ccc", "ddd", "eee");
        list.stream().map(String::toUpperCase).forEach(System.out::println);

        System.out.println("========================================================");

        employeeList.stream().map(Employee::getName).forEach(System.out::println);
    }

    public static Stream<Character> toCharacterArray(String str) {
        List<Character> list = new ArrayList<>();
        for (Character character : str.toCharArray()) {
            list.add(character);
        }
        return list.stream();
    }


    @Test
    public void testFlatMap() {
        List<String> list = Arrays.asList("aaa", "bbb", "ccc", "ddd", "eee");
        list.stream().flatMap(StreamTest::toCharacterArray).forEach(System.out::println);
    }

    @Test
    public void testSorted() {
        //自然排序
        List<String> list = Arrays.asList("aaa", "ccc", "ddd", "bbb", "eee");
        list.stream().sorted().forEach(System.out::println);

        //定制排序
        employeeList.stream().sorted((o1, o2) -> o1.getSalary().compareTo(o2.getSalary())).forEach(System.out::println);
    }

    @Test
    public void testAllMatch() {
        boolean b = employeeList.stream().allMatch((e) -> e.getSalary() <= 20000);
        System.out.println(b);
    }

    @Test
    public void testAnyMatch() {
        boolean b = employeeList.stream().anyMatch((e) -> e.getSalary() < 12000);
        System.out.println(b);
    }

    @Test
    public void testNoneMatch() {
        boolean b = employeeList.stream().noneMatch((e) -> e.getSalary() > 20000);
        System.out.println(b);
    }

    @Test
    public void testFindFirst() {
        Optional<Employee> first = employeeList.stream().findFirst();
        System.out.println(first.get());
    }

    @Test
    public void testFindAny() {
        Optional<Employee> any = employeeList.parallelStream().findAny();
        System.out.println(any.get());
    }

    @Test
    public void testCount() {
        long count = employeeList.stream().count();
        System.out.println(count);
    }

    @Test
    public void testMaxAndMin() {
        Optional<Employee> min = employeeList.stream().min(((o1, o2) -> Double.compare(o1.getSalary(), o2.getSalary())));
        System.out.println(min.get());
        System.out.println("------------------------------------------------");
        Optional<Employee> max = employeeList.stream().max(((o1, o2) -> Double.compare(o1.getSalary(), o2.getSalary())));
        System.out.println(max.get());
    }

    @Test
    public void testReduce() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Optional<Integer> reduce = list.stream().reduce((x, y) -> x + y);
        System.out.println(reduce.get());
    }

    @Test
    public void testCollect() {
        Double aDouble = employeeList.stream().collect(Collectors.averagingDouble(Employee::getSalary));
        System.out.println(aDouble);
        System.out.println("---------------------------------------------------------------------------");
        Optional<Employee> min = employeeList.stream().collect(Collectors.minBy((o1, o2) -> Double.compare(o1.getSalary(), o2.getSalary())));
        System.out.println(min.get().getSalary());
        System.out.println("---------------------------------------------------------------------------");
        Optional<Employee> max = employeeList.stream().collect(Collectors.maxBy((o1, o2) -> Double.compare(o1.getSalary(), o2.getSalary())));
        System.out.println(max.get().getSalary());
        System.out.println("---------------------------------------------------------------------------");
        DoubleSummaryStatistics collect = employeeList.stream().collect(Collectors.summarizingDouble(Employee::getSalary));
        System.out.println(collect.getAverage());
        System.out.println(collect.getMax());
        System.out.println(collect.getMin());
        System.out.println(collect.getCount());
        System.out.println(collect.getSum());
    }

    @Test
    public void testGroup() {
        Map<String, List<Employee>> collect = employeeList.stream().collect(Collectors.groupingBy(e -> {
            if (((Employee) e).getSalary() <= 15000) {
                return "灰领";
            } else {
                return "白领";
            }
        }));
        System.out.println(collect);
    }

    @Test
    public void testPartitioning() {
        Map<Boolean, List<Employee>> collect = employeeList.stream().collect(Collectors.partitioningBy((e) -> {
            return ((Employee) e).getSalary() >= 15000;
        }));
        System.out.println(collect);
    }


    @Test
    public void testToList() {
        List<Employee> employees = employeeList.stream().filter(e -> e.getSalary() >= 15000).collect(Collectors.toList());
        System.out.println(employees);
    }


}
