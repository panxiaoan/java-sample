package com.panxiaoan.sample.java8.base;

/**
 * @author <a href="mailto:xiaoan.pan@qq.com">潘小安</a>
 * @since 2018-11-13 12:02
 */
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
