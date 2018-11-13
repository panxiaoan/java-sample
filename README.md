# Java Sample

主要用于测试 Java 新版中出现的一些新的特性

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
```java
// 给库存排序，按重量
inventory.sort(comparing(Apple::getWeight));
```

### 接口默认方法

Java 8 中的接口支持在声明方法的同时提供实现
- 静态方法，只能在接口内使用，不能被子类继承和复写
- 默认方法，通过 default 关键字修饰，可以被子类继承和复写

```java
package com.panxiaoan.sample.java8.base;

public interface MyInterface {

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
