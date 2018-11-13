package com.panxiaoan.sample.java8.base;

/**
 * @author <a href="mailto:xiaoan.pan@qq.com">潘小安</a>
 * @since 2018-11-13 23:22
 */
public class MyInterfaceTest {
    public static void main(Object[] args) {

        /** 以前 */
        MyInterface innerClazz = new MyInterface() {
            @Override
            public int sum(int num1, int num2) {
                return num1 + num2;
            }
        };
        System.out.println(innerClazz.sum(1, 2));

        /** 现在 */
        MyInterface lambda = (int num1, int num2) -> {
            return num1 + num2;
        };
        System.out.println(lambda.sum(1, 2));

        MyInterface lambda2 = (num1, num2) -> num1 + num2;
        System.out.println(lambda2.sum(1, 2));
    }
}
