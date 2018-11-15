# Java Sample

> 主要用于测试 Java 新版中出现的一些新的特性

## Java 8 新特性

### 为什么要学习 Java 8

- 以前

```java
Collections.sort(inventory, new Comparator<Apple>() {
    public int compare(Apple a1, Apple a2) {
        return a1.getWeight().compareTo(a2.getWeight());
    }
});
```

- 现在

```code
// 给库存排序，按重量
inventory.sort(Comparator.comparing(Apple::getWeight));
```

### 接口默认方法

Java 8 中的接口支持在声明方法的同时提供实现
- 静态方法，只能在接口内使用，不能被子类继承和复写
- 默认方法，通过 `default` 关键字修饰，可以被子类继承和复写。
  该特性，有助于接口设计者不断的迭代接口，而不影响接口的实现者。

```java
package com.panxiaoan.sample.java8.base;

public interface MyInterface {

    /** 抽象方法 */
    int sum(int num1, int num2);

    /** 静态方法 */
    static void testStatic() {
        System.out.println("panxiaoan");
    }

    /** 默认方法 */
    default void testDefault() {
        MyInterface.testStatic();
    }
}
```

### 函数式接口

> 定义：接口中有且仅有一个抽象方法，但不限制默认和静态方法。 一般通过
> `@FunctionalInterface` 注解修饰。可以被隐式转换为 `Lambda` 表达式。

```java
package com.panxiaoan.sample.java8.base;

@FunctionalInterface
public interface MyInterface {

    /** 抽象方法 */
    int sum(int num1, int num2);

    /** 静态方法 */
    static void testStatic() {
        System.out.println("panxiaoan");
    }

    /** 默认方法 */
    default void testDefault() {
        MyInterface.testStatic();
    }
}
```

#### java.util.function 包中常用函数式接口

| 接口                | 描述符            |
|:--------------------|:------------------|
| Predicate<T>        | T -> boolean      |
| Consumer<T>         | T -> void         |
| Function<T, R>      | T -> R            |
| Supplier<T>         | () -> T           |
| UnaryOperator<T>    | T -> T            |
| BinaryOperator<T>   | (T, T) -> T       |
| BiPredicate<L, R>   | (L, R) -> boolean |
| BiConsumer<T, U>    | (T, U) -> void    |
| BiFunction<T, U, R> | (T, U) -> R       |

### Lambda 表达式

> 定义：是一个匿名函数，Lambda 表达式基于数学中的 λ
> 演算得名，直接对应于其中的lambda抽象(lambda
> abstraction)，是一个匿名函数，即没有函数名的函数。

- 以前

```java
MyInterface innerClazz = new MyInterface() {
    @Override
    public int sum(int num1, int num2) {
        return num1 + num2;
    }
};
System.out.println(innerClazz.sum(1, 2));
```

- 现在

```java
MyInterface lambda = (int num1, int num2) -> { return num1 + num2;};
System.out.println(lambda.sum(1, 2));

/** 更简单的写法 */
MyInterface lambda2 = (num1, num2) -> num1 + num2;
System.out.println(lambda2.sum(1, 2));
```

#### Lambda 表达式特点

- 基本构造：参数、箭头、实现主体 `(int arg1) -> {}`
- 参类型，可省略，JVM 自动推断
- 只有一个参数，可省略 `()`
- 没有参数，则用 `() -> {}`
- 实现只有一行代码，可省略 `{}` 和 `return`

#### 方法引用

方法引用让你可以重复使用现有的方法定义，并像 Lambda 一样传递它们。
- 构造函数引用 `User::new`
- 方法引用 `User::getAge`

```java
package com.panxiaoan.sample.java8.base;

public class UserFactoryTest {
    public static void main(String[] args) {
        UserFactory innerClazz = new UserFactory() {
            @Override
            public User create(String name, Integer age) {
                return new User(name, age);
            }
        };
        System.out.println(innerClazz.create("jack", 16));

        /** Lambda 常规写法 */
        UserFactory lambda1 = (name, age) -> new User(name, age);
        System.out.println(lambda1.create("lily", 13));

        /** 构造函数引用 */
        UserFactory lambda2 = User::new;
        System.out.println(lambda2.create("lucy", 23));

        /** 方法引用，按年龄排序用户 */
        userList.sort(Comparator.comparing(User::getAge));
        System.out.println(userList);
    }
}
```

### 函数式处理集合数据

#### 以流的方式透明地并行处理集合数据

> 流：以声明性方式处理数据集合（通过查询语句来表达，而不
> 是临时编写一个实现）。可以把流看成遍历数据集的高级迭代器。此外，流还
> 可以透明地并行处理，你无需写任何多线程代码了！

```java
package com.panxiaoan.sample.java8.base;

public class UserFactoryTest {

    private List<User> dataList = new ArrayList<>();

    @Before
    public void init() {
        UserFactory userFactory = User::new;
        dataList.add(userFactory.create("jack", 7, 1));
        dataList.add(userFactory.create("lily", 8, 6));
        dataList.add(userFactory.create("lucy", 8, 5));
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
}
```

- stream 获得流
- filter、map、limit 流水线操作
- collect 流水线执行，并关闭流

#### 使用流

流的使用一般包括三件事：
- 一个数据源（如集合）来执行一个查询
- 一个中间操作链，形成一条流的流水线
  - 筛选：filter
  - 映射：map、flatMap
  - 截断：limit、skip
  - 查找：allMatch、anyMatch、noneMatch、findFirst、findAny
  - sorted、distinct
- 一个终端操作，执行流水线，并能生成结果
  - forEach、count、collect、reduce

```java
package com.panxiaoan.sample.java8.base;

public class UserFactoryTest {

    /** 测试：流中的 flatMap */
    @Test
    public void test4() {
        System.out.println("去掉重复的字母");
        String[] words = {"Goodbye", "World"};
        List<String> tempList = Arrays.stream(words).map(w -> w.split("")).flatMap(Arrays::stream).distinct()
            .collect(Collectors.toList());
        tempList.stream().forEach(System.out::println);
    }
}
```

```java
package com.panxiaoan.sample.java8.base;

import java.util.Arrays;public class UserFactoryTest {

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
}
```

#### 使用流做数据收集

```java
package com.panxiaoan.sample.java8.base;

import java.util.Arrays;public class UserFactoryTest {

    /** 测试：使用做数据收集 */
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
```


