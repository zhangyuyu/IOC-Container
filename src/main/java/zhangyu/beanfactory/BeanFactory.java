package zhangyu.beanfactory;

import zhangyu.definition.BeanDefinition;
import zhangyu.handle.IHandler;
import zhangyu.util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;

public class BeanFactory implements IBeanFactory {
    private Map<String, Object> beanDefinitionMap;
    private Map<String, Object> beanCacheMap;

    public BeanFactory() {
        this.beanCacheMap = new HashMap<String, Object>();
        this.beanDefinitionMap = new HashMap<String, Object>();
    }

    public void setBeanDefinitionMap(Map<String, Object> beanDefinitionMap) {
        this.beanDefinitionMap = beanDefinitionMap;
    }

    public Object getBean(String beanName) {
        if (isBeanCached(beanName)) {
            return getBeanFromCache(beanName);
        }
        Object beanObject = createBean(beanName);
        addToBeanCacheMap(beanName, beanObject);
        return beanObject;
    }

    public void registerBeanDefinition(String beanResource, String handlerName) {
        IHandler handler = (IHandler) ReflectionUtil.getInstance(handlerName);
        beanDefinitionMap.putAll(handler.convert(beanResource));
    }

    private void addToBeanCacheMap(String beanName, Object beanObject) {
        beanCacheMap.put(beanName, beanObject);
    }

    private Object getBeanFromCache(String beanName) {
        return beanCacheMap.get(beanName);
    }

    private boolean isBeanCached(String beanName) {
        return beanCacheMap.containsKey(beanName);
    }

    private Object createBean(String beanName) {
        Object instance = ((BeanDefinition) beanDefinitionMap.get(beanName)).getBeanInstance();
        return instance;
    }
}
