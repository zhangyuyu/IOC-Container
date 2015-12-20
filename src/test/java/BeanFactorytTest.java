import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import zhangyu.test.HelloWorld;
import zhangyu.beanfactory.BeanFactory;
import zhangyu.definition.BeanDefinition;

import java.util.HashMap;
import java.util.Map;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

public class BeanFactorytTest {
    private BeanFactory beanFactory;
    private  BeanDefinition beanDefinition;
    private Map<String, Object> beanDefMap;

    @Before
    public void setUp() throws Exception {
        beanDefinition = EasyMock.createMock(BeanDefinition.class);

        beanDefMap = new HashMap<String, Object>();
        beanDefMap.put("helloWorld", beanDefinition);

        beanFactory = new BeanFactory();
        beanFactory.setBeanDefinitionMap(beanDefMap);
    }

    @Test
    public void should_get_bean_instance_when_given_bean_name() throws Exception {
        expect(beanDefinition.getBeanInstance()).andReturn(new HelloWorld());

        replay(beanDefinition);
        Object instance = beanFactory.getBean("helloWorld");
        verify(beanDefinition);

        assertEquals(HelloWorld.class, instance.getClass());
    }

    @Test
    public void should_cache_bean_after_create_bean() throws Exception {
        expect(beanDefinition.getBeanInstance()).andReturn(new HelloWorld()).times(1);

        replay(beanDefinition);
        beanFactory.getBean("helloWorld");
        beanFactory.getBean("helloWorld");
        verify(beanDefinition);
    }
}
