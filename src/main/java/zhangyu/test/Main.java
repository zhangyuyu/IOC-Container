package zhangyu.test;

import zhangyu.beanfactory.BeanFactory;
import zhangyu.util.ReflectionUtil;

public class Main {

    public static void main(String[] args) {
        BeanFactory beanFactory = (BeanFactory) ReflectionUtil.getInstance("zhangyu.beanfactory.BeanFactory");
        beanFactory.registerBeanDefinition("zhangyu.test",  "zhangyu.handle.AnnotationHandler");

        Animal dog = (Dog) beanFactory.getBean("dog");
        System.out.println(dog.toString());
    }
}
