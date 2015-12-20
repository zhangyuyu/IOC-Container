package zhangyu.definition;

import zhangyu.util.ReflectionUtil;

public class BeanDefinition {
    private String name;
    private Object value;
    private String type;

    public BeanDefinition(String name, String type) {
        this.type = type;
        this.name = name;
    }

    public BeanDefinition(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public Object getBeanInstance() {
        if(value == null) {
            value = ReflectionUtil.getInstance(type);
        }
        return value;
    }

    public String getName() {
        return name;
    }
}
