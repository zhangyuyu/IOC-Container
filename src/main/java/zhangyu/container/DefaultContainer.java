package zhangyu.container;

import zhangyu.beanfactory.IBeanFactory;
import zhangyu.util.ReflectionUtil;

public class DefaultContainer implements IContainer {

    private IBeanFactory beanFactory;

    public DefaultContainer() {
        IBeanFactory beanFactory = (IBeanFactory) ReflectionUtil.getInstance("zhangyu.beanfactory.BeanFactory");
        beanFactory.registerBeanDefinition("zhangyu.test",  "zhangyu.handle.AnnotationHandler");
    }

    @Override
    public Object getBean(String beanName) {
        return beanFactory.getBean(beanName.toLowerCase());
    }
}
