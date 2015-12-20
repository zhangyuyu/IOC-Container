package zhangyu.beanfactory;

public interface IBeanFactory {
    void registerBeanDefinition(String beanResource, String handlerName);
    Object getBean(String beanName);
}
