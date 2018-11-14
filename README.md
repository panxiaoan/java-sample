# Java Sample

主要用于测试 Java 新版中出现的一些新的特性

## Java 8 新特性

### 为什么要学习 Java 8

- 以前

```code
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

定义：接口中有且仅有一个抽象方法，但不限制默认和静态方法。 一般通过
`@FunctionalInterface` 注解修饰。可以被隐式转换为 `Lambda` 表达式。

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

定义：是一个匿名函数，Lambda 表达式基于数学中的 λ
演算得名，直接对应于其中的lambda抽象(lambda
abstraction)，是一个匿名函数，即没有函数名的函数。

- 以前

```code
MyInterface innerClazz = new MyInterface() {
    @Override
    public int sum(int num1, int num2) {
        return num1 + num2;
    }
};
System.out.println(innerClazz.sum(1, 2));
```

- 现在

```code
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

