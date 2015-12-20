import org.junit.Test;
import zhangyu.test.HelloWorld;
import zhangyu.definition.BeanDefinition;

import static org.junit.Assert.assertEquals;

public class BeanDefinitionTest {
    @Test
    public void should_get_instance_when_given_value() throws Exception {
        BeanDefinition bean = new BeanDefinition("hello", new Object());
        Object instance = bean.getBeanInstance();

        assertEquals(Object.class, instance.getClass());
    }


    @Test
    public void should_get_object_instance_when_given_bean_value() {
        HelloWorld helloWorld = new HelloWorld();
        BeanDefinition beanDefinition = new BeanDefinition("helloWorld", helloWorld);

        Object beanInstance = beanDefinition.getBeanInstance();

        assertEquals(helloWorld, beanInstance);
    }

    @Test
    public void should_get_object_instance_by_type_when_given_bean_type() {
        BeanDefinition beanDefinition = new BeanDefinition("helloWorld", "zhangyu.test.HelloWorld");

        Object beanInstance = beanDefinition.getBeanInstance();

        assertEquals(HelloWorld.class, beanInstance.getClass());
    }

    @Test(expected = Exception.class)
    public void should_throw_exception_when_not_found_bean() {
        BeanDefinition beanDefinition = new BeanDefinition("notFound", "NotFound");

        beanDefinition.getBeanInstance();
    }

}
