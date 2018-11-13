package com.panxiaoan.sample.java8.base;

/**
 * @author <a href="mailto:xiaoan.pan@qq.com">潘小安</a>
 * @since 2018-11-13 23:54
 */
public class UserFactoryTest {
    public static void main(String[] args) {
        UserFactory innerClazz = new UserFactory() {
            @Override
            public User create(String name, Integer age) {
                return new User(name, age);
            }
        };
        System.out.println(innerClazz.create("jack", 18));

        UserFactory lambda1 = (name, age) -> new User(name, age);
        System.out.println(lambda1.create("lily", 18));

        UserFactory lambda2 = User::new;
        System.out.println(lambda2.create("lucy", 18));
    }
}
