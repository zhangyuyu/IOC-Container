package zhangyu.handle;

import zhangyu.annotation.Bean;
import zhangyu.annotation.Configuration;
import zhangyu.definition.BeanDefinition;
import zhangyu.util.ReflectionUtil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

public class AnnotationHandler implements IHandler {

    private static final char DOT_CHAR = '.';
    private static final char SLASH_CHAR = '/';

    @Override
    public Map<String, Object> convert(String beanPackage) {
        Map<String, Object> beanMap = new HashMap<String, Object>();
        String packageDirectory = beanPackage.replace(DOT_CHAR, SLASH_CHAR);
        try {
            Enumeration<URL> directories = Thread.currentThread().getContextClassLoader().getResources(packageDirectory);
            while (directories.hasMoreElements()) {
                URL url = directories.nextElement();
                File file = new File(url.getFile());
                beanMap.putAll(extractFromClsNames(beanPackage, Arrays.asList(file.list())));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return beanMap;
    }

    private Map<String, Object> extractFromClsNames(String beanPackage, List<String> classNames) {
        Map<String, Object> beanMap = new HashMap<String, Object>();
        for(String className: classNames){
            beanMap.putAll(handleClassName(beanPackage, className));
        }
        return beanMap;
    }

    private Map<String, Object> handleClassName(String beanPackage, String className) {
        Map<String, Object> beanMap = new HashMap<String, Object>();
        className = removeClassSuffix(className);
        String fullName = buildFullClassName(beanPackage, className);
        Class<?> clazz = ReflectionUtil.getClass(fullName);

        if (hasConfigurationAnnotation(clazz)) {
            List<Method> methods = Arrays.asList(clazz.getDeclaredMethods());
            for(Method method: methods){
                beanMap.putAll(handleBeanMethod(fullName, method));
            }
        }
        return beanMap;
    }

    private Map<String, Object> handleBeanMethod(String fullName, Method method) {
        Map<String, Object> beanMap = new HashMap<String, Object>();
        if (hasBeanAnnotation(method)) {
            try {
                Object instance = method.invoke(ReflectionUtil.getInstance(fullName));
                BeanDefinition beanDefinition = new BeanDefinition(extractBeanName(method), instance);
                beanMap.put(beanDefinition.getName(), beanDefinition);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return beanMap;
    }

    private boolean hasConfigurationAnnotation(Class<?> clazz) {
        return clazz != null && clazz.isAnnotationPresent(Configuration.class);
    }

    private String extractBeanName(Method method) {
        String beanName = method.getAnnotation(Bean.class).name();
        return (beanName.isEmpty() ? method.getName() : beanName).toLowerCase();
    }

    private boolean hasBeanAnnotation(Method method) {
        return method != null && method.isAnnotationPresent(Bean.class);
    }

    private String buildFullClassName(String beanPackage, String className) {
        return beanPackage + DOT_CHAR + className;
    }

    private String removeClassSuffix(String className) {
        return className.substring(0, className.lastIndexOf(DOT_CHAR));
    }
}
