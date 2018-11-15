package com.panxiaoan.sample.java8.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author <a href="mailto:xiaoan.pan@qq.com">潘小安</a>
 * @since 2018-11-13 23:54
 */
public class UserFactoryTest {

    private List<User> dataList = new ArrayList<>();

    @Before
    public void init() {
        UserFactory userFactory = User::new;
        dataList.add(userFactory.create("Limeimei", 7, 1));
        dataList.add(userFactory.create("Wanghuahua", 8, 1));

        dataList.add(userFactory.create("Lucy", 7, 5));
        dataList.add(userFactory.create("Lily", 8, 5));

        dataList.add(userFactory.create("Jack", 8, 6));
        dataList.add(userFactory.create("Bob", 9, 6));
    }

    @After
    public void clear() {
        this.dataList.clear();
    }

    /** 测试：构造函数、方法引用 */
    @Test
    public void test1() {
        List<User> userList = new ArrayList<>();
        UserFactory innerClazz = new UserFactory() {
            @Override
            public User create(String name, Integer age, Integer grade) {
                return new User(name, age, grade);
            }
        };
        userList.add(innerClazz.create("jack", 7, 1));

        /** 常规写法 */
        UserFactory lambda1 = (name, age, grade) -> new User(name, age, grade);
        userList.add(lambda1.create("lily", 10, 6));

        /** 构造函数引用 */
        UserFactory lambda2 = User::new;
        userList.add(lambda2.create("lucy", 10, 5));

        /** 方法引用 */
        userList.sort(Comparator.comparing(User::getAge));
        System.out.println(userList);
    }

    /** 测试：复合 Lambda 表达式 */
    @Test
    public void test2() {

        /** 逆序排序 */
        dataList.sort(Comparator.comparing(User::getAge).reversed());
        System.out.println(String.format("逆序排序: %s", dataList));

        /** 多条件排序 */
        dataList.sort(Comparator.comparing(User::getAge).reversed().thenComparing(User::getGrade));
        System.out.println(String.format("多条件排序: %s", dataList));

    }

    /** 测试：流式集合数据处理 */
    @Test
    public void test3() {
        System.out.println("过滤年级大于 1， 以年龄排序，提取姓名，保存到 List 中");
        List<String> tempList = this.dataList.stream().filter(user -> user.getGrade() > 1)
            .sorted(Comparator.comparing(User::getAge)).map(User::getName).collect(Collectors.toList());
        tempList.stream().forEach(System.out::println);

        System.out.println("多核并行计算");
        tempList = this.dataList.parallelStream().filter(user -> user.getGrade() > 1)
            .sorted(Comparator.comparing(User::getAge)).map(User::getName).limit(1).collect(Collectors.toList());
        tempList.stream().forEach(System.out::println);
    }

    /** 测试：流中的 flatMap */
    @Test
    public void test4() {
        System.out.println("去掉重复的字母");
        String[] words = {"Goodbye", "World"};
        List<String> tempList = Arrays.stream(words).map(w -> w.split("")).flatMap(Arrays::stream).distinct()
            .collect(Collectors.toList());
        tempList.stream().forEach(System.out::println);
    }

    /** 测试：流中的 reduce */
    @Test
    public void test5() {
        System.out.println("规约求和");
        Integer[] nums = {1, 2, 3, 4, 5};
        Integer sum = Arrays.stream(nums).reduce(0, (a, b) -> a + b);
        System.out.println(sum);
        sum = Arrays.stream(nums).reduce(0, Integer::sum);
        System.out.println(sum);

        System.out.println("规约求最大值和最小值");
        Optional<Integer> max = Arrays.stream(nums).reduce(Integer::max);
        System.out.println("max: " + max.get());

        Optional<Integer> min = Arrays.stream(nums).reduce(Integer::min);
        System.out.println("min: " + min.get());
    }

    /** 测试：使用流做数据收集 */
    @Test
    public void test6() {
        System.out.println("按年级分组用户");
        Map<Integer, List<User>> tempMap = dataList.stream().collect(Collectors.groupingBy(User::getGrade));
        tempMap.entrySet().iterator().forEachRemaining(System.out::println);

        System.out.println("按年级和年龄分组用户");
        Map<Integer, Map<Integer, List<User>>> tempMap2 = dataList.stream()
            .collect(Collectors.groupingBy(User::getGrade, Collectors.groupingBy(User::getAge)));
        tempMap2.entrySet().iterator().forEachRemaining(System.out::println);

        System.out.println("计算每个年级有多少个用户");
        Map<Integer, Long> tempMap3 = dataList.stream()
            .collect(Collectors.groupingBy(User::getGrade, Collectors.counting()));
        tempMap3.entrySet().iterator().forEachRemaining(System.out::println);

        System.out.println("年龄最大和最小的用户");
        Optional<User> maxAgeUser = dataList.stream().collect(Collectors.maxBy(Comparator.comparing(User::getAge)));
        System.out.println("maxAgeUser: " + maxAgeUser.get());
        Optional<User> minAgeUser = dataList.stream().collect(Collectors.minBy(Comparator.comparing(User::getAge)));
        System.out.println("minAgeUser: " + minAgeUser.get());

        System.out.println("汇总年龄和平均年龄");
        Integer sumAge = dataList.stream().collect(Collectors.summingInt(User::getAge));
        System.out.println("sumAge: " + sumAge);
        Double avgAge = dataList.stream().collect(Collectors.averagingDouble(User::getAge));
        System.out.println("avgAge: " + avgAge);

        System.out.println("将姓名连接成字符串");
        String name = dataList.stream().map(User::getName).collect(Collectors.joining(", "));
        System.out.println(name);
    }
}
