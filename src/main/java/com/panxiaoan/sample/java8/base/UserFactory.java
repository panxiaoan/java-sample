package com.panxiaoan.sample.java8.base;

/**
 * 函数式接口：演示 Lambda 构造函数引用
 *
 * @author <a href="mailto:xiaoan.pan@qq.com">潘小安</a>
 * @since 2018-11-13 23:52
 */
@FunctionalInterface
public interface UserFactory {
    User create(String name, Integer age, Integer grade);
}
